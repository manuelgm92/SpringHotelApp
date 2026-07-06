<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestión de Usuarios - Spring Hotel</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css"
	integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/views.css">

</head>
<body class="fondo">
	<!-- Navbar -->
	<%@ include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card shadow-lg border-0"
			style="border-radius: 15px; background: rgba(255, 255, 255, 0.95);">
			<div class="card-body p-4">

				<div class="row align-items-center mb-4">
					<div class="col-md-6">
						<h2 class="fw-bold text-dark mb-0">
							<i class="bi bi-person-gear text-primary me-2"></i>Gestión de
							Usuarios
						</h2>
					</div>
					<div class="col-md-6 text-md-end mt-3 mt-md-0">
						<button class="btn-crear"
							onclick="location.href='${pageContext.request.contextPath}/usuarios/nuevo'">
							<i class="bi bi-plus-lg"></i> Nuevo Usuario
						</button>
					</div>
				</div>

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

				<div class="table-responsive">
					<table class="table table-hover align-middle">
						<thead class="table-light">
							<tr>
								<th class="border-0">Usuario</th>
								<th class="border-0">Nombre Completo</th>
								<th class="border-0">Rol</th>
								<th class="border-0 text-center">Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="u" items="${usuarios}">
								<tr>
									<td>
										<div class="d-flex align-items-center">
											<div
												class="bg-primary bg-opacity-10 text-primary rounded-circle p-2 me-3"
												style="width: 40px; height: 40px; display: flex; align-items: center; justify-content: center;">
												<i class="bi bi-person"></i>
											</div>
											<span class="fw-bold text-dark">${u.nombreUsuario}</span>
										</div>
									</td>
									<td>${u.nombreCompleto}</td>
									<td><c:choose>
											<c:when test="${u.perfil == 'SUPERVISOR'}">
												<span class="badge-perfil-supervisor"><i
													class="bi bi-shield-check me-1"></i>${u.perfil}</span>
											</c:when>
											<c:otherwise>
												<span class="badge-perfil-recepcionista"><i
													class="bi bi-person-badge me-1"></i>${u.perfil}</span>
											</c:otherwise>
										</c:choose></td>
									<td class="text-center">
										<div class="d-flex justify-content-center gap-2">
											<button class="btn-action btn-editar"
												onclick="location.href='${pageContext.request.contextPath}/usuarios/editar?id=${u.id}'"
												title="Modificar">
												<i class="bi bi-pencil-fill"></i> <span
													class="d-none d-xl-inline ms-1">Modificar</span>
											</button>
											
											<form
												action="${pageContext.request.contextPath}/usuarios/eliminar"
												method="post" class="d-inline"
												onsubmit="return confirm('¿Estás seguro de que deseas eliminar a este usuario?');">
												<input type="hidden" name="id" value="${u.id}" />
												<button type="submit" class="btn-action btn-eliminar"
													title="Eliminar">
													<i class="bi bi-trash-fill"></i> <span
														class="d-none d-xl-inline ms-1">Eliminar</span>
												</button>
											</form>
										</div>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty usuarios}">
								<tr>
									<td colspan="4" class="text-center py-5"><i
										class="bi bi-person-exclamation display-1 text-muted opacity-25"></i>
										<p class="text-muted mt-3">No hay usuarios registrados
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
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
		crossorigin="anonymous"></script>
</body>
</html>
