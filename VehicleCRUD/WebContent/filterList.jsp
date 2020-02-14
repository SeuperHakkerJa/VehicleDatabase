<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Vehicle DataBase Application</title>
</head>
<body>
    <center>
        <h1>Vehicles Management</h1>
        <h2>
            <a href="new">Add New Vehicles</a>
            &nbsp;&nbsp;&nbsp;
            <a href="list">List All Vehicles</a>
            &nbsp;&nbsp;&nbsp;
            <a href="find">Add Filters</a>
        </h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Filtered Results</h2></caption>
            <tr>
                <th>Id</th>
                <th>Year</th>
                <th>Make</th>
                <th>Model</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="v" items="${filteredVehicle}">
                <tr>
                    <td><c:out value="${v.getID()}" /></td>
                    <td><c:out value="${v.getYear()}" /></td>
                    <td><c:out value="${v.getMake()}" /></td>
                    <td><c:out value="${v.getModel()}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${v.getID()}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${v.getID()}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>