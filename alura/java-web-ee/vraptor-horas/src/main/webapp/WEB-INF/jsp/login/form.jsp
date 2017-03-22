<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="alura" %>

<c:import url="/WEB-INF/jsp/header.jsp"/>

<form action="${linkTo[LoginController].login(null, null)}" method="post">
    <alura:validationMessage name="login_invalido"/>

    <label for="login">Login:</label>
    <input type="text" id="login" name="login" class="form-control"/>

    <label for="senha">Senha:</label>
    <input type="password" id="senha" name="senha" class="form-control"/>

    <input type="submit" value="Autenticar" class="btn"/>
</form>

<c:import url="/WEB-INF/jsp/footer.jsp"/>