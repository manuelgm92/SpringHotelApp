<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nuevo Usuario - Spring Hotel</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">

</head>
<body class="fondo">
	<!-- Navbar -->
	<%@ include file="includes/navbar.jsp" %>

	<div class="container">

		<div class="page-header">
			<h2><i class="bi bi-person-plus"></i> Nuevo Usuario</h2>
			<button onclick="location.href='${pageContext.request.contextPath}/usuarios'" class="btn-volver">
				<i class="bi bi-arrow-left"></i> Volver al Listado
			</button>
		</div>

		<div class="form-card">
			<div class="form-card-header">
				<h3><i class="bi bi-person-gear"></i> Datos del Usuario</h3>
			</div>
			<div class="form-card-body">

				<c:if test="${not empty error}">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						<i class="bi bi-exclamation-triangle-fill"></i> ${error}
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/usuarios/guardar" method="post">
					<div class="row g-4">
						<div class="col-md-6">
							<span class="form-label">Nombre de usuario</span>
							<input type="text" name="nombreUsuario" class="form-control" placeholder="Ej: jperez" required>
						</div>
						<div class="col-md-6">
							<span class="form-label">Nombre y apellidos</span>
							<input type="text" name="nombreCompleto" class="form-control" placeholder="Ej: Juan Pérez García" required>
						</div>
						<div class="col-md-6">
							<span class="form-label">Contraseña</span>
							<input type="password" name="passwordUsuario" class="form-control" placeholder="Mínimo 6 caracteres" required>
						</div>
						<div class="col-md-6">
							<span class="form-label">Perfil</span>
							<select name="perfil" class="form-select">
								<option value="RECEPCIONISTA">Recepcionista</option>
								<option value="SUPERVISOR">Supervisor</option>
							</select>
						</div>
					</div>

					<div class="d-flex gap-3 mt-4">
						<button type="submit" class="btn-guardar">
							<i class="bi bi-floppy"></i> Guardar Usuario
						</button>
						<button onclick="location.href='${pageContext.request.contextPath}/usuarios'" class="btn-cancelar">
							<i class="bi bi-x-lg"></i> Cancelar
						</button>
					</div>
				</form>

			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
