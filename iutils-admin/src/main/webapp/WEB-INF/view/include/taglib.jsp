<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fnc" uri="/WEB-INF/tld/fnc.tld" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tld/fns.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}${fnc:getConfig('adminPath')}"/>
<c:set var="ctxF" value="${pageContext.request.contextPath}${fnc:getConfig('frontPath')}"/>
<c:set var="rest" value="${pageContext.request.contextPath}${fnc:getConfig('restPath')}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="urlSuffix" value="${fnc:getConfig('urlSuffix')}"/>