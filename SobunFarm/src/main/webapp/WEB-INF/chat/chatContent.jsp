<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // chatId 값을 받아오기
    String chatId = request.getParameter("chatId");
    if (chatId == null || chatId.trim().isEmpty() || "-1".equals(chatId)) {
        chatId = "-1"; // chatId가 없는 경우 또는 -1인 경우
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>채팅방 내용</title>
</head>
<body>
<% if ("-1".equals(chatId)) { %>
    <!-- chatId가 유효하지 않을 때 표시할 내용 -->
    <div class="error-message">
        <h3>유효하지 않은 채팅방입니다. 다시 시도해 주세요.</h3>
        
    </div>
<% } else { %>
    <h3>채팅방C: ${chatId}</h3> <!-- 채팅방 ID 표시 -->

    <div class="messages">
	    <c:forEach var="message" items="${messageList}">
	        <div class="message">
	            <span class="sender">${message.sender}</span>: <!-- 메시지 보낸 사람 -->
	            <span class="message-content">${message.content}</span> <!-- 메시지 내용 -->
	            <span class="timestamp">${message.timestamp}</span> <!-- 메시지 전송 시간 -->
	        </div>
	    </c:forEach>
	</div>
	
	<button onclick="exitChat()">채팅방 나가기</button> <!-- 채팅방 나가기 버튼 -->
	
    <script>
	    function exitChat() {
	        // 채팅방 목록으로 이동
	        document.location.href = "${pageContext.request.contextPath}/chat";
	    }
	    setTimeout(function() {
	        location.reload();
	    }, 3000);  // 3초마다 새로고침
	</script>
<% } %>
</body>
</html>
