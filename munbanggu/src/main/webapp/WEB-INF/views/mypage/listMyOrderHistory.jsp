<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>

<meta   charset="utf-8">
<script>
window.onload =  function onload() {
	const search = $('input[name=t_search_word]');
	search.focus();
	search.val("${search_word}") ;
	// 검색창 자동포커스 및 커서이동을 위함 
	// t_search_word 테그는 viewPageTopSearchTable 안에 있음 
}

function fn_cancel_order(order_id){
	var answer=confirm("주문을 취소하시겠습니까?");
	if(answer==true){
		var formObj=document.createElement("form");
		var i_order_id = document.createElement("input"); 
	    
	    i_order_id.name="order_id";
	    i_order_id.value=order_id;
		
	    formObj.appendChild(i_order_id);
	    document.body.appendChild(formObj); 
	    formObj.method="post";
	    formObj.action="${contextPath}/mypage/cancelMyOrder.do";
	    formObj.submit();	
	}
}

</script>


</head>
<body>
	<H3>주문 조회</H3>
	
<jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="order_id,orderer_name,orderer_hp,receiver_name,goods_title"/>
	<jsp:param name="type_captions_streams" value="주문번호,주문자 성함,주문자 전화번호,수령자 성함,주문상품명"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminOrderMain.do"/>
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
	<jsp:param name="uri" value="${contextPath}/admin/order/adminOrderMain.do"/>
</jsp:include> 

			<tr style="background:#33ff00" >
				<td class="fixed" >주문번호</td>
				<td class="fixed">주문일자</td>
				<td>주문내역</td>
				<td>주문금액/수량</td>
				<td>주문상태</td>
				<td>주문자</td>
				<td>수령자</td>
				<td>주문취소</td>
			</tr>
   <c:choose>
     <c:when test="${empty myOrderHistList }">			
			<tr>
		       <td colspan=8 class="fixed">
				  <strong>주문한 상품이 없습니다.</strong>
			   </td>
		     </tr>
	 </c:when>
	 <c:otherwise> 
     <c:forEach var="item" items="${myOrderHistList }" varStatus="i">
        <c:choose>
          <c:when test="${item.order_id != pre_order_id }">   
            <tr>       
				<td>
				  <a href="${contextPath}/mypage/myOrderDetail.do?order_id=${item.order_id }"><strong>${item.order_id }</strong>  </a>
				</td>
				<td >
				 <strong>${item.pay_order_time }</strong> 
				</td>
				<td> 
				    <strong>
					   <c:forEach var="item2" items="${myOrderHistList}" varStatus="j">
				          <c:if  test="${item.order_id ==item2.order_id}" >
				            <a href="${contextPath}/goods/goodsDetail.do?goods_id=${item2.goods_id }">${item2.goods_title }</a><br>
				         </c:if>   
					 </c:forEach>
					 </strong>
				</td>
				<td>
				   <strong>
				      <c:forEach var="item2" items="${myOrderHistList}" varStatus="j">
				          <c:if  test="${item.order_id ==item2.order_id}" >
				             ${item.goods_sales_price*item.order_goods_qty }원/${item.order_goods_qty }<br>
				         </c:if>   
					 </c:forEach>
				   </strong>
				</td>
				<td>
				  <strong>
				    <c:choose>
					    <c:when test="${item.delivery_state=='delivery_prepared' }">
					       배송준비중
					    </c:when>
					    <c:when test="${item.delivery_state=='delivering' }">
					       배송중
					    </c:when>
					    <c:when test="${item.delivery_state=='finished_delivering' }">
					       배송완료
					    </c:when>
					    <c:when test="${item.delivery_state=='cancel_order' }">
					       주문취소
					    </c:when>
					    <c:when test="${item.delivery_state=='returning_goods' }">
					       반품
					    </c:when>
				  </c:choose>
				  </strong>
				</td>
				<td>
				 <strong>${item.orderer_name }</strong> 
				</td>
				<td>
					<strong>${item.receiver_name }</strong>
				</td>
				<td>
			     <c:choose>
			   <c:when test="${item.delivery_state=='delivery_prepared'}">
			       <input class="look_up_btn"  type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소"  />
			   </c:when>
			   <c:otherwise>
			      <input  class="look_up_btn" type="button" onClick="fn_cancel_order('${item.order_id}')" value="주문취소" disabled />
			   </c:otherwise>
			  </c:choose>
			    </td>
			</tr>
			<c:set  var="pre_order_id" value="${item.order_id }" />
		   </c:when>	
	  </c:choose>		
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
	<jsp:param name="uri" value="${contextPath}/admin/order/adminOrderMain.do"/>
</jsp:include> 
  
  
		</tbody>
	</table>
     	
	<div class="clear"></div>
</body>
</html>