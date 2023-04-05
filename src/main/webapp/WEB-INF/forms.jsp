<%@ page import="itstep.learning.model.FormsModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    FormsModel model = (FormsModel) request.getAttribute( "formsModel" ) ;
    String path = request.getContextPath() ;
%>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Forms</title>
</head>
<style>
    .container{
        width: 80%;
        border: 1px solid green;
    }
    .d-flex{
        display: flex;
    }
    .f-col{
        display: flex;
        flex-direction: column;
    }
    .mb-2{
        margin-bottom: 2em;
    }
    .m-auto{
        margin: auto;
    }
    .maxW-200{
        max-width: 200px;
    }
    .minH-200{
        min-height: 200px;
    }
</style>
<body>
<div class="d-flex">
    <div>
        <form action="<%= path %>/forms" method="post" class="f-col mb-2 maxW-200">
            <label>Text:   <input name="text"   type="text"></label>
            <label>Number: <input name="number" type="number" step="0.01"></label>
            <input name="date"   type="date">
            <input name="color"  type="color">
            <input type="submit" value="Post Form">
        </form>
        <form action="<%= path %>/forms" method="get" class="f-col mb-2 maxW-200">
            <label>Text:   <input name="text"   type="text"></label>
            <label>Number: <input name="number" type="number" step="0.01"></label>
            <input name="date"   type="date">
            <input name="color"  type="color">
            <input type="submit" value="Get Form">
        </form>
    </div>
    <div class="container m-auto minH-200" >
        Method = <%= model.getMethod() %> <br>
        Text   = <%= model.getText() %>   <br>
        Number = <%= model.getNumber() %> <br>
        Date   = <%= model.getDate() %>   <br>
        Color  = <%= model.getColor() %>  <br>
    </div>
</div>
</body>
</html>