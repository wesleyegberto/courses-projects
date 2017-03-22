<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tags:pageTemplate titulo="Bookommerce">
	<div class="jumbotron">
		<h1><fmt:message key="i18n.home.mensagem" /></h1>
	</div>
	<div class="row">
		<div class="col-md-12">
			<a href="${rootScope}produtos" class="btn btn-default">Listar produtos cadastrados</a>
			<a href="${rootScope}produtos/form" class="btn btn-default">Cadastrar produto</a>
		</div>
	</div>
	<hr />
	<div class="row">
		<c:forEach items="${produtos}" var="produto">
			<div class="col-md-3 pushleft">
				<div class="panel panel-default">
  					<div class="panel-body">
						<p style="font-weight:bold;">${produto.titulo}</p>
						<a class="btn btn-warning btn-block" href="${rootScope}/produtos/detalhe/${produto.id}">Comprar</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</tags:pageTemplate>