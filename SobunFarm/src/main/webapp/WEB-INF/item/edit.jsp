<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래 수정</title>
    <!-- 헤더 파일 포함 -->
    <jsp:include page="/WEB-INF/views/header.jsp" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/edit.css" />
</head>
<body>
    <div class="container">
        <form action="<%=request.getContextPath()%>/detail" method="post" class="edit-form">
            <!-- 물품명 -->
            <div class="form-group">
                <label for="item-name">물품명 <span class="required">*</span></label>
                <input type="text" id="title" name="title" value="신라면" required />
            </div>

            <!-- 카테고리 -->
            <div class="form-group">
                <label for="category">카테고리 <span class="required">*</span></label>
                <select id="category" name="category" required>
                    <option value="식료품" selected>식료품</option>
                    <option value="생활용품">생활용품</option>
                    <option value="기타">기타</option>
                </select>
            </div>

            <!-- 가격 -->
            <div class="form-group">
                <label for="price">가격 <span class="required">*</span></label>
                <input type="text" id="price" name="price" value="21000" required />
            </div>

            <!-- 지역 -->
            <div class="form-group">
                <label for="region">지역</label>
                <select id="region" name="region" required>
                    <option value="성북구" selected>성북구</option>
                    <option value="강남구">강남구</option>
                    <option value="종로구">종로구</option>
                </select>
            </div>
            
            <!-- 가격(1인당) -->
            <div class="form-group">
                <label for="pricePerPerson">가격(1인당)</label>
                <input type="text" id="pricePerPerson" name="pricePerPerson" value="7000" readonly />
            </div>

            <!-- 마감일 -->
            <div class="form-group">
                <label for="deadline">마감일 <span class="required">*</span></label>
                <input type="date" id="deadline" name="deadline" value="2025-01-02" required />
            </div>

            <!-- 총 참여 인원 -->
            <div class="form-group">
                <label for="totalParticipants">총 참여 인원 <span class="required">*</span></label>
                <select id="totalParticipants" name="totalParticipants" required>
                    <option value="3" selected>3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>

            <!-- 현재 참여 인원 -->
            <div class="form-group">
                <label for="currentParticipants">현재 참여 인원</label>
                <input type="text" id="currentParticipants" name="currentParticipants" value="2" readonly />
            </div>

            <!-- 구매 장소 -->
            <div class="form-group">
                <label for="purchaseLocation">구매 장소</label>
                <input type="text" id="purchaseLocation" name="purchaseLocation" value="쿠팡" />
            </div>
            
            <!-- 제품 사진 -->
            <div class="form-group image-upload">
                <label for="productImage">제품 사진</label>
                <input type="file" id="productImage" name="productImage" />
            </div>

            <!-- 거래 장소 -->
            <div class="form-group">
                <label for="transactionLocation">거래 장소</label>
                <input type="text" id="transactionLocation" name="transactionLocation" value="동덕여대 백주년 앞" />
            </div>

            <!-- 거래 시간 -->
            <div class="form-group">
                <label for="transactionTime">거래 시간</label>
                <input type="text" id="transactionTime" name="transactionTime" value="오후 3시 15분" />
            </div>

            <!-- 제목 -->
            <div class="form-group">
                <label for="title">제목 <span class="required">*</span></label>
                <input type="text" id="title" name="title" value="신라면(컵) 박스로 구매해서 10개씩 나누실 분" required />
            </div>

            <!-- 상세 정보 -->
            <div class="form-group full-width">
                <label for="details">상세 정보</label>
                <textarea id="details" name="details" required>쿠팡에서 라면 사서 나누실 분 구해요~&#13;&#10;시간, 장소 조율해서 가능합니다~&#13;&#10;소분 장소는 동덕여대 주변에서 거래 가능하신 분들 위주면 좋겠네요.</textarea>
            </div>

            <!-- 버튼 -->
            <div class="form-buttons">
			    <button type="submit" class="save-button" onclick="history.back()">거래 수정 완료</button>
 				<button type="button" class="start-button" onclick="history.back()">거래 정보 확정</button>
			</div>
        </form>
    </div>
</body>
</html>