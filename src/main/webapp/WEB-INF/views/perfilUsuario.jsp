<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>

<body class="fondo">

<div class="container mt-5">

    <div class="card shadow-lg">
        <div class="card-body p-4">

            <h3 class="mb-4">Mi Perfil</h3>

            <!-- MENSAJES -->
            <c:if test="${param.error == 1}">
                <div class="alert alert-danger">
                    Las contraseñas no coinciden
                </div>
            </c:if>

            <c:if test="${param.success == 1}">
                <div class="alert alert-success">
                    Contraseña actualizada correctamente
                </div>
            </c:if>

            <!-- DATOS DEL USUARIO -->
            <div class="mb-3">
                <span class="form-label">Usuario</span>
                <input type="text" class="form-control" value="${usuario.nombreUsuario}" readonly>
            </div>

            <div class="mb-3">
                <span class="form-label">Nombre completo</span>
                <input type="text" class="form-control" value="${usuario.nombreCompleto}" readonly>
            </div>

            <div class="mb-3">
                <span class="form-label">Perfil</span>
                <input type="text" class="form-control" value="${usuario.perfil}" readonly>
            </div>

            <hr>

            <!-- CAMBIO DE CONTRASEÑA -->
            <h5 class="mb-3">Cambiar contraseña</h5>

            <form action="${pageContext.request.contextPath}/usuarios/actualizarPassword" method="post">

                <div class="mb-3">
                    <span class="form-label">Nueva contraseña</span>
                    <input type="password" name="passwordNueva" class="form-control" required>
                </div>

                <div class="mb-3">
                    <span class="form-label">Confirmar contraseña</span>
                    <input type="password" name="passwordConfirmar" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success">
                    Actualizar contraseña
                </button>

                <button onclick="location.href='${pageContext.request.contextPath}/principal'" class="btn btn-secondary">
                    Volver
                </button>

            </form>

        </div>
    </div>

</div>

</body>
</html>