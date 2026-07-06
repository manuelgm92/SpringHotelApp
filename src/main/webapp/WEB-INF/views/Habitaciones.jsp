<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Listado de Habitaciones - Spring Hotel</title>

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
				<i class="bi bi-door-open"></i> Listado de Habitaciones
			</h2>
			<!-- Mostrar botón "Nueva Habitación" solo para ADMIN -->
			<button
				onclick="location.href='${pageContext.request.contextPath}/habitaciones/nueva'"
				class="btn-crear">
				<i class="bi bi-plus-lg"></i> Nueva Habitación
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

		<!-- Tabla de habitaciones -->
		<c:choose>
			<c:when test="${not empty habitaciones}">
				<div class="table-container">
					<div class="table-responsive">
						<table class="table">
							<thead>
								<tr>
									<th>Número</th>
									<th>Tipo</th>
									<th>Precio/Noche</th>
									<th>Capacidad</th>
									<th>Estado</th>
									<th>Adaptada</th>
									<th>Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="hab" items="${habitaciones}">
									<tr style="cursor: pointer;">
										<td data-label="Número"
											onclick="window.location='${pageContext.request.contextPath}/habitaciones/${hab.id}/detalle';"
											onkeydown="if(event.key === 'Enter' || event.key === ' ') window.location='${pageContext.request.contextPath}/habitaciones/${hab.id}/detalle';"
											tabindex="0">${hab.numeroHabitacion}</td>
										<td data-label="Tipo">${hab.tipoHabitacion}</td>
										<td data-label="Precio/Noche" class="precio-noche">${hab.precioNoche}
											€</td>
										<td data-label="Capacidad" class="capacidad-info"><i
											class="bi bi-people-fill"></i> ${hab.capacidad} pers.</td>
										<td data-label="Estado"><c:choose>
												<c:when test="${hab.estadoHabitacion == 'LIBRE'}">
													<span class="badge-estado badge-disponible"> <i
														class="bi bi-check-circle-fill"></i> Libre
													</span>
												</c:when>
												<c:when test="${hab.estadoHabitacion == 'OCUPADA'}">
													<span class="badge-estado badge-ocupada"> <i
														class="bi bi-x-circle-fill"></i> Ocupada
													</span>
												</c:when>
												<c:when test="${hab.estadoHabitacion == 'MANTENIMIENTO'}">
													<span class="badge-estado badge-mantenimiento"> <i
														class="bi bi-exclamation-circle-fill"></i> Mantenimiento
													</span>
												</c:when>
												<c:when test="${hab.estadoHabitacion == 'LIMPIEZA'}">
													<span class="badge-estado badge-limpieza"> <i
														class="bi bi-brush-fill"></i> Limpieza
													</span>
												</c:when>
												<c:otherwise>
													<span class="badge-estado">${hab.estadoHabitacion}</span>
												</c:otherwise>
											</c:choose></td>
										<td data-label="Adaptada"><c:choose>
												<c:when test="${hab.adaptadaDiscapacidad}">
													<span class="badge-estado badge-si"> <i
														class="bi bi-check-lg"></i> Sí
													</span>
												</c:when>
												<c:otherwise>
													<span class="badge-estado badge-no"> <i
														class="bi bi-x-lg"></i> No
													</span>
												</c:otherwise>
											</c:choose></td>


										<td data-label="Acciones" class="actions-cell">
											<!-- Botón Ver Detalle --> <a
											href="${pageContext.request.contextPath}/habitaciones/${hab.id}/detalle"
											class="btn-action btn-detalle" title="Ver Detalle"
											onclick="event.stopPropagation();"> <i
												class="bi bi-eye-fill"></i> Detalle
										</a> <c:if test="${esRecepcionista}">

												<!-- Botón Modificar -->
												<button type="button" class="btn-action btn-editar"
													title="Modificar"
													onclick="event.stopPropagation(); window.location='${pageContext.request.contextPath}/habitaciones/${hab.id}/editar';">
													<i class="bi bi-pencil-fill"></i> Modificar
												</button>

												<!-- Botón Eliminar -->
												<form
													action="${pageContext.request.contextPath}/habitaciones/${hab.id}/eliminar"
													method="post" style="display: inline;"
													onsubmit="event.stopPropagation(); return confirm('¿Está seguro de que desea eliminar la habitación ${hab.numeroHabitacion}?');">

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
					<h3>No hay habitaciones registradas</h3>
					<p>Comienza a agregar habitaciones al sistema hotelero</p>
					<button
						onclick="location.href='${pageContext.request.contextPath}/habitaciones/nueva'"
						class="btn-crear">
						<i class="bi bi-plus-lg"></i> Crear Primera Habitación
					</button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
