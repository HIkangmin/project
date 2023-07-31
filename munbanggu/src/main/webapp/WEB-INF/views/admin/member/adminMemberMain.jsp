<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
window.onload=()=>
{
	onLoad();
}

function onLoad() {
	const search = $('input[name=t_search_word]');
	search.focus();
	search.val("${search_word}") ;
	// 검색창 자동포커스 및 커서이동을 위함 
	// t_search_word 테그는 viewPageTopSearchTable 안에 있음 
}


function fn_member_detail(order_id){
	//alert(order_id);
	var frm_delivery_list=document.frm_delivery_list;
	

	var formObj=document.createElement("form");
	var i_order_id = document.createElement("input");
	
	i_order_id.name="order_id";
	i_order_id.value=order_id;
	
    formObj.appendChild(i_order_id);
    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/admin/member/memberDetail.do";
    formObj.submit();
	
}


</script>
</head>
<body  >
	<H3>회원 관리</H3>
	
	

<jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="member_name,member_id,member_hp_num,member_addr,member_email,member_gender,member_birth_y,joindate"/>
	<jsp:param name="type_captions_streams" value="회원성함,회원아이디,회원휴대폰번호,회원주소,회원이메일,회원성별,출생년도,가입일"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminMemberMain.do"/>
</jsp:include>

					
	
<div class="clear"></div>
<table class="list_view">
		<tbody align=center >
		
		
<jsp:include page="/WEB-INF/views/common/tableBottomPagination.jsp" flush="false">
	<jsp:param name="section" value="${section}"/>
	<jsp:param name="pageNum" value="${pageNum}"/>
	<jsp:param name="maxPage" value="${maxPage}"/>
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="uri" value="${contextPath}/admin/member/adminMemberMain.do"/>
</jsp:include> 
		
		
			<tr align=center bgcolor="#ffcc00">
				<td class="fixed" >회원아이디</td>
				<td class="fixed">회원성함</td>
				<td>휴대폰번호</td>
				<td>주소</td>
				<td>가입일</td>
				<td>탈퇴여부</td>
			</tr>
   <c:choose>
     <c:when test="${empty member_list}">			
			<tr>
		       <td colspan=5 class="fixed">
				  <strong>조회된 회원이 없습니다.</strong>
			   </td>
		     </tr>
	 </c:when>
	 <c:otherwise>
	     <c:forEach var="item" items="${member_list}" varStatus="item_num">
	            <tr >       
					<td width=10%>
					
					  <a href="${pageContext.request.contextPath}/admin/member/memberDetail.do?member_id=${item.member_id}">
					     <strong>${item.member_id}</strong>
					  </a>
					</td>
					<td width=10%>
					  <strong>${item.member_name}</strong><br>
					</td>
					<td width=10% >
					  <strong>${item.hp1}-${item.hp2}-${item.hp3}</strong><br>
					</td>
					<td width=50%>
					  <strong>${item.roadAddress}</strong><br>
					  <strong>${item.jibunAddress}</strong><br>
					  <strong>${item.namujiAddress}</strong><br>
					</td>
					<td width=10%>
					   <c:set var="join_date" value="${item.joinDate}" />
					   <c:set var="arr" value="${fn:split(join_date,' ')}" />
					   <strong><c:out value="${arr[0]}" /></strong>
				    </td>
				    <td width=10%>
				       <c:choose>
				         <c:when test="${item.del_yn=='N' }">
				           <strong>활동중</strong>  
				         </c:when>
				         <c:otherwise>
				           <strong>탈퇴</strong>
				         </c:otherwise>
				       </c:choose>
				    </td>
				</tr>
		</c:forEach>
	</c:otherwise>
  </c:choose>	


<jsp:include page="/WEB-INF/views/common/tableBottomPagination.jsp" flush="false">
	<jsp:param name="section" value="${section}"/>
	<jsp:param name="pageNum" value="${pageNum}"/>
	<jsp:param name="maxPage" value="${maxPage}"/>
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="uri" value="${contextPath}/admin/member/adminMemberMain.do"/>
</jsp:include> 




		</tbody>
	</table>
	<div class="clear"></div>
<c:choose>
 <c:when test="${not empty order_goods_list }">	
   <DIV id="page_wrap">
		 <c:forEach   var="page" begin="1" end="10" step="1" >
		         <c:if test="${chapter >1 && page==1 }">
		          <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?chapter=${chapter-1}&pageNum=${(chapter-1)*10 +1 }">&nbsp;pre &nbsp;</a>
		         </c:if>
		          <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?chapter=${chapter}&pageNum=${page}">${(chapter-1)*10 +page } </a>
		         <c:if test="${page ==10 }">
		          <a href="${pageContext.request.contextPath}/admin/member/adminMemberMain.do?chapter=${chapter+1}&pageNum=${chapter*10+1}">&nbsp; next</a>
		         </c:if> 
	      </c:forEach> 
	</DIV>	
 </c:when>
</c:choose>
</body>
</html>

