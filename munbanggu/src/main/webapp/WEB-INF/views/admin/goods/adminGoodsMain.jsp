<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<script>
function onload() {
	const search = $('input[name=t_search_word]');
	search.focus();
	search.val("${search_word}") ;
	// 검색창 자동포커스 및 커서이동을 위함 
	// t_search_word 테그는 viewPageTopSearchTable 안에 있음 
}


</script>
</head>
<body onload="onload()">
 <H3>상품 관리</H3>
 
 <jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="goods_id,goods_sort,goods_title,goods_writer,goods_publisher,goods_price,goods_sales_price,goods_point,goods_published_date,goods_delivery_price,goods_delivery_date,goods_status,goods_credate"/>
	<jsp:param name="type_captions_streams" value="제품 번호,제품 분류,제품 이름,제품 저자,출판사 (공급사),가격,할인가격,마일리지,출판일,배송비,배송일,제품 상태,입고 일자"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminGoodsMain.do"/>
</jsp:include>

<DIV class="clear"></DIV>
<TABLE class="list_view">
  <TBODY align=center >
  
  
   <jsp:include page="/WEB-INF/views/common/tableBottomPagination.jsp" flush="false">
	<jsp:param name="section" value="${section}"/>
	<jsp:param name="pageNum" value="${pageNum}"/>
	<jsp:param name="maxPage" value="${maxPage}"/>
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminGoodsMain.do"/>
</jsp:include> 
  
  
   <tr style="background:#33ff00" >
    <td>상품번호</td>
    <td>상품이름</td>
    <td>판매사</td>
    <td>제조사</td>
    <td>상품가격</td>
    <td>입고일자</td>
    <td>출판일</td>
   </tr>
   <c:choose>
     <c:when test="${empty newGoodsList }">   
   <TR>
         <TD colspan=8 class="fixed">
      <strong>조회된 상품이 없습니다.</strong>
      </TD>
       </TR>
  </c:when>
  <c:otherwise>
     <c:forEach var="item" items="${newGoodsList }">
    <TR>       
    <TD>
      <strong>${item.goods_id }</strong>
    </TD>
    <TD >
     <a href="${pageContext.request.contextPath}/admin/goods/modifyGoodsForm.do?goods_id=${item.goods_id}">
        <strong>${item.goods_title } </strong>
     </a> 
    </TD>
    <TD>
    <strong>${item.goods_writer }</strong> 
    </TD>
    <TD >
       <strong>${item.goods_publisher }</strong> 
    </TD>
    <td>
      <strong>${item.goods_sales_price }</strong>
    </td>
    <td>
     <strong>${item.goods_credate }</strong> 
    </td>
    <td>
        <c:set var="pub_date" value="${item.goods_published_date}" />
        <c:set var="arr" value="${fn:split(pub_date,' ')}" />
     <strong>
        <c:out value="${arr[0]}" />
     </strong>
    </td>
    
   </TR>
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
    <jsp:param name="uri" value="${contextPath}/admin/goods/adminGoodsMain.do"/>
</jsp:include>

   
  </TBODY>
  
 </TABLE>
 <DIV class="clear"></DIV>
 <br><br><br>
<H3>상품등록하기</H3>
<DIV id="search">
 <form action="${contextPath}/admin/goods/addNewGoodsForm.do">
  <input  type="submit" value="상품 등록하기">
 </form>
</DIV>
</body>
</html>