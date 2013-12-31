<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create / Put the Internet to work for you.</title>
	<link rel="stylesheet" href="/res/css/footer.css">
	<link href="/res/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/res/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
	<div style="background-color: #EBEBEB; margin-top:-20px; height:80px;">
	<div class="container" style="max-width:980px">
	<div><header><h1>Create</h1></header></div>
	</div>
	</div>
<div class="container" style="max-width:980px">
<div style="text-align:center"> <h1>IF THIS THEN THAT</h1></div>

<form role="form" action="/task/create" method="post" onsubmit="return task_validate(this)">
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">THIS</h3>
  </div>
  <div class="panel-body">
  	<input type="hidden" value="0" id="triggertype" name="triggertype">
    <ul class="nav nav-tabs" id="myTab">
  	<li class="active"><a href="#timepanel" >On Time</a></li>
  	<li><a href="#receivegmailpanel">On Gmail</a></li>
  	<li><a href="#receiveweibopanel">On Weibo</a></li>
	</ul>
	<div class="tab-content">
	<div style="height:20px"></div>
	
 	<div class="tab-pane fade in active" id="timepanel" style="max-width:300px">
 	<div class="form-group">
 	<div class="alert alert-danger" style="display: none" id="settimewarning">Please set the time.</div>
 	<div class="input-group date form_datetime" data-date="2013-09-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:ii" data-link-field="settime">
    <input class="form-control" type="text" value="" readonly placeholder="2013-xx-xx xx:xx">
    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
	<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
    </div>
	<input type="hidden" id="settime" value="" name="reaching_time"/>
	</div>
 	</div>
 	
  	<div class="tab-pane fade" id="receivegmailpanel" style="max-width:300px">
  	<div class="form-group">
  	<div class="alert alert-danger" style="display: none" id="receiveemailaddresswarning">Please enter the gmail address.</div>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Your Gmail" name="receive_mail_address"  autocomplete="off"></div>
    <div class="form-group">
    <div class="alert alert-danger" style="display: none" id="receiveemailpasswordwarning">Please enter the password.</div> 
   	<input type="password" class="form-control" id="inputPassword"  placeholder="Password" name="receive_mail_password"  autocomplete="off"></div>
	</div>
	
	<div class="tab-pane fade" id="receiveweibopanel" style="max-width:300px">
  	<div class="form-group">
  	<div class="alert alert-danger" style="display: none" id="receiveweibocontentwarning">Please enter the weibo matching text.</div>
    <textarea class="form-control" rows="5" placeholder="weibo matching text" name="receive_weibo_content"  autocomplete="off"></textarea>
	</div>
	</div>
	
	</div>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">THAT</h3>
  </div>
  <div class="panel-body">
  	<input type="hidden" value="0" id="actiontype" name="actiontype">
    <ul class="nav nav-tabs" id="myTab">
  	<li class="active"><a href="#sendmailpanel" >Mailto</a></li>
  	<li><a href="#sendweibopanel">Weibo</a></li>
	</ul>
	<div class="tab-content">
	<div style="height:20px"></div>
	
 	<div class="tab-pane fade in active" id="sendmailpanel" style="max-width:300px">
 	<div class="form-group">
 	<div class="alert alert-danger" style="display: none" id="sendemailaddresswarning">Please enter the email address.</div>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Recipient" name="send_mail_address"  autocomplete="off"></div>
  	<div class="form-group">
  	<div class="alert alert-danger" style="display: none" id="sendemailpasswordwarning">Please enter the mail subject.</div>
    <input type="text" class="form-control" placeholder="Subject" name="send_mail_subject"></div>
    <div class="form-group">
    <div class="alert alert-danger" style="display: none" id="sendemailcontentwarning">Please enter the mail content.</div>
    <textarea class="form-control" rows="5" placeholder="Content" name="send_mail_content"></textarea></div>
 	</div>
 	
  	<div class="tab-pane fade" id="sendweibopanel" style="max-width:300px">
  	<!-- 
  	<div class="form-group">
    <input type="text" class="form-control" placeholder="Weibo Account" name="send_weibo_account"  autocomplete="off"></div>
  	<div class="form-group">
    <input type="password" class="form-control" placeholder="Password" name="send_weibo_password"  autocomplete="off"></div>
    -->
    <div class="form-group">
    <div class="alert alert-danger" style="display: none" id="sendweibocontentwarning">Please enter the weibo content.</div>
    <textarea class="form-control" rows="5" placeholder="Content" name="send_weibo_content"  autocomplete="off"></textarea></div>
	</div>
	
	</div>
  </div>
