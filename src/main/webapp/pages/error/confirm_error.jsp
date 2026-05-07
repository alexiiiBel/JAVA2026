<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="../fragments/locale_setup.jsp" %>
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="confirm.error.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="auth-page">
<div class="card card--centered">
    <div class="empty-icon u-mb-md">&#9888;&#65039;</div>
    <h2 class="u-mb-md"><fmt:message key="confirm.error.heading"/></h2>
    <c:choose>
        <c:when test="${confirmationStatus == 'invalid_token'}">
            <div class="alert alert-danger"><fmt:message key="confirm.error.invalid"/></div>
            <a href="${pageContext.request.contextPath}/pages/registration.jsp" class="btn btn-primary">
                <fmt:message key="confirm.error.register"/>
            </a>
        </c:when>
        <c:when test="${confirmationStatus == 'already_confirmed'}">
            <div class="alert alert-warning"><fmt:message key="confirm.error.already"/></div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <fmt:message key="confirm.error.login"/>
            </a>
        </c:when>
        <c:when test="${confirmationStatus == 'expired'}">
            <div class="alert alert-danger"><fmt:message key="confirm.error.expired"/></div>
            <a href="${pageContext.request.contextPath}/pages/registration.jsp" class="btn btn-primary">
                <fmt:message key="confirm.error.register"/>
            </a>
        </c:when>
        <c:otherwise>
            <div class="alert alert-danger"><fmt:message key="confirm.error.default"/></div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <fmt:message key="error.back"/>
            </a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
