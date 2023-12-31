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
    <p class="admin-title">게시판 관리</p>
    <p class="admin-subtitle">-공지사항 </p>
    <div class="notice_board">
     <c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id =='admin'}">
        <form action="modifyNotice.do" method="post">
            <c:forEach items="${selectNotice}" var="notice">
                <div class="notice_board_title">
                    <input type="hidden" name="notice_id" value="${notice.notice_id}" />
                    <input type="text" name="notice_title" value="${notice.notice_title}" />
                    <span>
                        <c:set var="dateVar" value="${notice.notice_date}" />
                        <fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" />
                    </span>
                </div>
                
                
           
                <div class="notice_board_content">
                    <textarea rows="" cols="" name="notice_content">${notice.notice_content}</textarea>
                </div>
                
                <input type="submit" value="수정" />
            </c:forEach>
        </form>    
        <a href="adminNoticeMain.do"><button>목록</button></a>
        </c:when>
        </c:choose>


<c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id !='admin'}">
        <form action="modifyNotice.do" method="post">
            <c:forEach items="${selectNotice}" var="notice">
                <div class="notice_board_title">
                    <input type="hidden" name="notice_id" value="${notice.notice_id}" />
                    <input type="text" name="notice_title" value="${notice.notice_title}" />
                    <span>
                        <c:set var="dateVar" value="${notice.notice_date}" />
                        <fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" />
                    </span>
                </div>      
                
           
					<div class="notice_board_content">
					  <div class="box">
					  ${notice.notice_content}
					  </div>
					</div>
                
            </c:forEach>
        </form>    
        <a href="adminNoticeMain.do"><button>목록</button></a>
        </c:when>
        </c:choose>


    </div>
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