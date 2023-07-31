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
#contents { padding:0 0 29px; }
.xans-layout-info { margin:14px 7px 0; border:1px solid #d5d5d5; font-size:13px; }
.xans-layout-info li { overflow:hidden; padding:12px 14px 12px 109px; border-top:1px solid #ececec; line-height:1.4em; background:#fff; }
.xans-layout-info li:first-child { border:0; }
.xans-layout-info li .btnTel { margin:-6px 0 0; padding:7px 10px 7px 28px; border:1px solid #d5d5d5; border-radius:2px; background:#f1f1f1 url("http://img.echosting.cafe24.com/skin/mobile_ko_KR/layout/bg_tel.png") no-repeat 10px center; background-size:8px 13px; }
.xans-layout-info li strong { float:left; margin-left:-95px; color:#757575; font-weight:normal; }
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
 
	        <h1>고객센터</h1><br>

                 <h3>고객센터</h3> <br>
            <dl>
                <dt>전화 문의</dt>
                <dd>02-1111-2222</dd>
                 <dt>이메일문의</dt>
                <dd>munbanggu@munbanggu.com</dd>
                 <dt>팩스 문의</dt>
                <dd>02-2222-3333</dd>               
                <dt>상담시간</dt>
                <dd>평일 11~17시, 점심 13:00~14:00 주말,공휴일 휴무</dd>
                
                <dt>은행정보</dt>
                	<dd>국민은행 042601-04-235632 (예금주:송민정(프로젝트302))</dd>
			</dl><br>
                <h3>CUSTOMER CENTER</h3><br>
            <dl>
 
                <dt>phone</dt>
                <dd>02-1111-2222</dd>
                 <dt>Email</dt>
                <dd>munbanggu@munbanggu.com</dd>
                 <dt>Fax</dt>
                <dd>02-2222-3333</dd>               
                <dt>Service Hour</dt>
                <dd> MON-FRI 11AM - 5PM  / break time PM 13:00 ~ PM 14:00
                <br>SAT/SUN/HOLIDAY OFF
                
                </dd>
                <dt>Service Hour</dt><br>
                <dd>전화연결 불가시 Q&amp;A게시판 또는 카카오톡 상담을 이용해주세요</dd><br>
                
 
			</dl>
 
		</div>
    </div>
	<div class="clear"></div>
		<p class="pageTop"><a href="#" title="화면 최상단으로 이동하기"><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/layout/btn_top1.gif" alt="맨위로"/></a></p>

	<div class="clear"></div>
 </body>
</html>	