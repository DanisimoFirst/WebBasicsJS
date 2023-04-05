<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    <h1>Hello JSP</h1>

<% for( int i = 1; i <= 10; ++i ) { %>
<p>
    <%= i %>
</p>
<% } %>
Д.З. Установить веб-сервер (один из Tomcat, WildFly, Glassfish)
Создать веб-проект (Maven web-app)
Настроить конфигурацию запуска и проверить работоспособность
</body>
</html>
