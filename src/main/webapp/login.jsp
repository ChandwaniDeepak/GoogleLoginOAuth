<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>

    <script src="./script/get-user.js" type="text/javascript"></script>

    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id" content="YOUR_CLIENT_ID">

    <style type="text/css">
        #t, #name_user, #email{
            visibility: hidden;;
        }
    </style>

</head>
<body>

<%--<jsp:include page="google-btn.jsp"/>--%>
include
<div class="g-signin2" data-onsuccess="onSignIn" id="myP"></div>
<img id="myImg"><br>
<p id="name"></p>
<div id="status">
</div>
<form action="/signin" method="post">
    <input type="text" placeholder="" id="t" name="t"><br>
    <input type="submit" value="submit">
</form>

<script type="text/javascript">
    function onSignIn(googleUser) {
        var t = googleUser.getAuthResponse().id_token;
        document.getElementById("t").value = t;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'https://localhost:8080/signin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        console.log('Signed in as: ' + xhr.responseText);
    };
    xhr.send('idtoken=' + id_token);
</script>


<button onclick="myFunction()">Sign Out</button>
<script>
    function myFunction() {
        gapi.auth2.getAuthInstance().disconnect();
        location.reload();
    }
</script>
</body>
</html>