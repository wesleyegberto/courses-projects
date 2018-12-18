<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<tags:pageTemplate titulo="Bookommerce - Listagem de Produtos">
    <jsp:attribute name="extraScripts">
        <script> console.log("Cadastrando novo produto"); </script>
    </jsp:attribute>
    <jsp:body>
    	<%-- Parte específica que será inserido no jsp:doBody --%>
		<form:form servletRelativeAction="/produtos" enctype="multipart/form-data" method="post" commandName="produto">
		    <div class="row">
		    	<div class="col-md-6">
		    		<div class="form-group col-mg-12">
				        <label for="titulo">Título: </label>
				        <form:input path="titulo" cssClass="form-control"/>
				        <form:errors path="titulo" />
			        </div>
			        <div class="row">
				    	<div class="form-group col-md-6">
					        <label>Páginas: </label>
					        <form:input path="paginas" cssClass="form-control" />
					        <form:errors path="paginas" />
				        </div>
				    	<div class="form-group col-md-6">
					    	<label>Data de lançamento: </label>
					    	<form:input path="dataLancamento" cssClass="form-control" />
					    	<form:errors path="dataLancamento" />
				    	</div>
			    	</div>
				    <div class="row">
				    	<div class="form-group col-md-12">
						    <label>Sumário</label>
						    <input name="foto" type="file" class="form-control" />
					    </div>
					</div>
		        </div>
		    	<div class="form-group col-md-6">
			        <label>Descrição: </label>
			        <form:textarea path="descricao" rows="10" cols="20" cssClass="form-control"/>
			        <form:errors path="descricao" />
		        </div>
		    </div>
	    	<div class="row">
			    <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
		    		<div class="form-group col-md-4">
			            <label>${tipoPreco}: </label>
			            <form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
			            <form:input path="precos[${status.index}].preco" cssClass="form-control" />
		            </div>
			    </c:forEach>
	        </div>
		    <button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</jsp:body>
</tags:pageTemplate>