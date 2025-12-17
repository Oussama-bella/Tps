<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h1>Edit Employee</h1>

<c:if test="${not empty errorMessage}">
    <div style="color:red">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color:green">${successMessage}</div>
</c:if>

<form:form method="POST" action="${pageContext.request.contextPath}/editsave" modelAttribute="empVo">
    <table>
        <tr>
            <td></td>
            <td><form:hidden path="id" /></td>
        </tr>
        <tr>
            <td>Name : </td>
            <td><form:input path="firstName"  /></td>
        </tr>
        <tr>
            <td>Salary :</td>
            <td><form:input path="salaire" /></td>
        </tr>
        <tr>
            <td>Fonction :</td>
            <td><form:input path="fonction" /></td>
        </tr>
        <tr>
            <td> </td>
            <td><input type="submit" value="Edit Save" /></td>
        </tr>
    </table>
</form:form>
<p><a href="${pageContext.request.contextPath}/viewemp">Retour</a></p>
</body>
</html>
