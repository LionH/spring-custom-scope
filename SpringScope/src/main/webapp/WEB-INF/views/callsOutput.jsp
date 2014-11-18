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
<style type="text/css">
body {
	padding-top: 130px;
}
</style>

<meta charset="utf-8">
<title>Calls</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header col-lg-1">
				<a class="navbar-brand" href="#"> <span
					class="glyphicon glyphicon-leaf"></span>
				</a>
			</div>
			<div class="navbar-header col-lg-11">
				<ul class="nav navbar-nav col-lg-12">
					<li class="active col-lg-12"><a class="navbar-brand col-lg-12"
						href="/SpringScope/call?username=lio">Perform a new scoped
							call</a></li>
				</ul>
			</div>
			<div class="well col-lg-12">This is an example of multithreaded
				spring conversation scope</div>
		</div>
	</nav>
	<div class="container-fluid">
		<table class="table table-condensed col-lg-12">
			<thead>
				<tr>
					<th><span class="glyphicon glyphicon-th-list"></span></th>
					<th>Conversation</th>
				</tr>
			</thead>
			<c:forEach var="listValue" items="${tableView.entrySet()}">
				<tbody class="table-striped table-hover">
					<tr>
						<td><c:out value="${listValue.getKey()}" /></td>
						<td>
							<ul class="list-group">
								<li class="list-group-item">Words <span class="badge">${listValue.getValue().size()}</span></li>
								<c:forEach var="word" items="${listValue.getValue()}">
									<li class="list-group-item"><c:out value="${word}" /></li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</body>
</html>
