<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- IA: Vista JSP generada con ayuda de IA(Copilot) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:choose>
		<c:when test="${incidencia.id != null}">Editar</c:when>
		<c:otherwise>Nueva</c:otherwise>
	</c:choose> Incidencia - Spring Hotel</title>

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
				<i class="bi bi-tools"></i>
				<c:choose>
					<c:when test="${incidencia.id != null}">Editar Incidencia</c:when>
					<c:otherwise>Nueva Incidencia</c:otherwise>
				</c:choose>
			</h2>
			<button onclick="location.href='${pageContext.request.contextPath}/incidencias'"
				class="btn-volver"> <i class="bi bi-arrow-left"></i> Volver al
				Listado
			</button>
		</div>

		<!-- MENSAJES -->
		<c:if test="${not empty errorMensaje}">
			<div class="alert alert-danger alert-dismissible fade show">
				<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<c:if test="${not empty exitoMensaje}">
			<div class="alert alert-success alert-dismissible fade show">
				<i class="bi bi-check-circle-fill"></i> ${exitoMensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<!-- CARD -->
		<div class="form-card">

			<div class="form-header">
				<h3>
					<c:choose>
						<c:when test="${incidencia.id != null}">
							<i class="bi bi-pencil"></i> Editar Incidencia #${incidencia.id}
            </c:when>
						<c:otherwise>
							<i class="bi bi-plus-lg"></i> Registrar Nueva Incidencia
            </c:otherwise>
					</c:choose>
				</h3>

				<p>
					<c:choose>
						<c:when test="${incidencia.id != null}">
                Modifica los datos de la incidencia seleccionada
            </c:when>
						<c:otherwise>
                Completa el formulario para registrar una incidencia en el sistema
            </c:otherwise>
					</c:choose>
				</p>
			</div>

			<div class="form-body">
				<c:choose>
					<c:when test="${incidencia.id != null}">
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/incidencias/${incidencia.id}/editar" />
					</c:when>
					<c:otherwise>
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/incidencias/nueva" />
					</c:otherwise>
				</c:choose>
				<!-- Formulario -->
				<form:form modelAttribute="incidencia" method="POST"
					action="${formAction}">

					<c:if test="${incidencia.id != null}">
						<form:hidden path="id" />
					</c:if>
					<!-- FECHA DE APERTURA ARRIBA COMO INFORMACIÓN -->
					<c:if test="${incidencia.id != null}">
						<div class="alert alert-secondary mt-3" style="font-size: 14px;">
							<i class="bi bi-clock-history"></i> <strong>Fecha de
								apertura:</strong> ${incidencia.fechaApertura}
						</div>

						<!-- Mantener la fecha en el formulario -->
						<form:hidden path="fechaApertura" />
					</c:if>

					<!-- INFORMACIÓN BÁSICA -->
					<div class="form-section">
						<h4>
							<i class="bi bi-info-circle"></i> Información Básica
						</h4>

						<div class="form-grid">

							<!-- HABITACIÓN -->
							<div class="form-group">
								<label for="habitacion" class="form-label"> Habitación <span
									class="required">*</span>
								</label>

						<form:select path="idHabitacion" id="habitacion"
									class="form-select" required="required">
									<form:option value="">-- Seleccionar habitación --</form:option>
									<form:options items="${habitaciones}" itemValue="id"
										itemLabel="numeroHabitacion" />
								</form:select>
							</div>

							<!-- PRIORIDAD -->
							<div class="form-group">
								<label for="prioridad" class="form-label"> Prioridad <span
									class="required">*</span>
								</label>

								<form:select path="prioridad" id="prioridad" class="form-select"
									required="required">
									<form:option value="">-- Seleccionar prioridad --</form:option>
									<form:option value="BAJA">Baja</form:option>
									<form:option value="MEDIA">Media</form:option>
									<form:option value="ALTA">Alta</form:option>
								</form:select>
							</div>

						</div>
					</div>
					<!-- ESTADO Y FECHAS -->
					<div class="form-section">
						<h4>
							<i class="bi bi-flag"></i> Estado 
						</h4>

						<div class="form-grid">

							<!-- ESTADO -->
							<div class="form-group">
								<label for="estadoIncidencia" class="form-label">Estado
									<span class="required">*</span>
								</label>
								<form:select path="estadoIncidencia" id="estadoIncidencia"
									class="form-select" required="required">
									<form:option value="ABIERTA">Abierta</form:option>
									<form:option value="EN_CURSO">En curso</form:option>
									<form:option value="CERRADA">Cerrada</form:option>
								</form:select>
							</div>


							<!-- FECHA CIERRE -->
							<div class="form-group">
								<label for="fechaCierre" class="form-label">Fecha de
									cierre</label>
								<form:input path="fechaCierre" id="fechaCierre"
									class="form-control"
									placeholder="Se asignará automáticamente al cerrar" 
									readonly="readonly"
									style="background-color: #e9ecef; cursor: not-allowed;"/>
							</div>

						</div>
					</div>

					<!-- DESCRIPCIÓN -->
					<div class="form-section">
						<h4>
							<i class="bi bi-pencil-square"></i> Descripción
						</h4>

						<div class="form-group">
							<label for="descripcion" class="form-label"> Descripción
								detallada <span class="required">*</span>
							</label>
							<form:textarea path="descripcion" id="descripcion"
								class="form-control" rows="4"
								placeholder="Describe el problema..." required="required" />
						</div>
					</div>

					<!-- DATOS OPCIONALES -->
					<div class="form-section">
						<h4>
							<i class="bi bi-person-badge"></i> Datos Opcionales
						</h4>

						<div class="form-grid">

							<div class="form-group">
								<label for="tecnicoAsignado" class="form-label">Técnico
									asignado</label>
								<form:input path="tecnicoAsignado" id="tecnicoAsignado"
									class="form-control" placeholder="Ej: Juan Pérez" />
							</div>

							<div class="form-group">
								<label for="observaciones" class="form-label">Observaciones</label>
								<form:textarea path="observaciones" id="observaciones"
									class="form-control" rows="3"
									placeholder="Notas adicionales..." />
							</div>

						</div>
					</div>

					<!-- Botones de acción -->
					<div class="action-buttons">
						<button type="submit" class="btn-action btn-guardar">
							<i class="bi bi-check-lg"></i>
							<c:choose>
								<c:when test="${incidencia.id != null}">Guardar Cambios</c:when>
								<c:otherwise>Crear Incidencia</c:otherwise>
							</c:choose>
						</button>
						<button onclick="location.href='${pageContext.request.contextPath}/incidencias'"
							class="btn-action btn-cancelar"> <i class="bi bi-x-lg"></i>
							Cancelar
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>
