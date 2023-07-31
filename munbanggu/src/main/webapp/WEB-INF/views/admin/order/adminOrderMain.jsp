<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>

<meta charset="utf-8">
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

 
function fn_modify_order_state(order_id,select_id){
	var s_delivery_state=document.getElementById(select_id);
    var index = s_delivery_state.selectedIndex;
    var value = s_delivery_state[index].value;
    //console.log("value: "+value );
	 
	$.ajax({
		type : "post",
		async : false,
		url : "${contextPath}/admin/order/modifyDeliveryState.do",
		data : {
			order_id:order_id,
			"delivery_state":value
		},
		success : function(data, textStatus) {
			if(data.trim()=='mod_success'){
				alert("주문 정보를 수정했습니다.");
				//location.href="${contextPath}/admin/order/adminOrderMain.do?section=${section}&pageNum=${page}&beginDate=${beginDate}&endDate=${endDate}&search_type=${search_type}&search_word=${search_word}";
				location.reload();
			}else if(data.trim()=='failed'){
				alert("다시 시도해 주세요.");	
			}
			
		},
		error : function(data, textStatus) {
			alert("에러가 발생했습니다."+data);
		},
		complete : function(data, textStatus) {
			//alert("작업을완료 했습니다");
			
		}
	}); //end ajax		
}

function fn_detail_order(order_id){
	//alert(order_id);
//	var frm_delivery_list=document.frm_delivery_list;
	

	var formObj=document.createElement("form");
	var i_order_id = document.createElement("input");
	
	i_order_id.name="order_id";
	i_order_id.value=order_id;
	
    formObj.appendChild(i_order_id);
    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/admin/order/orderDetail.do"; // 주문 상세 페이지 
    formObj.submit();
	
}


</script>

</head>
<body onload="onload()">

	<H3>주문 관리</H3>
	<%-- 동작이 없는것 같아서 제거 후 viewPageTopSearchTable에 통합함 문제가 있을 경우 살릴 것 20230712
	<form name="frm_delivery_list" action="${ contextPath }/admin/admin.do" method="get">	 --%>	
 	
 <jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="order_id,orderer_name,orderer_hp,receiver_name,goods_title"/>
	<jsp:param name="type_captions_streams" value="주문번호,주문자 성함,주문자 전화번호,수령자 성함,주문상품명"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminOrderMain.do"/>
</jsp:include>

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
				<td>배송상태</td>
				<td>배송수정</td>
			</tr>
   <c:choose>
     <c:when test="${empty newOrderList}">			
			<tr>
		       <td colspan=5 class="fixed">
				  <strong>주문한 상품이 없습니다.</strong>
			   </td>
		     </tr>
	 </c:when>
	 <c:otherwise>
     <c:forEach var="item" items="${newOrderList}" varStatus="i">
        <c:choose>
          <c:when test="${item.order_id != pre_order_id }">  
            <c:choose>
              <c:when test="${item.delivery_state=='delivery_prepared' }">
                <tr  bgcolor="lightgreen">
              </c:when>
              <c:when test="${item.delivery_state=='finished_delivering' }">
                <tr  bgcolor="lightgray">    
              </c:when>
              <c:otherwise>
                <tr  bgcolor="orange">   
              </c:otherwise>
            </c:choose>   
				 <td width=10%>
				   <a href="javascript:fn_detail_order('${item.order_id}')">
				     <strong>${item.order_id}</strong>
				   </a>
				</td>
				<td width=20%>
			   <a href="javascript:fn_detail_order('${item.order_id}')">
					 <strong>${item.pay_order_time }</strong>
			   </a> 
				</td>
				<td width=50% align=left >
				<a href="javascript:fn_detail_order('${item.order_id}')">
				    <strong>주문자:${item.orderer_name}</strong><br>
				  <strong>주문자 전화번호:${item.orderer_hp}</strong><br>
				  <strong>수령자:${item.receiver_name}</strong><br>
				  <strong>수령자 전화번호:${item.receiver_hp1}-${item.receiver_hp2}-${item.receiver_hp3}</strong><br>
				  <strong>주문상품명(수량):${item.goods_title}(${item.order_goods_qty})</strong><br>
				     <c:forEach var="item2" items="${newOrderList}" varStatus="j">
				       <c:if test="${j.index > i.index }" >
				          <c:if  test="${item.order_id ==item2.order_id}" >
				            <strong>주문상품명(수량):${item2.goods_title}(${item2.order_goods_qty})</strong><br>
				      </c:if>   
				       </c:if>
				    </c:forEach> 
		    </a>
				</td>
				<td width=10%>
				 <select name="s_delivery_state${i.index }"  id="s_delivery_state${i.index }">
				 <c:choose>
				   <c:when test="${item.delivery_state=='delivery_prepared' }">
				     <option  value="delivery_prepared" selected>배송준비중</option>
				     <option  value="delivering">배송중</option>
				     <option  value="finished_delivering">배송완료</option>
				     <option  value="cancel_order">주문취소</option>
				     <option  value="returning_goods">반품</option>
				   </c:when>
				    <c:when test="${item.delivery_state=='delivering' }">
				    <option  value="delivery_prepared" >배송준비중</option>
				     <option  value="delivering" selected >배송중</option>
				     <option  value="finished_delivering">배송완료</option>
				     <option  value="cancel_order">주문취소</option>
				     <option  value="returning_goods">반품</option>
				   </c:when>
				   <c:when test="${item.delivery_state=='finished_delivering' }">
				    <option  value="delivery_prepared" >배송준비중</option>
				     <option  value="delivering"  >배송중</option>
				     <option  value="finished_delivering" selected>배송완료</option>
				     <option  value="cancel_order">주문취소</option>
				     <option  value="returning_goods">반품</option>
				   </c:when>
				   <c:when test="${item.delivery_state=='cancel_order' }">
				    <option  value="delivery_prepared" >배송준비중</option>
				     <option  value="delivering"  >배송중</option>
				     <option  value="finished_delivering" >배송완료</option>
				     <option  value="cancel_order" selected>주문취소</option>
				     <option  value="returning_goods">반품</option>
				   </c:when>
				   <c:when test="${item.delivery_state=='returning_goods' }">
				    <option  value="delivery_prepared" >배송준비중</option>
				     <option  value="delivering"  >배송중</option>
				     <option  value="finished_delivering" >배송완료</option>
				     <option  value="cancel_order" >주문취소</option>
				     <option  value="returning_goods" selected>반품</option>
				   </c:when>
				   </c:choose>
				 </select> 
				</td>
				<td width=10%>
			     <input  type="button" value="배송수정"  onClick="fn_modify_order_state('${item.order_id}','s_delivery_state${i.index}')"/>
			    </td>
			</tr>
		</c:when>
		</c:choose>	
		<c:set  var="pre_order_id" value="${item.order_id }" />
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
  
	<div class="clear" ></div>
</body>
</html>
