<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.domain.Item" %>
<%@ page import="java.util.Base64" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>소분팜</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
</head>
<body>
    <header>
        <div class="logo">
            <a href="<%=request.getContextPath()%>/user?action=homePage">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="소분팜 로고" width="300" height="auto">
            </a>
            <span>소분팜</span>
        </div>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/user?action=homePage">
                    <img src="${pageContext.request.contextPath}/images/home.png" alt="Home">
                </a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/chat.png" alt="Chat"></a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/upload.png" alt="Upload"></a></li>
            </ul>
        </nav>
        <div class="search-login">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
            <button><img src="${pageContext.request.contextPath}/images/search.png" alt="Search"></button>
        </div>
                <div class="user">
            <span><a href="<%=request.getContextPath()%>/user?action=mainPage"><img src="${pageContext.request.contextPath}/images/user.png" alt="user"></a></span>
        </div>
        <div class="login">
            <% 
                Object loggedInUser = session.getAttribute("loggedInUser");
                if (loggedInUser != null) { 
            %>
                <span><a href="<%=request.getContextPath()%>/user?action=logout">로그아웃</a></span>
            <% 
                } else { 
            %>
                <span><a href="<%=request.getContextPath()%>/user?action=loginPage">로그인</a></span>
                <span> / </span>
                <span><a href="<%=request.getContextPath()%>/user?action=registerPage">회원가입</a></span>
            <% 
                } 
            %>
        </div>
    </header>
    
    <main>
        <!-- 필터 섹션 -->
        <form id="filterForm" action="<%=request.getContextPath()%>/home" method="get">
		    <div class="filter-container">
		        <div class="region-filter">
		            <label for="region">
		                <img src="${pageContext.request.contextPath}/images/region.png" alt="지역 선택" class="label-icon">
		            </label>
		            <select id="region" name="region">
		                <option value="" disabled>구 선택</option>
					      <option value="강남구" selected>강남구</option>
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
					      <option value="성북구">성북구</option>
					      <option value="송파구">송파구</option>
					      <option value="양천구">양천구</option>
					      <option value="영등포구">영등포구</option>
					      <option value="용산구">용산구</option>
					      <option value="은평구">은평구</option>
					      <option value="종로구">종로구</option>
					      <option value="중구">중구</option>
					      <option value="중랑구">중랑구</option>
		            </select>
		        </div>
		
		        <div class="category-filter">
		            <input type="hidden" id="categoryInput" name="category" value="전체보기" />
		            <button type="button" class="category-button" data-category="전체보기">전체보기</button>
		            <button type="button" class="category-button" data-category="식료품">식료품</button>
		            <button type="button" class="category-button" data-category="생필품">생필품</button>
		            <button type="button" class="category-button" data-category="기타">기타</button>
		        </div>
		
		        <button type="submit" class="filter-submit-button">검색</button>
		    </div>
		</form>
       
        
        <!-- 물품 리스트 -->
        <section class="product-list">
            <%
                List<Item> items = (List<Item>) request.getAttribute("items");
                if (items != null && !items.isEmpty()) {
                    for (Item item : items) {
            %>
                        <div class="product-card">
                            <h3><%=item.getTitle()%></h3>
                            <p>가격: <%=item.getPrice()%>원</p>
                            <p>현재 인원: <%=item.getParticipantsCount()%>/<%=item.getMaxParticipant()%>명</p>
                        </div>
            <%
                    }
                } else {
            %>
                <p>등록된 상품이 없습니다.</p>
            <%
                }
            %>
        </section>

        <!-- 페이지네이션 -->
        <div class="pagination">
            <%
                // 페이지네이션 기본값 설정
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                
                currentPage = (currentPage != null) ? currentPage : 1;
                totalPages = (totalPages != null) ? totalPages : 1;
            %>
            
            <!-- 맨 앞으로 이동 -->
            <% if (currentPage > 1) { %>
                <a href="?page=1" class="first-page"><<</a>
            <% } %>
            
            <!-- 이전 페이지 -->
            <% if (currentPage > 1) { %>
                <a href="?page=<%=currentPage - 1%>" class="prev-page"><</a>
            <% } %>
            
            <!-- 페이지 번호 -->
            <% 
                for (int i = 1; i <= totalPages; i++) { 
                    String activeClass = (i == currentPage) ? "active" : "";
            %>
                <a href="?page=<%=i%>" class="<%=activeClass%>"><%=i%></a>
            <% 
                } 
            %>
            
            <!-- 다음 페이지 -->
            <% if (currentPage < totalPages) { %>
                <a href="?page=<%=currentPage + 1%>" class="next-page">></a>
            <% } %>
            
            <!-- 맨 끝으로 이동 -->
            <% if (currentPage < totalPages) { %>
                <a href="?page=<%=totalPages%>" class="last-page">>></a>
            <% } %>
        </div>
    </main>
</body>
<script>
// 카테고리 버튼 클릭 이벤트 및 hidden input 값 설정
document.addEventListener('DOMContentLoaded', () => {
    const categoryButtons = document.querySelectorAll('.category-button');
    const categoryInput = document.getElementById('categoryInput');

    categoryButtons.forEach((button) => {
        button.addEventListener('click', () => {
            // 클릭된 버튼의 카테고리 값을 hidden input에 설정
            categoryInput.value = button.getAttribute('data-category');

            // 모든 버튼에서 selected 클래스를 제거하고 클릭된 버튼에 추가
            categoryButtons.forEach((btn) => btn.classList.remove('selected'));
            button.classList.add('selected');
        });
    });
});
</script>
</html>
