<%--
  Created by IntelliJ IDEA.
  User: Sergiu
  Date: 18.01.2016
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/signUpServlet" method="post">
  <h1>Welcome, please fill all fields</h1>
  <b>Enter Username:</b> <input type="text" name="username"/> <br/><br/>
  <b>Enter Password:</b> <input type="text" name="password"/> <br/><br/>
  <b>Enter Name:</b> <input type="text" name="name"/> <br/><br/>
  <b>Enter Email:</b> <input type="text" name="email"/> <br/><br/>
  <b>Enter Age:</b> <input type="text" name="age"/> <br/><br/>
  <b>Enter Hometown:</b> <input type="text" name="hometown"/> <br/><br/>
  <input type="submit" value="Sign Up"/>
</form>
</body>
</html>
