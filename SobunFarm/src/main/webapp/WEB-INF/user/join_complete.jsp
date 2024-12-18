<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>소분팜 회원가입 완료</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/join_complete.css" />
</head>
<body>
	<header>
     	 <div class="logo">
            <a href="<%=request.getContextPath()%>/user?action=homePage"><img src="${pageContext.request.contextPath}/images/logo.png" alt="소분팜 로고" width="300" height="auto"></a>
            <span >소분팜</span>
        </div>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/user?action=homePage"><img src="${pageContext.request.contextPath}/images/home.png" alt="Home"></a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/chat.png" alt="Chat" class="restricted"></a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/upload.png" alt="Upload" class="restricted"></a></li>
            </ul>
        </nav>
        <div class="search-login">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
            <button><a href="#"><img src="${pageContext.request.contextPath}/images/search.png"></a></button>
        </div>
        <div class="user">
            <span><a href="#"><img src="${pageContext.request.contextPath}/images/user.png" alt="user" class="restricted"></a></span>
        </div>
        <div class="login">
            <span><a href="<%=request.getContextPath()%>/user?action=loginPage">로그인</a></span>
            <span> / </span>
            <span><a href="<%=request.getContextPath()%>/user?action=registerPage">회원가입</a></span>
        </div>
    </header>
    
    <main>
    <div class="completion-message">
      <h1>회원가입이 완료되었습니다.</h1>
      <p>소분팜과 함께 합리적인 소비를 해보세요!</p><br>
      <button class="start-button"><a href="<%=request.getContextPath()%>/user?action=homePage">소분팜 시작하기</a></button>
    </div>
  </main>
<script>
    document.addEventListener('DOMContentLoaded', () => {
    const loggedIn = <%= (session.getAttribute("loggedInUser") != null) %>; // 로그인 여부 확인
    const restrictedLinks = document.querySelectorAll('.restricted'); // 접근 제한 클래스

    restrictedLinks.forEach((link) => {
        link.addEventListener('click', (event) => {
            if (!loggedIn) { // 로그인하지 않은 경우
                event.preventDefault(); // 기본 동작 막기
                alert("로그인이 필요한 서비스입니다."); // 팝업 메시지 표시
            }
        });
    });
});
</script>
</body>
</html>