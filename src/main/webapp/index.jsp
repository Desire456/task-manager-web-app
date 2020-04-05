<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Simple web application</title>
    <style>
        #modal {
            padding: 50px;
            position: fixed;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }

        input, button {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
        }
    </style>
    <script>
        function direct() {
            window.location.href = "sign_up";
        }
        function showError(){
            <%String error = (String) request.getAttribute("error");
            if (error != null) {%>
            let msgError = "<%=error%>";
            alert(msgError);
            <%}%>
        }
    </script>
</head>
<body onload = "showError()">
<div id="modal">
    <form id="login" action="${pageContext.request.contextPath}/journals" method="POST">
        <h1>Task Manager</h1>
        <input name="login" type="text" placeholder="Login" required>
        <label for="password"></label>
        <input name = "password" id="password" type="password" placeholder="Password" required>
        <input type="submit" id="submit" value="Sign in">
        <button onclick="direct()">Sign up</button>
    </form>
</div>
</body>
</html>