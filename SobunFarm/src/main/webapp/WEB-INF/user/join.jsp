<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>소분팜 회원가입</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/join.css" />
	<script defer src="<%=request.getContextPath()%>/js/join.js"></script>
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
                <li><a href="<%=request.getContextPath()%>/user?action=loginPage"><img src="${pageContext.request.contextPath}/images/chat.png" alt="Chat" class="restricted"></a></li>
                <li><a href="<%=request.getContextPath()%>/user?action=loginPage"><img src="${pageContext.request.contextPath}/images/upload.png" alt="Upload" class="restricted"></a></li>
            </ul>
        </nav>
        <div class="search-login">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
            <button><a href="#"><img src="${pageContext.request.contextPath}/images/search.png"></a></button>
        </div>
        <div class="user">
            <span><a href="<%=request.getContextPath()%>/user?action=loginPage"><img src="${pageContext.request.contextPath}/images/user.png" alt="user" class="restricted"></a></span>
        </div>
        <div class="login">
            <span><a href="<%=request.getContextPath()%>/user?action=loginPage">로그인</a>
</span>
            <span> / </span>
            <span><a href="<%=request.getContextPath()%>/user?action=registerPage">회원가입</a>
</span>
        </div>
    </header>

    <main>
      <section class="signup-form">
        <h2>회원가입</h2>
        <form id="signup-form" action="<%=request.getContextPath()%>/user?action=register" method="post">
          <input type="hidden" name="action" value="register">
          <div class="input-group">
            <input type="email" id="email" name="email" placeholder="이메일" required />
            <button type="button" id="email-check">중복확인</button>
          </div>

          <div class="input-group">
            <input type="password" id="password" name="password" placeholder="비밀번호" required />
          </div>

          <div class="input-group">
            <input type="password" id="password-confirm" name="passwordConfirm" placeholder="비밀번호 확인" required />
          </div>

          <div class="input-group">
            <input type="text" id="nickname" name="nickname" placeholder="닉네임" required />
            <button type="button" id="nickname-check">중복확인</button>
          </div>

          <div class="input-group">
            <select id="region" name="region" required>
              <option value="">거주지역</option>
              <option value="강남구">강남구</option>
              <option value="강동구">강동구</option>
              <option value="강북구">강북구</option>
              <option value="강서구">강서구</option>
              <option value="관악구">관악구</option>
              <option value="광진구">광진구</option>
              <option value="구로구">구로구</option>
              <option value="금천구">금천구</option>
              <option value="노원구">노원구</option>
              <option value="도봉구">도봉구</option>
              <option value="동대문구">동대문구</option>
              <option value="동작구">동작구</option>
              <option value="마포구">마포구</option>
              <option value="서대문구">서대문구</option>
              <option value="서초구">서초구</option>
              <option value="성동구">성동구</option>
              <option value="성북구">성북구</option>
              <option value="송파구">송파구</option>
              <option value="양천구">양천구</option>
              <option value="영등포구">영등포구</option>
              <option value="용산구">용산구</option>
              <option value="은평구">은평구</option>
              <option value="종로구">종로구</option>
              <option value="중구">중구</option>
              <option value="중랑구">중랑구</option>
            </select>
          </div>

          <button type="submit" id="signup-button" disabled>가입하기</button>
          <p class="error-message" id="password-error"></p>
        </form>
      </section>
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