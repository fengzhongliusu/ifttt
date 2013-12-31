<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="/res/bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="/res/bootstrap/js/bootstrap.js"></script>
<link rel="stylesheet" href="/res/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/res/css/footer.css">
<title>IFTTT / Put the Internet to work for you.</title>
</head>
<body>
<%
/*login redirect*/
if (session.getAttribute("log_condition") != null) {
	if((Boolean)session.getAttribute("log_condition")) {
		if (session.getAttribute("user_id") != null) {
			if ((Integer)session.getAttribute("user_id") == 1) 
				response.sendRedirect("/admin/index.jsp");
			else response.sendRedirect("/account/index.jsp");
		}
	}
}
%>
	<div id="wrap">
	<div class="container" style="max-width: 880px">
		<div class="header">
			<h2 class="text-muted"><a href="/index.jsp">IFTTT</a></h2>
		</div>
		<hr />		
		<div style="height: 15px"></div>
		
		<%if(request.getParameter("logerror")!=null){%>
			<div class="alert alert-danger"  id="logwarning">Opps! Invalid e-mail or password.</div>
		<%} %>
		<%if(request.getParameter("registererror")!=null){ %>
			<div class="alert alert-danger" id="registerwarning">Opps! The e-mail is already taken.</div>
		<%} %>
		<!-- three invisible warnings -->
		<div class="alert alert-danger" style="display: none" id="emailwarning">Please enter your email.</div>
		<div class="alert alert-danger" style="display: none" id="passwordwarning">Opps! Password too short.</div>
		<div class="alert alert-danger" style="display: none" id="confirmwarning">Opps! Confirm don't agree.</div>
		
		<h2>Put the Internet to Work for You</h2>
		<div style="height: 10px"></div>
		<div class="row marketing">
			<div class="col-lg-5" style="max-width: 400px">
				<img src="/res/img/ifttt.png" alt="ifttt" class="img-thumbnail">
			</div>
			<div class="col-lg-1"></div>
			<div class="col-lg-5" style="max-width: 400px">
				<form role="form" action="/register" onsubmit="return reigster_validate(this)" method="post" id="formRegister" autocomplete="off">
					<header>
						<h2>Register</h2>
					</header>
					<div class="form-group">
						<div class="form-group">
							<input type="email" class="form-control" id="exampleInputEmail1"
								name="user_name" placeholder="Enter email" autocomplete="off">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="inputPassword"
								name="user_password" placeholder="Password" autocomplete="off">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="user_password_confirm"
								id="inputConfirmPassword" placeholder="Confirm" autocomplete="off">
						</div>
						<button type="submit" class="btn btn-primary">Register</button>
						<button type="button" id="buttomLogin" class="btn btn-default"
							style="margin-left: 20px">or Login</button>
					</div>
				</form>
				<form role="form" action="/login"  onsubmit="return login_validate(this)" method="post" style="display: none"
					id="formLogin">
					<header>
						<h2>Login</h2>
					</header>
					<div class="form-group">
						<div class="form-group">
							<input type="email" class="form-control" id="exampleInputEmail1"
								name="user_name" placeholder="Enter email">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="inputPassword"
								name="user_password" placeholder="Password">
						</div>
						<button type="submit" class="btn btn-primary">Login</button>
						<button type="button" id="buttomRegister" class="btn btn-default"
							style="margin-left: 20px">or Register</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/component/footer.jsp"%>

	<script>
		$(document).ready(function() {
			$("#buttomLogin").click(function() {
				$("#formRegister").fadeOut(0);
				$("#formLogin").fadeIn();
			});
			$("#buttomRegister").click(function() {
				$("#formRegister").fadeIn();
				$("#formLogin").fadeOut(0);
			});
		});
		
		function reigster_validate(thisform){
			/*clear all existing warnings*/
			clear();
			/*validate input params*/
			with (thisform){
				if (user_name.value.length == 0){
					$("#emailwarning").fadeIn();
					return false;
				} else if(user_password.value.length < 6){
					$("#passwordwarning").fadeIn();
					return false;
				} else if(user_password.value != user_password_confirm.value){
					$("#confirmwarning").fadeIn();
					return false;
				} else {
					return true;
				}
			}
		}
		
		function login_validate(thisform){
			/*clear all existing warnings*/
			clear();
			/*validate input params*/
			with (thisform){
				if (user_name.value.length == 0){
					$("#emailwarning").fadeIn();
					return false;
				} else if(user_password.value.length < 2){//Because there are some short passwords in database。
					$("#passwordwarning").fadeIn();
					return false;
				} else {
					return true;
				}
			}
		}
		function clear(){
			$("#logwarning").fadeOut(0);
			$("#registerwarning").fadeOut(0);
			$("#emailwarning").fadeOut(0);
			$("#passwordwarning").fadeOut(0);
			$("#confirmwarning").fadeOut(0);
		}
	</script>
</body>
</html>