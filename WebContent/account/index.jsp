<%@page import="database.TaskData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard. / Put the Internet to work for you.</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="/res/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/res/css/footer.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<div id="wrap">

	<%@ include file="/component/nav.jsp"%>
	<%
		int taskNumber = TaskData.getAllTaskNumber(userID);
		int runningTaskNumber = TaskData.getAllRunningTaskNumber(userID);
	%>

	<div style="background-color: #EBEBEB; margin-top: -20px; height: 80px;">
		<div class="container" style="max-width: 980px">
				<header>
					<h1>Dashboard</h1>
				</header>
		</div>
	</div>
	<div class="container" style="max-width: 980px">
		<div style="height: 10px"></div>
		
		<%if (request.getParameter("sucess")!= null) {
		%>
		<div class="alert alert-success">New task created! :)
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button></div>
		<%} %>
		
		<%if (request.getParameter("fail")!= null) {
		%>
		<div class="alert alert-warning">Sorry, not enough points for a new task. :(
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button></div>
		<%} %>
		
		<div class="row marketing">
			<div class="col-lg-3">
				<header>
					<h3>All tasks</h3>
				</header>
				<div style="color: #428bca; font-size: 45px; font-weight: bold;"><%out.print(taskNumber);%></div>
				<a href="/task/browse.jsp" class="btn btn-primary btn-lg active"
					role="button">Browse</a>
			</div>
			<div class="col-lg-3">
				<header>
					<h3>Running tasks</h3>
				</header>
				<div style="color: #428bca; font-size: 45px; font-weight: bold;"><%out.print(runningTaskNumber);%></div>
				<a href="/task/create.jsp" class="btn btn-primary btn-lg active"
					role="button">Create</a>
			</div>
		</div>
		<hr />
	</div>
	<div class="container" style="max-width: 980px">
		<header>
			<h3>Active Channels</h3>
		</header>
		<div class="row marketing">
			<div class="col-lg-2">
				<img src="/res/img/gmail.png" alt="ifttt" class="img-rounded">
			</div>
			<div class="col-lg-2">
				<img src="/res/img/datetime.png" alt="ifttt" class="img-rounded">
			</div>
			<div class="col-lg-2">
				<img src="/res/img/weibo.png" alt="ifttt" class="img-rounded">
			</div>
			<div class="col-lg-2">
				<img src="/res/img/ifttt_small.png" alt="ifttt" class="img-thumbnail">
			</div>
		</div>
		<hr />
	</div>
	</div>
	<%@ include file="/component/footer.jsp"%>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="/res/bootstrap/js/jquery-1.8.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="/res/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>