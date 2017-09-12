<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
    <c:url value="/resources/text-jstl.txt" var="jstlUrl"/> <!-- "jstlUrl" variable defined -->
    <spring:url value="/resources/text-spring.txt" htmlEscape="true" var="springUrl" /> <!-- "springUrl" variable defined -->
    Spring URL: ${springUrl} at ${time} <!-- "time" defined in WebController -->
    <br>
    JSTL URL: ${jstlUrl}
    <br>
    Message1: ${message} <!-- "message" defined in WebController -->
</body>

</html>