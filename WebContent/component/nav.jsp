<%@page import="database.descriptor.UserDescriptor"%>
<%@page import="database.MessageData"%>
<%@page import="database.UserData"%>
<%
/*get username and not login redirect*/
String username = "default";
int userID = -1;
boolean redirect = true;
if (session.getAttribute("log_condition") != null) {
	if((Boolean)session.getAttribute("log_condition")) {
		userID = (Integer)session.getAttribute("user_id");
		username = UserData.getUsername(userID);
		redirect = false;
	}
}
if (redirect)
	response.sendRedirect("/index.jsp");

int unreadMessageCount = MessageData.getUnreadeMessageCount(userID);
%>
<!-- navigation bar -->
<nav class="navbar navbar-default" role="navigation">
		<div class="container" style="max-width:980px">
			<div class="navbar-header">
				<a class="navbar-brand" href="/account/index.jsp">IFTTT</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/task/create.jsp">Create</a></li>
					<li><a href="/task/browse.jsp">Browse</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/account/message.jsp">Messages
					<%int count = unreadMessageCount;
					if(count != 0) {%><span class="badge"><%=count %></span><% }%>
					</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><%out.print(username);%><b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/account/info.jsp">Account Info</a></li>
						<li><a href="/logout">Logout</a></li>
					</ul></li>
				</ul>
			</div>
		</div>
	</nav>

