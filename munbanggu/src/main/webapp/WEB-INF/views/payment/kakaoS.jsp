<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<H1>카카오 결제 성공</H1>


	<c:out value="${kakaoData.amount}" /> 
	<!-- 숫자포멧 -->
	<fmt:formatNumber value="${kakaoData.amount}" pattern="#,###" />
	<!-- 날짜포멧 -->
	<fmt:formatDate value="${dateValue}" pattern="yyyy-mm-dd" />

	<table border = '1' >
		<!-- {amount=100, orderNumber=TEST_ORDER,
		type=KAKAO_MONEY, authDateTime=20230601151907, cashReceipt=100,
		transactionId=20230601151831KK0661, responseCode=0000, responseMsg=정상처리} -->
		<tr>
		<td>결제금액</td> 
			<td>amount</td>
			<td>
			<fmt:formatNumber value="${kakaoData.amount}" pattern="#,###"/> 원
			</td>
		</tr>
		<tr>
		<td>가맹점 주문번호</td>
			<td>orderNumber</td>
			<td>${kakaoData.orderNumber}</td>
		</tr>
		<tr>
		<td>결제타입</td>
			<td>type</td>
			<td>${kakaoData.type}</td>
		</tr>
		<tr>
		<td>승인일시</td>
			<td>authDateTime</td>
			<td>
			<fmt:parseDate value="${kakaoData.authDateTime}" var="dateValue" pattern="yyyyMMddHHmmss"/>
			<fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
		</tr>
		<tr>
		<td>현금영수증 대상금액</td>
			<td>cashReceipt</td>
			<td>${kakaoData.cashReceipt}</td>
		</tr>
		<tr>
		<td>페이업 거래번호</td>
			<td>transactionId</td>
			<td>${kakaoData.transactionId}</td>
		</tr>
		<tr>
		<td>응답코드</td>
			<td>responseCode</td>
			<td>${kakaoData.responseCode}</td>
		</tr>
		<tr>
		<td>응답메세지</td>
			<td>responseMsg</td>
			<td>${kakaoData.responseMsg}</td>
		</tr>
	</table>



</body>
</html>