<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.domain.Item" %>
<%@ page import="java.util.Base64" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>소분팜</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
</head>
<body>
    <main>
        <!-- 필터 섹션 -->
        <form id="filterForm" action="<%=request.getContextPath()%>/home" method="get">
          <div class="filter-container">
              <div class="region-filter">
                  <label for="region">
                      <img src="${pageContext.request.contextPath}/images/region.png" alt="지역 선택" class="label-icon">
                  </label>
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
              <div class="category-filter">
                  <input type="hidden" id="categoryInput" name="category" value="<%= request.getAttribute("category") != null ? request.getAttribute("category") : "전체보기" %>" />
                  <button type="button" class="category-button" data-category="전체보기">전체보기</button>
                  <button type="button" class="category-button" data-category="식료품">식료품</button>
                  <button type="button" class="category-button" data-category="생필품">생필품</button>
                  <button type="button" class="category-button" data-category="기타">기타</button>
              </div>
              <button type="submit" class="filter-submit-button">검색</button>
          </div>
      </form>

        <!-- 기존 물품 리스트 코드 -->
   <section class="product-list">
       <%
           List<Item> items = (List<Item>) request.getAttribute("items");
           if (items != null && !items.isEmpty()) {
               for (Item item : items) {
                   String defaultImagePath = request.getContextPath() + "/images/default-image.png";
       %>
                   <div class="product-card">
                   <!-- item.getItemId() 이 부분 수정함 -->
                   <a href="${pageContext.request.contextPath}/detail?itemId=${item.getItemId()}">
                      <!-- 하연수정 -->
                       <img src="<%= request.getContextPath() + "/uploaded/" + (item.getFileRealName() != null ? item.getFileRealName() : "default_item.png") %>" 
                       alt="상품 이미지" class="product-image">
                   </a>    
                       <h3><%=item.getTitle()%></h3>
                       <p>가격: <%=item.getPrice()%>원</p>
                       <p>현재 인원: <%=item.getParticipantsCount()%>/<%=item.getMaxParticipant()%>명</p>
                   </div>
       <%
               }
           } else {
       %>
               <div class="no-items">
                   <p>등록된 상품이 없습니다.</p>
               </div>
       <%
           }
       %>
   </section>

   <!-- 페이지네이션 -->
   <div class="pagination">
       <%
           Integer currentPage = (Integer) request.getAttribute("currentPage");
           Integer totalPages = (Integer) request.getAttribute("totalPages");
           String currentRegion = (String) request.getAttribute("region");
           String currentCategory = (String) request.getAttribute("category");
   
           currentPage = (currentPage != null) ? currentPage : 1;
           totalPages = (totalPages != null) ? totalPages : 1;
       %>
       <% if (currentPage > 1) { %>
           <a href="?page=<%=currentPage - 1%>&region=<%=currentRegion%>&category=<%=currentCategory%>" class="prev-page">이전</a>
       <% } %>
       <% for (int i = 1; i <= totalPages; i++) { %>
           <a href="?page=<%=i%>&region=<%=currentRegion%>&category=<%=currentCategory%>" class="<%= (i == currentPage) ? "active" : "" %>"><%=i%></a>
       <% } %>
       <% if (currentPage < totalPages) { %>
           <a href="?page=<%=currentPage + 1%>&region=<%=currentRegion%>&category=<%=currentCategory%>" class="next-page">다음</a>
       <% } %>
   </div>
    </main>
<script>
document.addEventListener('DOMContentLoaded', () => {
    const categoryButtons = document.querySelectorAll('.category-button');
    const categoryInput = document.getElementById('categoryInput');

    const selectedCategory = categoryInput.value;

    categoryButtons.forEach((button) => {
        if (button.getAttribute('data-category') === selectedCategory) {
            button.classList.add('selected');
        }
    });

    categoryButtons.forEach((button) => {
        button.addEventListener('click', () => {
            categoryInput.value = button.getAttribute('data-category');
            categoryButtons.forEach((btn) => btn.classList.remove('selected'));
            button.classList.add('selected');
        });
    });
});
</script>
</body>
</html>
