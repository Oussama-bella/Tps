<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Liste des employés</h2>

<c:if test="${not empty errorMessage}">
    <div style="color:red; font-weight:bold;">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color:green; font-weight:bold;">${successMessage}</div>
</c:if>

<table border="1">
    <tr>
        <th>ID</th><th>Nom</th><th>Fonction</th><th>Salaire</th><th>Actions</th>
    </tr>
    <c:forEach var="e" items="${list}">
        <tr>
            <td>${e.id}</td>
            <td>${e.firstName}</td>
            <td>${e.fonction}</td>
            <td>${e.salaire}</td>
            <td>
                <a href="${pageContext.request.contextPath}/editemp/${e.id}">Edit</a>
                <a href="${pageContext.request.contextPath}/deleteemp/${e.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="${pageContext.request.contextPath}/empform">Ajouter un employé</a></p>
</body>
</html>
