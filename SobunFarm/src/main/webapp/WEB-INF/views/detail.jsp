<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<img src="<%=request.getContextPath()%>/images/detail_img.png" class="detail_img">
			<div class="profile_detail_box">
				<img src="<%=request.getContextPath()%>/images/profile_ex.png" class="profile_img">
				<div class="profile_detail">
				월곡라면킬러 (소분 성공: 76개)<br>
				성북구
				</div>
			</div>
		</div>
		
		<div class="box2">
		
		<div class="box2-detail1">
    		<div>
        	<img src="<%=request.getContextPath()%>/images/gray-triangle.png" id="gray-triangle">
        	식료품
        	<img src="<%=request.getContextPath()%>/images/black-triangle.png" id="black-triangle">
        	<span class="black-text">신라면</span>
    		</div>
    		<div id="count-participant">
        	참여 인원 2/3 (명)
    		</div>
    	</div>
    	
    	<div id="title">
    	신라면(컵)박스로 구매해서 10개씩 나누실 분 구합니다!
    	</div>
    	
    	<div id="price">
    	인당 7,000원 (총 21,000원)
    	</div>
    	
    	<div id="deadline">
    	마감일 2025년 1월 2일
    	</div>
    	
    	<div id="detail-info">
    	￭ 구매 장소	&nbsp; &nbsp; &nbsp;	쿠팡<br>
		￭ 거래 장소	&nbsp; &nbsp; &nbsp;	미정<br>
		￭ 거래 날짜	&nbsp; &nbsp; &nbsp;	미정<br>
		￭ 거래 시간	&nbsp; &nbsp; &nbsp;	미정
    	</div>
    	
    	<div class="button-container">
    	<button class="button1" onclick="location.href='<%=request.getContextPath()%>/edit';">거래 수정</button>

    	<button class="button2">거래 파기</button>
    	</div>
    	
		</div>
	</div>
	
	<div class="detail-form">
	
	<div class="line-container">
    <hr class="line">
	</div>

	<h1>상세 설명</h1>
	<div class = "item-detail">
	쿠팡에서 라면 사서 나누실 분 구해요~ <br>
	시간, 장소 조율해서 가능합니다~ <br>
	소분 장소는 동덕여대 주변에서 거래 가능하신 분들 위주면 좋겠네요. <br>
	</div>
	</div>
	

</body>
</html>