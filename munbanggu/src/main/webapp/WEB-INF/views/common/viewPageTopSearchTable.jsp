<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<%
String beginDate = "";
String endDate = "";
String search_type = "";
String search_word = "";
String uri = "";

String type_tags_streams = "";
String[] search_type_tags;

String type_captions_streams = "";
String[] search_type_captions;

String beginYear = "";
String beginMonth = "";
String beginDay = "";
String endYear = "";
String endMonth = "";
String endDay = "";

String[] beginDate1;
String[] endDate1;

beginDate = request.getParameter("beginDate");
System.out.println("viewPageTopSearchTable beginDate " + beginDate);
endDate = request.getParameter("endDate");
search_type = request.getParameter("search_type");
search_word = request.getParameter("search_word");
uri = request.getParameter("uri");

type_tags_streams = request.getParameter("type_tags_streams");
type_captions_streams = request.getParameter("type_captions_streams");

System.out.println("viewPageTopSearchTable type_tags_streams " + type_tags_streams);
System.out.println("viewPageTopSearchTable type_captions_streams " + type_captions_streams);


beginDate1 = beginDate.split("-");
endDate1 = endDate.split("-");
search_type_tags = type_tags_streams.split(",");
search_type_captions = type_captions_streams.split(",");
System.out.println("viewPageTopSearchTable search_type_tags " + search_type_tags.length);
System.out.println("viewPageTopSearchTable search_type_captions " + search_type_captions[0]);
System.out.println("viewPageTopSearchTable search_word " + search_word);

beginYear = beginDate1[0];
beginMonth = beginDate1[1];
beginDay = beginDate1[2];
endYear = endDate1[0];
endMonth = endDate1[1];
endDay = endDate1[2];
System.out.println("viewPageTopSearchTable beginDate1 " + beginDate1[0]);
System.out.println("viewPageTopSearchTable endDate1 " + endDate1[0]);

/* 	사용예시

 <jsp:include page="/WEB-INF/views/common/viewPageTopSearchTable.jsp" flush="false">
	<jsp:param name="beginDate" value="${beginDate}"/>
	<jsp:param name="endDate" value="${endDate}"/>
	<jsp:param name="search_type" value="${search_type}"/>
	<jsp:param name="search_word" value="${search_word}"/>
	<jsp:param name="type_tags_streams" value="order_id,orderer_name,orderer_hp,receiver_name,goods_title"/>
	<jsp:param name="type_captions_streams" value="주문번호,주문자 성함,주문자 전화번호,수령자 성함,주문상품명"/>
	<jsp:param name="uri" value="${contextPath}/admin/order/adminOrderMain.do"/>
</jsp:include>

*/
%>

<script type="text/javascript">
	function search_period(fixedSearchPeriod) {
		var formObj = document.createElement("form");
		var i_fixedSearch_period = document.createElement("input");
		var i_section = document.createElement("input");
		var i_pageNum = document.createElement("input");

		formObj.style.visibility = 'hidden';

		i_fixedSearch_period.name = "fixedSearchPeriod";
		i_fixedSearch_period.value = fixedSearchPeriod;
		i_section.name = "section";
		i_section.value = "0";
		i_pageNum.name = "pageNum";
		i_pageNum.value = "1";
		formObj.appendChild(i_fixedSearch_period);
		formObj.appendChild(i_section);
		formObj.appendChild(i_pageNum);
		document.body.appendChild(formObj);

		formObj.method = "get";
		formObj.action = "${uri}";

		alert(i_fixedSearch_period.value);
		formObj.submit();
	}

	//조회 버튼 클릭 시 수행	
	function search_keyword() {
		var search_frm = document.search_frm;
		beginYear = search_frm.beginYear.value;
		beginMonth = search_frm.beginMonth.value;
		beginDay = search_frm.beginDay.value;
		endYear = search_frm.endYear.value;
		endMonth = search_frm.endMonth.value;
		endDay = search_frm.endDay.value;

		search_type = search_frm.s_search_type.value;
		search_word = search_frm.t_search_word.value;

		var formObj = document.createElement("form");
		var i_command = document.createElement("input");
		var i_beginDate = document.createElement("input");
		var i_endDate = document.createElement("input");
		var i_search_type = document.createElement("input");
		var i_search_word = document.createElement("input");

		formObj.style.visibility = 'hidden';

		//alert("beginYear:"+beginYear);
		//alert("endDay:"+endDay);
		//alert("search_type:"+search_type);
		//alert("search_word:"+search_word);

		i_command.name = "command";
		i_beginDate.name = "beginDate";
		i_endDate.name = "endDate";
		i_search_type.name = "search_type";
		i_search_word.name = "search_word";

		i_command.value = "list_detail_order_goods";
		i_beginDate.value = beginYear + "-" + beginMonth + "-" + beginDay;
		i_endDate.value = endYear + "-" + endMonth + "-" + endDay;
		i_search_type.value = search_type;
		i_search_word.value = search_word;

		formObj.appendChild(i_command);
		formObj.appendChild(i_beginDate);
		formObj.appendChild(i_endDate);
		formObj.appendChild(i_search_type);
		formObj.appendChild(i_search_word);
		document.body.appendChild(formObj);
		formObj.method = "get";
		formObj.action = "${uri}"

		formObj.submit();
		alert("submit");

	}
</script>
<form name="search_frm" >
	<table cellpadding="10" cellspacing="10">
		<tr>
			<td>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('one_day')">1 일</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('two_day')">2 일</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('three_day')">3 일</button>
				|
				<button class="look_up_btn" type="button" onClick="javascript:search_period('one_week')">1 주</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('two_week')">2 주</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('three_week')">3 주</button>
				|
				<button class="look_up_btn" type="button" onClick="javascript:search_period('one_month')">1 개월</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('two_month')">2 개월</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('three_month')">3 개월</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('four_month')">4 개월</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('six_month')">6 개월</button>
				<button class="look_up_btn" type="button" onClick="javascript:search_period('twelve_month')">12 개월</button>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				조회 기간 :
				<input type="text" size="4" name="beginYear" value="${beginYear}" />
				년
				<input type="text" size="4" name="beginMonth" value="${beginMonth}" />
				월
				<input type="text" size="4" name="beginDay" value="${beginDay}" />
				일 &nbsp; ~ &nbsp;
				<input type="text" size="4" name="endYear" value="${endYear}" />
				년
				<input type="text" size="4" name="endMonth" value="${endMonth}" />
				월
				<input type="text" size="4" name="endDay" value="${endDay}" />
				일
			</td>
		</tr>
		<tr>
			<td>
				<select name="s_search_type">
					<option value="all" ${search_type == 'all' ? 'selected' : ''}>전체</option>

					<c:set var="search_type_tags" value="<%=search_type_tags%>" />
					<c:set var="search_type_captions" value="<%=search_type_captions%>" />
					<c:forEach var="idx" begin="0" end="<%=search_type_tags.length - 1%>">
						<option value="${search_type_tags[ idx ]}" ${search_type == search_type_tags[ idx ] ? 'selected' : ''}>${search_type_captions[idx]}</option>
					</c:forEach>

				</select>
				<input type="text" size="30" name="t_search_word" onkeypress="if( event.keyCode == 13 ){search_keyword();}" />
				<input type="button" value="조회" name="btn_search" onClick="javascript:search_keyword()" />
			</td>
		</tr>

	</table>
</form>