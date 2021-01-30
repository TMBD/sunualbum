<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Liste des invités</title>
</head>
<body>
	<h1>Inscription à la conférence</h1><hr>
	<form method="post">
		<fieldset>
			<legend>Inscription d'un invité</legend>
			<label>Nom</label>
			<input type="text" name="nom">
			<input type="submit" value="Inscrire">
		</fieldset>
	</form>
	<hr>
	<h1>Liste des inscris</h1>
	<c:choose>
		<c:when test="${ !empty requestScope.guests }">
			<c:forEach var="guest" items="${ requestScope.guests }">
				<c:out value="${ guest }"/><br>
			</c:forEach>
		</c:when>
		<c:otherwise>Aucune inscription n'est encore effectuée !</c:otherwise>
	</c:choose>
</body>
</html>