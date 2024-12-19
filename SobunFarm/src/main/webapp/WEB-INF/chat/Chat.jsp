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
                <br>
                <c:choose>
                   <c:when test="${not empty chatRoomList}">
                    <c:forEach var="chatRoom" items="${chatRoomList}">
    <div class="chat-room${param.chatId == chatRoom.chatId ? '-selected' : ''}" onclick="selectChatRoom('${chatRoom.chatId}');">
        <img src="${pageContext.request.contextPath}/uploaded/${chatRoom.itemImagePath != null ? chatRoom.itemImagePath : 'default.png'}" alt="아이템 이미지" />
        <h3>${chatRoom.itemName}</h3>
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
               <button class="exit-button" onclick="exitChat()">채팅방 목록보기</button> <!-- 채팅방 나가기 버튼 -->
            </div>
            
                
                    <iframe id="chatListFrame" name="chatListFrame" src="/chatContent?chatId=${param.chatId}" width="100%" height="70%"></iframe>
               
            <!-- 메세지 입력 -->
                <div class="messages_input">
                <form name="msgForm" action="${pageContext.request.contextPath}/chatContent" method="post" target="chatListFrame" >
                   <input type="hidden" name="chatId" value="${param.chatId}">
                   <input id="messageInput" type="text" name="messageContent" placeholder="메시지를 입력하세요" required onkeyup="enterkey();">
                   <button type="button" onClick="submitMsg();">전송</button>
                   <input type="text" name="dummy" size="2" style="display: none;" />
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
        
        function enterkey() {
           if (window.event.keyCode == 13) {
               // 엔터키가 눌렸을 때
              document.msgForm.submit();
               // input 필드 값을 초기화
                document.getElementById('messageInput').value = '';
            }
        }
        

        function submitMsg() {
           document.msgForm.submit();
           // input 필드 값을 초기화
            document.getElementById('messageInput').value = '';
        }

    </script>
</body>
</html>

