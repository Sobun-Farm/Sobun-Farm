    <%@ page contentType="text/html; charset=UTF-8" %>
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
                <li><a href="<%=request.getContextPath()%>/new_item">
            <img src="${pageContext.request.contextPath}/images/upload.png" alt="Upload">
        </a></li>
            </ul>
        </nav>
         <div class="search-login" style="opacity: 0; pointer-events: none;">
		    <input type="text" class="search-input" placeholder="검색어를 입력하세요.">
		    <button><img src="${pageContext.request.contextPath}/images/search.png" alt="Search"></button>
		</div>
        <div class="user">
            <span><a href="#"><img src="${pageContext.request.contextPath}/images/user.png" alt="User"></a></span>
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