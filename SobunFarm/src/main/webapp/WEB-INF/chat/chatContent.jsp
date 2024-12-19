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
    
    <style>
    
      .messagebox{
         width : 100%;
         height : 520px;
         background-color: #f8f8f8;
           border: 1px solid #d3e898;
           overflow-y : scroll;
          
      }
      
       .message {
         margin-bottom: 10px;
      }
       
        .message-content {
          padding: 8px;
          border-radius: 8px;
          display: inline-block;
          font-size: 14px;
      }
      
      .input {
           outline: none;
      }

    </style>
</head>
<body onLoad="scrollToBottom();";>
<div class="space">
<% if ("-1".equals(chatId)) { %>
    <!-- chatId가 유효하지 않을 때 표시할 내용 -->
    <div class="error-message">
        <img src="${pageContext.request.contextPath}/images/noChat.png" alt="채팅방 없음 이미지">

        
    </div>
<% } else { %>
    <h3>채팅방: ${itemName}</h3> <!-- 채팅방 이름 출력 -->

    <div class="messagebox" id="messagesContainer">

        <c:forEach var="message" items="${messageList}">
            <c:choose>
                <c:when test="${sessionScope.userId == message.userId}">
                    <!-- 로그인한 사용자의 메시지 -->
                    <div class="message" style="text-align: right;">
                        <span class="message-content" style="background-color: #FBC6A0;">${message.content}</span>
                       <span class="timestamp">${message.formattedTimestamp}</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- 다른 사용자의 메시지 -->
                    <div class="message" style="text-align: left;">
                        <span class="sender">${message.sender}</span>
                       <span class="message-content" style="background-color: #E7F1B6;">${message.content}</span>
                       <span class="timestamp">${message.formattedTimestamp}</span>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>

    </div>
 </div>   

    <script>
       
    
       // 페이지 로드 시 메시지 영역을 가장 아래로 스크롤
       function scrollToBottom() {
           const messagesContainer = document.getElementById('messagesContainer');
           //alert(messagesContainer.scrollTop);
           //alert(messagesContainer.scrollHeight);
           messagesContainer.scrollTop = messagesContainer.scrollHeight;
       }
   
       // 페이지 로드 후 바로 스크롤 실행
       //window.onload = function() {
           //scrollToBottom();
           //let mySpace = document.getElementById("messagesContainer");
           //mySpace.scrollTop = mySpace.scrollHeight;
       //};

       
       
       setTimeout(function() {
           location.reload();
       }, 3000);  // 3초마다 새로고침
   </script>
<% } %>
</body>
</html>
