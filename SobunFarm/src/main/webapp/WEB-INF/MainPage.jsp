<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.domain.Item" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   List<Item> itemList = (List<Item>)request.getAttribute("itemList");
   List<Item> paticipationItemList = (List<Item>)request.getAttribute("paticipationItemList");
%>
<% 
    String base64Image = (String) session.getAttribute("base64Image");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/mypage.css">
    <script>
    function showImageUploadPopup() {
        const popup = document.getElementById('imageUploadPopup');
        popup.style.display = 'block';
    }

    // 팝업 창 닫기
    function closePopup() {
        const popup = document.getElementById('imageUploadPopup');
        popup.style.display = 'none';
    }

    // 이미지 업로드 처리
    function handleImageUpload() {
        const fileInput = document.getElementById('imageFile');
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const profileImage = document.getElementById('profileImage');
                profileImage.src = e.target.result; // 프로필 이미지를 변경
                alert('이미지 파일이 업로드되었습니다: ' + file.name);
                window.location.reload();
                closePopup();
            };
            reader.readAsDataURL(file);
        } else {
            alert('이미지 파일을 선택해주세요.');
        }
    }
    </script>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <!-- 프로필 섹션 -->
        <div class="profile-section">
            <div class="profile-image">
            <%
                // base64Image가 null이거나 비어있을 경우 기본 이미지 경로 사용
                String imageSrc;
                if (base64Image == null || base64Image.isEmpty()) {
                    imageSrc = request.getContextPath() + "/images/default.png";
                } else {
                    imageSrc = "data:image/jpeg;base64, " + base64Image;
                }
            %>
            <img id="profileImage" src="<%= imageSrc %>" alt="프로필 이미지" onclick="showImageUploadPopup()">
                <h2>${nickname}</h2>
                <!-- <button class="btn btn-edit-name">이름 수정</button> -->
            </div>
            <div class="profile-info">
                <form action="updateRegion" method="post">
                   <div class="region-div">
                   <select id="region" name="region" required>
                       <option value="" disabled>구 선택</option>
                       <option value="강남구" ${region == '강남구' ? 'selected' : ''}>강남구</option>
                       <option value="강동구" ${region == '강동구' ? 'selected' : ''}>강동구</option>
                       <option value="강북구" ${region == '강북구' ? 'selected' : ''}>강북구</option>
                       <option value="강서구" ${region == '강서구' ? 'selected' : ''}>강서구</option>
                       <option value="관악구" ${region == '관악구' ? 'selected' : ''}>관악구</option>
                       <option value="광진구" ${region == '광진구' ? 'selected' : ''}>광진구</option>
                       <option value="구로구" ${region == '구로구' ? 'selected' : ''}>구로구</option>
                       <option value="금천구" ${region == '금천구' ? 'selected' : ''}>금천구</option>
                       <option value="노원구" ${region == '노원구' ? 'selected' : ''}>노원구</option>
                       <option value="도봉구" ${region == '도봉구' ? 'selected' : ''}>도봉구</option>
                       <option value="동대문구" ${region == '동대문구' ? 'selected' : ''}>동대문구</option>
                       <option value="동작구" ${region == '동작구' ? 'selected' : ''}>동작구</option>
                       <option value="마포구" ${region == '마포구' ? 'selected' : ''}>마포구</option>
                       <option value="서대문구" ${region == '서대문구' ? 'selected' : ''}>서대문구</option>
                       <option value="서초구" ${region == '서초구' ? 'selected' : ''}>서초구</option>
                       <option value="성동구" ${region == '성동구' ? 'selected' : ''}>성동구</option>
                       <option value="성북구" ${region == '성북구' ? 'selected' : ''}>성북구</option>
                       <option value="송파구" ${region == '송파구' ? 'selected' : ''}>송파구</option>
                       <option value="양천구" ${region == '양천구' ? 'selected' : ''}>양천구</option>
                       <option value="영등포구" ${region == '영등포구' ? 'selected' : ''}>영등포구</option>
                       <option value="용산구" ${region == '용산구' ? 'selected' : ''}>용산구</option>
                       <option value="은평구" ${region == '은평구' ? 'selected' : ''}>은평구</option>
                       <option value="종로구" ${region == '종로구' ? 'selected' : ''}>종로구</option>
                       <option value="중구" ${region == '중구' ? 'selected' : ''}>중구</option>
                       <option value="중랑구" ${region == '중랑구' ? 'selected' : ''}>중랑구</option>
                   </select>
                   <button class="btn btn-edit-region">지역 변경</button>
               </div>

                </form>
                <div class="stats">
                    <button class="btn btn-progress">진행 소분 수</button>
                    <span>${count}</span>
                </div>
                <form action="updateDescription" method="post">
               <%
                   String text = (String) session.getAttribute("text");
                   if (text == null) {
                       text = "";
                   }
               %>
               
               <textarea class="description" name="text" spellcheck="false"><%= text %></textarea>

                <button class="btn btn-edit-desc" type="submit">소개 수정</button>
            </form>
            </div>
        </div>

        <!-- 소분 그룹 섹션 -->
        <div class="group-section">
          <div class="tabs">
              <button class="tab active" onclick="showTab('myItems')">나의 소분</button>
              <button class="tab" onclick="showTab('participatedItems')">참여 소분</button>
          </div>
      
          <div id="myItems" class="groups"">
              <c:forEach var="item" items="${ItemList}">
                  <div class="group-card">
                      <img src="<%=request.getContextPath()%>/images/oc.jpg" alt="소분 이미지">
                      <p>${item.title}</p>
                  </div>
              </c:forEach>
          </div>
      
          <div id="participatedItems" class="groups" style="display: none;">
              <c:forEach var="item" items="${paticipationItemList}">
                  <div class="group-card">
                      <img src="<%=request.getContextPath()%>/images/oc.jpg" alt="소분 이미지">
                      <p>${item.title}</p>
                  </div>
              </c:forEach>
          </div>
      </div>
</div>

    <!-- 이미지 업로드 팝업 -->
   <div id="imageUploadPopup" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background: white; padding: 20px; border: 1px solid black; z-index: 1000;">
       <h3>이미지 파일을 입력해주세요</h3>
       <!-- 이미지 업로드를 위한 form -->
       <form action="updateProfileImage" method="post" enctype="multipart/form-data" onsubmit="closePopup();">
           <input type="file" id="imageFile" name="profileImage" accept="image/*" required>
           <div style="margin-top: 10px;">
               <button type="submit">확인</button>
               <button type="button" onclick="closePopup()">취소</button>
           </div>
       </form>
   </div>

    
    <script>
       function showTab(tabId, element) {
           // 모든 탭 섹션을 숨기기
           document.getElementById("myItems").style.display = "none";
           document.getElementById("participatedItems").style.display = "none";
   
           // 선택한 탭 섹션만 보이기
           document.getElementById(tabId).style.display = "flex";
   
           // 탭 버튼 스타일 초기화
           const tabs = document.querySelectorAll('.tab');
           tabs.forEach(tab => tab.classList.remove('active'));
   
           // 현재 클릭한 탭에 active 클래스 추가
           element.classList.add('active');
       }

   </script>
    
</body>
</html>
