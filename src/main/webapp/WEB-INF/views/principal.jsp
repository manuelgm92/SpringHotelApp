<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Panel Principal - Spring Hotel</title>

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
		<div class="row">
			<div class="col-lg-12">
				<div class="card shadow-lg border-0"
					style="border-radius: 15px; background: rgba(255, 255, 255, 0.95);">
					<div class="card-body p-5">
						<div class="row align-items-center">
							<div class="col-md-8">
								<h1 class="display-5 fw-bold text-dark">Bienvenido,
									${usuarioSesion.nombreUsuario}</h1>
								<p class="lead text-muted">Panel de administración del
									sistema hotelero.</p>
								<div class="mt-4">
									<span class="badge bg-primary px-3 py-2 rounded-pill me-2">
										<i class="bi bi-shield-check me-1"></i> Perfil:
										${usuarioSesion.perfil != null ? usuarioSesion.perfil : 'Empleado'}
									</span> <span class="badge bg-success px-3 py-2 rounded-pill">
										<i class="bi bi-clock me-1"></i> Último acceso: Hoy
									</span>
								</div>
							</div>
							<div class="col-md-4 text-center d-none d-md-block">
								<i class="bi bi-person-circle text-primary"
									style="font-size: 8rem; opacity: 0.2;"></i>
							</div>
						</div>

						<hr class="my-5 opacity-10">

						<div class="row g-4">
							<div class="col-md-3">
								 <button class="btn btn-light border-0 p-0"
            						onclick="location.href='${pageContext.request.contextPath}/habitaciones'">
									<div class="card h-100 border-0 shadow-sm hover-card bg-light">
										<div class="card-body text-center py-4">
											<i class="bi bi-door-open fs-1 text-primary mb-3"></i>
											<h5 class="text-dark fw-bold">Habitaciones</h5>
											<p class="text-muted small">Gestionar disponibilidad</p>
										</div>
									</div>
								</button>
							</div>
							<div class="col-md-3">
								<button class="btn btn-light border-0 p-0"
            						onclick="location.href='${pageContext.request.contextPath}/huespedes'">
									<div class="card h-100 border-0 shadow-sm hover-card bg-light">
										<div class="card-body text-center py-4">
											<i class="bi bi-people-fill fs-1 text-primary mb-3"></i>
											<h5 class="text-dark fw-bold">Huéspedes</h5>
											<p class="text-muted small">Registro de clientes</p>
										</div>
									</div>
								</button>
							</div>
							<div class="col-md-3">
								<button class="btn btn-light border-0 p-0"
            						onclick="location.href='${pageContext.request.contextPath}/reservas'">
									<div class="card h-100 border-0 shadow-sm hover-card bg-light">
										<div class="card-body text-center py-4">
											<i class="bi bi-calendar-check-fill fs-1 text-primary mb-3"></i>
											<h5 class="text-dark fw-bold">Reservas</h5>
											<p class="text-muted small">Nuevos registros</p>
										</div>
									</div>
								</button>
							</div>
							<div class="col-md-3">
								<button class="btn btn-light border-0 p-0"
            						onclick="location.href='${pageContext.request.contextPath}/incidencias'">
									<div class="card h-100 border-0 shadow-sm hover-card bg-light">
										<div class="card-body text-center py-4">
											<i class="bi bi-tools fs-1 text-primary mb-3"></i>
											<h5 class="text-dark fw-bold">Incidencias</h5>
											<p class="text-muted small">Mantenimiento</p>
										</div>
									</div>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>
