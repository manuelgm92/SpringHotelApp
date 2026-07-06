<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Listado de Incidencias - Spring Hotel</title>

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

		<!-- Header -->
		<div class="page-header">
			<h2>
				<i class="bi bi-exclamation-octagon"></i> Listado de Incidencias
			</h2>

			<button onclick="location.href='${pageContext.request.contextPath}/incidencias/nueva'"
				class="btn-crear"> <i class="bi bi-plus-lg"></i> Nueva
				Incidencia
			</button>
		</div>
		<!-- Filtros -->
		<div class="filtros-container">

			<form method="get"
				action="${pageContext.request.contextPath}/incidencias"
				class="filtro-form">

				<label for="prioridad" class="form-label"> <i
					class="bi bi-funnel"></i> Filtrar por prioridad:
				</label> <select name="prioridad" id="prioridad" class="form-select"
					style="width: 200px; display: inline-block;">
					<option value="">-- Todas --</option>
					<option value="ALTA">Alta</option>
					<option value="MEDIA">Media</option>
					<option value="BAJA">Baja</option>
				</select>

				<button type="submit" class="btn btn-primary btn-sm">
					<i class="bi bi-search"></i> Aplicar
				</button>

				<button onclick="location.href='${pageContext.request.contextPath}/incidencias'"
					class="btn btn-secondary btn-sm"> <i class="bi bi-x-circle"></i>
					Limpiar
				</button>

			</form>

		</div>

		<!-- Mostrar filtro aplicado -->
		<c:if test="${not empty filtroAplicado}">
			<div class="alert alert-info alert-dismissible fade show mt-3"
				role="alert">
				<i class="bi bi-funnel-fill"></i> ${filtroAplicado}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
		<!-- Mensajes -->
		<c:if test="${not empty okMensaje}">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<i class="bi bi-check-circle-fill"></i> ${okMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<c:if test="${not empty errorMensaje}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<!-- Tabla de incidencias -->
		<c:choose>

			<c:when test="${not empty incidencias}">
				<div class="table-container">
					<div class="table-responsive">

						<table class="table">
							<thead>
								<tr>

									<th>Habitación</th>
									<th>Estado</th>
									<th>Prioridad</th>
									<th>Descripción</th>
									<th>Fecha Apertura</th>
									<th>Fecha Cierre</th>
									<th>Técnico</th>
									<th>Observaciones</th>
									<th>Acciones</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="inc" items="${incidencias}">
									<tr>



										<td>${habitacionPorId[inc.idHabitacion]}</td>

										<td><c:choose>
												<c:when test="${inc.estadoIncidencia == 'ABIERTA'}">
													<span class="badge bg-danger"> <i
														class="bi bi-exclamation-circle"></i> Abierta
													</span>
												</c:when>

												<c:when test="${inc.estadoIncidencia == 'EN_CURSO'}">
													<span class="badge bg-warning text-dark"> <i
														class="bi bi-tools"></i> En curso
													</span>
												</c:when>

												<c:otherwise>
													<span class="badge bg-success"> <i
														class="bi bi-check-circle"></i> Cerrada
													</span>
												</c:otherwise>
											</c:choose></td>

										<td><c:choose>
												<c:when test="${inc.prioridad == 'ALTA'}">
													<span class="badge bg-danger">Alta</span>
												</c:when>
												<c:when test="${inc.prioridad == 'MEDIA'}">
													<span class="badge bg-warning text-dark">Media</span>
												</c:when>
												<c:otherwise>
													<span class="badge bg-info">Baja</span>
												</c:otherwise>
											</c:choose></td>

										<td>${inc.descripcion}</td>

										<td>${inc.fechaApertura}</td>

										<td><c:choose>
												<c:when test="${inc.fechaCierre != null}">
                                                    ${inc.fechaCierre}
                                                </c:when>
												<c:otherwise>
													<span class="text-muted">—</span>
												</c:otherwise>
											</c:choose></td>

										<td><c:choose>
												<c:when test="${not empty inc.tecnicoAsignado}">
                                                    ${inc.tecnicoAsignado}
                                                </c:when>
												<c:otherwise>
													<span class="text-muted">Sin asignar</span>
												</c:otherwise>
											</c:choose></td>

										<td><c:choose>
												<c:when test="${not empty inc.observaciones}">
                                                    ${inc.observaciones}
                                                </c:when>
												<c:otherwise>
													<span class="text-muted">—</span>
												</c:otherwise>
											</c:choose></td>


										<td class="actions-cell">
											<!-- Ver detalle --> <button
											onclick="location.href='${pageContext.request.contextPath}/incidencias/${inc.id}/detalle'"
											class="btn-action btn-detalle" title="Ver Detalle"> <i
												class="bi bi-eye-fill"></i>
										</button> <c:if test="${esRecepcionista}">
												<!-- Editar -->
												<button
													onclick="location.href='${pageContext.request.contextPath}/incidencias/${inc.id}/editar'"
													class="btn-action btn-editar" title="Editar"> <i
													class="bi bi-pencil-fill"></i>
												</button>

												<!-- Eliminar -->
												<form
													action="${pageContext.request.contextPath}/incidencias/${inc.id}/eliminar"
													method="post" style="display: inline;"
													onsubmit="return confirm('¿Está seguro de que desea eliminar la incidencia #${inc.id}?');">
													<button type="submit" class="btn-action btn-eliminar"
														title="Eliminar">
														<i class="bi bi-trash-fill"></i>
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
					<h3>No hay incidencias registradas</h3>
					<p>Comienza a registrar incidencias del hotel</p>

					<button onclick="location.href='${pageContext.request.contextPath}/incidencias/nueva'"
						class="btn-crear"> <i class="bi bi-plus-lg"></i> Crear Primera
						Incidencia
					</button>
				</div>
			</c:otherwise>

		</c:choose>

	</div>

</body>
