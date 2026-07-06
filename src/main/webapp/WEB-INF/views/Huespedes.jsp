<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestión de Huéspedes - Spring Hotel</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq" crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body class="fondo">
	<!-- Navbar -->
	<%@ include file="includes/navbar.jsp" %>

	<div class="container">
		<div class="card shadow-lg border-0"
			style="border-radius: 15px; background: rgba(255, 255, 255, 0.95);">
			<div class="card-body p-4">

				<div class="row align-items-center mb-4">
					<div class="col-md-6">
						<h2 class="fw-bold text-dark mb-0">
							<i class="bi bi-people-fill text-primary me-2"></i>Listado de
							Huéspedes
						</h2>
					</div>
					<div class="col-md-6 text-md-end mt-3 mt-md-0">
						<button onclick="location.href='${pageContext.request.contextPath}/huespedes/nuevo'"
							class="btn-crear"> <i class="bi bi-plus-circle me-2"></i>Registrar
							Huésped
						</button>
					</div>
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
				<!-- Formulario de búsqueda -->
				<form method="get"
					action="${pageContext.request.contextPath}/huespedes"
					class="row g-3 mb-4">

					<div class="col-md-8">
						<input type="text" name="textoBusqueda" class="form-control"
							placeholder="Buscar por nombre o apellidos..."
							value="${textoBusqueda}">
					</div>

					<div class="col-md-4 d-flex gap-2">
						<button type="submit" class="btn btn-primary">
							<i class="bi bi-search"></i> Buscar
						</button>

						<button type="button" onclick="location.href='${pageContext.request.contextPath}/huespedes'"
							class="btn btn-secondary"> <i class="bi bi-x-circle"></i>Limpiar </button>
					</div>

				</form>
				<div class="table-responsive">
					<table class="table table-hover align-middle">
						<thead class="table-light">
							<tr>
								<th class="border-0">Nombre Completo</th>
								<th class="border-0">DNI/Pasaporte</th>
								<th class="border-0">Teléfono</th>
								<th class="border-0">Email</th>
								<th class="border-0">Dirección</th>
								<th class="border-0 text-center">Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="h" items="${huespedes}">
								<tr>
									<td>
										<div class="d-flex align-items-center">
											<div
												class="bg-primary bg-opacity-10 text-primary rounded-circle p-2 me-3"
												style="width: 40px; height: 40px; display: flex; align-items: center; justify-content: center;">
												<i class="bi bi-person"></i>
											</div>
											<div>
												<span class="d-block fw-bold text-dark">${h.nombreHuesped}
													${h.apellidosHuesped}</span>
											</div>
										</div>
									<td><span class="badge bg-light text-dark border">${h.dniPasaporte}</span>
									</td>

									<td><span class="badge bg-light text-dark border">${h.telefonoHuesped}</span>
									</td>
									<td>${h.email}</td>
									<td>${h.direccion}</td>
									<td class="text-center">
										<div class="d-flex justify-content-center gap-2">
											<button
												onclick="location.href='${pageContext.request.contextPath}/huespedes/${h.id}/detalle'"
												class="btn-action btn-detalle" title="Ver Detalle"> <i
												class="bi bi-eye-fill"></i> <span
												class="d-none d-xl-inline ms-1">Detalle</span>
											</button>
											<c:if test="${esRecepcionista}">
												<button
													onclick="location.href='${pageContext.request.contextPath}/huespedes/${h.id}/editar'"
													class="btn-action btn-editar" title="Modificar"> <i
													class="bi bi-pencil-fill"></i> <span
													class="d-none d-xl-inline ms-1">Modificar</span>
												</button>

												<form
													action="${pageContext.request.contextPath}/huespedes/${h.id}/eliminar"
													method="post" class="d-inline"
													onsubmit="return confirm('¿Está seguro de que desea eliminar el Huésped con id: ${h.id}?');">
													<button type="submit" class="btn-action btn-eliminar"
														title="Eliminar">
														<i class="bi bi-trash-fill"></i> <span
															class="d-none d-xl-inline ms-1">Eliminar</span>
													</button>
												</form>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty huespedes}">
								<tr>
									<td colspan="5" class="text-center py-5"><i
										class="bi bi-person-exclamation display-1 text-muted opacity-25"></i>
										<p class="text-muted mt-3">No hay huéspedes registrados
											actualmente.</p></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
