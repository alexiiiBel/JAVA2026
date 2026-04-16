<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" type="text/css" href="./main/resources/style.css">
</head>
<body>

<div>
    <h1><%= "Hello World!" %></h1>

    <form action="controller" method="GET">
        <input type="hidden" name="command" value="add_User">

        <label>Login:</label>
        <input type="text" name="login" placeholder="Введите логин">

        <label>Password:</label>
        <input type="password" name="pass" placeholder="Введите пароль">

        <input type="submit" name="sub" value="Push">

        <div class="error-msg">
            ${login_msg}
        </div>
    </form>
</div>

<script src="./main/resources/script.js"></script>
</body>
</html>