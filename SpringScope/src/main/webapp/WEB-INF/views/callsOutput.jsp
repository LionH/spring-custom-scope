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

<link rel="stylesheet" href="css/slick.grid.css" type="text/css" />

<!-- Latest compiled and minified JavaScript -->
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.event.drag-2.2.js"></script>
<script type="text/javascript" src="js/slick.core.js"></script>
<script type="text/javascript" src="js/slick.grid.js"></script>
<script type="text/javascript" src="js/bootstrap-slickgrid.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/jolokia.js"></script>
<script type="text/javascript" src="js/jolokia-simple.js"></script>
<script type="text/javascript">

	var reqCount=0;
	var conversations=[];
	
	window.setInterval(jmxRequest, 500);

	function jmxRequest() {

		// Create a new jolokia client accessing the agent on the same
		// Host which serves this document:
		var j4p = new Jolokia("/jolokia");

		// Request the memory information asynchronously and print it on
		// the console
		j4p.request({
			type : "read",
			mbean : "java.lang:type=Memory",
			attribute : "HeapMemoryUsage"
		}, {
			success : function(response) {
				console.log("Heap-Memory used: " + response.value.used);
			}
		});

		// Request the memory information asynchronously and print it on
		// the console
		j4p.request({
			type : "read",
			mbean : "bean:name=app-monitoring",
			attribute : "MonitorConversations"
		}, {
			success : function(response) {
				conversations=JSON.stringify(response.value);
				refreshProgressBar();
			}
		});
	}

	var tableView = [ {
		id : "none",
		sender : "none",
		conversation : "none",
		content : "none"
	} ];

	var grid;
	var columns = [ {
		id : "id",
		name : "#",
		field : "id"
	}, {
		id : "sender",
		name : "Sender",
		field : "sender"
	}, {
		id : "conversation",
		name : "Conversation",
		field : "conversation"
	}, {
		id : "content",
		name : "Content",
		field : "content"
	} ];
	var options = {
		enableCellNavigation : true,
		enableColumnReorder : false
	};
	$(function() {
		var data = tableView;
		grid = new Slick.Grid("#myGrid", data, columns, options);
		grid.autosizeColumns();
	})

	function getTableData() {
		reqCount++;
		
		// Assign handlers immediately after making the request,
		// and remember the jqXHR object for this request
		var jqxhr = $.ajax("restCalls").done(function(result) {
			console.log("getTableData success");
			tableView = result;
			grid.setData(tableView, false);
			grid.render();
		}).fail(function() {
			console.log("getTableData failed");
		}).always(function() {
			grid.scrollRowToTop(tableView.length);
			reqCount--;
			console.log("getTableData complete");
		});
	}
	
	function refreshProgressBar() {
		console.log("refreshing Progress Bar with conversations: " + conversations.length + ", reqCount: " +  reqCount);
		var newValue = (100*(reqCount/conversations.length));
		console.log(newValue);
		$("#progressBar").attr("aria\-valuenow", 100);
		$("#progressBar").css("width", newValue);
	}
</script>
<style type="text/css">
body {
	padding-top: 110px;
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
					<li><a class="navbar-brand" href="#" onclick="getTableData()">Perform
							a new scoped call</a></li>
				</ul>
			</div>
			<div class="navbar-text navbar-right">This is an example of
				multithreaded spring conversation scope</div>
		</div>
	</nav>
	<nav class="navbar navbar-inverse navbar-fixed-bottom"
		role="navigation">
		<div class="container-fluid">
			<div class="col-lg-12">
				<!-- Standard button -->
				<button type="button" class="btn btn-default"
					onclick="javascript:jmxRequest()">Request</button>
			</div>
			<div class="progress">
				<div class="progress-bar progress-bar-success" role="progressbar"
					aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
					style="width: 0%">
					<span class="sr-only">40% Complete (success)</span>
				</div>
			</div>
			<div class="progress">
				<div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
					aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
					style="width: 0%">
					<span class="sr-only">40% Complete (success)</span>
				</div>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div id="myGrid" style="width: 100%;height:600px;" class="grid"></div>
	</div>
</body>
</html>
