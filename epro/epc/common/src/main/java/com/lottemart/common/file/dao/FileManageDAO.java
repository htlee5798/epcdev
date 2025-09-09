package com.lottemart.common.file.dao;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottemart.common.file.model.FileVO;

import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : FileMngDAO.java
 * @Description : 파일정보 관리를 위한 데이터 처리 클래스
 * @Modification Information
 * 
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   
 *
 * @author 
 * @since 
 * @version 1.0
 * @see 
 * 
 *  Copyright (C) 2011 by lottemart  All right reserved.
 */

@Repository("FileManageDAO")
public class FileManageDAO extends AbstractDAO {

	final static Logger logger = LoggerFactory.getLogger(FileManageDAO.class);

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fileList
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public int insertFileDetail(FileVO vo) throws Exception {

		insert("FileManageDAO.insertFileDetail", vo);
		
		return 1;
	}
	
	public String insertFileMaster(FileVO vo) throws Exception {

		insert("FileManageDAO.insertFileMaster", vo);

		return vo.getAtchFileId();
	}

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param vo
     * @throws Exception
     */
	public void insertFileInf(FileVO vo) throws Exception {
		insert("FileManageDAO.insertFileMaster", vo);
		insert("FileManageDAO.insertFileDetail", vo);
	}

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * 
     * @param fileList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fileList) throws Exception {
	FileVO vo;
	Iterator iter = fileList.iterator();
	while (iter.hasNext()) {
	    vo = (FileVO)iter.next();
	    
	    insert("FileManageDAO.insertFileDetail", vo);
	}
    }
    
    /**
     * 파일에 추가한.
     * 
     * @param fileList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void insertAppendFileInf(FileVO vo) throws Exception {
	    insert("FileManageDAO.insertFileDetail", vo);
    }

    /**
     * 여러 개의 파일을 삭제한다.
     * 
     * @param fileList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void deleteFileInfs(List fileList) throws Exception {
	Iterator iter = fileList.iterator();
	FileVO vo;
	while (iter.hasNext()) {
	    vo = (FileVO)iter.next();
	    
	    delete("FileManageDAO.deleteFileDetail", vo);
	}
    }

    /**
     * 하나의 파일을 삭제한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
	delete("FileManageDAO.deleteFileDetail", fvo);
    }

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<FileVO> selectFileInfs(FileVO vo) throws Exception {
	return list("FileManageDAO.selectFileList", vo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("FileManageDAO.getMaxFileSN", fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
	return (FileVO)selectByPk("FileManageDAO.selectFileInf", fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     * 
     * @param fvo
     * @throws Exception
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
	update("FileManageDAO.deleteCOMTNFILE", fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<FileVO> selectFileListByFileNm(FileVO fvo) throws Exception {
	return list("FileManageDAO.selectFileListByFileNm", fvo);
    }

    /**
     * 파일명 검색에 대한 목록 전체 건수를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws Exception
     */
    public int selectFileListCntByFileNm(FileVO fvo) throws Exception {
	return (Integer)getSqlMapClientTemplate().queryForObject("FileManageDAO.selectFileListCntByFileNm", fvo);
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
	return list("FileManageDAO.selectImageFileList", vo);
    }
    

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<FileVO> selectFileList(FileVO vo) throws Exception {
	return list("FileManageDAO.selectFileAllList", vo);
    }
    
    
    /** 
      * @see deleteNFileSN
      * @Location : com.lottemart.common.file.dao
      * @Method Name  : deleteNFileSN
      * @Description : TB_NFILE 사용 안함으로 변경  
      * @param vo
      * @return
      * @throws Exception 
    */
    public int deleteNFileSN(FileVO vo) throws Exception {
    	return update("FileManageDAO.deleteTB_FILE", vo);
    }
}
