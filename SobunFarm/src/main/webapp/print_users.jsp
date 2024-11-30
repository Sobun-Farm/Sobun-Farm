<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 목록</title>
</head>
<body>
    <h1>현재 저장된 사용자 목록</h1>
    <table border="1">
        <thead>
            <tr>
                <th>이메일</th>
                <th>닉네임</th>
                <th>지역</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                if (users != null && !users.isEmpty()) {
                    for (User user : users) {
            %>
                        <tr>
                            <td><%= user.getEmail() %></td>
                            <td><%= (user.getNickname() != null) ? user.getNickname() : "닉네임 없음" %></td>
                            <td><%= (user.getRegion() != null) ? user.getRegion() : "지역 없음" %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="3">저장된 사용자가 없습니다.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
