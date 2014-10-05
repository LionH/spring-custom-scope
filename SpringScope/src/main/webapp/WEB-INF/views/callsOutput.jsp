<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<meta charset="utf-8">
<title>Calls</title>
</head>
<body>

	<div class="page-header">
		<h1>Calls</h1>
	</div>

	<div class="header">
		<div class="col-md-12">This is an example of multithreaded spring conversation scope</div>
	</div>
	<br/>
	<br/>
	<div class="col-md-12">
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>Conversation</th>
				</tr>
			</thead>
			<c:forEach var="listValue" items="${tableView.entrySet()}">
				<tbody>
					<tr>
						<td><c:out value="${listValue.getKey()}" /></td>
						<td><c:out value="${listValue.getValue()}" /></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</body>
</html>
