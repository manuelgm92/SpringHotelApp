<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Detalle de Huésped - Spring Hotel</title>

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
				<i class="bi bi-door-open"></i> Detalle de Huésped
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

		<!-- Card de detalle -->
		<div class="detail-card">
			<!-- Header -->
			<div class="detail-header">
				<div>
					<h3>Huésped ${huesped.nombreHuesped}</h3>
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
							<span>NOMBRE</span>
							<div class="value">${huesped.nombreHuesped}</div>
						</div>
						<div class="info-item">
							<span>APELLIDOS</span>
							<div class="value">${huesped.apellidosHuesped}</div>
						</div>
						<div class="info-item">
							<span>DNI - PASAPORTE</span>
							<div class="value">${huesped.dniPasaporte}</div>
						</div>
					</div>
				</div>

				<div class="info-section">
					<h4>
						<i class="bi bi-person-lines-fill"></i> Contacto
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>EMAIL</span>
							<div class="value">${huesped.email}</div>
						</div>
						<div class="info-item">
							<span>TELÉFONO</span>
							<div class="value">${huesped.telefonoHuesped}</div>
						</div>
					</div>
				</div>

				<div class="info-section">
					<h4>
						<i class="bi bi-geo-alt-fill"></i> Dirección
					</h4>
					<div class="info-row">
						<div class="info-item">
							<span>DIRECCIÓN</span>
							<div class="value">${huesped.direccion}</div>
						</div>
					</div>
				</div>

				<!-- Botones de Acción -->
				
				<div class="action-buttons">
					<c:if test="${esRecepcionista}">
						<button onclick="location.href='${pageContext.request.contextPath}/huespedes/${huesped.id}/editar'"
							class="btn-action btn-editar">
							<i class="bi bi-pencil"></i> Editar Huésped
						</button>
						<button class="btn-action btn-eliminar"
							onclick="return eliminarHuesped(${huesped.id});">
							<i class="bi bi-trash"></i> Eliminar Huésped
						</button>
					</c:if>
				</div>
				
				<form id="formEliminarHuesped" method="post" style="display:none;"></form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
	<script>
		function eliminarHuesped(id) {
			if (!confirm('¿Está seguro de que desea eliminar a este huésped?')) {
				return false;
			}

			const form = document.getElementById("formEliminarHuesped");
			form.action = '${pageContext.request.contextPath}/huespedes/' + id + '/eliminar';
			form.submit();
			return false;
		}
	</script>
	
</body>
</html>
