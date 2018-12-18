<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:url value="/" var="rootContext" />
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${rootContext}">Spring MVC</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${rootContext}">Home</a></li>
				<security:authorize access="isAuthenticated()">
					<li><a href="${rootContext}produtos/form">Cadastrar produto</a></li>
				</security:authorize>
				<li><a href="${rootContext}produtos">Produtos cadastrados</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<security:authorize access="isAuthenticated()">
  					<li><a href="#"><security:authentication property="principal.username"/></a></li>
 				</security:authorize>
				<li><a href="${rootContext}carrinho">Carrinho <span class="badge">${carrinhoCompras.quantidadeTotalItens}</span></a></li>
				<security:authorize access="isAuthenticated()">
					<li><a href="${rootContext}logout">Sair</a></li>
				</security:authorize>
				<li><a href="${rootContext}?locale=pt">Português</a></li>
				<li><a href="${rootContext}?locale=en">English</a></li>
			</ul>
		</div>
	</div>
</nav>