<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<c:choose>
<c:when test='${modified_personal_info==true }'>
<script>
window.onload=function()
{
  test();
}

function test(){
	init();
	alert("회원 정보가 수정되었습니다.");
}
function init(){
 
}

</script>
</c:when>
<c:otherwise>
<script>
window.onload=function()
{
  test();
}

function test(){
	init();
	//alert("회원 정보가 수정되었습니다.");
//	init();
}
function init(){
 
}
</script>
</c:otherwise>
</c:choose>
 <script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 도로명 조합형 주소 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('roadAddress').value = fullRoadAddr;
                document.getElementById('jibunAddress').value = data.jibunAddress;

                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    //예상되는 도로명 주소에 조합형 주소를 추가한다.
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    document.getElementById('guide').innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    document.getElementById('guide').innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';

                } else {
                    document.getElementById('guide').innerHTML = '';
                }
            }
        }).open();
    }
</script>
<script>
function fn_modify_company_info(company_id,mod_type,mod_val){
	var value="";

	 
		var frm_mod_company=document.frm_mod_company;
		if(mod_type=='license_date'){
			value=frm_mod_company.license_date.value;

		}else if(mod_type=='address'){
			var zipcode=frm_mod_company.zipcode;
			var roadAddress=frm_mod_company.roadAddress;
			var jibunAddress=frm_mod_company.jibunAddress;
			var namujiAddress=frm_mod_company.namujiAddress;
			
			value_zipcode=zipcode.value;
			value_roadAddress=roadAddress.value;
			value_jibunAddress=jibunAddress.value;
			value_namujiAddress=namujiAddress.value;
			
			value=value_zipcode+","+value_roadAddress+","+value_jibunAddress+","+value_namujiAddress;
		}else{
			value= mod_val;
		}
	 

		$.ajax({
			type : "post",
			async : false, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/munbanggu/admin/company/modifyCompanyInfo.do",
			data : {
				company_id:company_id,
				mod_type:mod_type,
				value:value
			},
			success : function(data, textStatus) {
				if(data.trim()=='mod_success'){
					alert("거래처 정보를 수정했습니다.");
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

function fn_delete_company(company_id,mod_type,del_yn){
	var frm_mod_company=document.frm_mod_company;
	var value = "";
	if(mod_type=='del_yn'){
		if(frm_mod_company.del_yn.value=='N'){
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
function fn_add_company(){
	var frm_mod_company=document.frm_mod_company;
	var value="";

	var formObj=document.createElement("form");
	var i_company_id = document.createElement("input");
	var i_business_registration_number = document.createElement("input");
	var i_company_name = document.createElement("input");
	var i_representative_name = document.createElement("input");
	var i_business_address = document.createElement("input");
	var i_business_type = document.createElement("input");
	var i_business_item = document.createElement("input");
	var i_corporation_registration_number = document.createElement("input");
	var i_industry_code = document.createElement("input");
	var i_industry_name = document.createElement("input");
	var i_telephone = document.createElement("input");
	var i_fax = document.createElement("input");
	var i_email = document.createElement("input");
	var i_license_number = document.createElement("input");
	var i_license_date = document.createElement("input");
	var i_del_yn = document.createElement("input");

 
	i_company_id.name="company_id";
	i_company_id.value=0;
	
	i_business_registration_number.name="business_registration_number";
	i_business_registration_number.value=frm_mod_company.business_registration_number.value;
	
	i_company_name.name="company_name";
	i_company_name.value=frm_mod_company.company_name.value;
	i_representative_name.name="representative_name";
	i_representative_name.value=frm_mod_company.representative_name.value;
	i_business_address.name="business_address";
	i_business_address.value=frm_mod_company.business_address.value;
	i_business_type.name="business_type";
	i_business_type.value=frm_mod_company.business_type.value;
	i_business_item.name="business_item";
	i_business_item.value=frm_mod_company.business_item.value;
	i_corporation_registration_number.name="corporation_registration_number";
	i_corporation_registration_number.value=frm_mod_company.corporation_registration_number.value;
	i_industry_code.name="industry_code";
	i_industry_code.value=frm_mod_company.industry_code.value;
	i_industry_name.name="industry_name";
	i_industry_name.value=frm_mod_company.industry_name.value;
	i_telephone.name="telephone";
	i_telephone.value=frm_mod_company.telephone.value;
	i_fax.name="fax";
	i_fax.value=frm_mod_company.fax.value;
	
 
	i_email.name="email";
	i_email.value=frm_mod_company.email.value;
	i_license_number.name="license_number";
	i_license_number.value=frm_mod_company.license_number.value;
	i_license_date.name="license_date";
	i_license_date.value=frm_mod_company.license_date.value;
	i_del_yn.name="del_yn";
	i_del_yn.value=frm_mod_company.del_yn.value;
	
    formObj.appendChild(i_company_id);
    formObj.appendChild(i_business_registration_number);
    
    formObj.appendChild(i_company_name);
    formObj.appendChild(i_representative_name);
    formObj.appendChild(i_business_address);
    formObj.appendChild(i_business_type);
    formObj.appendChild(i_business_item);
    formObj.appendChild(i_corporation_registration_number);
    formObj.appendChild(i_industry_code);
    formObj.appendChild(i_industry_name);
    formObj.appendChild(i_telephone);
    formObj.appendChild(i_fax);
    formObj.appendChild(i_email);
    formObj.appendChild(i_license_number);
    formObj.appendChild(i_license_date);
    formObj.appendChild(i_del_yn); 
    document.body.appendChild(formObj); 

    formObj.method="post";

    formObj.action="${contextPath}/munbanggu/admin/company/addCompanyInfo.do";
    formObj.submit();

}
</script>
</head>

<body>
	<h3>거래처 상세 정보</h3>
<form name="frm_mod_company" action="${contextPath}/munbanggu/admin/company/modifyCompany.do" method="post"  >	
	<div id="detail_table">
		<table>
			<tbody>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">company_id</td>
					<td>
						<input name="company_id" type="text" size="30" value="${company_info.company_id }"  />
					</td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','company_id')" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">사업자등록번호</td>
					<td>
						<input name="business_registration_number" type="text" size="30" value="${company_info.business_registration_number }"  />
					</td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','business_registration_number',business_registration_number.value)" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">거래처상호</td>
					<td>
					  <input name="company_name" type="text" size="30" value="${company_info.company_name }" />
					</td>
					<td>
					  <input type="button" value="수정하기" onClick="fn_modify_company_info('${company_info.company_id }','company_name', company_name.value)" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">대표자명</td>
					<td>
					  <input name="representative_name" type="text" size="30" value="${company_info.representative_name }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','representative_name', representative_name.value  )" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">사업자주소</td>
					<td>
					   <input type="text" id="zipcode" name="zipcode" size=5 value="${company_info.zipcode }" > <a href="javascript:execDaumPostcode()">우편번호검색</a>
					  <br>
					  <p> 
					   지번 주소:<br><input type="text" id="roadAddress"  name="roadAddress" size="50" value="${company_info.roadAddress }"><br><br>
					  도로명 주소: <input type="text" id="jibunAddress" name="jibunAddress" size="50" value="${company_info.jibunAddress }"><br><br>
					  나머지 주소: <input type="text"  name="namujiAddress" size="50" value="${company_info.namujiAddress }" />
					   <span id="guide" style="color:#999"></span>
					   </p>
					</td>
					<td>
					  <input type="button" value="수정하기" onClick="fn_modify_company_info('${company_info.company_id }','address',zipcode.value)" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">업태</td>
					<td>
					  <input name="business_type" type="text" size="30" value="${company_info.business_type }" />
					</td>
					<td>
					  <input type="button" value="수정하기" onClick="fn_modify_company_info('${company_info.company_id }','business_type',business_type.value)" />
					</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">종목</td>
					<td>
					  <input name="business_item" type="text" size="30" value="${company_info.business_item }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','business_item',business_item.value)" />
					</td>
				</tr>
 				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">법인등록번호</td>
					<td>
					  <input name="corporation_registration_number" type="text" size="30" value="${company_info.corporation_registration_number }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','corporation_registration_number',corporation_registration_number.value)" />
					</td>
				</tr>
 				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">전화번호</td>
					<td>
					  <input name="telephone" type="text" size="30" value="${company_info.telephone }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','telephone',telephone.value)" />
					</td>
				</tr>
 				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">팩스</td>
					<td>
					  <input name="fax" type="text" size="30" value="${company_info.fax }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','fax',fax.value)" />
					</td>
				</tr>				
 				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">이메일</td>
					<td>
					  <input name="email" type="text" size="30" value="${company_info.email }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','email',email.value)" />
					</td>
				</tr>	
 				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">인허가번호</td>
					<td>
					  <input name="license_number" type="text" size="30" value="${company_info.license_number }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','license_number',license_number.value)" />
					</td>
				</tr>	
 
  				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">등록일자</td>
					<td>
					  <input name="license_date" type="date" size="30" value="${company_info.license_date }"   />
					 </td>
					 <td>
					  <input type="button" value="수정하기"  onClick="fn_modify_company_info('${company_info.company_id }','license_date',license_date.value)" />
					</td>
				</tr>	

				<tr class="dot_line">
					<td class="fixed_join" style="font-size : 14px;">탈퇴여부</td>
					<td>
					  <c:choose >
					    <c:when test="${company_info.del_yn  =='N' }">
					      <input type="radio" name="del_yn" value="Y" />
						  Y&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   <input type="radio" name="del_yn" value="N" checked />N
					    </c:when>
					    <c:otherwise>
					      <input type="radio" name="del_yn" value="Y"  checked />
						   Y&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					      <input type="radio" name="del_yn" value="N"  />N
					   </c:otherwise>
					   </c:choose>
					</td>
					<td>
					  <input type="button" value="수정하기" onClick="fn_modify_company_info('${company_info.company_id }','del_yn',del_yn.value)" />
					</td>
				</tr>
				
 
			</tbody>
		</table>
		</div>
		<div class="clear">
		<br><br>
		<table align=center>
		<tr>
			<td >
				<input type="hidden" name="command"  value="modify_my_info" /> 
				<c:choose>
				  <c:when test="${company_info.del_yn=='Y' }">
				    <input  type="button"  value="복원" onClick="fn_delete_company('${item.company_id }','del_yn','Y')"   />  
				  </c:when>
				  <c:when  test="${company_info.del_yn=='N' }">
				    <input  type="button"  value="탈퇴" onClick="fn_delete_company('${item.company_id }','del_yn','Y')"   />
				  </c:when>
				  
				</c:choose>
				
			</td>
			<td >
 
				    <button type="submit" >저장</button>
 
				
			</td>
		</tr>
	</table>
	</div>
 
</form>	
</body>
</html>
