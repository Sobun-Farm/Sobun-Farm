<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   <title>소분팜</title>
   <!-- CSS 경로 수정 -->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css" />
</head>
<body>
   <!-- 헤더 파일 포함 -->
   <jsp:include page="/WEB-INF/views/header.jsp" />
    
   <main>
      <div class="filter-container">
         <div class="region-filter">
            <label for="region">
               <!-- 이미지 경로 수정 -->
               <img src="<%=request.getContextPath()%>/images/region.png" alt="구 선택" class="label-icon">
            </label>
            <select id="region" name="region" required>
               <option value="" disabled>구 선택</option>
               <option value="강남구">강남구</option>
               <option value="강동구">강동구</option>
               <option value="강북구">강북구</option>
               <option value="강서구">강서구</option>
               <option value="관악구">관악구</option>
               <option value="광진구">광진구</option>
               <option value="구로구">구로구</option>
               <option value="금천구">금천구</option>
               <option value="노원구">노원구</option>
               <option value="도봉구">도봉구</option>
               <option value="동대문구">동대문구</option>
               <option value="동작구">동작구</option>
               <option value="마포구">마포구</option>
               <option value="서대문구">서대문구</option>
               <option value="서초구">서초구</option>
               <option value="성동구">성동구</option>
               <option value="성북구" selected>성북구</option>
               <option value="송파구">송파구</option>
               <option value="양천구">양천구</option>
               <option value="영등포구">영등포구</option>
               <option value="용산구">용산구</option>
               <option value="은평구">은평구</option>
               <option value="종로구">종로구</option>
               <option value="중구">중구</option>
               <option value="중랑구">중랑구</option>
            </select>
            <label for="drop-down">
               <!-- 이미지 경로 수정 -->
               <img src="<%=request.getContextPath()%>/images/arrow_drop_down.png" alt="드롭다운" class="label-icon">
            </label>
         </div>
      
         <div class="category-filter">
            <button class="category-button" data-category="전체보기">전체보기</button>
            <button class="category-button" data-category="식료품">식료품</button>
            <button class="category-button" data-category="생필품">생필품</button>
            <button class="category-button" data-category="기타">기타</button>
         </div>
      </div>
      <%
         // 페이지네이션 변수
         int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
         int itemsPerPage = 10;
         int totalItems = 40; // 전체 상품 수 (하드코딩 예제)
         int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
      
         // 시작 및 끝 인덱스 계산
         int startIndex = (currentPage - 1) * itemsPerPage + 1;
         int endIndex = Math.min(startIndex + itemsPerPage - 1, totalItems);
      %>

      <section class="product-list">
         <% 
            // 현재 페이지의 상품만 출력
            for (int i = startIndex; i <= endIndex; i++) {
         %>
         <a href="<%=request.getContextPath()%>/detail" class="product-card">
            <!-- 이미지 경로 수정 -->
            <img src="<%=request.getContextPath()%>/images/example.png" alt="상품 이미지 <%=i%>">
            <h3>상품 제목 <%=i%></h3>
            <p><%= i * 1000 %>원/명</p>
            <span>현재 인원: 2/5 (명)</span>
         </a>
         <% } %>
      </section>

      <!-- 페이지네이션 -->
      <div class="pagination">
         <!-- 맨 앞으로 이동 버튼 -->
         <% if (currentPage > 1) { %>
         <a href="?page=1" class="first-page"><<</a>
         <% } %>
   
         <!-- 이전 페이지 버튼 -->
         <% if (currentPage > 1) { %>
         <a href="?page=<%= currentPage - 1 %>" class="prev-page"><</a>
         <% } %>
   
         <!-- 페이지 번호 -->
         <% 
            for (int i = 1; i <= totalPages; i++) { 
               String activeClass = (i == currentPage) ? "active" : "";
         %>
         <a href="?page=<%=i%>" class="<%=activeClass%>"><%=i%></a>
         <% } %>
   
         <!-- 다음 페이지 버튼 -->
         <% if (currentPage < totalPages) { %>
         <a href="?page=<%= currentPage + 1 %>" class="next-page">></a>
         <% } %>
   
         <!-- 맨 끝으로 이동 버튼 -->
         <% if (currentPage < totalPages) { %>
         <a href="?page=<%=totalPages%>" class="last-page">>></a>
         <% } %>
      </div>
   </main>
</body>
</html>
