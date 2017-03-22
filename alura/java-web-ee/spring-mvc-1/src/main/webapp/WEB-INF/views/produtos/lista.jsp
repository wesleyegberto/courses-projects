<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tags:pageTemplate titulo="Bookommerce - Listagem de Produtos">
	<h1>Produtos cadastrados</h1>
	
	<c:if test="${not empty mensagem}">
		<div class="alert alert-info">
			<p>${mensagem}</p>
		</div>
	</c:if>
	
	<table class="table table-bordered table-striped table-hover">
		<tr>
			<td>Título</td>
			<td>Descrição</td>
			<td>Páginas</td>
		</tr>
		<c:forEach items="${listaProdutos}" var="produto">
			<tr>
				<td>
					<a href="/spring-mvc/produtos/detalhe/${produto.id}">${produto.titulo}</a>
				</td>
				<td>${produto.descricao}</td>
				<td>${produto.paginas}</td>
			</tr>
		</c:forEach>
	</table>
</tags:pageTemplate>