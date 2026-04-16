<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Main</title>
</head>
<body>
Hello ${user}!
<form action="controller" method="GET">
        <input type="hidden" name="command" value="logout">

        <input type="submit" name="sub" value="Logout">

        <div class="error-msg">
            ${logout_msg}
        </div>
    </form>
</body>
</html>