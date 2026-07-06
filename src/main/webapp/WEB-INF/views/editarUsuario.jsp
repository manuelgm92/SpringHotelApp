<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Editar Usuario</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>

<body class="fondo">

	<div class="container mt-5">

		<div class="card shadow-lg">
			<div class="card-body p-4">

				<h3 class="mb-4">Editar Usuario</h3>

				<c:if test="${not empty error}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						${error}
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
				</c:if>

				<form
					action="${pageContext.request.contextPath}/usuarios/actualizar"
					method="post">

					<!-- ID oculto -->
					<input type="hidden" name="id" value="${usuario.id}">

					<!-- NOMBRE -->
					<div class="mb-3">
						<span class="form-label">Nombre usuario</span> <input
							type="text" name="nombreUsuario" class="form-control"
							value="${usuario.nombreUsuario}" required>
					</div>

					<!-- NOMBRE COMPLETO -->
					<div class="mb-3">
						<span class="form-label">Nombre y apellidos</span> <input
							type="text" name="nombreCompleto" class="form-control"
							value="${usuario.nombreCompleto}" required>
					</div>

					<!-- PERFIL -->
					<div class="mb-3">
						<span class="form-label">Perfil</span> <select name="perfil"
							class="form-select">

							<option value="RECEPCIONISTA"
								<c:if test="${usuario.perfil == 'RECEPCIONISTA'}">selected</c:if>>
								Recepcionista</option>

							<option value="SUPERVISOR"
								<c:if test="${usuario.perfil == 'SUPERVISOR'}">selected</c:if>>
								Supervisor</option>

						</select>
					</div>

					<!-- BOTONES -->
					<button type="submit" class="btn btn-success">Actualizar</button>

					<button onclick="location.href='${pageContext.request.contextPath}/usuarios'"
						class="btn btn-secondary"> Cancelar </button>

				</form>

			</div>
		</div>

	</div>

</body>
</html>