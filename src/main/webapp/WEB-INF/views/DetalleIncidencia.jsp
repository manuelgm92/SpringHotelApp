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

		<!-- Header -->
		<div class="page-header">
			<h2>
				<i class="bi bi-exclamation-octagon"></i> Detalle de Incidencia
			</h2>

			<button onclick="location.href='${pageContext.request.contextPath}/incidencias'"
				class="btn-volver"> <i class="bi bi-arrow-left"></i> Volver al
				Listado
			</button>
		</div>

		<!-- Mensajes -->
		<c:if test="${not empty errorMensaje}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<!-- Card -->
		<div class="detail-card">

			<!-- Header -->
			<div class="detail-header">
				<div>
					<h3>Incidencia #${incidencia.id}</h3>

					<div style="margin-top: 10px; display: flex; gap: 10px;">
						<!-- Estado -->
						<c:choose>
							<c:when test="${incidencia.estadoIncidencia == 'ABIERTA'}">
								<span class="badge-estado badge-libre"> <i
									class="bi bi-exclamation-circle"></i> Abierta
								</span>
							</c:when>

							<c:when test="${incidencia.estadoIncidencia == 'EN_CURSO'}">
								<span class="badge-estado badge-mantenimiento"> <i
									class="bi bi-tools"></i> En curso
								</span>
							</c:when>

							<c:otherwise>
								<span class="badge-estado badge-ocupada"> <i
									class="bi bi-check-circle"></i> Cerrada
								</span>
							</c:otherwise>
						</c:choose>

						<!-- Prioridad -->
						<c:choose>
							<c:when test="${incidencia.prioridad == 'ALTA'}">
								<span class="badge bg-danger">Alta</span>
							</c:when>
							<c:when test="${incidencia.prioridad == 'MEDIA'}">
								<span class="badge bg-warning text-dark">Media</span>
							</c:when>
							<c:otherwise>
								<span class="badge bg-info">Baja</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<!-- Info rápida -->
				<div class="info-rapida">
					<div>
						<i class="bi bi-door-open"></i>
						<div>
							<div style="font-size: 12px; opacity: 0.9;">Habitación</div>
							<div>#${incidencia.idHabitacion}</div>
							<i class="bi bi-calendar-event"></i>
							<div>
								<div style="font-size: 12px; opacity: 0.9;">Apertura</div>
								<div>${incidencia.fechaApertura}</div>
							</div>
						</div>

						<div>
							<i class="bi bi-calendar-check"></i>
							<div>
								<div style="font-size: 12px; opacity: 0.9;">Cierre</div>
								<div>
									<c:choose>
										<c:when test="${incidencia.fechaCierre != null}">
                                    ${incidencia.fechaCierre}
                                </c:when>
										<c:otherwise>
											<span class="text-muted">—</span>
										</c:otherwise>
									</c:choose>
								</div>
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
								<span>Descripción</span>
								<div class="value">${incidencia.descripcion}</div>
							</div>

							<div class="info-item">
								<span>Técnico Asignado</span>
								<div class="value">
									<c:choose>
										<c:when test="${not empty incidencia.tecnicoAsignado}">
                                    ${incidencia.tecnicoAsignado}
                                </c:when>
										<c:otherwise>
											<span class="text-muted">Sin asignar</span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>

							<div class="info-item">
								<span>Observaciones</span>
								<div class="value">
									<c:choose>
										<c:when test="${not empty incidencia.observaciones}">
                                    ${incidencia.observaciones}
                                </c:when>
										<c:otherwise>
											<span class="text-muted">—</span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>

					<!-- Botones -->
					<div class="action-buttons">

						<c:if test="${esRecepcionista}">
							<button
								onclick="location.href='${pageContext.request.contextPath}/incidencias/${incidencia.id}/editar'"
								class="btn-action btn-editar"> <i class="bi bi-pencil"></i>
								Editar Incidencia
							</button>
							<button class="btn-action btn-eliminar"
								onclick="return eliminarIncidencia(${incidencia.id});"> <i
								class="bi bi-trash"></i> Eliminar Incidencia
							</button>
						</c:if>
						<button
							onclick="location.href='${pageContext.request.contextPath}/habitaciones/${incidencia.idHabitacion}/detalle'"
							class="btn-action btn-incidencias"> <i
							class="bi bi-door-open"></i> Ver Habitación
						</button>
					</div>

					<form id="formEliminarIncidencia" method="post"
						style="display: none;"></form>

				</div>
			</div>
		</div>

		<script>
    function eliminarIncidencia(id) {
        if (!confirm('¿Está seguro de que desea eliminar esta incidencia?')) {
            return false;
        }

        const form = document.getElementById("formEliminarIncidencia");
        form.action = `${pageContext.request.contextPath}/incidencias/${id}/eliminar`;
        form.submit();
        return false;
    }
</script>
