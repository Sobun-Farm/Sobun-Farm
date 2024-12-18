<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>소분팜 로그인</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
</head>
<body>
<main>
    <div class="login-form">
        <h1>LOGIN</h1>
        <form id="loginForm" action="<%=request.getContextPath()%>/user?action=login" method="post">
            <input type="hidden" name="action" value="login" />
            <input type="email" name="email" placeholder="이메일" id="email" required />
            <input type="password" name="password" placeholder="비밀번호" id="password" required />
            <button type="submit">로그인</button>
        </form>
        <div class="signup-link">
            계정이 없으신가요? <a href="<%=request.getContextPath()%>/user?action=registerPage">가입하기</a>
        </div>
        <br>
        <div class="error-message" id="error-message">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
        </div>
    </div>
</main>
</body>
</html>