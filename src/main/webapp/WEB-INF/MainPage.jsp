<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/mypage.css">
    <script>
        // 팝업 창 표시
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
                <img id="profileImage" src="<%=request.getContextPath()%>/images/profile.png" alt="프로필 이미지" onclick="showImageUploadPopup()">
                <h2>커햄쿵야</h2>
                <button class="btn btn-edit-name">이름 수정</button>
            </div>
            <div class="profile-info">
                <p class="region">서울 <button class="btn btn-edit-region">지역 변경</button></p>
                <div class="stats">
                    <button class="btn btn-success">성공 소분 수</button>
                    <span>3</span>
                    <button class="btn btn-progress">진행 소분 수</button>
                    <span>1</span>
                </div>
                <textarea class="description">안녕하세요, 커햄쿵야에요.</textarea>
                <button class="btn btn-edit-desc">소개 수정</button>
            </div>
        </div>

        <!-- 소분 그룹 섹션 -->
        <div class="group-section">
            <div class="tabs">
                <button class="tab active">나의 소분</button>
                <button class="tab">참여 소분</button>
            </div>
            <div class="groups">
                <div class="group-card">
                    <img src="<%=request.getContextPath()%>/images/oc.jpg" alt="소분 이미지">
                    <p>소분완료</p>
                </div>
                <div class="group-card">
                    <img src="<%=request.getContextPath()%>/images/pa.jpg" alt="소분 이미지">
                    <p>소분완료</p>
                </div>
                <div class="group-card">
                    <img src="<%=request.getContextPath()%>/images/oc.jpg" alt="소분 이미지">
                    <p>소분완료</p>
                </div>
                <div class="group-card">
                    <img src="<%=request.getContextPath()%>/images/pa.jpg" alt="소분 이미지">
                    <p>소분완료</p>
                </div>
            </div>
        </div>
    </div>

    <!-- 이미지 업로드 팝업 -->
    <div id="imageUploadPopup" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background: white; padding: 20px; border: 1px solid black; z-index: 1000;">
        <h3>이미지 파일을 입력해주세요</h3>
        <input type="file" id="imageFile" accept="image/*">
        <div style="margin-top: 10px;">
            <button onclick="handleImageUpload()">확인</button>
            <button onclick="closePopup()">취소</button>
        </div>
    </div>
</body>
</html>
