<%@include file="../common.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>롯데마트 Value Chain 알리미 서비스</title>

<script>
</script>

</head>


<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" background="/images/epc/edi/ems/data_bg.gif">
	<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td><img src="/images/epc/edi/ems/mail_t.gif" width="700" height="89"></td>
		</tr>
		<tr>
			<td><img src="/images/epc/edi/ems/mail_img_2.gif" width="700" height="148"></td>
		</tr>
		<tr>
			<td background="/images/epc/edi/ems/mail_bg.gif"></td>
		</tr>
		<tr>
			<td background="/images/epc/edi/ems/mail_bg.gif">
				<table width="630" border="0" cellspacing="0" cellpadding="0" align="center">
					<tr>
						<td height="20">
							<font color="#394E60"><b><font color="#333333"><c:out value="${ems.comNm}" /> 님 안녕하십니까? LOTTE Mart Value Chain e-mail 알리미 서비스 입니다.</font></b></font>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="25">
							<font color="#333333">전일의 요약정보는 다음과 같습니다. </font>
						</td>
					</tr>
					<tr>
						<td background="/images/epc/edi/ems/popup_line.gif" height="3"></td>
					</tr>

					<c:choose>
						<c:when test="${empty ems}">
							<tr>
								<td height="20">
									<font color="#394E60"><b><font color="#333333">전일 요약정보가 없습니다.</font></b></font>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<td height="25">
								<font color="#333333"><b>업체코드 : <c:out value="${ems.venCd}" /><b></font>
							</td>
						</c:otherwise>
					</c:choose>
					<tr>
						<td height="25"><font color="#333333">※ 발주정보</font></td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#CCCCCC">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#999999">
											<tr bgcolor="#FFFFFF">
												<td height="26">
													<div align="center">
														<font color="#666666"><b>발주일</b></font>
													</div>
												</td>
												<td height="26">
													<div align="center">
														<font color="#666666"><b>발주전표수</b></font>
													</div>
												</td>
												<td height="26">
													<div align="center">
														<b><font color="#666666">SKU</font></b>
													</div>
												</td>
												<td height="26">
													<div align="center">
														<b><font color="#666666">발주수량</font></b>
													</div>
												</td>
												<td height="26">
													<div align="center">
														<font color="#666666"><b>발주금액</b></font>
													</div>
												</td>
											</tr>
											<tr bgcolor="#FFFFFF">
												<td height="25">
													<div align="center">
														<font color="#330000"><c:out value="${ems.ordDy}" /></font>
													</div>
												</td>
												<td height="25">
													<div align="center">
														<font color="#330000">
															<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.ordSlipCnt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25">
													<div align="center">
														<font color="#330000"><fmt:formatNumber
																value="<c:out value="${ems.ordSku}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25">
													<div align="center">
														<font color="#330000"><font color="#330000"><fmt:formatNumber
																	value="<c:out value="${ems.ordQty}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25">

													<div align="center">
														<font color="#330000"><fmt:formatNumber
																value="<c:out value="${ems.ordAmt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="25"><font color="#333333">※ 매입,매출,재고</font></td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="2" cellpadding="0"
								bgcolor="#CCCCCC">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="2"
											bgcolor="#999999">
											<tr bgcolor="#FFFFFF">
												<td width="11%">
													<div align="center">
														<font color="#666666"><b>매출일</b></font>
													</div>
												</td>
												<td height="26" width="9%">
													<div align="center">
														<font color="#666666"><b>매입수량</b></font>
													</div>
												</td>
												<td height="26" width="10%">
													<div align="center">
														<font color="#666666"><b>매입금액</b></font>
													</div>
												</td>
												<td height="26" width="10%">
													<div align="center">
														<font color="#666666"><b>매출수량</b></font>
													</div>
												</td>
												<td height="26" width="11%">
													<div align="center">
														<b><font color="#666666">매출금액</font></b>
													</div>
												</td>
												<td height="26" width="14%">
													<div align="center">
														<font color="#666666"><b>누계매출수량</b></font>
													</div>
												</td>
												<td height="26" width="14%">
													<div align="center">
														<b><font color="#666666">누계매출금액</font></b>
													</div>
												</td>
												<td height="26" width="10%">
													<div align="center">
														<b><font color="#666666">재고수량</font></b>
													</div>
												</td>
												<td height="26" width="11%">
													<div align="center">
														<font color="#666666"><b>재고금액</b></font>
													</div>
												</td>
											</tr>
											<tr bgcolor="#FFFFFF">
												<td width="11%">
													<div align="center">
														<font color="#330000"><c:out value="<c:out value="${ems.ordDy}" />" /></font>
													</div>
												</td>
												<td height="25" width="10%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.buyQty}" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="10%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.buyAmt}" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="9%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.saleQty}" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="11%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.saleAmt }" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="14%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.sumSaleQty }" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="14%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.sumSaleAmt }" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="10%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.stkQty}" />" type="number" currencySymbol="" />
													</div>
												</td>
												<td height="25" width="11%">
													<div align="center">
														<fmt:formatNumber value="<c:out value="${ems.stkAmt}" />" type="number" currencySymbol="" />
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="25"><font color="#333333">※ 매입상세</font></td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="2" cellpadding="0"
								bgcolor="#CCCCCC">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="2"
											bgcolor="#999999">
											<tr bgcolor="#FFFFFF">
												<td height="26" width="12%">
													<div align="center">
														<font color="#666666"><b>매입일</b></font>
													</div>
												</td>
												<td height="26" width="17%">
													<div align="center">
														<font color="#666666"><b>매입</b>(납품)<b>전표수</b></font>
													</div>
												</td>
												<td height="26" width="12%">
													<div align="center">
														<font color="#666666"><b>매입금액</b></font>
													</div>
												</td>
												<td height="26" width="15%">
													<div align="center">
														<font color="#666666"><b>반품전표수</b></font>
													</div>
												</td>
												<td height="26" width="14%">
													<div align="center">
														<b><font color="#666666">반품금액</font></b>
													</div>
												</td>
												<td height="26" width="13%">
													<div align="center">
														<b><font color="#666666">정정전표수</font></b>
													</div>
												</td>
												<td height="26" width="16%">
													<div align="center">
														<font color="#666666"><b>정정금액</b></font>
													</div>
												</td>
											</tr>
											<tr bgcolor="#FFFFFF">
												<td height="25" width="12%">
													<div align="center">
														<font color="#330000"><c:out value="${ems.ordDy}" /></font>
													</div>
												</td>
												<td height="25" width="17%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buySlipCnt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="12%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buyAmt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="15%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buyRtnSlipCnt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="14%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buyRtnAmt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="13%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buyChgSlipCnt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="16%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.buyChgAmt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="25"><font color="#333333">※ 미납상세</font></td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="2" cellpadding="0"
								bgcolor="#CCCCCC">
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="2"
											bgcolor="#999999">
											<tr bgcolor="#FFFFFF">
												<td height="26" width="14%">
													<div align="center">
														<font color="#666666"><b>매입일</b></font>
													</div>
												</td>
												<td height="26" width="24%">
													<div align="center">
														<font color="#666666"><b>미납전표수</b></font>
													</div>
												</td>
												<td height="26" width="15%">
													<div align="center">
														<font color="#666666"><b>미납SKU</b></font>
													</div>
												</td>
												<td height="26" width="17%">
													<div align="center">
														<font color="#666666"><b>미납수량</b></font>
													</div>
												</td>
												<td height="26" width="20%">
													<div align="center">
														<b><font color="#666666">미납금액</font></b>
													</div>
												</td>
											</tr>
											<tr bgcolor="#FFFFFF">
												<td height="25" width="14%">
													<div align="center">
														<font color="#330000"><c:out value="${ems.ordDy}" /></font>
													</div>
												</td>
												<td height="25" width="24%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.usplySlipCnt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="15%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.usplySku}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="17%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.usplyQty}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
												<td height="25" width="20%">
													<div align="center">
														<font color="#330000"><fmt:formatNumber value="<c:out value="${ems.usplyAmt}" />" type="number" currencySymbol="" /></font>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="25"></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>

					<tr>
						<td height="6" background="/images/epc/edi/ems/popup_line.gif"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td background="/images/epc/edi/ems/mail_bg.gif"></td>
		</tr>
		<tr>
			<td background="/images/epc/edi/ems/mail_bg.gif">
				<table width="600" border="0" cellspacing="0" cellpadding="0" align="center">
					<tr>
						<td height="30">
						<font color="#666666">본 메일은 발송용 계정으로만 사용되어 고객님께서 [회신]을 눌러 문의하실경우 확인되지않아 회신을 받을 수 없게 되오니 아래 연락처를 참조하시기 바랍니다.</font></td>
					</tr>
					<tr>
						<td height="20">
							<div align="right">
								<font color="#394E60"><b><font color="#660000">( TEL : 02) 2145-8601~7 )</font></b></font>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><img src="/images/epc/edi/ems/mail_bottom.gif" width="700" height="40"></td>
		</tr>
	</table>
</body>
</html>
