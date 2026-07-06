<%-- Fragmento reutilizable: barra de navegacion comun a todas las vistas autenticadas --%>
<nav class="navbar navbar-expand-lg navbar-dark bg-transparent border-bottom border-white border-opacity-10 mb-4">
	<div class="container">
		<a class="navbar-brand fw-bold fs-3" href="${pageContext.request.contextPath}/">
			<i class="bi bi-building-fill-check me-2"></i>Spring Hotel
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
			aria-controls="navbarNav" aria-expanded="false" aria-label="Abrir navegaci&#243;n">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav me-auto">
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/principal"><i class="bi bi-house-door me-1"></i> Inicio</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/habitaciones"><i class="bi bi-door-closed me-1"></i> Habitaciones</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/huespedes"><i class="bi bi-people me-1"></i> Hu&#233;spedes</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/reservas"><i class="bi bi-calendar-check me-1"></i> Reservas</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/incidencias"><i class="bi bi-exclamation-triangle me-1"></i> Incidencias</a></li>
				<c:if test="${usuarioSesion.perfil == 'SUPERVISOR'}">
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/usuarios"><i class="bi bi-person-gear me-1"></i> Usuarios</a></li>
				</c:if>
			</ul>
			<div class="d-flex align-items-center">
				<div class="text-white me-3 d-none d-md-block text-end">
					<small class="d-block text-white-50">Empleado Conectado</small>
					<span class="fw-bold">${usuarioSesion.nombreUsuario}</span>
					<button onclick="location.href='${pageContext.request.contextPath}/usuarios/perfilUsuario'" class="btn btn-outline-light btn-sm">
						<i class="bi bi-person-circle"></i> Perfil
					</button>
				</div>
				<button onclick="location.href='${pageContext.request.contextPath}/logout'" class="btn btn-outline-light btn-sm rounded-pill px-3">
					<i class="bi bi-box-arrow-right me-1"></i> Salir
				</button>
			</div>
		</div>
	</div>
</nav>
