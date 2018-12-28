<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Lista de Produtos</title>
	<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<p>Listagem de produtos do ${usuarioLogado.usuario.nome}</p>
		<div>
			<a href="<c:url value='/produto/formulario'/>" class="btn btn-default">Adicionar mais produtos!</a>
		</div>
		<c:if test="${not empty mensagem}">
		    <div class="alert alert-info">${mensagem}</div>
		</c:if>
		<table class="table table-stripped table-hover table-bordered">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Valor</th>
					<th>Quantidade</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtoList}" var="produto">
					<tr>
						<td>${produto.nome}</td>
						<td>${produto.valor}</td>
						<td>${produto.quantidade}</td>
						<td>
        					<a class="btn btn-info" href="<c:url value='/produto/enviaPedidoEstoque?produto.nome=${produto.nome}'/>">Solicitar estoque</a>
							<a class="btn btn-danger" href="<c:url value='/produto/remove?produto.id=${produto.id}'/>">Remover</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>