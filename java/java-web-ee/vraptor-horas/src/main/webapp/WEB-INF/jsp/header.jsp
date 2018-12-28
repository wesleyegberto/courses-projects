<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Título</title>
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet" />
    <link href="<c:url value='/css/site.css'/>" rel="stylesheet" />
</head>
<body>
    <header>

    </header>
    <div class="container">
	    <nav>
	        <ul class="nav nav-tabs">
	            <li><a href="<c:url value='/' />">Home</a></li>
	            <li><a href="${linkTo[UsuarioController].lista()}">Usuarios</a></li>
	            <li><a href="${linkTo[HoraLancadaController].lista()}">Horas Cadastradas</a></li>
	            <li><a href="${linkTo[HoraLancadaController].form()}">Cadastrar Horas</a></li>
	        </ul>
	    </nav>
        <main class="col-sm-8">