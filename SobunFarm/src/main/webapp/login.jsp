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
<header>
      <div class="logo">
        <a href="#"
          ><img
            src="image/logo.png"
            alt="소분팜 로고"
            width="300"
            height="auto"
        /></a>
        <span>소분팜</span>
      </div>
      <nav>
        <ul>
          <li>
            <a href="<%=request.getContextPath()%>/home.jsp"><img src="image/home.png" alt="Home" /></a>
          </li>
          <li>
            <a href="#"><img src="image/chat.png" alt="Chat" /></a>
          </li>
          <li>
            <a href="#"><img src="image/upload.png" alt="Upload" /></a>
          </li>
        </ul>
      </nav>
      <div class="search-login">
        <input type="text" placeholder="검색어를 입력하세요." />
        <button>
          <a href="#"><img src="image/search.png" /></a>
        </button>
      </div>
      <div class="user">
        <span
          ><a href="#"><img src="image/user.png" alt="user" /></a
        ></span>
      </div>
      <div class="login">
        <span><a href="<%=request.getContextPath()%>/login.jsp">로그인</a></span>
        <span> / </span>
        <span><a href="<%=request.getContextPath()%>/join.jsp">회원가입</a></span>
      </div>
    </header>


    <main>
      <div class="login-form">
        <h1>LOGIN</h1>
        <form id="loginForm">
          <input type="email" placeholder="이메일" id="email" required />
          <input
            type="password"
            placeholder="비밀번호"
            id="password"
            required
          />
          <button type="submit">로그인</button>
        </form>
        <!-- 여기닌 추후에 forwarding으로 바꾸기 -->
        <div class="signup-link">
          계정이 없으신가요? <a href="<%=request.getContextPath()%>/join.jsp">가입하기</a>
        </div>
        <br>
        <div class="error-message" id="error-message"></div>
      </div>
    </main>

    <script>
      document
        .getElementById("loginForm")
        .addEventListener("submit", function (event) {
          event.preventDefault();


          const emailInput = document.getElementById("email").value;
          const passwordInput = document.getElementById("password").value;
          const errorMessage = document.getElementById("error-message");


          // 성공 아이디와 비밀번호(오류 체크용입니다./ 이후 변경)
          if (emailInput === "aa@naver.com" && passwordInput === "1111") {
            errorMessage.textContent = "";
            alert("로그인 성공");
          } else {
            errorMessage.textContent =
              "이메일 또는 비밀번호가 틀렸습니다. 다시 입력해 주세요.";
            errorMessage.classList.add("show");
          }
        });
    </script>
  </body>

</html>