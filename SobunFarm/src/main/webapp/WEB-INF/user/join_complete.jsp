<%@ include file="/WEB-INF/views/header.jsp" %>
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
    <main>
    <div class="completion-message">
      <h1>회원가입이 완료되었습니다.</h1>
      <p>소분팜과 함께 합리적인 소비를 해보세요!</p><br>
      <button class="start-button"><a href="<%=request.getContextPath()%>/user?action=homePage">소분팜 시작하기</a></button>
    </div>
  </main>

</body>
</html>