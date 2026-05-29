<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Welcome</title></head>
<body>
    <h2>Welcome, ${sessionScope.user}!</h2>
    <a href="${pageContext.request.contextPath}/login">Logout</a>
</body>
</html>