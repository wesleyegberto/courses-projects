<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/header.jsp"/>

<h2>Relatório de horas cadastradas</h2>

<table class="table">
    <thead>
        <tr>
            <th>Data</th>
            <th>Horas Normais</th>
            <th>Horas Extras</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="horasDoDia" items="${relatorio.horasPorDia}">
            <tr>
                <td>${horasDoDia.data.time}</td>
                <td>${horasDoDia.horasNormais}</td>
                <td>${horasDoDia.horasExtras}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<c:import url="/WEB-INF/jsp/footer.jsp"/>s