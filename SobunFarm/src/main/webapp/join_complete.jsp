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
            <a href="#"><img src="image/logo.png" alt="소분팜 로고" width="300" height="auto"></a>
            <span >소분팜</span>
        </div>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/home.jsp"><img src="image/home.png" alt="Home"></a></li>
                <li><a href="#"><img src="image/chat.png" alt="Chat"></a></li>
                <li><a href="#"><img src="image/upload.png" alt="Upload"></a></li>
            </ul>
        </nav>
        <div class="search-login">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
            <button><a href="#"><img src="image/search.png"></a></button>
        </div>
        <div class="user">
            <span><a href="#"><img src="image/user.png" alt="user"></a></span>
        </div>
        <div class="login">
            <span><a href="<%=request.getContextPath()%>/login.jsp">로그인</a></span>
            <span> / </span>
            <span><a href="<%=request.getContextPath()%>/join.jsp">회원가입</a></span>
        </div>
    </header>
    
    <main>
    <div class="completion-message">
      <h1>회원가입이 완료되었습니다.</h1>
      <p>소분팜과 함께 합리적인 소비를 해보세요!</p><br>
      <button class="start-button"><a href="<%=request.getContextPath()%>/home.jsp">소분팜 시작하기</a></button>
    </div>
  </main>

</body>
</html>