</div>
<div class="alert alert-info">100 points for a new task.</div>
<button type="submit" class="btn btn-primary">Create</button>
</form>
</div>
</div>
	<%@ include file="/component/footer.jsp"%>

<script type="text/javascript" src="/res/bootstrap/js/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/res/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/res/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script>

function task_validate(thisform){
	/*clear all existing warnings*/
	$("#settimewarning").fadeOut(0);
	$("#receiveemailaddresswarning").fadeOut(0);
	$("#receiveemailpasswordwarning").fadeOut(0);
	$("#receiveweibocontentwarning").fadeOut(0);
	$("#sendemailaddresswarning").fadeOut(0);
	$("#sendemailpasswordwarning").fadeOut(0);
	$("#sendemailcontentwarning").fadeOut(0);
	$("#sendweibocontentwarning").fadeOut(0);
	/*validate input params*/
	with (thisform){
		if (triggertype.value == 0) {
			if (reaching_time.value.length == 0) {
				$("#settimewarning").fadeIn();
				return false;
			}
		}
		if (triggertype.value == 1) {
			if (receive_mail_address.value.length == 0) {
				$("#receiveemailaddresswarning").fadeIn();
				return false;
			}
			if (receive_mail_password.value.length == 0) {
				$("#receiveemailpasswordwarning").fadeIn();
				return false;
			}
		}
		if (triggertype.value == 2) {
			if (receive_weibo_content.value.length == 0) {
				$("#receiveweibocontentwarning").fadeIn();
				return false;
			}
		}
		if (actiontype.value == 0) {
			if (send_mail_address.value.length == 0) {
				$("#sendemailaddresswarning").fadeIn();
				return false;
			}
			if (send_mail_subject.value.length == 0) {
				$("#sendemailpasswordwarning").fadeIn();
				return false;
			}
			if (send_mail_content.value.length == 0) {
				$("#sendemailcontentwarning").fadeIn();
				return false;
			}
		}
		if (actiontype.value == 1) {
			if (send_weibo_content.value.length == 0) {
				$("#sendweibocontentwarning").fadeIn();
				return false;
			}
		}
	var text = "It will cost you 100 points, OK?";
	return confirm(text);
	}
}


$('#myTab a[href="#timepanel"]').click(function (e) {
	document.getElementById("triggertype").value = "0";
	e.preventDefault();
	$(this).tab('show');
});
$('#myTab a[href="#receivegmailpanel"]').click(function (e) {
	document.getElementById("triggertype").value = "1";
	  e.preventDefault();
	  $(this).tab('show');
});
$('#myTab a[href="#receiveweibopanel"]').click(function (e) {
	document.getElementById("triggertype").value = "2";
	  e.preventDefault();
	  $(this).tab('show');
});
$('#myTab a[href="#sendmailpanel"]').click(function (e) {
	document.getElementById("actiontype").value = "0";
	  e.preventDefault();
	  $(this).tab('show');
});
$('#myTab a[href="#sendweibopanel"]').click(function (e) {
	document.getElementById("actiontype").value = "1";
	  e.preventDefault();
	  $(this).tab('show');
});
$('.form_datetime').datetimepicker({
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
    showMeridian: 0
});
</script>
</body>
</html>