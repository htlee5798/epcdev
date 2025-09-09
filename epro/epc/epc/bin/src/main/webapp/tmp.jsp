<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

 SELECT  CTR_CD,  
    VEN_CD,  
    PROD_CD,  
    STR_NM,  
    LOGI_BCD,  
    SRCMK_CD,  
    PROD_NM,  
    PROD_STD,  
    ORD_IPSU,  
    DETAIL_NM,  
    SUM( ORD_QTY),   
    SUM( ORD_AMT),   
    SUM( BUY_QTY),   
    SUM( BUY_AMT),   
    BUY_FG,  
    SPLY_FG,  
    GETSTRNM_FNC(CTR_CD) CTR_NM  
  FROM   (  
      SELECT  /*+ ORDERED USE_NL( T S P AC) */  
            T.CTR_CD,    
            T.VEN_CD,    
            T.PROD_CD,   
            S.STR_NM,    
            T.LOGI_BCD,  
            T.SRCMK_CD,  
            P.PROD_NM,   
            P.PROD_STD,  
            T.ORD_IPSU,  
            AC.DETAIL_NM,    
            SUM(ORD_QTY) ORD_QTY,    
            SUM(ORD_AMT) ORD_AMT,    
            SUM(BUY_QTY) BUY_QTY,    
            SUM(BUY_AMT) BUY_AMT,    
            'C' BUY_FG,  
            SPLY_FG      
    FROM (  SELECT  ORD_SLIP_NO,     
            CTR_CD,      
            STR_CD,      
            VEN_CD,      
            PROD_CD,     
            LOGI_BCD,    
            SRCMK_CD,    
            ORD_IPSU,    
            ORD_UNIT,    
            SUM( ORD_QTY ) ORD_QTY,  
            SUM( ORD_AMT ) ORD_AMT,  
            SUM( BUY_QTY ) BUY_QTY,  
            SUM( BUY_AMT ) BUY_AMT,  
            SPLY_FG          
        FROM (   
        SELECT  O.ORD_SLIP_NO,   
                O.CTR_CD,    
                O.STR_CD,    
                O.VEN_CD,    
                A.PROD_CD,   
                A.LOGI_BCD,  
                OP.SRCMK_CD,     
                OP.ORD_IPSU,     
                OP.ORD_UNIT,     
                SUM( ORD_QTY ) ORD_QTY,  
                SUM( ((ORD_QTY * ORD_IPSU)/GET_CONV_VAL(ORD_UNIT) ) * BUY_PRC) ORD_AMT,  
                0 BUY_QTY,   
                0 BUY_AMT,   
                GET_ALL_CODE( 'ASN01', SPLY_FG, '') SPLY_FG  
            FROM (  
                SELECT  /*+ ORDERED USE_NL(F, FP) INDEX( FP WMS_FWD_PROD_PK) */  
                         ORD_SLIP_NO,     
                         PROD_CD,     
                         LOGI_BCD,    
                         WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD) SPLY_FG    
                FROM    WMS_FORWARDING F,    
                    WMS_FWD_PROD FP      
                WHERE   F.FWD_SLIP_NO = FP.FWD_SLIP_NO   
                AND F.ROUTE_FG = '2'     
                AND F.ORD_FG NOT IN ('4', '7', '8')  

If Trim(ymd_from) = Trim(ymd_to) Then
  AND   F.PICK_DY   = '" & Trim(ymd_from) & "'"
Else
  AND   F.PICK_DY   between '" & Trim(ymd_from) & "' and '" & Trim(ymd_to) & "'"
End If
If Len(ven_cd) > 6 Then
  AND   F.VEN_CD  IN (" & Trim(ven_cd_string) & ") "
Else
  AND   F.VEN_CD= '" & Trim(ven_cd) & "' "
End If
If Len(prod_cd) > 8 Then
  AND   FP.PROD_CD  IN (" & Trim(prod_cd) & ") "
End If
If Len(str_cd) > 3 Then
  AND   F.STR_CD in (" & Trim(str_cd_string) & ") "
End If

                AND   'C'     LIKE '" & Trim(cen_buy_fg) & "%' 
                AND     WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD) LIKE '" & Trim(asn_fg) & "' 
                AND CHG_SLIP_FG <> '2'   
                GROUP BY             
                    ORD_SLIP_NO,         
                    PROD_CD,         
                    LOGI_BCD,        
                    WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD) ) A,   
                ORD O,       
                ORD_PROD OP  
            WHERE   A.ORD_SLIP_NO = O.ORD_SLIP_NO    
            AND O.ORD_SLIP_NO = OP.ORD_SLIP_NO   
            AND A.PROD_CD = OP.PROD_CD       
            AND O.ROUTE_FG = '2'         
            AND O.ORD_FG NOT IN ('4', '7', '8')  

