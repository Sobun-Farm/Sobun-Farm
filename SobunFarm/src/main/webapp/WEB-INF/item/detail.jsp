<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.domain.Item" %>
<%@ page import="model.domain.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <!-- 헤더 파일 포함 -->
    <jsp:include page="/WEB-INF/views/header.jsp" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css" />
    <!-- <link rel="stylesheet" href="css/detail.css" />  -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/detail.css" />
</head>
<body>
	<div class="container">
		<!-- container 요소 (box1, box2)는 가로 정렬 -->
		<!-- box1은 상품 이미지와 프로필, 닉네임, 소분 성공 개수, 지역 -->
		<div class="box1"> 
			<%
    		Item item = (Item) request.getAttribute("item");
			User user = (User) request.getAttribute("user");
			int perPersonPrice = (int) (item.getPrice() / item.getMaxParticipant());
			int price = (int) (item.getPrice());
			%>
			
			<img src="<%= request.getContextPath() + "/uploaded/" + (item.getFileRealName() != null ? item.getFileRealName() : "default_item.png") %>" class="detail_img">
			<div class="profile_detail_box">
				<img src="<%=request.getContextPath()%>/images/profile_ex.png" class="profile_img">
				<div class="profile_detail">
				<%= user.getNickname() %> <!-- (소분 성공: 76개) --><br>
				<%= user.getRegion() %>
				</div>
			</div>
		</div>
		
		<div class="box2">
		
		<div class="box2-detail1">
    		<div>
        	<img src="<%=request.getContextPath()%>/images/gray-triangle.png" id="gray-triangle">
        	<%= item.getCategory() %>
        	<img src="<%=request.getContextPath()%>/images/black-triangle.png" id="black-triangle">
        	<span class="black-text"><%= item.getItemName() %></span>
    		</div>
    		<div id="count-participant">
        	참여 인원 <%= item.getParticipantsCount() %>/<%= item.getMaxParticipant() %> (명)
    		</div>
    	</div>
    	
    	<div id="title">
    	<%= item.getTitle() %>
    	</div>
    	
    	<div id="price">
    	인당 <%= perPersonPrice %>원 (총 <%= price %>원)
    	</div>
    	
    	<div id="deadline">
    	마감일 <%= item.getDeadline() %>
    	</div>
    	
    	<div id="detail-info">
    	￭ 구매 장소	&nbsp; &nbsp; &nbsp;	<%= item.getPurchaseLocation() %><br>
		￭ 거래 장소	&nbsp; &nbsp; &nbsp;	<%= item.getTransactionLocation() %><br>
		￭ 거래 시간	&nbsp; &nbsp; &nbsp;	<%= item.getTransactionTime() %>
    	</div>
    	
    	<div class="button-container">
    	
    	<button class="button2">거래 시작</button>
    	</div>
    	
		</div>
	</div>
	
	<div class="detail-form">
	
	<div class="line-container">
    <hr class="line">
	</div>

	<h1>상세 설명</h1>
	<div class = "item-detail">
	<%= item.getDescription() %>
	</div>
	</div>
	

</body>
</html>