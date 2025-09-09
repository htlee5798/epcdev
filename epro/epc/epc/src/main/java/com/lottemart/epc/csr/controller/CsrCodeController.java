package com.lottemart.epc.csr.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.PropertyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lottemart.epc.csr.model.CsrCodeDefaultVO;
import com.lottemart.epc.csr.model.CsrCodeVO;
import com.lottemart.epc.csr.model.CsrCodeCat1VO;
import com.lottemart.epc.csr.model.CsrCodeCat2VO;
import com.lottemart.epc.csr.model.CsrCodeCat3VO;
import com.lottemart.epc.csr.model.CsrCodeL1VO;
import com.lottemart.epc.csr.model.CsrCodeL2VO;
import com.lottemart.epc.csr.model.CsrDyRegDefaultVO;

import com.lottemart.epc.csr.model.CsrDyRegVO;
import com.lottemart.epc.csr.model.CsrEmpDefaultVO;
import com.lottemart.epc.csr.model.CsrEmpVO;
import com.lottemart.epc.csr.model.CsrEmpAllListVO;

import com.lottemart.epc.csr.model.CsrSearchParam;

import com.lottemart.epc.csr.service.CsrCodeService;
import com.lottemart.epc.csr.service.CsrDyRegService;
import com.lottemart.epc.csr.service.CsrEmpService;
import com.lottemart.epc.edi.comm.model.SearchParam;



