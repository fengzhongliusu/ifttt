<%@page import="program.Task"%>
<%@page import="database.descriptor.TaskDescriptor"%>
<%@page import="program.trigger.Trigger"%>
<%@page import="program.action.Action"%>
<%@page import="database.TaskData"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="database.UserData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="/res/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/res/css/footer.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
<title>Browse. / Put the Internet to work for you.</title>
</head>
  <body>
   
   	<div id="wrap">
   	<%@ include file="/component/nav.jsp"%>
  
	<div style="background-color: #EBEBEB; margin-top:-20px; height:80px;">
	<div class="container" style="max-width:980px">
	<div><header><h1>Browse</h1></header></div>
	</div>
	</div>
	
	<div class="container" style="max-width:980px">
	<div style="height:10px"></div>
	<%
	String timeImage = "/res/img/datetime.png";
	String weiboImage = "/res/img/weibo.png";
	String gmailImage = "/res/img/gmail.png";
	
	ArrayList<Integer> taskIDList = TaskData.getAllTaskID(userID);
	int num = taskIDList.size();//number of tasks of current user
	if(num == 0){
		%>
		<div class="container">
		<h3>No tasks.</h3>
		</div>
		<%
	}
	else{
	//display all tasks.
	for (int i = 0; i < num ; i++) {
		TaskDescriptor task = TaskData.getTask(taskIDList.get(i));
		String triggleImage = timeImage;
		if (task.triggerType == Trigger.TIME_TRIGGER)
			triggleImage = timeImage;
		if (task.triggerType == Trigger.GMAIL_TRIGGER)
			triggleImage = gmailImage;
		if (task.triggerType == Trigger.WEIBO_TRIGGER)
			triggleImage = weiboImage;
		String actionImage = (task.actionType == Action.GMAIL_ACTION)? gmailImage : weiboImage;
		String description = task.description;
		String status = "";
		switch (task.status) {
		case Task.INITIAL:
			status = "Not Start";break;
		case Task.RUNNING:
			status = "Running";break;
		case Task.PAUSED:
			status = "Paused";break;
		case Task.FINISHED:
			status = "Finished";break;
		case Task.STOPPED:
			status = "Stopped";break;
		default:
			status = "Error";break;
		}
	%>
	<div class="row marketing">
	<div class="col-lg-7">
	<div><h2>IF&nbsp;&nbsp;<img src="<% out.print(triggleImage);%>" alt="datime" class="img-rounded">&nbsp;&nbsp;Then
	&nbsp;&nbsp;<img src="<% out.print(actionImage);%>" alt="gmail" class="img-rounded"></h2>
	<div><h3 style="font-family:Microsoft YaHei ;font-size:20px">
	<% out.print("Trigger: " + description.split("split")[0]);%><br/>
	<% out.print("Action : " + description.split("split")[1]);%></h3></div>
	</div>
	</div>
	<div class="col-lg-4">
	<div style="height:30px"></div>
	<div><h2><span class="label label-primary"><%out.print(status); %></span></h2></div>
	<div style="height:20px"></div>
	<div class="btn-group">
	<a href="/task/control?control=start&task_id=<% out.print(task.taskID);%>">
	<button type="button" class="btn btn-default">
	<img src="/res/img/start.png" alt="start" class="img-rounded" title="start"></button></a>
	<a href="/task/control?control=pause&task_id=<% out.print(task.taskID);%>">
	<button type="button" class="btn btn-default">
	<img src="/res/img/pause.png" alt="pause" class="img-rounded" title="pause"></button></a>
	<a href="/task/control?control=stop&task_id=<% out.print(task.taskID);%>">
	<button type="button" class="btn btn-default">
	<img src="/res/img/stop.png" alt="stop" class="img-rounded" title="stop"></button></a>
	<a href="/task/control?control=delete&task_id=<% out.print(task.taskID);%>">
	<button type="button" class="btn btn-default">
	<img src="/res/img/delete.png" alt="delete" class="img-rounded" title="delete"></button></a>
	</div>
	</div>
	</div>
	<hr/>
	<%
		}
	}
	%>
	</div>
	</div>
	<%@ include file="/component/footer.jsp"%>
 	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/res/bootstrap/js/jquery-1.8.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/res/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>