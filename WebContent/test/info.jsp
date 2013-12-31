<%@page import="database.descriptor.UserDescriptor"%>
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
<title>Account Info. / Put the Internet to work for you.</title>
</head>
  <body>
  
  	<div id="wrap">
   
   	<%@ include file="/component/nav.jsp"%>
   
	<div style="background-color: #EBEBEB; margin-top:-20px; height:80px;">
	<div class="container" style="max-width:980px">
	<div><header><h1>Account Info</h1></header></div>
	</div>
	</div>
	
	<%
	/*get current user info*/
	UserDescriptor currentUser =  UserData.getUser(userID);
	assert currentUser!= null;
	%>
	
	<div class="container" style="max-width:980px; margin-top:15px">

            <ul class="nav nav-tabs">
                <li><a href="#info" data-toggle="tab">Information</a></li>
                <li><a href="#setting" data-toggle="tab">Settings</a></li>
                <li><a href="#recharge" data-toggle="tab">More Points!</a></li>      
            </ul>
			
            <div class="tab-content" style="margin-top: 50px;">
                <div class="tab-pane fade in active" id="info">	  		    
                    <div class="row">          
                        <div class="col-md-5" style="max-width: 400px;margin-left:50px">
                            <img src="/res/img/info.jpg" alt="ifttt">
                        </div>
                        <div class="col-md-5 col-sm-offset-1" style="max-width: 400px">
                            <h3>Email:</h3>
                            <h3 style="margin-top:-5px"><span class="label label-default"><%out.print(currentUser.username);%></span></h3>
                            <h3 style="margin-top:20px">Level:</h3>
                            <h3 style="margin-top:-5px"><span class="label label-default"><%out.print(currentUser.levelString + " (level " + currentUser.level + ")" );%></span></h3>
                            <h3 style="margin-top:20px">Points:</h3>
                            <h3 style="margin-top:-5px"><span class="label label-default"><%out.print(currentUser.points);%></span></h3>
                        </div>
                    </div>  
                </div>

				
                <div class="tab-pane fade" id="setting">
                    <div class="row">
                        <div class="col-md-5" style="max-width: 400px;margin-left:50px">
                            <img src="/res/img/setting.jpg" alt="ifttt">
                        </div>
                        <div class="col-md-5" style="max-width: 400px;margin-top:40px">
                            <form class="form-horizontal" role="form" action="/account/info" method="post" id="alter_info">
                                <div class="form-group">
                                    <label for="Old_Password" class="col-md-6 control-label">Old Password:</label>
                                    <div class="col-md-6">
                                        <input type="password" class="form-control" name="old_password" placeholder="old password"  autocomplete="off">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="new_Password" class="col-md-6 control-label">New Password:</label>
                                    <div class="col-md-6">
                                        <input type="password" class="form-control" name="new_password" placeholder="new password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="confirm_Password" class="col-md-6 control-label">Confirm:</label>
                                    <div class="col-md-6">
                                        <input type="password" class="form-control" name="new_password_confirm" placeholder="confirm">
                                    </div>
                                </div>          
                                <div class="form-group">
                                    <div class="col-md-offset-6 col-md-4">
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="tab-pane fade" id="recharge">          	                   
                    <div class="row">          
                        <div class="col-md-5" style="max-width:400px;margin-left:50px">
                            <img src="/res/img/recharge.jpg" alt="ifttt">
                        </div>
                        <div class="col-md-5 col-sm-offset-1" style="max-width: 400px">
                            <h3>Account: <%out.print(currentUser.username);%></h3>
                            <form role="form" action="/account/recharge" method="post" id="Recharging">
                                <div class="form-group" >
                               		<h3>Special Code:</h3>
                                    <input type="text" class="form-control" id="code"
                                           placeholder="Enter special code">
                                    <h3>Recharge Amount:</h3>
                                    <input type="text" class="form-control" id="num" name="points"
                                           placeholder="Enter number">
                                </div>
                                <button style="margin-top:10px" type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>       
                </div>  	   
            </div>

        </div>
	
	
	</div>
	<div id="footer">
      <div class="container" style="max-width: 880px; max-height:50px;">
        <p class="text-muted credit">This is a personal IFTTT website.</p>
      </div>
    </div>
 	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/res/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>