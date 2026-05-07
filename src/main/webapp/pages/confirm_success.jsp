<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="fragments/locale_setup.jsp" %>
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="confirm.success.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="auth-page">
<div class="card card--centered">
    <div class="empty-icon u-mb-md">&#9989;</div>
    <h2 class="u-mb-md"><fmt:message key="confirm.success.heading"/></h2>
    <p class="u-mb-lg"><fmt:message key="confirm.success.text"/></p>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
        <fmt:message key="confirm.success.login"/>
    </a>
</div>
</body>
</html>
