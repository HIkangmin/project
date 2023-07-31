<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%

/* 	사용예시 
<table>
<tbody> // <tr> 로 시작해서 </tr> 로 끝남


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


</table>
</tbody>

*/


int section = 0 ;
int pageNum = 1 ;
int maxPage = 1 ;
String beginDate = "" ;
String endDate = "" ;
String search_type = "" ;
String search_word = "" ;
String uri = request.getParameter( "uri" ) ;

try
{
	if ( ( String ) request.getParameter( "section" ) != null )
	{
		section = Integer.parseInt( request.getParameter( "section" ).toString( ) ) ;
	}
	if ( ( String ) request.getParameter( "pageNum" ) != null )
	{
		pageNum = Integer.parseInt( request.getParameter( "pageNum" ).toString( ) ) ;
	}
	if ( ( String ) request.getParameter( "maxPage" ) != null )
	{
		pageNum = Integer.parseInt( request.getParameter( "maxPage" ).toString( ) ) ;
	}
} catch ( NumberFormatException e )
{
	section = 0 ;
	pageNum = 1 ;
	maxPage = 1 ;
}

beginDate = request.getParameter( "beginDate" ) ;
endDate = request.getParameter( "endDate" ) ;
search_type = request.getParameter( "search_type" ) ;
search_word = request.getParameter( "search_word" ) ;
uri = request.getParameter( "uri" ) ;


%>
<tr>
	<td colspan=12 class="fixed">
		<c:forEach var="page" begin="${ section * 10 + 1 }" end="${ section * 10 + 10 }" step="1">
			<c:if test="${(section >= 1) and( page == (section * 10 + 1 ))}">
				<a href="${uri}?section=${section - 1}&pageNum=${ section*10 }&pageNum=${page}&beginDate=${beginDate}&endDate=${endDate}&search_type=${search_type}&search_word=${search_word}">&nbsp; 이전 &nbsp;</a>
			</c:if>
			<c:choose>
				<c:when test="${ pageNum == page }">
					<a style="color: #a5a5a5;"> ${ page } </a>
				</c:when>
				<c:when test="${ page <= maxPage }">
					<a href="${uri}?section=${section}&pageNum=${page}&beginDate=${beginDate}&endDate=${endDate}&search_type=${search_type}&search_word=${search_word}"> ${ page } </a>
				</c:when>
			</c:choose>
			<c:if test="${ page == (section * 10 + 10)  and  section * 10 + 10  <= maxPage and page < maxPage }">
				<a href="${uri}?section=${section + 1}&pageNum=${(section + 1)*10 + 1}&pageNum=${page}&beginDate=${beginDate}&endDate=${endDate}&search_type=${search_type}&search_word=${search_word}">&nbsp; 다음</a>
			</c:if>
		</c:forEach>
	</td>
</tr>
