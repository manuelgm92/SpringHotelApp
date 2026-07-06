<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:choose>
		<c:when test="${reserva.id != null}">Editar</c:when>
		<c:otherwise>Nueva</c:otherwise>
	</c:choose> Reserva - Spring Hotel</title>

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
		<div class="row justify-content-center">
			<div class="col-12 col-md-10 col-xl-8">

				<div class="page-header">
					<h2>
						<i class="bi bi-calendar-check"></i>
						<c:choose>
							<c:when test="${reserva.id != null}">Editar Reserva</c:when>
							<c:otherwise>Nueva Reserva</c:otherwise>
						</c:choose>
					</h2>
					<button
						onclick="location.href='${pageContext.request.contextPath}/reservas'"
						class="btn-volver">
						<i class="bi bi-arrow-left"></i> Volver al Listado
					</button>
				</div>

				<c:if test="${not empty errorMensaje}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<i class="bi bi-exclamation-triangle-fill"></i> ${errorMensaje}
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
				</c:if>

				<div class="form-card">
					<div class="form-header">
						<h3>
							<c:choose>
								<c:when test="${reserva.id != null}">
									<i class="bi bi-pencil"></i> Editar Reserva
		                        </c:when>
								<c:otherwise>
									<i class="bi bi-plus-lg"></i> Crear Nueva Reserva
		                        </c:otherwise>
							</c:choose>
						</h3>
						<p>
							<c:choose>
								<c:when test="${reserva.id != null}">
		                            Modifica los datos de la reserva seleccionada en el sistema.
		                        </c:when>
								<c:otherwise>
		                            Completa el formulario para registrar una nueva reserva en el sistema.
		                        </c:otherwise>
							</c:choose>
						</p>
					</div>

					<div class="form-body">
						<c:choose>
							<c:when test="${reserva.id != null}">
								<c:set var="formAction"
									value="${pageContext.request.contextPath}/reservas/${reserva.id}/editar" />
							</c:when>
							<c:otherwise>
								<c:set var="formAction"
									value="${pageContext.request.contextPath}/reservas/nueva" />
							</c:otherwise>
						</c:choose>

						<form:form modelAttribute="reserva" method="POST"
							action="${formAction}" novalidate="novalidate">
							<form:hidden path="id" />
							<div class="form-section">

								<h4>
									<i class="bi bi-info-circle"></i> Fechas de Estancia
								</h4>
								<div class="form-grid">
									<div class="form-group">
										<label for="checkIn" class="form-label">CHECK-IN <span
											class="required">*</span></label>
										<form:input path="checkIn" id="checkIn" type="date"
											class="form-control form-control-date" required="required"
											onclick="if(typeof this.showPicker === 'function') this.showPicker();" />
										<c:if test="${reserva.id == null}">
											<script>
												document
														.getElementById('checkIn').min = new Date()
														.toISOString().split(
																'T')[0];
											</script>
										</c:if>
										<form:errors path="checkIn" class="invalid-feedback" />
									</div>

									<div class="form-group">
										<label for="checkOut" class="form-label">CHECK-OUT <span
											class="required">*</span></label>
										<form:input path="checkOut" id="checkOut" type="date"
											class="form-control form-control-date" required="required"
											onclick="if(typeof this.showPicker === 'function') this.showPicker();" />
										<c:if test="${reserva.id == null}">
											<script>
												document
														.getElementById('checkOut').min = new Date()
														.toISOString().split(
																'T')[0];
											</script>
										</c:if>
										<form:errors path="checkOut" class="invalid-feedback" />
									</div>
								</div>
							</div>
							<div class="form-section">
								<h4>
									<i class="bi bi-gear"></i> Detalles de Reserva
								</h4>
								<div class="form-grid">
									<div class="form-group">
										<label for="numPersonas" class="form-label">Número de
											personas <span class="required">*</span>
										</label>
										<form:input path="numPersonas" id="numPersonas" type="number"
											placeholder="1" class="form-control" min="1"
											required="required" />
										<form:errors path="numPersonas" class="invalid-feedback" />
									</div>
									<div class="form-group">
										<label for="estadoReserva" class="form-label">ESTADO
											RESERVA <span class="required">*</span>
										</label>
										<form:select path="estadoReserva" id="estadoReserva"
											class="form-select" required="required">
											<form:option value="PENDIENTE">Pendiente</form:option>
											<form:option value="CONFIRMADA">Confirmada</form:option>
											<form:option value="CANCELADA">Cancelada</form:option>
											<form:option value="FINALIZADA">Finalizada</form:option>
										</form:select>
										<form:errors path="estadoReserva" class="invalid-feedback" />
									</div>
								</div>
							</div>

							<div class="form-section">
								<h4>
									<i class="bi bi-person-lines-fill"></i> Huésped y Habitación
									Asignados
								</h4>
								<div class="form-grid">
									<div class="form-group">
										<label for="huesped" class="form-label">HUÉSPED <span
											class="required">*</span></label>
										<form:select path="idHuesped" id="huesped" class="form-select"
											required="required">
											<form:option value="">-- Selecciona un huésped --</form:option>
											<c:forEach var="h" items="${huespedes}">
												<form:option value="${h.id}">${h.nombreHuesped} ${h.apellidosHuesped} - ${h.dniPasaporte}</form:option>
											</c:forEach>
										</form:select>
										<form:errors path="idHuesped" class="invalid-feedback" />
									</div>
									<div class="form-group">
										<label for="habitacion" class="form-label">HABITACIÓN
											<span class="required">*</span>
										</label>
										<form:select path="idHabitacion" id="habitacion"
											class="form-select" required="required">
											<form:option value="">-- Selecciona una habitación --</form:option>
											<c:forEach var="hab" items="${habitaciones}">
												<form:option value="${hab.id}">Hab. ${hab.numeroHabitacion} - ${hab.tipoHabitacion} (Cap. ${hab.capacidad})</form:option>
											</c:forEach>
										</form:select>
										<form:errors path="idHabitacion" class="invalid-feedback" />
									</div>
								</div>
							</div>

							<div class="action-buttons">
								<button
									onclick="location.href='${pageContext.request.contextPath}/reservas'"
									class="btn-action btn-cancelar">
									<i class="bi bi-x-lg"></i> Cancelar
								</button>
								<button type="submit" class="btn-action btn-guardar">
									<i class="bi bi-check-lg"></i>
									<c:choose>
										<c:when test="${reserva.id != null}">Guardar Cambios</c:when>
										<c:otherwise>Crear Reserva</c:otherwise>
									</c:choose>
								</button>
							</div>
						</form:form>
					</div>
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
