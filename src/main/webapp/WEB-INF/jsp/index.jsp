<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget"%>
<!DOCTYPE html>
<html>
<head>
<title>funreco</title>
<!-- Bootstrap -->
<link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<widget:header />
		<c:if test="${not empty flashMessage}">
			<div class="alert">${flashMessage}</div>
		</c:if>

		<form action="/upload">
			<button class="submit btn-primary" type="button">Import data</button>
		</form>




		<div class="row">
			<div class="span9">
				<legend> Recommendations for all users</legend>

			</div>
		</div>
		
		<c:if test="${not empty email || not empty facebookId}">
			<div class="row">
				<div class="span12">
					<legend>Profile</legend>
					<span id="profile">${profile == null ? 'unknown profile' :
						profile}</span>
				</div>
			</div>
		</c:if>
		
		<div class="row">
			<div class="span12">
				<legend>
					Latests actions <a href="/allActions"
						class="btn btn-info pull-right"> View all </a>
				</legend>
				<ul id="actions">
					<c:forEach var="action" items="${actions}">

						<li>
							<p>${action}</p>
						</li>
					</c:forEach>

				</ul>
			</div>
		</div>
		
		


	</div>
</body>
</html>



