<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script>
</script>
<style>
	.shopinfo {
	    text-align : left;
	    border-width : 15px;
		width: 94%;
	    border-style: ridge;
	    margin: 20px;
	}
	#path {
		list-style: none;
		float: right;
		font-size: 12px;	
	}
 
 	.agreement { 
 		font-size: 12px;
 		margin: 16px;
 	 }
	.fa-angle-right:before {
	  content: "\003E";
	  float: left;
	}
	ol li {list-style-type: none; float: left;}
	ul li {list-style-type: none;}
</style> 
</head>
<body  >

<div id="path">

    <ol><li><a href="${contextpath}/munbanggu/main/main.do">홈</a></li>
        <li class="fa-angle-right"  class="fa-angle-right"  title="현재 위치"><strong>이용안내</strong></li>
    </ol></div>
<div class="shopinfo">
 
	<div class="agreement">
			<div id="member">
	 
			<div class="cont">
	            <h1>회원가입 안내</h1>
	            <div class="fr-view fr-view-faq-member-info">[회원가입] 메뉴를 통해  일정 양식의 가입항목을 기입함으로써 회원에
				가입되며, 가입 즉시 서비스를 무료로 이용하실 수 있습니다.<br>
				주문하실 때에 입력해야하는 각종 정보들을 일일이 입력하지 않으셔도 됩니다. 공동구매, 경매행사에 항상 참여하실 수 있습니다. 회원을
				위한 이벤트 및 각종 할인행사에 참여하실 수 있습니다.
				</div>        
			</div>
	    	</div>
		<div id="order">
	 
			<div class="cont">
				<h3>주문 안내</h3>
				<div class="fr-view fr-view-faq-order-info"> 상품주문은
					다음단계로 이루어집니다.<br>
					<br>
					- Step1: 상품검색<br>
					- Step2: 장바구니에 담기<br>
					- Step3: 회원ID 로그인 주문<br>
					- Step4: 주문서 작성<br>
					- Step5: 결제방법선택 및 결제<br>
					- Step6: 주문 성공 화면 (주문번호)<br>
					<br>
 
				</div>        </div>
	   </div>
		<div id="payment">
	 
		<div class="cont">
	            <h3>결제안내</h3>
	            <div class="fr-view fr-view-faq-payment-info"><p>무통장 입금의 경우 주문시 입력한&nbsp;입금자명과 실제입금자의 성명이 반드시 일치하여야 하며, </p><p>7일 이내로 입금을 하셔야 하며&nbsp;입금되지
	      않은 주문은 자동취소 됩니다. <br>
		</p></div>        </div>
		    </div>
		<div id="delivery">
		 
		<div class="cont">
		            <h3>배송안내</h3>
		            <ul>
		<li>배송 방법 : 택배</li>
			<li>배송 지역 : 전국지역</li>
			<li>배송 비용 : 고정배송비 : 주문 금액에 상관없이 배송비는 무료입니다.</li>
			<li>배송 기간 : 2일 ~ 3일</li>
			<li>배송 안내 : <div class="fr-view fr-view-faq-shipping-agreement">산간벽지나 도서지방은 별도의 추가금액을 지불하셔야 하는 경우가 있습니다.<br>
				고객님께서 주문하신 상품은 입금 확인후 배송해 드립니다. <br>
		</div></li>
		            </ul>
		</div>
		    </div>
		<div id="change">
		 
		<div class="cont">
			<h3>교환/반품안내</h3>
			<div class="fr-view fr-view-faq-prod-change-agreement"><p><b>교환 및 반품이 가능한 경우</b><br>
				- 상품을 공급 받으신 날로부터 7일이내 </p><p><br>
				
				<b>교환 및 반품이 불가능한 경우</b><br>- 제품의 포장을 개봉하였거나 포장이 훼손되어 상품가치가 상실된 경우에는 교환/반품이 불가능합니다.<br>
				- 시간의 경과에 의하여 재판매가 곤란할 정도로 상품등의 가치가 현저히 감소한 경우<br>
				<br>
				※ 고객님의 마음이 바뀌어 교환, 반품을 하실 경우 상품반송 비용은 고객님께서 부담하셔야 합니다.<br></p>
			</div>        </div>
	    </div>
		<div id="refund">
	 
		<div class="cont">
			<h3>환불안내</h3>
			<div class="fr-view fr-view-faq-change-agreement">환불시 반품 확인여부를 확인한 후 3영업일 이내에 결제 금액을 환불해 드립니다. <br>
			신용카드로 결제하신 경우는 신용카드 승인을 취소하여 결제 대금이 청구되지 않게 합니다.<br>
			(단, 신용카드 결제일자에 맞추어 대금이 청구 될수 있으면 이경우 익월 신용카드 대금청구시 카드사에서 환급처리<br>
			됩니다.)
		</div>        </div>
		    </div>
		<div id="etc">
		 
		<div class="cont">
			<h3>기타안내</h3>
			<div class="fr-view fr-view-faq-etc-info"><b>이용기간</b><br>
			주문으로 발생한 적립금은 배송완료 체크시점으로 부터 20일이 지나야 실제 사용 가능 적립금으로 변환됩니다.
			20일 동안은 미가용 적립금으로 분류 됩니다. 미가용 적립금은 반품, 구매취소 등을 대비한 실질적인 구입이 되지 않은
			주문의 적립금 입니다.
			<br>사용가능한 적립금(총 적립금-사용된적립금-미가용적립금)은 상품구매시 즉시 사용하실 수 있습니다.<br>
			<br>
			<b>이용조건</b><br>
			적립금사용시 최소구매가능적립금(구매가능한 적립금 요구선)은 0원 입니다. 적립금 사용시
			최대구매가능적립금(적립금 1회 사용 가능 최대금액)은 '한도제한없음' 입니다.<br>
			<br>
			<b>소멸조건</b><br>
			주문취소/환불시에 상품구매로 적립된 적립금은 함께 취소됩니다. 회원 탈퇴시에는 적립금은 자동적으로
			소멸됩니다. 최종 적립금 발생일로부터 3년 동안 추가적립금 누적이 없을 경우에도 적립금은 소멸됩니다.
			</div>        </div>
			    </div>
		</div>          
</div>
	<div class="clear">
		<p class="pageTop"><a href="#" title="화면 최상단으로 이동하기"><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/layout/btn_top1.gif" alt="맨위로"/></a></p>
	<div class="clear">
 
</body>
</html>	