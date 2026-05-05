<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration / Login</title>
    <link rel="stylesheet" type="text/css" href="./main/resources/style.css">
</head>
<body>

<div>
    <h1>Registration</h1>

    <form action="controller" method="GET">
        <input type="hidden" name="command" value="add_user">

        <label>Login:</label>
        <input type="text" name="login" placeholder="Enter login" required>

        <label>Email:</label>
        <input type="email" name="email" placeholder="Enter email" required>

        <label>Password:</label>
        <input type="password" name="pass" placeholder="Enter password" required>

        <input type="submit" value="Register">

        <div class="error-msg">
            ${login_msg}
        </div>

        <c:if test="${not empty validation_errors}">
            <ul class="error-msg">
                <c:forEach var="error" items="${validation_errors}">
                    <li>${error.message}</li>
                </c:forEach>
            </ul>
        </c:if>
    </form>

    <hr>

    <h2>Login</h2>
    <form action="controller" method="GET">
        <input type="hidden" name="command" value="login">

        <label>Login:</label>
        <input type="text" name="login" placeholder="Enter login" required>

        <label>Password:</label>
        <input type="password" name="pass" placeholder="Enter password" required>

        <input type="submit" value="Login">
    </form>
</div>

<script src="./main/resources/script.js"></script>
</body>
</html>
