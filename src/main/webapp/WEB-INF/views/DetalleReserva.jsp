<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Detalle de Reserva - Spring Hotel</title>

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
				<i class="bi bi-calendar-check"></i> Detalle de Reserva
			</h2>
			<button onclick="location.href='${pageContext.request.contextPath}/reservas'"
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
					<h3>Reserva #${reserva.id}</h3>
					<div style="margin-top: 10px; display: flex; gap: 10px;">
						<c:choose>
							<c:when test="${reserva.estadoReserva == 'PENDIENTE'}">
								<span class="badge-estado badge-pendiente"> <i
									class="bi bi-clock-fill"></i> Pendiente
								</span>
							</c:when>
							<c:when test="${reserva.estadoReserva == 'CONFIRMADA'}">
								<span class="badge-estado badge-confirmada"> <i
									class="bi bi-check-circle-fill"></i> Confirmada
								</span>
							</c:when>
							<c:when test="${reserva.estadoReserva == 'CANCELADA'}">
								<span class="badge-estado badge-cancelada"> <i
									class="bi bi-x-circle-fill"></i> Cancelada
								</span>
							</c:when>
							<c:when test="${reserva.estadoReserva == 'FINALIZADA'}">
								<span class="badge-estado badge-finalizada"> <i
									class="bi bi-check2-all"></i> Finalizada
								</span>
							</c:when>
							<c:otherwise>
								<span class="badge-estado">${reserva.estadoReserva}</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="info-rapida">
					<div>
						<i class="bi bi-calendar-event"></i>
						<div>
							<span>Fecha de Check-In</span>
							<div class="value fecha-destacada">
								<i class="bi bi-calendar-event"></i> ${reserva.checkIn}
							</div>
						</div>
					</div>
					<div>
						<i class="bi bi-calendar-x"></i>
						<div>
							<span>Fecha de Check-Out</span>
							<div class="value fecha-destacada">
								<i class="bi bi-calendar-event"></i> ${reserva.checkOut}
							</div>
						</div>
					</div>
					<div>
						<i class="bi bi-people-fill"></i>
						<div>
							<div style="font-size: 12px; opacity: 0.9;">Personas</div>
							<div>${reserva.numPersonas}</div>
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
							<span>Estado de la Reserva</span>
							<div class="value">
								<c:choose>
									<c:when test="${reserva.estadoReserva == 'PENDIENTE'}">
										<span class="badge-estado badge-pendiente" style="margin: 0;">
											<i class="bi bi-clock-fill"></i> Pendiente
										</span>
									</c:when>
									<c:when test="${reserva.estadoReserva == 'CONFIRMADA'}">
										<span class="badge-estado badge-confirmada" style="margin: 0;">
											<i class="bi bi-check-circle-fill"></i> Confirmada
										</span>
									</c:when>
									<c:when test="${reserva.estadoReserva == 'CANCELADA'}">
										<span class="badge-estado badge-cancelada" style="margin: 0;">
											<i class="bi bi-x-circle-fill"></i> Cancelada
										</span>
									</c:when>
									<c:when test="${reserva.estadoReserva == 'FINALIZADA'}">
										<span class="badge-estado badge-finalizada" style="margin: 0;">
											<i class="bi bi-check2-all"></i> Finalizada
										</span>
									</c:when>
									<c:otherwise>
										<span class="badge-estado" style="margin: 0;">
											${reserva.estadoReserva} </span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="info-item">
							<span>Fecha de Creacion</span>
							<div class="value fecha-destacada">
								<i class="bi bi-calendar-event"></i>
								${reserva.fechaCreacionReserva}

							</div>
						</div>
					</div>
				</div>

				<!-- Información de Fechas -->
				<div class="info-section">
					<h4>
						<i class="bi bi-calendar-range"></i> Fechas de Estancia
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>Fecha de Check-In</span>
							<div class="value fecha-destacada">
								<i class="bi bi-calendar-event"></i> ${reserva.checkIn}
							</div>
						</div>
						<div class="info-item">
							<span>Fecha de Check-Out</span>
							<div class="value fecha-destacada">
								<i class="bi bi-calendar-x"></i> ${reserva.checkOut}
							</div>
						</div>
						<div class="info-item">
							<span>Número de Personas</span>
							<div class="value">
								<i class="bi bi-people-fill"></i> ${reserva.numPersonas}
								personas
							</div>
						</div>
					</div>
				</div>

				<!-- Información del Huésped -->
				<div class="info-section">
					<h4>
						<i class="bi bi-person-circle"></i> Información del Huésped
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>Nombre Completo</span>
							<div class="value">${reserva.nombreHuesped}
								${reserva.apellidosHuesped}</div>
						</div>
						<div class="info-item">
							<span>Email</span>
							<div class="value">
								<i class="bi bi-envelope"></i> ${reserva.emailHuesped}
							</div>
						</div>
						<div class="info-item">
							<span>Teléfono</span>
							<div class="value">
								<i class="bi bi-telephone"></i>
								${reserva.telefonoHuesped}
							</div>
						</div>
					</div>
				</div>

				<!-- Información de la Habitación -->
				<div class="info-section">
					<h4>
						<i class="bi bi-door-open"></i> Información de la Habitación
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>Número de Habitación</span>
							<div class="value">${reserva.numeroHabitacion}</div>
						</div>
						<div class="info-item">
							<span>Tipo de Habitación</span>
							<div class="value">${reserva.tipoHabitacion}</div>
						</div>
						<div class="info-item">
							<span>Capacidad</span>
							<div class="value">
								<i class="bi bi-people-fill"></i>
								${reserva.capacidadHabitacion} personas
							</div>
						</div>
						<div class="info-item">
							<span>Precio por Noche</span>
							<div class="value" style="font-size: 20px; color: #2e7d32;">
								${reserva.precioNoche} €</div>
						</div>
					</div>
				</div>

				<!-- Botones de Acción -->
				<div class="action-buttons">
					<c:if test="${esRecepcionista}">
						<button
							onclick="location.href='${pageContext.request.contextPath}/reservas/${reserva.id}/editar'"
							class="btn-action btn-editar"> <i class="bi bi-pencil"></i>
							Editar Reserva
						</button>
						<button class="btn-action btn-eliminar"
							onclick="return eliminarReserva(${reserva.id});"> <i
							class="bi bi-trash"></i> Eliminar Reserva
						</button>
					</c:if>
					<button
						onclick="location.href='${pageContext.request.contextPath}/habitaciones/${reserva.idHabitacion}/detalle'"
						class="btn-action btn-ver-habitacion"> <i
						class="bi bi-door-open"></i> Ver Habitación
					</button> <button
						onclick="location.href='${pageContext.request.contextPath}/huespedes/${reserva.idHuesped}/detalle'"
						class="btn-action btn-ver-huesped"> <i
						class="bi bi-person-circle"></i> Ver Huésped
					</button>
				</div>

				<form id="formEliminarReserva" method="post" style="display: none;"></form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
	<script>
		function eliminarReserva(id) {
			if (!confirm('¿Está seguro de que desea eliminar esta reserva?')) {
				return false;
			}

			const form = document.getElementById("formEliminarReserva");
			form.action = '${pageContext.request.contextPath}/reservas/' + id + '/eliminar';
			form.submit();
			return false;
		}
	</script>
</body>
</html>
