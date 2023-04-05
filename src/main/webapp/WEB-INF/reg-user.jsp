<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String regMessage = (String) request.getAttribute( "reg-message" ) ;
%>

<% if( regMessage == null ) { %>
    <h4>Введіть облікові дані:</h4>
<% } else if( "OK".equals( regMessage ) ) { %>
    <h4>Реєстрація успішна.</h4>
<% } else { %>
    <h4>Помилка реєстрації: <%= regMessage %></h4>
<% } %>


<div class="row">
    <form class="col s10 offset-s1 m8 offset-m2 l6 offset-l3" method="post" enctype="multipart/form-data">
        <div class="row input-field"><i class="material-icons prefix">person</i>
            <input id="user-login" type="text" name="user-login">
            <label for="user-login">Login</label>
        </div>
        <div class="row input-field"><i class="material-icons prefix">person_outline</i>
            <input id="user-name" type="text" name="user-name">
            <label for="user-name">Real Name</label>
        </div>
        <div class="row input-field"><i class="material-icons prefix">lock_outline</i>
            <input id="user-pass1" type="password" name="user-pass1">
            <label for="user-pass1">Type Password</label>
        </div>
        <div class="row input-field">
        <i class="material-icons prefix">lock_open</i>
            <input id="user-pass2" type="password" name="user-pass2">
            <label for="user-pass2">Repeat Password</label>
        </div>
        <div class="row input-field"><i class="material-icons prefix">mail_outline</i>
            <input id="user-email" type="email" name="user-email">
            <label for="user-email">Email</label>
        </div>
        <div class="row input-field file-field">
            <input type="file" name="user-avatar">
            <div class="file-path-wrapper no-padding">
                <i class="material-icons prefix">account_box</i>
                <input class="file-path validate" type="text" placeholder="Select Avatar">
            </div>
       </div>
       <div class="row input-field right-align">
            <button class="btn waves-effect waves-teal" type="submit">REGISTER<i class="material-icons right">exit_to_app</i></button>
       </div>
    </form>
</div>