If Len(ven_cd) > 6 Then
  AND   O.VEN_CD  IN (" & Trim(ven_cd_string) & ") "
Else
  AND   O.VEN_CD= '" & Trim(ven_cd) & "' "
End If
If Len(prod_cd) > 8 Then
  AND   OP.PROD_CD  IN (" & Trim(prod_cd) & ") "
End If
If Len(str_cd) > 3 Then
  AND   O.STR_CD in (" & Trim(str_cd_string) & ") "
End If

            GROUP BY         
                O.ORD_SLIP_NO,   
                O.CTR_CD,    
                O.STR_CD,    
                O.VEN_CD,    
                A.PROD_CD,   
                A.LOGI_BCD,  
                OP.SRCMK_CD,     
                OP.ORD_IPSU,     
                OP.ORD_UNIT,     
                SPLY_FG      
            UNION ALL        
            SELECT  /*+ ORDERED USE_NL( F FP ) INDEX( FP WMS_FWD_PROD_PK) */     
                F.ORD_SLIP_NO,   
                F.CTR_CD,    
                F.STR_CD,    
                F.VEN_CD,        
                FP.PROD_CD,  
                FP.LOGI_BCD,     
                FP.SRCMK_CD,     
                FP.ORD_IPSU,     
                FP.ORD_UNIT,     
                0 ORD_QTY,   
                0 ORD_AMT,   
                SUM( DECODE( FP.BUY_SIGN_FG, '1', 1, -1) * FP.FWD_EA_QTY / (GET_CONV_VAL( FP.ORD_UNIT) * FP.ORD_IPSU)) BUY_QTY,  
                SUM( DECODE( FP.BUY_SIGN_FG, '1', 1, -1) * ( FP.FWD_EA_QTY / GET_CONV_VAL( FP.ORD_UNIT)) * FP.BUY_PRC) BUY_AMT,  
                GET_ALL_CODE( 'ASN01', WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD), '') SPLY_FG        
            FROM    WMS_FORWARDING F,    
                WMS_FWD_PROD FP      
            WHERE   F.FWD_SLIP_NO   = FP.FWD_SLIP_NO     
            AND     F.ROUTE_FG  IN ( '2')            
            AND     F.ORD_FG    NOT IN ( '4', '7', '8')  

If Trim(ymd_from) = Trim(ymd_to) Then
  AND   F.PICK_DY   = '" & Trim(ymd_from) & "'"
Else
  AND   F.PICK_DY   between '" & Trim(ymd_from) & "' and '" & Trim(ymd_to) & "'"
End If
If Len(ven_cd) > 6 Then
  AND   F.VEN_CD  IN (" & Trim(ven_cd_string) & ") "
Else
  AND   F.VEN_CD= '" & Trim(ven_cd) & "' "
End If
If Len(prod_cd) > 8 Then
  AND   FP.PROD_CD  IN (" & Trim(prod_cd) & ") "
End If
If Len(str_cd) > 3 Then
  AND   F.STR_CD in (" & Trim(str_cd_string) & ") "
