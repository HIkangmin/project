<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script>
</script>
<style>
	.shopinfo {
	    text-align : middle;
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
 
 	.agreement { font-size: 12px; }
	.fa-angle-right:before {
	  content: "\003E";
	  float: left;
	}
	ul li {list-style-type: none; float: left;}
</style>
</head>
<body  >
	<div class="clear">
	</div>
	<div id="path">
 
	    <ul>
	    	<li ><a href="${contextpath}/munbanggu/main/main.do">홈</a></li>
	        <li class="fa-angle-right" title="현재 위치"><strong>회사소개</strong></li>
	    </ul>
	</div>

	<div class="titleArea">
	 
	</div>
	<div class="clear">
	</div>
	<div class="shopinfo">
	 
		<div class="agreement">
 
	        <h1>회사소개</h1>
 
 
			<div class="desc_01">
				<h2>대한민국 최고 건강 종합식품 기업으로 성장 발전해 나가겠습니다.</h2>
				<p>
					문방구의 건강식품 브랜드인 ‘문방구웰라이프’는 건강기능식품과 건강관련 서비스 등 고부가가치 토탈 건강사업을 통해 지속하여 성장하고 있는 국내 최고의 건강 브랜드입니다.
					<br>
					2002년 국내 최초로 건강 전문 브랜드 ‘문방구웰라이프’를 도입했으며, 반세기 식품연구 노하우를 건강과학으로 발전시킨 ‘health & food science’ 를 모토로 고객의 건강한 삶과 즐거운 생활에 기여하고 있습니다.
				</p>
			</div>
	 
	
			<div class="desc_02">
				<div class="container">
		 
					<p>
						일반인 및 환자 영양식 브랜드인 ‘문방구뉴케어’는 1995년부터 현재까지 전문제조시설을 바탕으로 균형영양식 뉴케어 제품을 만들어 왔습니다. 가장 좋은 원료와 과학적 효능을 바탕으로 최고의 품질, 경쟁력 있는 제품 개발을 통해 국민건강에 기여하고 있습니다.
					</p>
					<p>
						끊임없는 변화를 지향하는 문방구웰라이프는 국내와 글로벌 선도기업으로 도약하기 위해 건강사업, 실버사업등 미래형 성장사업에 핵심역량을 집중하고 있습니다. 최고의 기술과 품질, 서비스 경쟁력을 갖춘 기업으로 성장하기 위해 최선을 다하고 있으며, 지속적인 경영혁신을 통해 “사람, 기술, 정보의 조화를 통해 고객을 건강한 삶으로 안내하는 100세 시대의 올바른 개척자”를 실현해 나아갈 것입니다.
					</p>
					<p>
						문방구웰라이프에 대한 끊임없는 관심과 사랑을 부탁드리며, 고객 여러분의 가정에 평화와 행운이 깃들기를 기원합니다.
					</p>
				</div>
			</div>	
		</div>   
	</div>

	<div class="clear"></div>
		<p class="pageTop"><a href="#" title="화면 최상단으로 이동하기"><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/layout/btn_top1.gif" alt="맨위로"/></a></p>

	<div class="clear"></div>
 </body>
</html>	