<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tags:pageTemplate titulo="Bookommerce">
	<h1>Ops, ocorreu um erro! =s</h1>
	<div>
		<c:if test="${exception != null}">
			<p class="text-danger">${exception.message}</p>
		</c:if>
	</div>
</tags:pageTemplate>