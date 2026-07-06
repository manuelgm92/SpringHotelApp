<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- IA: Vista JSP generada con ayuda de IA(Gemini) para estructura HTML y integración Bootstrap. -->

<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido - Sistema Hotelero</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" integrity="sha384-QuGBSgV5Im3DzL2z+8Ko9/hqNy/N0O7zwvXAtfd1MvPKWa/UbeLV65cfm4BV5Wgq" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/views.css">
</head>
<body class="home-bg">

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="welcome-card">
                    <i class="bi bi-hospital fs-1 mb-3"></i>
                    <h1 class="display-4 fw-bold mb-3">Spring Hotel</h1>
                    <p class="lead mb-5">Bienvenido al sistema de gestión integral de reservas y habitaciones.</p>
                    
                    <button onclick="location.href='${pageContext.request.contextPath}/login'" class="btn btn-light btn-lg px-5 py-3 fw-bold shadow-sm">
                        <i class="bi bi-box-arrow-in-right me-2"></i> Acceder al Sistema
                    </button>
                    
                    <div class="mt-4">
                        <small class="text-white-50">v1.0.0 - 2026</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>