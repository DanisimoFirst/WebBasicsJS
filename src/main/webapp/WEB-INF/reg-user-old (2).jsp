<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    // HashMap   message   = (HashMap  )request.getAttribute("RegMessage");
    // UserModel userModel = (UserModel)request.getAttribute("UserModel" );
%>
<html>
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>
<style>
    @media (max-width: 768px){
        .reg-form{
            width: 80%;
            max-width: 300px;
        }
        .form-group .col{
            height: auto;
        }
        .col{
            flex-direction: column;
        }
        .form-group .col .col-auto{
            width: 100%;
        }
    }
    @media (min-width: 768px){
        .reg-form{
            width: 80%;
            margin: auto;
            max-width: 700px;
        }
        .form-group .col .col-auto{
            width: 25%;
        }
    }
    body{
        background: #2b2b2b;
    }
    label{
        color: #898a86;
    }
    small{
        color: #64ab29;
    }
    .form-control{
        background: #b27642;
        transition: 0.5s;
        /*border:transparent;*/
    }
    .form-control:focus{
        background: #f3aa6f;
        transition: 0.5s;
    }
    input:-webkit-autofill,
    input:-webkit-autofill:hover,
    input:-webkit-autofill:focus,
    input:-webkit-autofill:active{
        -webkit-box-shadow: 0 0 0 30px #f3aa6f inset !important;
    }
    input:not([type="image" i]) {
        border-radius: 0.375em;
        border-width: 2px;
        border-style: inset;
        border-color: rgba(43, 43, 43, 0.85);
    }
    .form-group{
        max-width: 350px;
        margin: auto;
    }
    .invalid-feedback{
        display: block;
    }
