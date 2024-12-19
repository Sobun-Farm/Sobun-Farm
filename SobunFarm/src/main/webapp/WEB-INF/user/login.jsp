<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>소분팜 로그인</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
    <script>
        function showErrorPopup(message) {
            if (message) {
                alert(message); // 로그인 실패 메시지 팝업
            }
        }
    </script>
</head>
<body>
<header>
    <div class="logo">
        <a href="<%=request.getContextPath()%>/user?action=homePage">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="소분팜 로고" width="300" height="auto"/>
        </a>
        <span>소분팜</span>
    </div>
    <nav>
        <ul>
            <li>
                <a href="<%=request.getContextPath()%>/user?action=homePage"><img src="${pageContext.request.contextPath}/images/home.png" alt="Home" /></a>
            </li>
            <li>
                <a href="#"><img src="${pageContext.request.contextPath}/images/chat.png" alt="Chat" class="restricted"/></a>
            </li>
            <li>
                <a href="#"><img src="${pageContext.request.contextPath}/images/upload.png" alt="Upload" class="restricted"/></a>
            </li>
        </ul>
    </nav>
    <div class="search-login">
        <input type="text" placeholder="검색어를 입력하세요." />
        <button>
            <a href="#"><img src="${pageContext.request.contextPath}/images/search.png" /></a>
        </button>
    </div>
    <div class="user">
        <span><a href="#"><img src="${pageContext.request.contextPath}/images/user.png" alt="user" class="restricted"/></a></span>
    </div>
    <div class="login">
        <span><a href="<%=request.getContextPath()%>/user?action=loginPage">로그인</a></span>
        <span> / </span>
        <span><a href="<%=request.getContextPath()%>/user?action=registerPage">회원가입</a></span>
    </div>
</header>

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
<%-- 로그인 실패 시 팝업 호출 --%>
<%
    Boolean loginFailed = (Boolean) request.getAttribute("loginFailed");
    if (loginFailed != null && loginFailed) {
        request.removeAttribute("loginFailed"); // 확인 후 삭제
%>
    <script>
        showErrorPopup("아이디 또는 비밀번호가 일치하지 않습니다.");
    </script>
<%
    }
%>

<script>
    document.addEventListener('DOMContentLoaded', () => {
    const loggedIn = <%= (session.getAttribute("loggedInUser") != null) %>; // 로그인 여부 확인
    const restrictedLinks = document.querySelectorAll('.restricted'); // 접근 제한 클래스

    restrictedLinks.forEach((link) => {
        link.addEventListener('click', (event) => {
            if (!loggedIn) { // 로그인하지 않은 경우
                event.preventDefault(); // 기본 동작 막기
                alert("로그인 후 이용하세요."); // 팝업 메시지 표시
            }
        });
    });
});
</script>
</body>
</html>
