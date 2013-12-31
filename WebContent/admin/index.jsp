<%@page import="database.descriptor.TaskDescriptor"%>
<%@page import="weibo4j.model.UserTrend"%>
<%@page import="program.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="database.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="/res/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/res/css/footer.css">
<link rel="stylesheet" href="/res/css/admin.css">
<title>IFTTT / Put the Internet to work for you.</title>
</head>
<body>
	<%
		/*special login redirect*/
		int userID = -1;
		boolean redirect = true;
	    if(session.getAttribute("log_condition") != null){
			if ((Boolean)session.getAttribute("log_condition")) {
		if((Integer)session.getAttribute("user_id") == 1){
			redirect = false;
		}
			}
	    }
		if (redirect)
			response.sendRedirect("/index.jsp");
	    
	    ArrayList<String> userNameList = UserData.getAllUserName();
	%>

	<div id="wrap">

		<nav class="navbar navbar-default" role="navigation">
			<div class="container" style="max-width: 980px">
				<div class="navbar-header">
					<a class="navbar-brand" href="/admin/index.jsp">IFTTT</a>
				</div>

				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/admin/message.jsp">Message</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Admin<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="/logout">Logout</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="container" style="max-width: 880px">
			<!-- <div class="header">
                <h2 class="text-muted">Admin</h2>
            </div>
            <hr /> -->

			<div class="row marketing" style="margin-top: -10px">
				<div class="col-lg-5" style="max-width: 400px">
					<h2>Users：</h2>
					<div style="height: 10px"></div>
					<div class="divconetnt">
						<table id="user_table" class="table table-responsive">
							<thead>
								<tr align=center>
									<td>account</td>
									<td>browse tasks</td>
									<td>delete</td>
								</tr>
							</thead>
							<tbody>
								<%
									for (String name : userNameList) {
								%>
								<tr align=center style="display: table-row">
									<td><%=name%></td>
									<td>
										<button type="button" class="btn btn-primary btn-xs"
											onclick="showTask(this)">view</button>
									</td>
									<td><a
										href="/admin?delete=user&id=<%=UserData.getUserID(name)%>">
											<button type="button" class="btn btn-primary btn-xs"
												onclick="delUser(this)">delete</button>
									</a></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>

				<div class="col-lg-5 col-md-offset-1" style="max-width: 400px">
					<h2>task list：</h2>
					<div style="height: 10px"></div>
					<div class="divconetnt">
						<table id="task_table" class="table table-responsive">
							<thead>
								<tr align="center">
									<td>task id</td>
									<td>account</td>
									<td>status</td>
									<td>delete</td>
								</tr>
							</thead>
							<%
								for (int i = 0; i < userNameList.size(); i++) {
							                                    String name = userNameList.get(i);
							%>
							<tbody id="<%=name%>" align=center style="display: none">
								<%
									int ID = UserData.getUserID(name);
								                                    ArrayList<Integer> taskIDList = TaskData.getAllTaskID(ID);
								                                    for (int j = 0; j < taskIDList.size(); j++) {
								                                    	TaskDescriptor task = TaskData.getTask(taskIDList.get(j));
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
								<tr>
									<td><%=task.taskID%></td>
									<td><%=name%></td>
									<td><%=status%></td>
									<td><a href="/admin?delete=task&id=<%=task.taskID%>">
											<button type="button" class="btn btn-primary btn-xs"
												onclick="delTask(this)">delete</button>
									</a></td>
								</tr>
								<%
									}
								%>
							</tbody>
							<%
								}
							%>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/footer.jsp"%>

	<script src="/res/bootstrap/js/jquery-1.8.3.min.js"></script>
	<script src="/res/bootstrap/js/bootstrap.js"></script>
	<script type="text/JavaScript">
		function showTask(r) {
			reset_taskTable();
			var mail = r.parentNode.parentNode.cells[0].innerHTML;
			document.getElementById(mail).style.display = "table-row-group";
		}

		function reset_taskTable() {
			var table = document.getElementById("task_table");
			for (var i = 0; i < table.tBodies.length; i++) {
				table.tBodies[i].style.display = "none";
			}
		}
	</script>
</body>
</html>