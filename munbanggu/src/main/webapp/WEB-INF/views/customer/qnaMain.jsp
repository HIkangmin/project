<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>

<div id="page-nav-wrap">
	<div id="page-nav">
		<div class="titlediv">
			<span class="customer-title">고객센터</span>
		</div>
		<div id="customer-session-menu">
			<ul>
				<li onClick="location.href='noticeMain.do'" id="notice_menu"><a
				herf="/customer/noticeMain.do"> 공지사항 </a></li>
				<li onClick="location.href='faqMain.do'" id="faq_menu"><a
					herf="/customer/faqMain.do"> FAQ </a></li>
				<li onClick="location.href='qnaMain.do'" id="qna_menu"><a
					herf="/customer/qnaMain.do"> Q&A </a></li>
				
			</ul>
		</div>
    </div>
</div>
 
<div class="customerwrap">
	<div id="customer">
		<input onClick="location.href='qnaWrite.do'" type="button" value="QNA 작성하기">
	    <table class="customer-table">
	    


 <tr>
<jsp:include page="/WEB-INF/views/common/tableBottomPagination.jsp" flush="false">
	<jsp:param name="section" value="${section}"/>
	<jsp:param name="pageNum" value="${pageNum}"/>
	<jsp:param name="maxPage" value="${maxPage}"/>
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="uri" value="${contextPath}/admin/customer/adminQnaMain.do"/>
</jsp:include> 
	</tr>	
	    	    	        		            <tr>
			            <td>
			            	<br>
			            </td>
		            </tr>
		            
	    
	        <tr class="table-head">
	            <td class="table-td1 qna-table-td1">번호</td>
	            <td class="table-td2 qna-table-td2">상품</td>
	            <td class="table-td3 qna-table-td3">제목</td>
	            <td class="table-td4 qna-table-td4">작성자</td>
	            <td class="table-td5 qna-table-td5">작성일</td>
	        </tr>
	        <c:forEach var="list" items="${selectList}">
	        <tr class="table-tr" onclick="location.href='qnaDetail.do?qna_id=${ list.qna_id }'">
	            <td class="td1">${ list.qna_id }</td>
	            <td class="td2">
	            	<img src="${contextPath}/thumbnails.do?goods_id=${list.goods_id}&fileName=${list.fileName}">
	            </td>
	            <td class="td3 qna_title_td3">${ list.qna_title }</td>
	            <td class="td4">${ list.member_name }</td>
	            <td class="td5"><fmt:formatDate value="${ list.qna_date }" pattern = "yyyy-MM-dd"/></td>
	        </tr>
	        </c:forEach>
	        
	        		            <tr>
			            <td>
			            	<br>
			            </td>
		            </tr>
		            
		            <tr>
<jsp:include page="/WEB-INF/views/common/tableBottomPagination.jsp" flush="false">
	<jsp:param name="section" value="${section}"/>
	<jsp:param name="pageNum" value="${pageNum}"/>
	<jsp:param name="maxPage" value="${maxPage}"/>
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="uri" value="${contextPath}/admin/customer/adminQnaMain.do"/>
</jsp:include> 
	
	</tr>	
			
	        
	     </table>
	 
				

			
			
			
			
			
		</div>
	</div>
</div>
    