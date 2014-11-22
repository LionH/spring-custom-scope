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
<script type="text/javascript" src="js/sockjs.min.js"></script>
<script type="text/javascript" src="js/jolokia.js"></script>
<script type="text/javascript" src="js/jolokia-simple.js"></script>
<script type="text/javascript">

	var reqCount=0;
	var conversations=[];
	
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
				conversations=response.value;
				//conversations=response.value;
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
	
	function call() {
		reqCount++;
		
		// Assign handlers immediately after making the request,
		// and remember the jqXHR object for this request
		var jqxhr = $.ajax("restCall").done(function(result) {
			console.log("call success");
		}).fail(function() {
			console.log("call failed");
		}).always(function() {
			reqCount--;
		});
	}

	function getTableData() {
		console.log("refreshing table data with incoming calls");
		// Assign handlers immediately after making the request,
		// and remember the jqXHR object for this request
		var jqxhr = $.ajax("restCalls").done(function(result) {
			tableView = result;
			grid.setData(tableView, false);
			grid.render();
			grid.scrollRowToTop(tableView.length);
		}).fail(function() {
			console.log("getTableData failed");
		}).always(function() {
			console.log("getTableData complete");
		});
	}
	
	function refreshProgressBar() {
		//var newValue = (reqCount/(conversations.length+1));
		var newValue = conversations.length;
		$("#progressBarValue").html(newValue);
		$("#progressBar").width(newValue * 10 + "%");
		$("#progressBar").attr("aria-valuenow", newValue);
		$("#progressBar").html(newValue + "%");
	}
	
	window.setInterval(dataRequest, 300);
	
	var mouseDown = false;
	
	function dataRequest() {
		jmxRequest();
		if (conversations.length>0) {
			getTableData();
		}
		if (mouseDown) {
			call();
		}
	}
	
	var socket = new SockJS('/SpringScope/wsCalls');
	var client = Stomp.over(socket);

	client.connect('user', 'password', function(frame) {

	  client.subscribe("/data", function(message) {
	    console.log(message);
	  });

	});
	
	function mouseDownHandler() {
		mouseDown=true;
	}
	
	function mouseUpHandler() {
		mouseDown=false;
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
					<li><a class="navbar-brand" href="#" onmousedown="mouseDownHandler()" onmouseup="mouseUpHandler()">Perform
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
					aria-valuenow="0" aria-valuemin="0" aria-valuemax="10"
					style="width: 100%">
					<span class="sr-only">100% Complete (success)</span>
				</div>
			</div>
			<div class="progress">
				<div id="progressBar" class="progress-bar progress-bar-striped active" role="progressbar"
					aria-valuenow="0" aria-valuemin="0" aria-valuemax="10"
					style="width: 100%">
					10%
				</div>
			</div>
			<div id="progressBarValue" class="label"/>
		</div>
	</nav>
	<div class="container-fluid">
		<div id="myGrid" style="width: 100%;height:600px;" class="grid"></div>
	</div>
</body>
</html>
