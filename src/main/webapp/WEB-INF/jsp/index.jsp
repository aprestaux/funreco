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
                <form action="/makeReco">
                    <input type="hidden" name="facebookId" value="${facebook_id}"/>
                    <button class="btn btn-primary" type="submit" style="margin-top: 20px">Generate recommendation for this user</button>
                </form>
                <div class="row">
                    <div class="span12">
                        <legend>Profile</legend>
					    <span id="profile">${profile.name[0]} (${profile.email[0]})</span>
                    </div>
                </div>

                <c:if test="${not empty recommendations}">
                    <div class="row">
                        <div class="span9">
                            <legend> Recommendations for ${profile.name[0]}</legend>
                            <ul id="recommendations">
                                <c:forEach var="reco" items="${recommendations}">
                                    <li>
                                        <p><a href="${reco.object.attributes["url"][0]}" target="_blank"> ${reco.object.attributes}</a></p>
                                        <p><b> Recommended By : </b></p> 
                                        <c:forEach var="email" items="${reco.byEmail}">
                                       	  	
                                       		<p> <i class="icon-user"> </i> ${email} </p>
                                      	  
                                        </c:forEach>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>

            </c:when>
            <c:otherwise>
                <form action="/makeReco">
                    <input type="hidden" name="facebookId" value=""/>
                    <button class="btn btn-primary" type="submit" style="margin-top: 20px">Make generic recommendation</button>
                </form>

                <c:if test="${not empty recommendations}">
                    <div class="row">
                        <div class="span9">
                            <legend> Recommendations for all users</legend>
                            <ul id="recommendations">
                                <c:forEach var="reco" items="${recommendations}">
                                    <li>
                                        <p><a href="${reco.object.attributes["url"][0]}" target="_blank"> ${reco.object.attributes}</a></p>
                                        <p><b> Recommended By : </b></p> 
                                        <c:forEach var="email" items="${reco.byEmail}">
                                       	  	
                                       		<p> <i class="icon-user"> </i> ${email} </p>
                                      	  
                                        </c:forEach>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>

            </c:otherwise>
        </c:choose>

                <c:if test="${not empty actions}">
                    <div class="row">
                        <div class="span12">
                            <legend>
                                Latests actions
                            </legend>


                            <ul id="actions">
                                <c:forEach var="action" items="${actions}">
                                    <li>
                                        <p>${action.date} by ${action.profile.email} - ${action.object.attributes}</p>
                                    </li>
                                </c:forEach>

                            </ul>
                        </div>
                    </div>
                </c:if>

	</div>
</body>
</html>



