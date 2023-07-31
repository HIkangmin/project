<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${contextPath}/resources/css/adminQnaDetail.css" rel="stylesheet" type="text/css">

	<% session = request.getSession(); %>

<div class="content-wrap">
        <p class="admin-title">Q&A</p>
        
                      
        <div id="qna-answer-wrap">
              <c:forEach var="qna" items="${selectQna}">
<form action="/adminQnaDetailImage.do" method="get">
            <table>
                <tr class="taps" style="width:50%;">
                    <td>이름</td>
                    <td class="taps_td">${ qna.member_name }</td>                    
                </tr>
                <tr class="taps" style="width:50%;">
                    <td>작성일</td>
                    <td class="taps_td"><fmt:formatDate value="${ qna.qna_date }" pattern = "yyyy-MM-dd"/></td>                    
                </tr>
                <tr>
                    <td>제목</td>
                    <td class="taps_td">${ qna.qna_title }</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td class="taps_td">${ qna.qna_content }</td>
                </tr>
                <tr>
                    <td>사진</td>
                    <td>
                        <div class="qna-answer-img">
                            <img src="${contextPath}/qnathumbnails.do?qna_id=${qna.qna_id}&fileName=${qna.fileName}" alt="" width="70px" height="70px" onClick="view('${qna.qna_id}', '${qna.fileName}')" style="cursor:pointer;">
                        </div>
                    </td>
                </tr>    
                
              
              <c:if test="${ !empty selectQna[0].qna_comment }">
              <tr id="qna_comment">
		<td class="qna-td1 qna-td-content2"></td>
		      <td colspan='4' class="qna-td2">
		      <br>
		<br>${ selectQna[0].qna_comment }</td> 
        
			</tr>
        </c:if>      
            </table>
            
</form>

     <c:choose>
			<c:when test="${isLogOn==true and memberInfo.member_id =='admin'}">
                 <div id="answerform-wrap">
                <form action="modifyQna.do">
                    <div>
                        <p>답변내용</p>
                        <p>
                        	<input type="hidden" name="qna_id" value="${ qna.qna_id }">
                            <textarea name="qna_comment" id="">${ qna.qna_comment }</textarea>
                        </p>
                    </div>
                <input type="submit" value="답변하기">
                </form>
            </c:when>
		</c:choose> 
              </c:forEach>      
                   
            </div><!--answerform-wrap 답변부분 랩-->
        </div><!--one2one-answer-wrap-->

    
    
	
    
<script>
function view(qna_id, fileName) {
    window.name = "adminQnaDetail"
    window.open("adminQnaDetailImage.do?qna_id=" + qna_id + "&fileName=" + fileName, "adminQnaDetailImage.do ", '');
    form.submit();
  };    
</script>