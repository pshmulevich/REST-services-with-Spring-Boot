<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
    <c:url value="/resources/text-jstl.txt" var="jstlUrl"/> <!-- "jstlUrl" variable defined -->
    <spring:url value="/resources/text-spring.txt" htmlEscape="true" var="springUrl" /> <!-- "springUrl" variable defined -->
    Spring URL: ${springUrl} at ${time} <!-- "time" defined in WebController and added to model map -->
    <br>
    JSTL URL: ${jstlUrl}
    <br>
    WebController Message: ${webControllerMessage} <!-- "webControllerMessage" added to the model map -->
</body>

</html>