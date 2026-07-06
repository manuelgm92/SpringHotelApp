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
		<c:when test="${habitacion.id != null}">Editar</c:when>
		<c:otherwise>Nueva</c:otherwise>
	</c:choose> Habitación - Spring Hotel</title>

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
				<i class="bi bi-door-open"></i>
				<c:choose>
					<c:when test="${habitacion.id != null}">Editar Habitación</c:when>
					<c:otherwise>Nueva Habitación</c:otherwise>
				</c:choose>
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

		<!-- Card de formulario -->
		<div class="form-card">
			<!-- Header -->
			<div class="form-header">
				<h3>
					<c:choose>
						<c:when test="${habitacion.id != null}">
							<i class="bi bi-pencil"></i>
                            Editar Habitación #${habitacion.numeroHabitacion}
                        </c:when>
						<c:otherwise>
							<i class="bi bi-plus-lg"></i>
                            Crear Nueva Habitación
                        </c:otherwise>
					</c:choose>
				</h3>
				<p>
					<c:choose>
						<c:when test="${habitacion.id != null}">
                            Modifica los datos de la habitación seleccionada
                        </c:when>
						<c:otherwise>
                            Completa el formulario para registrar una nueva habitación en el sistema
                        </c:otherwise>
					</c:choose>
				</p>
			</div>

			<!-- Body -->
			<div class="form-body">
				<!-- Determinar la action según si es alta o edición -->
				<c:choose>
					<c:when test="${habitacion.id != null}">
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/habitaciones/${habitacion.id}/editar" />
					</c:when>
					<c:otherwise>
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/habitaciones/nueva" />
					</c:otherwise>
				</c:choose>

				<form:form modelAttribute="habitacion" method="POST"
					action="${formAction}" novalidate="novalidate">
					<!-- Campo ID oculto si es edición -->
					<c:if test="${habitacion.id != null}">
						<form:hidden path="id" />
					</c:if>

					<!-- Sección Información Básica -->
					<div class="form-section">
						<h4>
							<i class="bi bi-info-circle"></i> Información Básica
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="numeroHabitacion" class="form-label"> Número
									de Habitación <span class="required">*</span>
								</label>
								<form:input path="numeroHabitacion" id="numeroHabitacion"
									type="number" class="form-control" placeholder="Ej: 101"
									required="required" />
								<form:errors path="numeroHabitacion" class="invalid-feedback" />
							</div>

							<div class="form-group">
								<label for="tipoHabitacion" class="form-label"> Tipo de
									Habitación <span class="required">*</span>
								</label> 
								<select name="tipoHabitacion" id="tipoHabitacion"
									class="form-select" required>
									<option value="">-- Seleccionar tipo --</option>
									<option value="INDIVIDUAL"
										${habitacion.tipoHabitacion == 'INDIVIDUAL' ? 'selected' : ''}>Individual</option>
									<option value="DOBLE"
										${habitacion.tipoHabitacion == 'DOBLE' ? 'selected' : ''}>Doble</option>
									<option value="SUITE"
										${habitacion.tipoHabitacion == 'SUITE' ? 'selected' : ''}>Suite</option>
								</select>
								<form:errors path="tipoHabitacion" class="invalid-feedback" />
							</div>

							<div class="form-group">
								<label for="capacidad" class="form-label"> Capacidad
									(personas) <span class="required">*</span>
								</label>
								<form:input path="capacidad" id="capacidad" type="number"
									class="form-control" placeholder="Ej: 2" min="1"
									required="required" />
								<form:errors path="capacidad" class="invalid-feedback" />
							</div>
						</div>
					</div>

					<!-- Sección Precios -->
					<div class="form-section">
						<h4>
							<i class="bi bi-cash-coin"></i> Tarifas
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="precioNoche" class="form-label"> Precio por
									Noche (€) <span class="required">*</span>
								</label>
								<form:input path="precioNoche" id="precioNoche" type="number"
									class="form-control" placeholder="Ej: 80.00" step="0.01"
									required="required" />
								<form:errors path="precioNoche" class="invalid-feedback" />
							</div>
						</div>
					</div>

					<!-- Sección Estado -->
					<div class="form-section">
						<h4>
							<i class="bi bi-gear"></i> Estado y Características
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="estadoHabitacion" class="form-label"> Estado
									<span class="required">*</span>
								</label> 
								<select name="estadoHabitacion" id="estadoHabitacion"
									class="form-select" required>
									<option value="">-- Seleccionar estado --</option>
									<option value="LIBRE"
										${habitacion.estadoHabitacion == 'LIBRE' ? 'selected' : ''}>Libre</option>
									<option value="OCUPADA"
										${habitacion.estadoHabitacion == 'OCUPADA' ? 'selected' : ''}>Ocupada</option>
									<option value="MANTENIMIENTO"
										${habitacion.estadoHabitacion == 'MANTENIMIENTO' ? 'selected' : ''}>Mantenimiento</option>
									<option value="LIMPIEZA"
										${habitacion.estadoHabitacion == 'LIMPIEZA' ? 'selected' : ''}>Limpieza</option>
								</select>
								<form:errors path="estadoHabitacion" class="invalid-feedback" />
							</div>

							<div class="form-group">
								<div class="form-check" style="margin-top: 34px;">
									<form:checkbox path="adaptadaDiscapacidad"
										id="adaptadaDiscapacidad" class="form-check-input" />
									<label class="form-check-label" for="adaptadaDiscapacidad">
										Adaptada para personas con discapacidad 
									</label>
								</div>
								<small class="form-text">Marca esta opción si la
									habitación dispone de accesibilidad para discapacitados</small>
							</div>
						</div>
					</div>

					<!-- Botones de acción -->
					<div class="action-buttons">
						<button type="submit" class="btn-action btn-guardar">
							<i class="bi bi-check-lg"></i>
							<c:choose>
								<c:when test="${habitacion.id != null}">Guardar Cambios</c:when>
								<c:otherwise>Crear Habitación</c:otherwise>
							</c:choose>
						</button>
						<button onclick="location.href='${pageContext.request.contextPath}/habitaciones'"
							class="btn-action btn-cancelar"> 
							<i class="bi bi-x-lg"></i> Cancelar
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
