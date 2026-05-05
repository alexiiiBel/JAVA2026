<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка сервера (500)</title>
    <style>
        body { text-align: center; padding: 10%; font-family: sans-serif; background-color: #f9f9f9; }
        .container { display: inline-block; text-align: left; max-width: 600px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #c0392b; margin-top: 0; }
        .details { font-family: monospace; background: #eee; padding: 10px; font-size: 12px; overflow-x: auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Ошибка сервера</h1>
        <p>На сервере что-то пошло не так. Наши инженеры уже уведомлены.</p>

        <%-- Код ниже полезен для отладки, но на "боевом" сайте его лучше скрыть --%>
        <div class="details">
            <b>Статус код:</b> <%= request.getAttribute("javax.servlet.error.status_code") %><br>
            <b>Тип ошибки:</b> <%= exception != null ? exception.getClass().getName() : "Неизвестно" %>
        </div>

        <p><a href="javascript:history.back()">Вернуться назад</a></p>
    </div>
</body>
</html>