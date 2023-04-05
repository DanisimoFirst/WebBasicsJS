<%@ page import="itstep.learning.data.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User profileUser = (User) request.getAttribute( "profileUser" ) ;
    String contextPath = request.getContextPath() ;
%>
<div class="row">
<div class="col l8 m10 s12">
    <h2 class="header">Personal profile</h2>
    <div class="card horizontal">
        <div class="card-image">
            <img style="max-width: 90%"
                 src="<%= contextPath %>/image/<%= profileUser.getAvatar() %>"/>
        </div>
        <div class="card-stacked">
            <div class="card-content">
                <p>Login: <%= profileUser.getLogin() %></p>
                <p>Real Name: <%= profileUser.getName() %></p>
                <p>Email: <%= profileUser.getEmail() %></p>
                <p>Active from: <%= profileUser.getRegDt() %></p>
            </div>
            <div class="card-action">
                <a href="mailto:<%= profileUser.getEmail() %>">Send Message</a>
            </div>
        </div>
    </div>
</div>
</div>