<%@page import="javax.mail.search.RecipientTerm"%>
<%@page import="database.descriptor.MessageDescriptor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="database.MessageData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Message. / Put the Internet to work for you.</title>
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
</head>

<body>
	<div id="wrap">
	<%@ include file="/component/nav.jsp"%>
	<div style="background-color: #EBEBEB; margin-top: -20px; height: 80px;">
		<div class="container" style="max-width: 980px">
				<header>
					<h1>Message</h1>
				</header>
		</div>
	</div>

	<div class="container" style="width: 980px; margin-top: 10px">
	
		<%if(request.getParameter("sendsuccess")!=null){%>
			<div class="alert alert-success"  id="sendsucess">
			 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			 Message sent.</div>
		<%} %>
		<%if(request.getParameter("sendfailed")!=null){ %>
			<div class="alert alert-danger" id="sendfailed">
			 <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			 Send message failed. Invalid recipient!</div>
		<%} %>
		
		<!-- Nav tabs -->
		<ul class="nav nav-tabs">

			<li><a href="#inbox" data-toggle="tab">Inbox</a></li>
			<li><a href="#outbox" data-toggle="tab">Outbox</a></li>
			<li><a href="#send" data-toggle="tab">Send A Message</a></li>

		</ul>
		<!-- Tab panes -->
		<div class="tab-content">

			<div class="tab-pane fade in active" id="inbox">
				<div style="width: 700px; margin: 0 auto; margin-top: 30px"
					id="contains">

					<div class="panel-group" id="accordion">
						<%
						ArrayList<MessageDescriptor> inboxMessage = MessageData.getInboxMessage(userID);
						  MessageData.setAllMessageRead(userID);
						int inboxNumber = inboxMessage.size();
						if(inboxNumber > 0){
							for (int i = inboxNumber - 1; i >=0 ; i--) { 
								MessageDescriptor message = inboxMessage.get(i);
						%>
						<div class="panel panel-default">
							<div class="panel-heading">

								<h4 class="panel-title" style="display: inline;color: #428bca;">
								<%if(message.unread)
									out.print("<b>");%>
									<a data-toggle="collapse"
										data-parent="#accordion" href="#collapse<%=i%>">From:<%=message.senderName %> </a>
										
								<%if(message.unread)
									out.print("</b>");%></h4>
								<div style="display: inline; float:right">
											<h4 style="display: inline;"><%=message.deliveryTime %></h4></div>

							</div>

							<div id="collapse<%=i %>" class="panel-collapse collapse">
								<div class="panel-body">
									<%=message.content %>
									<div class="modal-footer" style="height:55px">
									<a href="/message/remove?message_id=<%=message.messageID%>">
										<button type="button" class="btn btn-default">Remove</button></a>
									</div>
								</div>
							</div>
						</div>
						<%}//end of for loop
						}//end of if
						else{
							%>
							No messages.
							<%
						}
						%>
					</div>

				</div>
			</div>
			
			<div class="tab-pane fade" id="outbox">
				<div style="width: 700px; margin: 0 auto; margin-top: 30px"
					id="contains">

					<div class="panel-group" id="accordion2">
						<%
						ArrayList<MessageDescriptor> outboxMessage = MessageData.getOutboxMessage(userID);
						  MessageData.setAllMessageRead(userID);
						int outboxNumber = outboxMessage.size();
						if(outboxNumber > 0){
							for (int i = outboxNumber - 1; i >=0 ; i--) { 
								MessageDescriptor message = outboxMessage.get(i);
						%>
						<div class="panel panel-default">
							<div class="panel-heading">

								<h4 class="panel-title" style="display: inline;color: #428bca;">
									<a data-toggle="collapse"
										data-parent="#accordion2" href="#collapse2<%=i%>">To:<%=message.recipientName %> </a>
								</h4>
								<div style="display: inline;  float:right">
											<h4 style="display: inline;"><%=message.deliveryTime %></h4></div>

							</div>

							<div id="collapse2<%=i %>" class="panel-collapse collapse">
								<div class="panel-body">
									<%=message.content %>
								</div>
							</div>
						</div>
						<%}//end of for loop
						}//end of if
						else{
							%>
							No messages.
							<%
						}
						%>
					</div>

				</div>
			</div>

			<div class="tab-pane fade " id="send">
				<div style="height:20px"></div>
				<div class="alert alert-danger" style="display: none" id="emailwarning">Please enter recipient's email.</div>
				<div class="alert alert-danger" style="display: none" id="contentwarning">Content is empty!</div>
				<form role="form" action="/message/create"  onsubmit="return message_validate(this)" method="post">
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

<script>
		function message_validate(thisform){
			/*clear all existing warnings*/
			$("#sendsuccess").fadeOut(0);
			$("#sendfailed").fadeOut(0);
			$("#emailwarning").fadeOut(0);
			$("#contentwarning").fadeOut(0);
			/*validate input params*/
			with (thisform){
				if (message_user_name.value.length == 0){
					$("#emailwarning").fadeIn();
					return false;
				} else if(message_content.value.length == 0){
					$("#contentwarning").fadeIn();
					return false;
				} else {
					return true;
				}
			}
		}
	</script>
</body>
</html>