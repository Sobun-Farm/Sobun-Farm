<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   <title>소분팜</title>
   <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css" />
<title>소분팜</title>
</head>
<body>
   <header>
         <div class="logo">
            <a href="#"><img src="<%=request.getContextPath()%>/images/logo.png" alt="소분팜 로고" width="300" height="auto"></a>
            <span >소분팜</span>
        </div>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/home.jsp"><img src="<%=request.getContextPath()%>/images/home.png" alt="Home"></a></li>
                <li><a href="#"><img src="<%=request.getContextPath()%>/images/chat.png" alt="Chat"></a></li>
                <li><a href="#"><img src="<%=request.getContextPath()%>/images/upload.png" alt="Upload"></a></li>
            </ul>
        </nav>
        <div class="search-login">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
            <button><a href="#"><img src="<%=request.getContextPath()%>/images/search.png"></a></button>
        </div>
        <div class="user">
            <span><a href="#"><img src="<%=request.getContextPath()%>/images/user.png" alt="user"></a></span>
        </div>
        <div class="login">
            <span><a href="<%=request.getContextPath()%>/login.jsp">로그인</a></span>
            <span> / </span>
            <span><a href="<%=request.getContextPath()%>/join.jsp">회원가입</a></span>
        </div>
    </header>
</body>
</html>