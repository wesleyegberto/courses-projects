<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="titulo" required="true" %>
<%@ attribute name="extraScripts" fragment="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <title>${titulo}</title>
	<c:url value="/" var="rootScope" />
	<link rel="stylesheet" href="${rootScope}resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${rootScope}resources/css/bootstrap-theme.min.css" />
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/views/_menu.jsp" />
		<jsp:doBody />
	</div>
	
	<%-- Inclusão de scripts (para usar é preciso ter jsp:body em quem passar o atributo extraScripts) --%>
	<jsp:invoke fragment="extraScripts"></jsp:invoke>
</body>
</html>