End If
            AND     'C'     LIKE '" & Trim(cen_buy_fg) & "%'"
            AND     WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD) LIKE '" & Trim(asn_fg) & "'"

            GROUP BY         
                F.ORD_SLIP_NO,   
                F.CTR_CD,    
                F.STR_CD,    
                F.VEN_CD,    
                FP.PROD_CD,  
                FP.LOGI_BCD,     
                FP.SRCMK_CD,     
                FP.ORD_IPSU,     
                ORD_UNIT,    
                GET_ALL_CODE( 'ASN01', WMS_GET_ANS_SPLY_FG( 'FORWARDING', F.FWD_SLIP_NO, FP.PROD_CD), '') )  
        GROUP BY             
            ORD_SLIP_NO,         
            CTR_CD,          
            STR_CD,          
            VEN_CD,          
            PROD_CD,         
            LOGI_BCD,        
            SRCMK_CD,        
            ORD_IPSU,        
            ORD_UNIT,        
            SPLY_FG  ) T,        
        STORE S,             
        PRODUCT P,           
        ALL_CD AC            
    WHERE   T.STR_CD = S.STR_CD      
    AND T.PROD_CD = P.PROD_CD        
    AND T.ORD_UNIT = AC.DETAIL_CD    
    AND     AC.GRP_CD   = 'CSA01'        
    GROUP BY         
        T.CTR_CD,    
        T.VEN_CD,    
        T.PROD_CD,   
        S.STR_NM,    
        T.LOGI_BCD,      
        T.SRCMK_CD,      
        P.PROD_NM,   
        P.PROD_STD,  
        T.ORD_IPSU,  
        AC.DETAIL_NM,    
        SPLY_FG      
        UNION ALL 
        SELECT  A.CTR_CD,        
            A.VEN_CD,        
            AP.PROD_CD,  
            S.STR_NM, 
            AP.LOGI_BCD, 
            AP.SRCMK_CD, 
            SP.PROD_NM, 
            SP.PROD_STD, 
            AP.ORD_IPSU, 
            AC.DETAIL_NM, 
            MAX( OP.ORD_QTY) ORD_QTY, 
            MAX( OP.ORD_QTY * AP.ORD_IPSU * AP.BUY_PRC) ORD_AMT, 
            SUM( DECODE( AP.BUY_SIGN_FG, '1', 1, -1) * ( AP.EA_QTY / ( GET_CONV_VAL( AP.ORD_UNIT) * AP.ORD_IPSU)))BUY_QTY, 
            SUM( DECODE( AP.BUY_SIGN_FG, '1', 1, -1) * ( AP.EA_QTY / GET_CONV_VAL( AP.ORD_UNIT)) * AP.BUY_PRC) BUY_AMT, 
            GET_ALL_CODE( 'ASN01', WMS_GET_ANS_SPLY_FG( 'ARRIVAL', A.ARR_SLIP_NO, AP.PROD_CD), '') SPLY_FG, 
            'C' BUY_FG 
        FROM    WMS_ARRIVAL A, 
            WMS_ARR_PROD AP, 
            STORE S, 
            STR_PROD SP, 
            ALL_CD AC, 
            ORD_PROD OP 
        WHERE   A.ARR_SLIP_NO   = AP.ARR_SLIP_NO 
        AND A.STR_CD    = S.STR_CD 
        AND A.ROUTE_FG  IN ( '3', '4') 
        AND AP.PROD_CD  = SP.PROD_CD 
        AND A.STR_CD    = SP.STR_CD 
        AND AP.ORD_UNIT = AC.DETAIL_CD 
        AND AC.GRP_CD   = 'CSA01' 
        AND A.ORD_FG    NOT IN ( '4', '7', '8') 
        AND A.ARR_TYP_FG    = '1' 
        AND A.ORD_SLIP_NO   = OP.ORD_SLIP_NO 
        AND AP.PROD_CD  = OP.PROD_CD 

If Trim(ymd_from) = Trim(ymd_to) Then
      AND   A.ARR_DY   = '" & Trim(ymd_from) & "'"
Else
      AND   A.ARR_DY   between '" & Trim(ymd_from) & "' and '" & Trim(ymd_to) & "'"
End If
If Len(ven_cd) > 6 Then
      AND   A.VEN_CD  IN (" & Trim(ven_cd_string) & ") "
Else
      AND   A.VEN_CD= '" & Trim(ven_cd) & "' "
End If
If Len(prod_cd) > 8 Then
      AND   AP.PROD_CD  IN (" & Trim(prod_cd) & ") "
End If
If Len(str_cd) > 3 Then
      AND   S.STR_CD in (" & Trim(str_cd_string) & ") "
