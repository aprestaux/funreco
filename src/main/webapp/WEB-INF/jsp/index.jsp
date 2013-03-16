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

        <c:choose>
            <c:when test="${not empty profile}">
                <form action="/makereco?id=${profile_id}">
                    <button class="submit btn btn-primary" type="button" style="margin-top: 50px">Generate recommendation for this user</button>
                </form>
                <div class="row">
                    <div class="span12">
                        <legend>Profile</legend>
					    <span id="profile">${profile.name[0]} (${profile.email[0]})</span>
                    </div>
                </div>
                <div class="row">
                    <div class="span9">
                        <legend> Recommendations for ${profile.name[0]}</legend>

                    </div>
                </div>
                <div class="row">
                    <div class="span12">
                        <legend>
                            Latests actions of ${profile.name[0]}
                        </legend>
            </c:when>
            <c:otherwise>
                <form action="/makereco">
                    <button class="submit btn btn-primary" type="button" style="margin-top: 50px">Make generic recommendation</button>
                </form>

                <div class="row">
                    <div class="span9">
                        <legend> Recommendations for all users</legend>

                    </div>
                </div>

                <div class="row">
                    <div class="span12">
                        <legend>
                            Latests actions
                        </legend>
            </c:otherwise>
        </c:choose>


		

                        <ul id="actions">
                            <c:forEach var="action" items="${actions}">
                                <li>
                                    <p>${action.date} - ${action.object.attributes}</p>
                                </li>
                            </c:forEach>

				        </ul>
                    </div>
                </div>

	</div>
</body>
</html>



