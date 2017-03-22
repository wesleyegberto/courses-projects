<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<tags:pageTemplate titulo="${produto.titulo}">
	<%-- o form do spring já adiciona o token CSRF --%>
	<form:form servletRelativeAction="/carrinho/add" method="post">
		<input type="hidden" name="produtoId" value="${produto.id}" />
		<div class="row">
			<div class="col-md-12">
				<h1>${produto.titulo}</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<p>${produto.descricao}</p>
				<p>Número de páginas: ${produto.paginas}</p>
				<p>Data de lançamento: <fmt:formatDate pattern="dd/MM/yyyy" value="${produto.dataLancamento}" /></p>
			</div>
			<div class="col-md-6">
				<img class="img-responsive center-block" height="250" width="190" 
					src="https://cdn.shopify.com/s/files/1/0155/7645/products/arquitetura-java-featured_large.png?v=1411489903" />
			</div>
		</div>			
		<div class="row">
			<c:forEach items="${produto.precos}" var="preco">
				<div class="col-md-3">
					<div class="form-group">
						<input type="radio" name="tipoSelec" value="${preco.tipo}" required="required" checked="checked" />
						<label class="control-label" for="tipoSelec">${preco.tipo}</label>
						<span>R$ ${preco.preco}</span>
					</div>
				</div>
			</c:forEach>
			<div class="col-md-3">
				<input type="submit" class="btn btn-success" value="Adicionar ao carrinho" />
			</div>
		</div>
	</form:form>
</tags:pageTemplate>