/**
 * @Class Name : CsrCodeController.java
 * @Description : CsrCode Controller class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Controller
@SessionAttributes(types=CsrCodeVO.class)
public class CsrCodeController {

    @Resource(name = "csrCodeService")
    private CsrCodeService csrCodeService;
    
    @Resource(name = "csrEmpService")
    private CsrEmpService csrEmpService;
    
    @Resource(name = "csrDyRegService")
    private CsrDyRegService csrDyRegService;
    
    
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected PropertyService propertiesService;
	
    /**
	 * CSR_CODE 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 CsrCodeDefaultVO
	 * @return "/csrCode/CsrCodeList"
	 * @exception Exception
	 */
    @RequestMapping(value="/csr/csrList.ldcc")
    public String selectCsrCodeList(@ModelAttribute("searchVO") CsrSearchParam csrSearchParam, CsrEmpDefaultVO searchVO, 
    		ModelMap model)
            throws Exception {
    	
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));
    	
    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	/*	
        List csrEmpList = csrEmpService.selectCsrEmpList(searchVO);
        model.addAttribute("resultList", csrEmpList);*/
        
        int totCnt = csrEmpService.selectCsrEmpListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        //전체 사원 리스트 출력 
        model.addAttribute("csrEmpAllListVO", new CsrEmpAllListVO());
        List<CsrEmpAllListVO> selectCsrEmpAllList = csrEmpService.selectCsrEmpAllList(csrSearchParam); //전체사원정보
     	model.addAttribute("empAllList",selectCsrEmpAllList); // 모든사원정보
        return "/csr/csrList";
    } 
    
    
    
    //CSR 입력하는 페이지 Controller
    
    @RequestMapping("/csr/csrInsert.ldcc")
    public String addCsrCodeView(
    		CsrSearchParam csrSearchParam,  @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO,  Model model)
            throws Exception {
        model.addAttribute("csrCodeVO", new CsrCodeVO());
        model.addAttribute("csrDyRegVO", new CsrDyRegVO());
        model.addAttribute("csrEmpVO", new CsrEmpVO());
        model.addAttribute("csrEmpAllListVO", new CsrEmpAllListVO());
        
        
        
        //이동빈 추가
        
       	csrSearchParam.setsDyReg("20131008");
       	csrSearchParam.setsEmpNo("L10042");  
        
        
        //사번 사원정보
        //model.addAttribute("teamList", 	  	   commService.selectDistinctTeamList());
        CsrEmpVO tmpcsrEmpVO = csrEmpService.selectCsrEmp(csrSearchParam);
        model.addAttribute("resultList",tmpcsrEmpVO);

        
        //사번 날짜 map  값    
   	 	List<CsrDyRegVO> csrRegEmpDayList = csrDyRegService.selectCsrRegEmpDayList(csrSearchParam);
     	model.addAttribute("csrRegEmpDayList",csrRegEmpDayList);
     	 
     	
     	
     	//코드    	
     	List<CsrCodeCat1VO> selectCsrCodeCat1List = csrCodeService.selectCsrCodeCat1List(csrSearchParam); //대코드
     	List<CsrCodeCat2VO> selectCsrCodeCat2List = csrCodeService.selectCsrCodeCat2List(csrSearchParam); //중코드
     	List<CsrCodeCat3VO> selectCsrCodeCat3List = csrCodeService.selectCsrCodeCat3List(csrSearchParam); //소코드
     	List<CsrCodeL1VO> selectCsrCodeL1List = csrCodeService.selectCsrCodeL1List(csrSearchParam); //L1코드
     	List<CsrCodeL2VO> selectCsrCodeL2List = csrCodeService.selectCsrCodeL2List(csrSearchParam); //L2코드
     	List<CsrEmpAllListVO> selectCsrEmpAllList = csrEmpService.selectCsrEmpAllList(csrSearchParam); //전체사원정보
     	
     	
     	model.addAttribute("catCd1List",selectCsrCodeCat1List); //대분류
     	model.addAttribute("catCd2List",selectCsrCodeCat2List); //중분류
     	model.addAttribute("catCd3List",selectCsrCodeCat3List); //소분류
     	model.addAttribute("l1CdList",selectCsrCodeL1List); //L1코드
     	model.addAttribute("l2CdList",selectCsrCodeL2List); //L2코드 	
     //	model.addAttribute("empAllList",selectCsrEmpAllList); // 모든사원정보
     	
        
        return "/csr/csrInsert";
    }
    
    //Csr 정보 저장하는 Controller

    @RequestMapping("/csr/csrAdd.ldcc")
    public String csrAdd(
    		
    		@RequestParam Map<String,Object> map,
            @ModelAttribute("searchVO") CsrDyRegDefaultVO searchVO, Model model)
            throws Exception {
    	
    	
    	String vals = map.get("forward_values").toString();
   /*   등록일 닐찌
      	사번 하나 
       	아래값붙임값 1 11 1111   211313  51313 513135       2 
   */
    	
    	//csrDyRegService.deleteCsrDyRegs(객체 :날짜 사번 );     
        csrDyRegService.insertCsrDyRegs(vals);
      
        
        return "forward:/csr/csrList.ldcc";
    }
    
    
 
    
    @RequestMapping("/csrCode/addCsrCode.do")
    public String addCsrCode(
            CsrCodeVO csrCodeVO,
            @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO, SessionStatus status)
            throws Exception {
        csrCodeService.insertCsrCode(csrCodeVO);
        status.setComplete();
        return "forward:/csrCode/CsrCodeList.do";
    }
    
    
    
    
    @RequestMapping("/csrCode/updateCsrCodeView.do")
    public String updateCsrCodeView(
            @RequestParam("catNm") java.lang.String catNm ,
            @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO, Model model)
            throws Exception {
        CsrCodeVO csrCodeVO = new CsrCodeVO();
        csrCodeVO.setCatNm(catNm);        
        // 변수명은 CoC 에 따라 csrCodeVO
        model.addAttribute(selectCsrCode(csrCodeVO, searchVO));
        return "/csrCode/CsrCodeRegister";
    }

    @RequestMapping("/csrCode/selectCsrCode.do")
    public @ModelAttribute("csrCodeVO")
    CsrCodeVO selectCsrCode(
            CsrCodeVO csrCodeVO,
            @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO) throws Exception {
        return csrCodeService.selectCsrCode(csrCodeVO);
    }

    @RequestMapping("/csrCode/updateCsrCode.do")
    public String updateCsrCode(
            CsrCodeVO csrCodeVO,
            @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO, SessionStatus status)
            throws Exception {
        csrCodeService.updateCsrCode(csrCodeVO);
        status.setComplete();
        return "forward:/csrCode/CsrCodeList.do";
    }
    
    @RequestMapping("/csrCode/deleteCsrCode.do")
    public String deleteCsrCode(
            CsrCodeVO csrCodeVO,
            @ModelAttribute("searchVO") CsrCodeDefaultVO searchVO, SessionStatus status)
            throws Exception {
        csrCodeService.deleteCsrCode(csrCodeVO);
        status.setComplete();
        return "forward:/csrCode/CsrCodeList.do";
    }

}