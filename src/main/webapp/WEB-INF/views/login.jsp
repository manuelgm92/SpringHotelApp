<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!doctype html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq" crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/custom.css?v=1"><link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
<title>Gestión Hotelera - Login</title>
</head>

<body class="fondo">

	<div
		class="login-container d-flex justify-content-center align-items-center">
		<div class="card shadow-lg p-4"
			style="width: 100%; max-width: 450px; border-radius: 15px; background-color: white;">

			<div class="text-center mb-4">
				<div
					class="icon-circle bg-primary text-white d-inline-flex align-items-center justify-content-center mb-3"
					style="width: 60px; height: 60px; border-radius: 50%;">
					<i class="bi bi-person-lock fs-2"></i>
				</div>
				<h3 class="fw-bold text-dark">Acceso al Sistema</h3>
				<p class="text-muted small">Por favor, introduce tus
					credenciales</p>
			</div>
			<%-- 
			  Bloque de validación dinámica:
			  Si el objeto 'error' existe en el request, se renderiza una alerta de Bootstrap 
			  con el mensaje capturado desde la capa de servicio (UsuarioServiceImpl).
			--%>
			<div class="card-body p-0">
				<c:if test="${not empty error}">
					<div class="alert alert-danger d-flex align-items-center"
						role="alert">
						<i class="bi bi-exclamation-triangle-fill me-2"></i>
						<div>${error}</div>
					</div>
				</c:if>

				<form id="login-usuarios" autocomplete="off" method="post"
					action="${pageContext.request.contextPath}/login">

					<div class="mb-3">
						<span class="form-label fw-semibold text-dark">Usuario</span>
						<div class="input-group">
							<span class="input-group-text bg-light"><i
								class="bi bi-person"></i></span> <input name="nombreUsuario"
								type="text" class="form-control" placeholder="Nombre de usuario"
								required>
						</div>
					</div>

					<div class="mb-4">
						<span class="form-label fw-semibold text-dark">Contraseña</span>
						<div class="input-group">
							<span class="input-group-text bg-light"><i
								class="bi bi-key"></i></span> <input name="passwordUsuario"
								type="password" class="form-control" placeholder="••••••••"
								required>
						</div>
					</div>

					<div class="d-grid gap-2">
						<button type="submit" class="btn btn-primary btn-lg fw-bold">
							Ingresar <i class="bi bi-box-arrow-in-right ms-2"></i>
						</button>
					</div>


				</form>
			</div>
		</div>
	</div>

</body>
</html>