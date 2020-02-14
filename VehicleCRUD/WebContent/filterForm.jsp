<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Vehicle Database Application</title>
</head>
<body>
	<center>
		<h1>Vehicles Management</h1>
		<h2>
			<a href="new">Add New Vehicles</a> &nbsp;&nbsp;&nbsp; <a href="list">List
				All Vehicles</a>

		</h2>
	</center>
	<div align="center">

		<form action="filter" method="post">

			<table border="1" cellpadding="5">
				<caption>
					<h2>Find Vehicle</h2>
				</caption>




				<tr>
					<th>Id:</th>
					<td><input type="text" name="Id" size="45"
						value="<c:out value="0" />" /></td>
				</tr>



				<tr>
					<th>Year:</th>
					<td><input type="text" name="Year" size="5"
						value="<c:out value="0" />" /></td>
				</tr>
				
				
				<tr>
					<th>Make:</th>
					<td><input type="text" name="Make" size="45"
						value="<c:out value='' />" /></td>
				</tr>
				
				
				<tr>
					<th>Model:</th>
					<td><input type="text" name="Model" size="45"
						value="<c:out value='' />" /></td>
				</tr>
				
				
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
