<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Detalle de Habitación - Spring Hotel</title>

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
		<!-- Header de página -->
		<div class="page-header">
			<h2>
				<i class="bi bi-door-open"></i> Detalle de Habitación
			</h2>
			<button onclick="location.href='${pageContext.request.contextPath}/habitaciones'"
				class="btn-volver"> <i class="bi bi-arrow-left"></i> Volver al
				Listado
			</button>
		</div>

		<!-- Mensajes de error -->
		<c:if test="${not empty errorMensaje}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>

		<!-- Card de detalle -->
		<div class="detail-card">
			<!-- Header -->
			<div class="detail-header">
				<div>
					<h3>Habitación #${habitacion.numeroHabitacion}</h3>
					<div style="margin-top: 10px; display: flex; gap: 10px;">
						<c:choose>
							<c:when test="${habitacion.estadoHabitacion == 'LIBRE'}">
								<span class="badge-estado badge-libre">
									<i class="bi bi-check-circle-fill"></i> Libre
								</span>
							</c:when>
							<c:when test="${habitacion.estadoHabitacion == 'OCUPADA'}">
								<span class="badge-estado badge-ocupada">
									<i class="bi bi-x-circle-fill"></i> Ocupada
								</span>
							</c:when>
							<c:when test="${habitacion.estadoHabitacion == 'MANTENIMIENTO'}">
								<span class="badge-estado badge-mantenimiento">
									<i class="bi bi-exclamation-circle-fill"></i> Mantenimiento
								</span>
							</c:when>
							<c:when test="${habitacion.estadoHabitacion == 'LIMPIEZA'}">
								<span class="badge-estado badge-mantenimiento">
									<i class="bi bi-exclamation-circle-fill"></i> Limpieza
								</span>
							</c:when>
							<c:otherwise>
								<span class="badge-estado">${habitacion.estadoHabitacion}</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="info-rapida">
					<div>
						<i class="bi bi-tag"></i>
						<div>
							<div style="font-size: 12px; opacity: 0.9;">Tipo</div>
							<div>${habitacion.tipoHabitacion}</div>
						</div>
					</div>
					<div>
						<i class="bi bi-currency-euro"></i>
						<div>
							<div style="font-size: 12px; opacity: 0.9;">Precio/Noche</div>
							<div>${habitacion.precioNoche}€</div>
						</div>
					</div>
					<div>
						<i class="bi bi-people-fill"></i>
						<div>
							<div style="font-size: 12px; opacity: 0.9;">Capacidad</div>
							<div>${habitacion.capacidad} personas</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Body -->
			<div class="detail-body">
				<!-- Información General -->
				<div class="info-section">
					<h4>
						<i class="bi bi-info-circle"></i> Información General
					</h4>
					<div class="info-row">
						
						<div class="info-item">
							<span>Número de Habitación</span>
							<div class="value">${habitacion.numeroHabitacion}</div>
						</div>
						<div class="info-item">
							<span>Tipo de Habitación</span>
							<div class="value">${habitacion.tipoHabitacion}</div>
						</div>
					</div>
				</div>

				<!-- Información de Precio y Capacidad -->
				<div class="info-section">
					<h4>
						<i class="bi bi-cash-coin"></i> Tarifas y Capacidad
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>Precio por Noche</span>
							<div class="value" style="font-size: 20px; color: #2e7d32;">
								${habitacion.precioNoche} €
							</div>
						</div>
						<div class="info-item">
							<span>Capacidad Máxima</span>
							<div class="value">${habitacion.capacidad} personas</div>
						</div>
					</div>
				</div>

				<!-- Estado y Características -->
				<div class="info-section">
					<h4>
						<i class="bi bi-gear"></i> Estado y Características
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>Estado Actual</span>
							<div class="value">
								<c:choose>
									<c:when test="${habitacion.estadoHabitacion == 'LIBRE'}">
										<span class="badge-estado badge-libre" style="margin: 0;">
											<i class="bi bi-check-circle-fill"></i> Libre
										</span>
									</c:when>
									<c:when test="${habitacion.estadoHabitacion == 'OCUPADA'}">
										<span class="badge-estado badge-ocupada" style="margin: 0;">
											<i class="bi bi-x-circle-fill"></i> Ocupada
										</span>
									</c:when>
									<c:when test="${habitacion.estadoHabitacion == 'MANTENIMIENTO'}">
										<span class="badge-estado badge-mantenimiento" style="margin: 0;">
											<i class="bi bi-exclamation-circle-fill"></i> Mantenimiento
										</span>
									</c:when>
									<c:when test="${habitacion.estadoHabitacion == 'LIMPIEZA'}">
										<span class="badge-estado badge-mantenimiento" style="margin: 0;">
											<i class="bi bi-exclamation-circle-fill"></i> Limpieza
										</span>
									</c:when>
									<c:otherwise>
										<span class="badge-estado" style="margin: 0;">
											${habitacion.estadoHabitacion}
										</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="info-item">
							<span>Adaptada para Discapacidad</span>
							<div class="value">
								<c:choose>
									<c:when test="${habitacion.adaptadaDiscapacidad}">
										<span class="badge-estado badge-si" style="margin: 0;">
											<i class="bi bi-check-lg"></i> Sí
										</span>
									</c:when>
									<c:otherwise>
										<span class="badge-estado badge-no" style="margin: 0;">
											<i class="bi bi-x-lg"></i> No
										</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>

				<!-- Botones de Acción -->
				<div class="action-buttons">
					<c:if test="${esRecepcionista}">
						<button onclick="location.href='${pageContext.request.contextPath}/habitaciones/${habitacion.id}/editar'"
							class="btn-action btn-editar">
							<i class="bi bi-pencil"></i> Editar Habitación
						</button>
						<button class="btn-action btn-eliminar"
							onclick="return eliminarHabitacion(${habitacion.id});">
							<i class="bi bi-trash"></i> Eliminar Habitación
						</button>
					</c:if>
					<button onclick="location.href='${pageContext.request.contextPath}/incidencias/habitacion/${habitacion.id}'"
						class="btn-action btn-incidencias">
						<i class="bi bi-exclamation-triangle"></i> Ver Incidencias
					</button>
				</div>
				
				<form id="formEliminarHabitacion" method="post" style="display:none;"></form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
	<script>
		function eliminarHabitacion(id) {
			if (!confirm('¿Está seguro de que desea eliminar esta habitación?')) {
				return false;
			}

			const form = document.getElementById("formEliminarHabitacion");
			form.action = '${pageContext.request.contextPath}/habitaciones/' + id + '/eliminar';
			form.submit();
			return false;
		}
	</script>
</body>
</html>
