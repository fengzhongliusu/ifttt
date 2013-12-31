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
<title>IFTTT Admin / Put the Internet to work for you.</title>
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
		<div class="container" style="max-width:980px">
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
		<h2>Send Message</h2>
		<%if (request.getParameter("sendsucess") != null) {
		%>
		<div class="alert alert-success"  id="sendsucess">
			 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			 Message sent.</div>
		<% }%>
		<%if(request.getParameter("sendfailed")!=null){ %>
			<div class="alert alert-danger" id="sendfailed">
			 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			 Send message failed. Invalid recipient!</div>
		<%} %>
		<ul class="nav nav-tabs">
  		<li><a href="#sendpublicmsg" data-toggle="tab">Send public message</a></li>
  		<li><a href="#sendprivatemsg" data-toggle="tab">Send private message</a></li>
		</ul>
		
		<div class="tab-content">
		<div class="tab-pane fade in active" id="sendpublicmsg">
			<form role="form" action="/message/create?public"  onsubmit="return message_validate(this)" method="post">
				<div style="width: 400px; margin: 0 auto; margin-top: 30px"
					id="contains">
					<div style="margin-top: 45px; width: 60">
						<textarea id="statements" cols="42" rows="4" name="message_content"
							placeholder="Enter message contents here." style="color: #999; font-size: 20px;"></textarea>
					</div>

					<button type="submit" class="btn btn-primary"
						style="margin-top: 35px; margin-left: 120px"
						data-loading-text="...">Send it</button>

				</div>
			</form>
		</div>
		<div class="tab-pane fade" id="sendprivatemsg">
			<form role="form" action="/message/create?private"  onsubmit="return message_validate(this)" method="post">
				<div style="width: 400px; margin: 0 auto; margin-top: 30px"
					id="contains">

					<div class="input-group input-group-lg">
						<span class="input-group-addon ">@</span>
						<input type="email" name="message_user_name"
							class="form-control" placeholder="recipient email">
					</div>

					<div style="margin-top: 45px; width: 60">
						<textarea id="statements" cols="42" rows="4" name="message_content"
							placeholder="Enter message contents here." style="color: #999; font-size: 20px;"></textarea>
					</div>

					<button type="submit" class="btn btn-primary"
						style="margin-top: 35px; margin-left: 120px"
						data-loading-text="...">Send it</button>
				</div>
			</form>
		</div>
		</div>
	</div>
	
</div>
<%@ include file="/component/footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/res/bootstrap/js/jquery-1.8.3.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/res/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>