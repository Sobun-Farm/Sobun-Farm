<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   <title>소분팜</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Chat.css" />
</head>
<body onLoad="loadChatContent('${param.chatId}');">    
   <main>
      <div class="container">
            <!-- 왼쪽: 채팅방 목록 -->
            <div class="chat-list">
                <h2>채팅방 목록</h2>
                <c:choose>
                   <c:when test="${not empty chatRoomList}">
                      <c:forEach var="chatRoom" items="${chatRoomList}">
                         
                         
                         <div class="chat-room" onclick="selectChatRoom('${chatRoom.chatId}');">
                               <img src="${pageContext.request.contextPath}/images/testItem.jpg" alt="아이템 이미지">
                               <h3>${chatRoom.itemName}</h3> <!-- ChatRoom 객체에서 itemName을 가져옴 -->
                               <p>아이디: ${chatRoom.chatId}</p> <!-- chatId를 클릭하면 해당 채팅방을 선택한 후 iframe에서 로드 -->
                           </div>
                           
                  </c:forEach>
               </c:when>
               
               <c:otherwise>
                  <div class="no-chat">
                     <p> 현재 참여중인 채팅방이 없습니다 </p>
                  </div>
               </c:otherwise>
            </c:choose>
            </div>

            <!-- 오른쪽: 선택된 채팅방의 채팅 내용 -->
            <div class="chat-content">
            <div>
               <button class="exit-button" onclick="exitChat()">채팅방 나가기</button> <!-- 채팅방 나가기 버튼 -->
            </div>
                <div class="messages">
                    <iframe id="chatListFrame" name="chatListFrame" src="/chatContent?chatId=${param.chatId}" width="100%" height="100%">
                    </iframe>
                </div>
               
            <!-- 메세지 입력 -->
                <div class="messages_input">
                <form action="${pageContext.request.contextPath}/chatContent" method="post" target="chatListFrame">
                    <input type="hidden" name="chatId" value="${param.chatId}">
                    <input type="text" name="messageContent" placeholder="메시지를 입력하세요" required>
                    <button type="submit">전송</button>
                </form>
            </div>
         

            </div>
            
        </div>
   </main>
   <script>
      function selectChatRoom(chatId){
         document.location.href="${pageContext.request.contextPath}/chat?chatId="+chatId;
      }
      
        function loadChatContent(chatId) {
            // 선택한 채팅방의 chatId로 iframe을 갱신
            document.getElementById('chatListFrame').src = '${pageContext.request.contextPath}/chatContent?chatId=' + chatId;
            // 선택된 채팅방에 대한 ID로 표시
            //document.querySelector('.chat-content h3').innerHTML = "채팅방: " + chatId;
        }
        
        function exitChat() {
           // 채팅방 목록으로 이동
           document.location.href = "${pageContext.request.contextPath}/chat";
       }
        
        
    </script>
</body>
</html>

