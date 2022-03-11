<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="ActAtt" value="${ForwardConst.ACT_ATT.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <fmt:parseDate value="${today}" pattern="yyyy-MM-dd" var="today" type="date" />
        <h2><fmt:formatDate value='${today}' pattern='yyyy年M月' /> 出退勤ページ</h2>
        <div id="attendance">
            <div id="today"><h3><fmt:formatDate value='${today}' pattern='yyyy年M月d日' /></h3></div>

            <c:if test="${clock_in_flag == false}">
                <form method="POST" action="<c:url value='?action=${ActAtt}&command=${commCrt}' />">
                    <button type="submit">出勤</button>
                </form>
            </c:if>
            <c:if test="${clock_in_flag == true && clock_out_flag == false}">
                <form method="POST" action="<c:url value='?action=${ActAtt}&command=${commUpd}' />">
                    <button type="submit">退勤</button>
                </form>
            </c:if>
        </div>
        <br />
        <table id="attendance_list">
            <tbody>
                <tr>
                    <th class="attendance_date">日</th>
                    <th class="attendance_in">出勤</th>
                    <th class="attendance_out">退勤</th>
                </tr>
                <c:forEach var="attendance" items="${attendances}" varStatus="status">
                    <fmt:parseDate value="${attendance.workDate}" pattern="yyyy-MM-dd" var="workDate" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="attendance_date"><fmt:formatDate value='${workDate}' pattern='d' /></td>
                        <td class="attendance_in"><c:out value="${attendance.clockedIn}" /></td>
                        <td class="attendance_out"><c:out value="${attendance.clockedOut}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br />
    </c:param>
</c:import>