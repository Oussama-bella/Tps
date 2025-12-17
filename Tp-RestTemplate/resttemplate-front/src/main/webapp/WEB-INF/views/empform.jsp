<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h1>Add New Employee</h1>

<c:if test="${not empty errorMessage}">
    <div style="color:red">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color:green">${successMessage}</div>
</c:if>

<form:form method="post" action="${pageContext.request.contextPath}/save" modelAttribute="empVo">
    <table>
        <tr>
            <td>Name :</td>
            <td><form:input path="firstName" /></td>
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
            <td></td>
            <td><input type="submit" value="Save" /></td>
        </tr>
    </table>
</form:form>

<p><a href="${pageContext.request.contextPath}/viewemp">View Employees</a></p>
</body>
</html>
