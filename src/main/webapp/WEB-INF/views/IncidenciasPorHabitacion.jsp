<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Incidencias de Habitación ${habitacion.numeroHabitacion}
	- Spring Hotel</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq" crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">

</head>

<body class="fondo">
	<!-- Navbar -->
	<%@ include file="includes/navbar.jsp" %>

	<div class="container">

		<!-- HEADER -->
		<div class="page-header">
			<h2>
				<i class="bi bi-exclamation-triangle"></i> Incidencias de la
				Habitación ${habitacionPorId[inc.idHabitacion]}
			</h2>

			<button
				onclick="location.href='${pageContext.request.contextPath}/habitaciones/${habitacion.id}/detalle'"
				class="btn-volver"> <i class="bi bi-arrow-left"></i> Volver a
				Habitación
			</button>
		</div>

		<!-- CARD -->
		<div class="detail-card">

			<div class="detail-header">
				<h3>Listado de Incidencias</h3>
			</div>

			<div class="detail-body">

				<c:if test="${empty incidencias}">
					<div class="alert alert-info mt-3">
						<i class="bi bi-info-circle"></i> Esta habitación no tiene
						incidencias registradas.
					</div>
				</c:if>

				<c:if test="${not empty incidencias}">
					<table class="table table-striped mt-3">
						<thead>
							<tr>

								<th>Descripción</th>
								<th>Prioridad</th>
								<th>Estado</th>
								<th>Fecha Apertura</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="i" items="${incidencias}">
								<tr>

									<td>${i.descripcion}</td>
									<td>${i.prioridad}</td>
									<td>${i.estadoIncidencia}</td>
									<td>${i.fechaApertura}</td>
									<td><button
										onclick="location.href='${pageContext.request.contextPath}/incidencias/${i.id}/detalle'"
										class="btn btn-detalle btn-sm"> <i class="bi bi-eye"></i>
											Ver Detalle
									</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>

			</div>
		</div>
	</div>

</body>
</html>
