<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 클라이언트를 "/home"으로 리다이렉트
    response.sendRedirect(request.getContextPath() + "/home");
%>
