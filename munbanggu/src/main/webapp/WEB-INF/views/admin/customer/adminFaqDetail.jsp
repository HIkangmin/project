<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<html>
<head>
<meta  charset="utf-8">

<link href="${contextPath}/resources/css/adminNoticeDetail.css" rel="stylesheet" type="text/css">
</head>
<body>
 <div class="content-wrap">
        <p class="admin-title">faq</p>
        <div class="notice_board">
        
         <c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id =='admin'}">
	        <form action="modifyFaq.do" method = "post">
	        <c:forEach items="${selectFaq}" var="selectFaq">
	        <div class="notice_board_title">
	            	<input type="hidden" name="faq_id" value="${ selectFaq.faq_id }"></input>
	            	<input type="text" name="faq_title" value="${ selectFaq.faq_title }"></input>
	            	<span>
	            		<c:set var="dateVar" value="${ selectFaq.faq_date }" />
						<fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" />
					</span>
			</div>
	        <div class="notice_board_content">
	                <textarea rows="" cols=""  name="faq_content">${selectFaq.faq_content}</textarea >               
	        </div>
	           <input type="submit" value="수정"></input>
	           </c:forEach>
	       	</form>
	       	
	       	</c:when>
	       	</c:choose>
	       	
	       	
	       	
   	        <c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id !='admin'}">
	        <form action="modifyFaq.do" method = "post">
	        <c:forEach items="${selectFaq}" var="selectFaq">
	        <div class="notice_board_title">
	            	<input type="hidden" name="faq_id" value="${ selectFaq.faq_id }" />
	            	<input type="text" name="faq_title" value="${ selectFaq.faq_title }" />
	            	<span>
	            		<c:set var="dateVar" value="${ selectFaq.faq_date }" />
						<fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" />
					</span>
			</div>
					
			
				<div class="notice_board_content">
					  <div class="box">
					  ${selectFaq.faq_content}					  
					</div>
				</div>
			
			</c:forEach>
			</form>
      	
	       	</c:when>
	       	</c:choose>   	
       	    
            <a href="adminFaqMain.do"><button>목록</button></a>
        </div>
       
    </div><!--content-wrap-->
    </body>
    <script>
    $('textarea').each(function () {
    	this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    	}).on('input', function () {
    	this.style.height = 'auto';
    	this.style.height = (this.scrollHeight) + 'px';
    	});
    </script>
   </html>