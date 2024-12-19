<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>거래 등록</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/edit.css" />
</head>
<body>
    <div class="container">
        <!-- 거래 등록 폼 -->
        <form action="<%=request.getContextPath()%>/new_item" 
        method="post" enctype="multipart/form-data" class="edit-form">
             <!-- 제목 -->
            <div class="form-group">
                <label for="title">제목 <span class="required">*</span></label>
                <input type="text" id="title" name="title" placeholder="예: 신라면 박스로 구매해서 나누실 분" required />
            </div>
            
            <!-- 물품명 -->
            <div class="form-group">
                <label for="item-name">물품명 <span class="required">*</span></label>
                <input type="text" id="item-name" name="itemName" placeholder="예: 신라면" required />
            </div>

            <!-- 카테고리 -->
            <div class="form-group">
                <label for="category">카테고리 <span class="required">*</span></label>
                <select id="category" name="category" required>
                    <option value="식료품">식료품</option>
                    <option value="생필품">생필품</option>
                    <option value="기타">기타</option>
                </select>
            </div>

            <!-- 가격 -->
            <div class="form-group">
                <label for="price">가격 <span class="required">*</span></label>
                <input type="number" id="price" name="price" placeholder="예: 21000" required />
            </div>

            <!-- 지역 -->
            <div class="form-group">
                <label for="region">지역</label>
                <select id="region" name="region">
                      <% 
                          String region = (String) request.getAttribute("region");
                          region = (region == null || region.isEmpty()) ? "강남구" : region; // 기본값 강남구
                      %>
                      <option value="강남구" <%= "강남구".equals(region) ? "selected" : "" %>>강남구</option>
                      <option value="강동구" <%= "강동구".equals(region) ? "selected" : "" %>>강동구</option>
                      <option value="강북구" <%= "강북구".equals(region) ? "selected" : "" %>>강북구</option>
                      <option value="강서구" <%= "강서구".equals(region) ? "selected" : "" %>>강서구</option>
                      <option value="관악구" <%= "관악구".equals(region) ? "selected" : "" %>>관악구</option>
                      <option value="광진구" <%= "광진구".equals(region) ? "selected" : "" %>>광진구</option>
                      <option value="구로구" <%= "구로구".equals(region) ? "selected" : "" %>>구로구</option>
                      <option value="금천구" <%= "금천구".equals(region) ? "selected" : "" %>>금천구</option>
                      <option value="노원구" <%= "노원구".equals(region) ? "selected" : "" %>>노원구</option>
                      <option value="도봉구" <%= "도봉구".equals(region) ? "selected" : "" %>>도봉구</option>
                      <option value="동대문구" <%= "동대문구".equals(region) ? "selected" : "" %>>동대문구</option>
                      <option value="동작구" <%= "동작구".equals(region) ? "selected" : "" %>>동작구</option>
                      <option value="마포구" <%= "마포구".equals(region) ? "selected" : "" %>>마포구</option>
                      <option value="서대문구" <%= "서대문구".equals(region) ? "selected" : "" %>>서대문구</option>
                      <option value="서초구" <%= "서초구".equals(region) ? "selected" : "" %>>서초구</option>
                      <option value="성동구" <%= "성동구".equals(region) ? "selected" : "" %>>성동구</option>
                      <option value="성북구" <%= "성북구".equals(region) ? "selected" : "" %>>성북구</option>
                      <option value="송파구" <%= "송파구".equals(region) ? "selected" : "" %>>송파구</option>
                      <option value="양천구" <%= "양천구".equals(region) ? "selected" : "" %>>양천구</option>
                      <option value="영등포구" <%= "영등포구".equals(region) ? "selected" : "" %>>영등포구</option>
                      <option value="용산구" <%= "용산구".equals(region) ? "selected" : "" %>>용산구</option>
                      <option value="은평구" <%= "은평구".equals(region) ? "selected" : "" %>>은평구</option>
                      <option value="종로구" <%= "종로구".equals(region) ? "selected" : "" %>>종로구</option>
                      <option value="중구" <%= "중구".equals(region) ? "selected" : "" %>>중구</option>
                      <option value="중랑구" <%= "중랑구".equals(region) ? "selected" : "" %>>중랑구</option>
                  </select>
            </div>

            <!-- 제품 사진 -->
            <div class="form-group image-upload">
                <label for="photo">제품 사진</label>
                <input type="file" id="photo" name="photo" />
            </div>

            <!-- 마감일 -->
            <div class="form-group">
                <label for="deadline">마감일 <span class="required">*</span></label>
                <input type="date" id="deadline" name="deadline" required />
            </div>

         <!-- 총 참여 인원 -->
         <div class="form-group">
             <label for="maxParticipant">총 참여 인원 <span class="required">*</span></label>
             <input type="number" id="maxParticipant" name="maxParticipant" placeholder="예: 4" min="1" required />
         </div>


            <!-- 구매 장소 -->
            <div class="form-group">
                <label for="purchaseLocation">구매 장소 <span class="required">*</span></label>
                <input type="text" id="purchaseLocation" name="purchaseLocation" placeholder="예: 쿠팡" required />
            </div>
            
            <!-- 거래 장소 -->
            <div class="form-group">
                <label for="transactionLocation">거래 장소 <span class="required">*</span></label>
                <input type="text" id="transactionLocation" name="transactionLocation" placeholder="예: 동덕여대 정문 앞" required />
            </div>

            <!-- 거래 시간 -->
            <div class="form-group">
                <label for="transactionTime">거래 시간 <span class="required">*</span></label>
                <input type="text" id="transactionTime" name="transactionTime" placeholder="예: 12/19 오후 3시 15분" required />
            </div>

            <!-- 상세 정보 -->
            <div class="form-group full-width">
                <label for="description">상세 정보 <span class="required">*</span></label>
                <textarea id="description" name="description" placeholder="상세 정보를 입력하세요." required></textarea>
            </div>

            <!-- 버튼 -->
            <div class="form-buttons">
                <button type="submit" class="save-button">등록</button>
            </div>
        </form>
    </div>
</body>

</html>



