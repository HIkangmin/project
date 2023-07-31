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


function fn_add_company(){
	var frm_delivery_list=document.frm_delivery_list;

	var formObj=document.createElement("form");
	var i_company_id = document.createElement("input");
 
	i_company_id.name="company_id";
	i_company_id.value=0;
 
    formObj.appendChild(i_company_id);
 
    document.body.appendChild(formObj); 

    formObj.method="post";

    formObj.action="${contextPath}/munbanggu/admin/company/addCompanyDetail.do";
    formObj.submit();

}

function fn_modify_company(company_id){
	var frm_delivery_list=document.frm_delivery_list;

	var formObj=document.createElement("form");
	var i_company_id = document.createElement("input");
 
	i_company_id.name="company_id";
	i_company_id.value=company_id;
 
    formObj.appendChild(i_company_id);
 
    document.body.appendChild(formObj); 

    formObj.method="post";

    formObj.action="${contextPath}/munbanggu/admin/company/modifyCompanyInfo.do";
    formObj.submit();

}

function fn_delete_companyinfo(company_id){
	var frm_delivery_list=document.frm_delivery_list;
	var value = "";
 
	var formObj=document.createElement("form");
	var i_company_id = document.createElement("input");
 
 
	i_company_id.name="company_id";
	i_company_id.value=company_id;
 
    formObj.appendChild(i_company_id);
 
    document.body.appendChild(formObj); 
 
    formObj.method="post";

    formObj.action="${contextPath}/munbanggu/admin/company/deleteCompanyInfo.do";
    formObj.submit(); 
} 
function fn_delete_company(company_id,mod_type,del_yn){
	var frm_delivery_list=document.frm_delivery_list;
	var value = "";
	if(mod_type=='del_yn'){
		if(frm_delivery_list.del_yn.value=='N'){
			value = 'Y';
		}else{
			value = 'N';
		}

	}
	var formObj=document.createElement("form");
	var i_company_id = document.createElement("input");
	var i_del_yn = document.createElement("input");
 
	i_company_id.name="company_id";
	i_company_id.value=company_id;
	i_del_yn.name="del_yn";
	i_del_yn.value=del_yn;
    formObj.appendChild(i_company_id);
    formObj.appendChild(i_del_yn);
    document.body.appendChild(formObj); 
 
    formObj.method="post";

    formObj.action="${contextPath}/munbanggu/admin/company/deleteCompany.do";
    formObj.submit(); 
}



function fn_company_detail(order_id){
	//alert(order_id);
	var frm_delivery_list=document.frm_delivery_list;
	

	var formObj=document.createElement("form");
	var i_order_id = document.createElement("input");
	
	i_order_id.name="company_id";
	i_order_id.value=order_id;
	
    formObj.appendChild(i_order_id);
    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="/munbanggu/admin/company/companyDetail.do";
    formObj.submit();
	
}



</script>
</head>
<body onload="onload()">

	<H3>거래처 관리</H3>
	

 <jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="business_registration_number,company_name,representative_name,zipcode,roadAddress,jibunAddress,business_address,business_type,business_item,corporation_registration_number,industry_code,industry_name,telephone,fax,email,license_number,etc"/>
	<jsp:param name="type_captions_streams" value="사업자번호,회사명,대표자성명,우편변호,도로명주소,지번주소,사업장주소지,업태,업목,회사대표번호,회사우편주소,담당자성명,전화번호,팩스번호,대표이메일,거래처등록일,비고"/>
	<jsp:param name="uri" value="${contextPath}/admin/company/adminCompanyMain.do"/>
</jsp:include>



		<div class="clear">
	</div>
	
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
	<jsp:param name="uri" value="${contextPath}/admin/company/adminCompunyMain.do"/>
</jsp:include> 			
		
			<tr align=center bgcolor="#ffcc00">
				<td width=5% > No</td>
				<td >사업자 번호</td>
				<td>거래처상호</td>
				<td>대표명</td>
				<td>사업자주소</td>
				<td>업태</td>
				<td>종목</td>
				
				<td>등록일자</td>
				<!-- <td>탈퇴여부</td>				 -->
				<td> </td>
				<td> </td>
			</tr>
   <c:choose>
     <c:when test="${empty Company_list}">			
			<tr>
		       <td colspan=5 class="fixed">
				  <strong>조회된 회원이 없습니다.</strong>
			   </td>
		     </tr>
	 </c:when>
	 <c:otherwise>
			<c:forEach var="item" items="${Company_list}" varStatus="item_num">
	                         
	            <tr >    
					<td width=5%> 	
					                     
					  <a href="${pageContext.request.contextPath}/admin/company/companyDetail.do?company_id=${item.company_id}">
					     <strong>${item.company_id}</strong>
					  </a>
					</td>
					<td width=10%>
					  <strong>${item.business_registration_number}</strong><br>
					</td>
					<td width=10% >
					  <strong>${item.company_name }</strong><br>
					</td>
					<td width=5% >
					  <strong>${item.representative_name }</strong><br>
					</td>
					<td width=20%>
					  <strong>${item.roadAddress}</strong><br>
					  <strong>${item.jibunAddress}</strong><br>
					  <strong>${item.namujiAddress}</strong><br>
					</td>
					
					<td width=10%>
					  <strong>${item.business_type}</strong><br>
					</td>
					<td width=10%>
					  <strong>${item.business_item}</strong><br>
					</td>
<%-- 					<td width=2%>
					  <strong>${item.corporation_registration_number}</strong><br>
					</td>					
					<td width=2%>
					  <strong>${item.industry_code}</strong><br>
					</td>
					<td width=2%>
					  <strong>${item.industry_name}</strong><br>
					</td>
					<td width=1%>
					  <strong>${item.telephone}</strong><br>
					</td>
					<td width=1%>
					  <strong>${item.fax}</strong><br>
					</td>
					<td width=1%>
					  <strong>${item.email}</strong><br>
					</td>
					<td width=1%>
					  <strong>${item.license_number}</strong><br>
					</td>					 --%>
					<td width=10%>
					 <strong>${item.license_date}</strong><br>
				    </td>
<%-- 				    <td width=5%>
				       <c:choose>
				         <c:when test="${item.del_yn=='N' }">
				           <strong>가입</strong>  
				         </c:when>
				         <c:otherwise>
				           <strong>탈퇴</strong>
				         </c:otherwise>
				       </c:choose>
				    </td> --%>
				    <td width=5%>
			           <%-- <input class="look_up_btn"  type="button" id="${item.company_id}" value="수정" name="del_yn" onClick="${ contextPath }/admin/company/companyDetail.do?company_id=${item.company_id}"   /> --%>
			           <a class="look_up_btn" herf="${pageContext.request.contextPath}/admin/company/companyDetail.do?company_id=${item.company_id}">
			           수정
			           </a>
				    </td>
				    <td width=5%>
 				           <input class="look_up_btn"  type="button" id="${item.company_id}" value="삭제" name="btn_add" onClick="fn_delete_companyinfo('${item.company_id }')"   />
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
	<jsp:param name="uri" value="${contextPath}/admin/order/adminCompunyMain.do"/>
</jsp:include> 		


         <tr>
             <td >
			<input class="look_up_btn" type="button" id="${item.company_id}" value="거래처 추가" name="btn_add" onClick="fn_add_company()"   />
           </td>
        </tr>
		</tbody>
	</table>
	<div class="clear"></div>

</body>
</html>
