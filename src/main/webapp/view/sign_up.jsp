<!DOCTYPE html>
<html>
<head>
    <title>TaskManager</title>
    <style>
        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
        }


        input[type=submit] {
            background-color: #194a47;
            color: white;
        }


        .container {
            background-color: #f1f1f1;
            padding: 20px;
        }


    </style>
    <script>
        function checkPass() {
            if(document.getElementById("password").value !== document.getElementById("passCh").value) {
                alert("Passwords must match");
                return false;
            }
            else return true;
        }
    </script>
</head>
<body>
<div class = "container">
    <h1>Registration</h1>
    <form  action = "${pageContext.request.contextPath}/well" method = "POST" onsubmit="return checkPass()">
        <label for="username">Create your login</label>
        <input id="username" type="text" name = "username" pattern ="(?=.*[A-Za-z0-9]).{6,20}"
               title = "Must contain from 6 to 20 characters" required>
        <label for="password">Create your password</label>
        <input id="password" type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>

        <label for="passCh">Retype your password</label>
        <input id="passCh" type="password" name = "passCh" required>
        <input type="submit" value="Sign up">
    </form>
</div>
</body>
</html>
