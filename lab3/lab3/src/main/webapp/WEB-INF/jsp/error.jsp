<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Error</title></head>
<body>
    <h2>An error occurred</h2>
    <p>${error}</p>
    <a href="${pageContext.request.contextPath}/login">Back to Login</a>
</body>
</html>