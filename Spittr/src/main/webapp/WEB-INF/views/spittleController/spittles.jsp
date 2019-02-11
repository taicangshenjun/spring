<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<title>Spittr</title>
		<link rel="stylesheet" type="text/css" href=".scc/style.css">
	</head>
	<body>
		<h1>SpittleList</h1>
		<c:forEach items="${spittleList}" var="spittle">
			<li id="spittle_${spittle.id} }">
			<div class="spittleMessage">${spittle.message}</div>
			<div>
				<span class="spittleTime">${spittle.time}</span>
				<span class="spittleLocation">${spittle.latitude},${spittle.longitude}</span>
			</div>
		</c:forEach>
	</body>
</html>