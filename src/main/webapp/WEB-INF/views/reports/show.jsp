<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commReact" value="${ForwardConst.CMD_REACT.getValue()}" />
<c:set var="commCreate" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commApprove" value="${ForwardConst.CMD_APPROVE.getValue()}" />
<c:set var="commReject" value="${ForwardConst.CMD_REJECT.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>日報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${report.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${report.content}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${report.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>いいね数</th>
                    <td><c:out value="${report.likeCount}" /></td>
                </tr>
                <tr>
                    <th>承認状況</th>
                    <td><c:choose>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_APPROVED.getIntegerValue()}">承認済</c:when>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_REJECTED.getIntegerValue()}">差戻</c:when>
                            <c:otherwise>承認待</c:otherwise>
                        </c:choose></td>
                </tr>
                <tr>
                    <th><c:choose>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_APPROVED.getIntegerValue()}">承認者</c:when>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_REJECTED.getIntegerValue()}">差戻者</c:when>
                            <c:otherwise>承認者</c:otherwise>
                        </c:choose></th>
                    <td><c:out value="${report.approver.name}" /></td>
                </tr>
                <tr>
                    <th><c:choose>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_APPROVED.getIntegerValue()}">承認日時</c:when>
                            <c:when test="${report.approvalStatus == AttributeConst.STATUS_REJECTED.getIntegerValue()}">差戻日時</c:when>
                            <c:otherwise>承認日時</c:otherwise>
                        </c:choose></th>
                    <fmt:parseDate value="${report.approvedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="approveDay" type="date" />
                    <td><fmt:formatDate value="${approveDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:choose>
            <c:when test="${sessionScope.login_employee.id == report.employee.id}">
                <c:if test="${report.approvalStatus != AttributeConst.STATUS_APPROVED.getIntegerValue()}">
                    <p>
                        <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
                    </p>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:if test="${reactions_count == 0}">
                    <p>
                        <a href="<c:url value='?action=${actRep}&command=${commReact}&id=${report.id}' />">この日報にいいねする</a>
                    </p>
                </c:if>
                <c:if test="${follows_count == 0}">
                    <p>
                        <a href="<c:url value='?action=${actFol}&command=${commCreate}&id=${report.id}' />">この日報の作成者をフォローする</a>
                    </p>
                </c:if>
                <c:if test="${sessionScope.login_employee.positionFlag >= AttributeConst.POSITION_AGM.getIntegerValue()
                        and sessionScope.login_employee.positionFlag >= report.employee.positionFlag
                        and report.approvalStatus == AttributeConst.STATUS_PENDING.getIntegerValue()}">
                    <p>
                        <a href="<c:url value='?action=${actRep}&command=${commApprove}&id=${report.id}' />">この日報を承認する</a>
                    </p>
                    <p>
                        <a href="<c:url value='?action=${actRep}&command=${commReject}&id=${report.id}' />">この日報を差戻す</a>
                    </p>
                </c:if>
            </c:otherwise>
        </c:choose>

        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>