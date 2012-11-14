<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>

<p>Spring Social Sparklr Demo : 

<c:choose>
<c:when test="${securityLevel eq 'Public'}" >

Public Area. 
<p> <a href="/protected/sparklr">Access</a> your Sparklr photos</p>


</c:when>
<c:otherwise>
<c:out value="${securityLevel}" /></p><p> <a href="/" >Home</a>
</c:otherwise>
</c:choose>
</p>

                    <authz:authorize access="!hasRole('ROLE_USER')">
						<p>You are not logged in. &nbsp;<a href="/oauthlogin.jsp" />Login</a></p>
                    </authz:authorize>
                                  <authz:authorize access="hasRole('ROLE_USER')">
						You are logged in locally as <c:out value="${userName}" />. &nbsp;<a href="/logout">Logout</a></p>
						
                    </authz:authorize>
                      
                      <authz:authorize access="hasRole('ROLE_USER_SPARKLR')">
					 <p>	You are connected with Sparklr. </p>
					 
					 <c:if test="${not empty photos}" >
					 	<label for="photo">Your Photos</label>
					 	<select name="photo">
					 	<c:forEach var="photo" items="${photos}">
					 		<option name="<c:out value="${photo.id}" />"><c:out value="${photo.name}" /></option>
					 	</c:forEach>
					 	</select>
					 
					 </c:if>
					 
                    </authz:authorize>
                    
           
                  
                    <authz:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_USER_SPARKLR')">
						<p><a href="/oauthconnect.jsp">Connect</a> your account with Sparklr</p>
                    </authz:authorize>
                       
                 
</body>                    
</html>