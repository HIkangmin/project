<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<html>
<head>
<meta  charset="utf-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<link href="${contextPath}/resources/css/adminNotice.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/resources/css/pagenav.css" rel="stylesheet" type="text/css">
    
<script type="text/javascript">


window.onload=()=>
{
	onLoad();
}

function onLoad() {
	// 검색창 자동포커스 및 커서이동을 위함 
	// t_search_word 테그는 viewPageTopSearchTable 안에 있음 
	const search = $('input[name=t_search_word]');
	search.focus();
	search.val("${search_word}") ;
}



	var loopSearch=true;
	function keywordSearch(){
		if(loopSearch==false)
			return;
	 var value=document.frmSearch.searchWord.value;
		$.ajax({
			type : "get",
			async : true, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/admin/customer/NoticekeywordSearch.do",
			data : {keyword:value},
			success : function(data, textStatus) {
			    var jsonInfo = JSON.parse(data);
				displayResult(jsonInfo);
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다."+data);
			},
			complete : function(data, textStatus) {
				//alert("작업을완료 했습니다");
				
			}
		}); //end ajax	
	}
</script>
</head>
<body>
<div id="page-nav-wrap">
	<div id="page-nav">
		<div class="titlediv">
		<span class="customer-title">게시판관리</span>
	</div>

		<div id="customer-session-menu">
		<ul>
			<li onClick="location.href='adminNoticeMain.do'" id="notice_menu"><a
				herf="/customer/adminNoticeMain.do"> 공지사항 </a></li>
			<li onClick="location.href='adminFaqMain.do'" id="faq_menu"><a
				herf="/customer/adminFaqMain.do"> FAQ </a></li>
			<li onClick="location.href='adminQnaMain.do'" id="qna_menu"><a
				herf="/customer/adminQnaMain.do"> Q&A </a></li>
		</ul>
	</div>
    </div>
</div>
<div class="boardwrap">
	<div class="border-qna-wrap">
	
		
	<jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="qna_id,goods_id,member_id,qna_title,qna_content,qna_comment,qna_date,fileName,member_name"/>
	<jsp:param name="type_captions_streams" value="글번호,상품 ID,유저 ID,글제목,내용,답글,작성한 날짜,파일이름,작성한 날짜,유저 이름"/>
	<jsp:param name="uri" value="${contextPath}/admin/customer/adminQnaMain.do"/>
</jsp:include>
	
	
	<c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id !='admin'}">
	
	<div class="customerwrap">
	<div id="customer">
		<input onClick="location.href='/munbanggu/customer/qnaWrite.do'" type="button" value="QNA 작성하기">
	    <table class="customer-table">
	    </table>
	    
	    </div>
	    </div>
	    </c:when>
	    </c:choose>
	

        <form action="deleteNotice.do"  method="post" id="admindelete">
	        <div>
	            <table class="noticeAdmin-table">
	            
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
	                <tr>
	                <td><br></td>
	                    <tr>
	                    </tr>
	                <tr>
	                    <td class="noticeAdmin-title"></td>
	                    <td class="noticeAdmin-title">No</td>
	                    <td class="noticeAdmin-title">제목</td>
	                    <td class="noticeAdmin-title">등록일</td>
	                </tr>
	                <c:forEach var="qna" items="${ selectList }">
	                <tr>
	                    <td class="noticeAdmin-td1"><input type="checkbox" name="qna_id" value="${ qna.qna_id }"></td>
	                    <td class="noticeAdmin-td2">${ qna.qna_id }</td>
	                    <td class="noticeAdmin-td3"><a class="notice-view" href="adminQnaDetail.do?qna_id=${ qna.qna_id }">${ qna.qna_title }</a></td>
	                    <td class="noticeAdmin-td5">
							<c:set var="dateVar" value="${ qna.qna_date }" />
							<fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" /></td>
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
        </form>    
    </div>
</div>
</body>
</html>
    