End If

  AND   'C'     LIKE '  & Trim(cen_buy_fg) & "%' 
        AND WMS_GET_ANS_SPLY_FG( 'ARRIVAL', A.ARR_SLIP_NO, AP.PROD_CD) LIKE '" & Trim(asn_fg) & "'"
        GROUP BY 
            A.ARR_SLIP_NO, 
            A.CTR_CD, 
            A.VEN_CD, 
            AP.PROD_CD, 
            S.STR_NM, 
            AP.LOGI_BCD, 
            AP.SRCMK_CD, 
            SP.PROD_NM, 
            SP.PROD_STD, 
            AP.ORD_IPSU, 
            AC.DETAIL_NM, 
            GET_ALL_CODE( 'ASN01', WMS_GET_ANS_SPLY_FG( 'ARRIVAL', A.ARR_SLIP_NO, AP.PROD_CD), '') 
        UNION ALL 
        SELECT  /*+ INDEX( AC ALL_CD_PK) */ 
            SB.STR_CD, 
            SB.VEN_CD, 
            SB.PROD_CD, 
            S.STR_NM, 
            '' LOGI_BCD, 
            SB.SRCMK_CD, 
            SP.PROD_NM, 
            SP.PROD_STD, 
            SB.ORD_IPSU, 
            AC.DETAIL_NM, 
            MAX( OP.ORD_QTY) ORD_QTY, 
            MAX( OP.ORD_QTY * SB.ORD_IPSU * SB.BUY_BUY_PRC) ORD_AMT, 
            SUM( SB.BUY_BOX_QTY) BUY_QTY,  
            SUM( SB.BUY_BUY_AMT) BUY_AMT,  
            '직납' SPLY_FG,  
            'S' BUY_FG  
        FROM    ( 
                SELECT  SB.STR_CD, 
                    SB.VEN_CD, 
                    SB.ORD_SLIP_NO, 
                    SBP.PROD_CD, 
                    SBP.SRCMK_CD, 
                    SBP.ORD_IPSU, 
                    SBP.BUY_BUY_PRC, 
                    SBP.ORD_UNIT, 
                    SUM( DECODE( SBP.ADJ_FG, '1', 1, -1) * SBP.BUY_BOX_QTY) BUY_BOX_QTY, 
                    SUM( DECODE( SBP.ADJ_FG, '1', 1, -1) * SBP.BUY_QTY) BUY_QTY, 
                    SUM( DECODE( SBP.ADJ_FG, '1', 1, -1) * SBP.BUY_BUY_AMT) BUY_BUY_AMT, 
                    SUM( DECODE( SBP.ADJ_FG, '1', 1, -1) * SBP.BUY_SALE_AMT) BUY_SALE_AMT 
                FROM    STR_BUY SB,  
                    STR_BUY_PROD SBP  
                WHERE   SB.BUY_SLIP_NO  = SBP.BUY_SLIP_NO 
                AND SB.ROUTE_FG = '1' 
                AND SB.BUY_RTN_FG   IN ( '2', '4') 

If Trim(ymd_from) = Trim(ymd_to) Then
      AND   SB.BUY_DY   = '" & Trim(ymd_from) & "'"
Else
      AND   SB.BUY_DY   between '" & Trim(ymd_from) & "' and '" & Trim(ymd_to) & "'"
End If
If Len(ven_cd) > 6 Then
      AND   SB.VEN_CD  IN (" & Trim(ven_cd_string) & ") "
Else
      AND   SB.VEN_CD= '" & Trim(ven_cd) & "' "
End If
If Len(prod_cd) > 8 Then
      AND   SBP.PROD_CD  IN (" & Trim(prod_cd) & ") "
End If
If Len(str_cd) > 3 Then
      AND   SB.STR_CD in (" & Trim(str_cd_string) & ") "
End If
                AND 'S'       LIKE '" & Trim(cen_buy_fg) & "%'"
                GROUP BY
                    SB.STR_CD,
                    SB.VEN_CD,
                    SB.ORD_SLIP_NO,
                    SBP.PROD_CD,
                    SBP.SRCMK_CD,
                    SBP.ORD_IPSU,
                    SBP.BUY_BUY_PRC,
                    SBP.ORD_UNIT
            ) SB,
            STORE S,
            STR_PROD SP,
            ALL_CD AC,
            ORD_PROD OP
        WHERE   SB.STR_CD   = S.STR_CD
        AND SB.STR_CD   = SP.STR_CD
        AND SB.PROD_CD  = SP.PROD_CD
        AND SB.ORD_UNIT = AC.DETAIL_CD
        AND AC.GRP_CD   = 'CSA01'
        AND SB.ORD_SLIP_NO  = OP.ORD_SLIP_NO(+)
        AND SB.PROD_CD  = OP.PROD_CD(+)
        GROUP BY    
            SB.ORD_SLIP_NO,
            SB.STR_CD,
            SB.VEN_CD,
            SB.PROD_CD,
            S.STR_NM,
            SB.SRCMK_CD,
            SP.PROD_NM,
            SP.PROD_STD,
            SB.ORD_IPSU,
            AC.DETAIL_NM
    ) 
  GROUP BY
    CTR_CD      ,
    VEN_CD      ,
    PROD_CD     ,
    STR_NM      ,
    LOGI_BCD    ,
    SRCMK_CD    ,
    PROD_NM     ,
    PROD_STD    ,
    ORD_IPSU    ,
    DETAIL_NM   ,
    BUY_FG      ,
    SPLY_FG         



</body>
</html>