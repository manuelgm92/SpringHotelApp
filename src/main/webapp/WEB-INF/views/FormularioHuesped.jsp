<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:choose>
		<c:when test="${huesped.id != null}">Editar</c:when>
		<c:otherwise>Nueva</c:otherwise>
	</c:choose> Huésped - Spring Hotel</title>

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
					<c:when test="${huesped.id != null}">Editar Huésped</c:when>
					<c:otherwise>Nuevo Huésped</c:otherwise>
				</c:choose>
			</h2>
			<button onclick="location.href='${pageContext.request.contextPath}/huespedes'"
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
						<c:when test="${huesped.id != null}">
							<i class="bi bi-pencil"></i>
                            Editar Huésped
                        </c:when>
						<c:otherwise>
							<i class="bi bi-plus-lg"></i>
                            Crear Nuevo Huésped
                        </c:otherwise>
					</c:choose>
				</h3>
				<p>
					<c:choose>
						<c:when test="${huesped.id != null}">
                            Modifica los datos del huésped seleccionado
                        </c:when>
						<c:otherwise>
                            Completa el formulario para registrar un nuevo huésped en el sistema
                        </c:otherwise>
					</c:choose>
				</p>
			</div>

			<!-- Body -->
			<div class="form-body">
				<!-- Determinar la action según si es alta o edición -->
				<c:choose>
					<c:when test="${huesped.id != null}">
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/huespedes/${huesped.id}/editar" />
					</c:when>
					<c:otherwise>
						<c:set var="formAction"
							value="${pageContext.request.contextPath}/huespedes/nuevo" />
					</c:otherwise>
				</c:choose>

				<form:form modelAttribute="huesped" method="POST"
					action="${formAction}" novalidate="novalidate">
					<!-- Campo ID oculto si es edición -->
					<c:if test="${huesped.id != null}">
						<form:hidden path="id" />
					</c:if>

					<!-- Sección Información Básica -->
					<div class="form-section">
						<h4>
							<i class="bi bi-info-circle"></i> Información Básica
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="nombreHuesped" class="form-label">NOMBRE<span class="required">*</span>
								</label>
								<form:input path="nombreHuesped" id="nombreHuesped"
									type="text" class="form-control" placeholder="Nombre"
									required="required" />
								<form:errors path="nombreHuesped" class="invalid-feedback" />
							</div>

							<div class="form-group">
								<label for="apellidosHuesped" class="form-label">APELLIDOS<span class="required">*</span>
								</label> 
								<form:input path="apellidosHuesped" id="apellidosHuesped"
									type="text" class="form-control" placeholder="Apellidos"
									required="required" />
								<form:errors path="apellidosHuesped" class="invalid-feedback" />
							</div>

							<div class="form-group">
								<label for="dniPasaporte" class="form-label"> DNI - PASAPORTE <span class="required">*</span>
								</label>
								<form:input path="dniPasaporte" id="dniPasaporte" type="text"
									class="form-control" placeholder="" 
									required="required" />
								<form:errors path="dniPasaporte" class="invalid-feedback" />
							</div>
						</div>
					</div>

					<!-- Sección Precios -->
					<div class="form-section">
						<h4>
							<i class="bi bi-person-lines-fill"></i> Contacto
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="email" class="form-label"> EMAIL <span class="required">*</span>
								</label>
								<form:input path="email" id="email" type="email"
									class="form-control" placeholder=""
									required="required" />
								<form:errors path="email" class="invalid-feedback" />
							</div>
						</div>
						<div class="form-grid">
							<div class="form-group">
								<label for="telefonoHuesped" class="form-label"> TELÉFONO <span class="required">*</span>
								</label>
								<form:input path="telefonoHuesped" id="telefonoHuesped" type="text"
									class="form-control" placeholder="" 
									required="required" />
								<form:errors path="telefonoHuesped" class="invalid-feedback" />
							</div>
						</div>
					</div>

					<!-- Sección Estado -->
					<div class="form-section">
						<h4>
							<i class="bi bi-geo-alt-fill"></i> Dirección
						</h4>
						<div class="form-grid">
							<div class="form-group">
								<label for="direccion" class="form-label"> Dirección</label> 
								<form:input path="direccion" id="direccion" type="text"
									class="form-control" placeholder=""/>
								<form:errors path="direccion" class="invalid-feedback" />
							</div>
						</div>
					</div>

					<!-- Botones de acción -->
					<div class="action-buttons">
						<button type="submit" class="btn-action btn-guardar">
							<i class="bi bi-check-lg"></i>
							<c:choose>
								<c:when test="${huesped.id != null}">Guardar Cambios</c:when>
								<c:otherwise>Crear Huésped</c:otherwise>
							</c:choose>
						</button>
						<button onclick="location.href='${pageContext.request.contextPath}/huespedes'"
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