</style>
<body>
    <div class="mt-5">
    <form method="post"
          action="<%= path %>/register"
          id="reg_form"
          class="reg-form"
          enctype="multipart/form-data">

        <div class="form-group mb-2">
            <div class="col has-validation">
                <label for="reg_login" class="col-auto">Login:</label>
                <input id="reg_login" class="col form-control" type="text" name="user-login" placeholder="Login" required
                    <%/*=  userModel == null || userModel.getLogin() == null ? ""
                            : "value='" + userModel.getLogin() + "'" */ %>
                />
                <div id="reglogin" class='invalid-feedback'>
                <%/*=  message == null || message.get("login") == null ? "" : message.get("login") */ %>
                </div>
            </div>
            <div class="col has-validation">
                <label for="reg_name" class="col-auto">Name:</label>
                <input id="reg_name" class="form-control col" type="text" name="user-name" placeholder="Name" required
                    <%/*= userModel == null || userModel.getName() == null ? ""
                            : "value='" + userModel.getName() + "'" */%>
                />
                <div id="regname" class='invalid-feedback'>
                <%/*= message == null || message.get("name") == null ? "" : message.get("name") */%>
                </div>
            </div>
            <div class="col has-validation">
                <label for="reg_pass1" class="col-auto">Password:</label>
                <input id="reg_pass1" class="form-control" type="password" name="user-pass1" placeholder="Password" required/>
                <div id="regpass1" class='invalid-feedback'>
                <%/*= message == null || message.get("pass1") == null ? "" : message.get("pass1") */%>
                </div>
            </div>
            <div class="col has-validation">
                <label for="reg_pass2" class="col-auto">Confirm:</label>
                <input id="reg_pass2" class="form-control" type="password" name="user-pass2" placeholder="Confirm" required/>
                <div id="regpass2" class='invalid-feedback'>
                <%/*= message == null || message.get("pass2"  ) == null ?
                    message == null || message.get("confirm") == null ? ""
                        : message.get("confirm")
                        : message.get("pass2") */ %>
                </div>
            </div>
            <div class="col has-validation">
                <label for="reg_email" class="col-auto">Email:</label>
                <input id="reg_email" class="form-control col" type="text" name="user-email" placeholder="Email" required
                    <%/*= userModel == null || userModel.getEmail() == null ? ""
                            : "value='" + userModel.getEmail() + "'" */ %>
                />
                <div id="regemail" class='invalid-feedback'>
                <%/*= message == null || message.get("email") == null ? "" : message.get("email") */%>
                </div>
            </div>
            <div class="col mt-4">
                <label for="ava" class="col-auto"></label>
                <input id="ava" class="form-control col" type="file" id="avatar" name="user-avatar" accept="image/png, image/jpeg"/>
                <span class="col-auto text-danger"></span>
            </div>
        </div>
        <div class="column text-center mt-2">
            <small>Do not use any of these common illegal symbols in fields: </small>
            <small>
                <span class="text-warning">}</span>,
                <span class="text-warning">`</span>,
                <span class="text-warning">'</span>,
                <span class="text-warning">"</span>
            </small>
            <br>
            <small id="emailHelp">
                Fields marked with
                <span class="text-danger">!</span>
                are required
            </small>
        </div>
        <div class="text-center mt-2">
            <button type="submit" class="btn btn-outline-info mb-2">Registration</button>
        </div>
    </form>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const Registration_form = document.querySelector("#reg_form");
            if(Registration_form !== null){
                const email_error      = "Email is incorrect";
                const pass_error       = "Do not match";
                const symbols_error    = "Do not use illegal symbols";
                const short_error      = "To short";
                const form_inputs_text = Registration_form.querySelectorAll('input[type="text"]');
                const form_inputs_pass = Registration_form.querySelectorAll('input[type="password"]');
                const form_submit      = Registration_form.querySelector('button[type="submit"]');
                const validateSymbols = (str) => {
                    return String(str)
                        .toLowerCase()
                        .match(/^[a-zA-Z0-9_ ]+$/);
                }
                const validateEmail = (email) => {
                    return String(email)
                        .toLowerCase()
                        .match(
                            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        );
                }
                const button_solver = () => {
                    if( pass_confirmed    === true &&
                        email_confirmed   === true &&
                        symbols_confirmed === true &&
                        text_inputs_filled_count === 0) {
                        form_submit.disabled = false;
                    }
                    else if(form_submit.disabled === false){
                        form_submit.disabled = true;
                    }
                }
                const feedback_init = (element) => {
                    let str = element.id.replace('_', '');
                    feedback = document.querySelector(`#${str}`);
                }
                var feedback = null;
                var pass_confirmed    = false;
                var email_confirmed   = false;
                var symbols_confirmed = false;
                var text_inputs_filled_count = form_inputs_text.length;
                form_inputs_text.forEach(element => {
                    console.log(element.value + " " + element.value.length);
                    if(element.value.length > 2){
                        element.classList.add("is-valid");
                    }
                    feedback_init(element);
                    let length = element.value.length;
                    if(length > 0){
                        text_inputs_filled_count--;
                        feedback.innerText = "";
                        if(text_inputs_filled_count === 0){
                            email_confirmed = true;
                            symbols_confirmed = true;
                            from_redirect = true;
                        }
                    }
                    element.oninput = (e) => {
                        feedback_init(element);
                        //console.log(length);
                        if(element.value.length > 0){
                            if(element.id === "reg_email" && validateEmail(element.value)){
                                element.classList.remove("is-invalid");
                                element.classList.add("is-valid");
                                feedback.innerText = "";
                                email_confirmed = true;
                            }
                            else if(element.id === "reg_email" && !validateEmail(element.value)){
                                element.classList.add("is-invalid");
                                feedback.innerText = email_error;
                                email_confirmed = false;
                            }
                            else if(element.value.length < 3){
                                element.classList.add("is-invalid");
                                feedback.innerText = short_error;
                                symbols_confirmed = false;
                            }
                            else if(element.value.length > 2 && !validateSymbols(element.value)){
                                element.classList.add("is-invalid");
                                feedback.innerText = symbols_error;
                                symbols_confirmed = false;
                            }
                            else {
                                element.classList.remove("is-invalid");
                                element.classList.add("is-valid");
                                feedback.innerText = "";
                                symbols_confirmed = true;
                            }
                            if((element.value.length > 0 || e.inputType === "insertFromPaste")
                                && e.inputType !== "deleteContentForward" && length === 0){
                                text_inputs_filled_count--;
                            }
                        }
                        else if(element.value.length === 0) {
                            feedback.innerText = "";
                            text_inputs_filled_count++;
                        }
                        length = element.value.length;
                        button_solver();
                    };
                });
                form_inputs_pass.forEach(element => {
                    element.oninput = (e) => {
                        feedback_init(form_inputs_pass[1]);
                        if(form_inputs_pass[0].length > 0){
                            form_inputs_pass[1].disabled = false;
                        }
                        else if(form_inputs_pass[1].disabled = false){
                            form_inputs_pass[1].disabled = true;
                        }
                        if((element.value.length > 0 && form_inputs_pass[0].value) !== form_inputs_pass[1].value){
                            form_inputs_pass[0].classList.add("is-invalid");
                            form_inputs_pass[1].classList.add("is-invalid");
                            feedback.innerText = pass_error;
                            pass_confirmed = false;
                        }
                        else{
                            form_inputs_pass[0].classList.remove("is-invalid");
                            form_inputs_pass[0].classList.add("is-valid");
                            form_inputs_pass[1].classList.remove("is-invalid");
                            form_inputs_pass[1].classList.add("is-valid");
                            pass_confirmed = true;
                            feedback.innerText = "";
                        }
                        button_solver();
                    };
                });
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</body>
</html>
