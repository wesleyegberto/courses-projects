<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Inclusão de Produto</title>
	<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="../base.css">
</head>
<body>
	<div class="container">
		<h1>Adicionar Produto</h1>
		<form method="post" action="<c:url value='/produto/adiciona'/>">
			<label>Nome:</label>
			<input class="form-control" type="text" name="produto.nome" value="${produto.nome}" />
			<label>Valor:</label>
			<input class="form-control" type="text" name="produto.valor" value="${produto.valor}" />
			<label>Quantidade:</label>
			<input class="form-control" type="text" name="produto.quantidade" value="${produto.quantidade}" />
			<button type="submit" class="btn btn-primary">Adicionar</button>
		</form>
		
		<c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <c:forEach var="error" items="${errors}">
                    ${error.category} - ${error.message}<br />
                </c:forEach>
            </div>
        </c:if>
	</div>
</body>
</html>