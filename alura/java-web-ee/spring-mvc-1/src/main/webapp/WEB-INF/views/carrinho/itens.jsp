<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<tags:pageTemplate titulo="Bookommerce - Carrinho">
	<h1>Meu carrinho</h1>
	<c:if test="${not empty mensagem}">
		<div class="alert alert-info">
			<p>${mensagem}</p>
		</div>
	</c:if>
	
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Produto</th>
				<th>Valor unit. (R$)</th>
				<th>Quantidade</th>
				<th>Subtotal (R$)</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${carrinhoCompras.itens}" var="item">
				<tr>
					<td>${item.produto.titulo} - ${item.tipoPreco}</td>
					<td>${item.valorUnit}</td>
					<td>${carrinhoCompras.getQuantidadeDo(item)}</td>
					<td>${carrinhoCompras.getSubtotalDo(item)}</td>
					<td>
						<form:form servletRelativeAction="/carrinho/remover" method="post">
							<input type="hidden" name="produtoId" value="${item.produto.id}" />
							<input type="hidden" name="tipoPreco" value="${item.tipoPreco}" />
							<input type="submit" class="btn btn-danger btn-sm" value="Remover item" />
						</form:form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="3" align="right"><label>Total:</label></td>
				<td colspan="2">${carrinhoCompras.valorTotalItens}</td>
			</tr>
		</tfoot>
	</table>
	
	<div class="row">
		<form:form class="col-md-12" servletRelativeAction="/pagamento/finalizar" method="post">
			<a class="btn btn-default" href="<c:url value="/produtos"/>">Continuar comprando...</a>
	    	<input type="submit" class="btn btn-success" name="checkout" value="Finalizar compra" />
    	</form:form>
    </div>
</tags:pageTemplate>