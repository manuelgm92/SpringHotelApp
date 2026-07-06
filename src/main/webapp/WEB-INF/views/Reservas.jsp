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
<title>Listado de Reservas - Spring Hotel</title>

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
				<i class="bi bi-calendar-check"></i> Listado de Reservas
			</h2>

			<button
				onclick="location.href='${pageContext.request.contextPath}/reservas/nueva'"
				class="btn-crear">
				<i class="bi bi-plus-lg"></i> Nueva Reserva
			</button>

		</div>

		<!-- Mensajes de éxito/error -->
		<c:if test="${not empty okMensaje}">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<i class="bi bi-check-circle-fill"></i> ${okMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>

		<c:if test="${not empty errorMensaje}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>

		<!-- Tabla de reservas -->
		<c:choose>
			<c:when test="${not empty reservas}">
				<div class="table-container">
					<div class="table-responsive">
						<table class="table">
							<thead>
								<tr>
									<th>Huésped</th>
									<th>Habitación</th>
									<th>Check-In</th>
									<th>Check-Out</th>
									<th>Personas</th>
									<th>Estado</th>
									<th>Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="reserva" items="${reservas}">
									<tr style="cursor: pointer;">
										<td data-label="Huésped"
											onclick="window.location='${pageContext.request.contextPath}/reservas/${reserva.id}/detalle';"
											onkeydown="if(event.key === 'Enter' || event.key === ' ') window.location='${pageContext.request.contextPath}/reservas/${reserva.id}/detalle';"
											tabindex="0"><strong>${reserva.nombreHuesped}
												${reserva.apellidosHuesped}</strong><br> <span
											class="info-secundaria"> <i class="bi bi-envelope"></i>
												${reserva.emailHuesped}
										</span></td>

										<td data-label="Habitación"><strong>Hab.
												${reserva.numeroHabitacion}</strong> <br> <span
											class="info-secundaria">${reserva.tipoHabitacion}</span></td>
										<td data-label="Check-In"><span class="fecha-destacada">
												<i class="bi bi-calendar-event"></i> ${reserva.checkIn}
										</span></td>
										<td data-label="Check-Out"><span class="fecha-info">
												<i class="bi bi-calendar-x"></i> ${reserva.checkOut}
										</span></td>
										<td data-label="Personas"><i class="bi bi-people-fill"></i>
											${reserva.numPersonas}</td>
										<td data-label="Estado"><c:choose>
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
											</c:choose></td>


										<td data-label="Acciones" class="actions-cell">
											<!-- Botón Ver Detalle --> <a
											href="${pageContext.request.contextPath}/reservas/${reserva.id}/detalle"
											class="btn-action btn-detalle" title="Ver Detalle"
											onclick="event.stopPropagation();"> <i
												class="bi bi-eye-fill"></i> Detalle
										</a> <c:if test="${esRecepcionista}">

												<!-- Botón Modificar -->
												<button type="button" class="btn-action btn-editar"
													title="Modificar"
													onclick="event.stopPropagation(); window.location='${pageContext.request.contextPath}/reservas/${reserva.id}/editar';">
													<i class="bi bi-pencil-fill"></i> Modificar
												</button>

												<!-- Botón Eliminar -->
												<form
													action="${pageContext.request.contextPath}/reservas/${reserva.id}/eliminar"
													method="post" style="display: inline;"
													onsubmit="event.stopPropagation(); return confirm('¿Está seguro de que desea eliminar la reserva #${reserva.id}?');">

													<button type="submit" class="btn-action btn-eliminar"
														title="Eliminar">
														<i class="bi bi-trash-fill"></i> Eliminar
													</button>

												</form>

											</c:if>

										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="empty-state">
					<i class="bi bi-inbox"></i>
					<h3>No hay reservas registradas</h3>
					<p>Comienza a gestionar las reservas del hotel</p>
					<c:if test="${esRecepcionista}">
						<button class="btn-crear"
							onclick="location.href='${pageContext.request.contextPath}/reservas/nueva'">
							<i class="bi bi-plus-lg"></i> Crear Primera Reserva
						</button>
					</c:if>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
