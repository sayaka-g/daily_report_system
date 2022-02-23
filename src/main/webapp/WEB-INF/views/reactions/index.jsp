<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRea" value="${ForwardConst.ACT_REA.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>いいねした人　一覧</h2>
        <table id="reaction_list">
            <tbody>
                <tr>
                    <th class="reaction_name">氏名</th>
                    <th class="reaction_date">日時</th>
                </tr>
                <c:forEach var="reaction" items="${reactions}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="reaction_name"><c:out value="${reaction.employee.name}" /></td>
                        <fmt:parseDate value="${reaction.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                        <td class="reaction_date"><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${reactions_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reactions_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actRea}&command=${commIdx}&id=${report.id}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a><p>

    </c:param>
</c:import>