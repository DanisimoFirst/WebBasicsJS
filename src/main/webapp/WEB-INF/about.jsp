<%@ page import="itstep.learning.model.AboutModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    AboutModel model = (AboutModel) request.getAttribute( "data" ) ;
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>About</h1>
<p>
    From servlet: <%= model.getMessage() %> <br/>
    <%= model.getMoment().toString() %>
</p>
</body>
</html>
