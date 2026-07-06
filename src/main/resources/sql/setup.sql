-- ORDEN DE EJECUCIÓN
-- 1. setup  (CREATE DATABASE + USE)
-- 2. usuarios
-- 3. habitaciones
-- 4. huespedes
-- 5. reservas
-- 6. incidencias

-- 1. setup
CREATE DATABASE IF NOT EXISTS hoteldb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hoteldb;