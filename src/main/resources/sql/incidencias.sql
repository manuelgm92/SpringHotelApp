-- 6. incidencias

CREATE TABLE `incidencias` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `id_habitacion` INTEGER NOT NULL,
  `estado_incidencia` ENUM('ABIERTA', 'EN_CURSO', 'CERRADA') NOT NULL DEFAULT 'ABIERTA',
  `prioridad` ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL DEFAULT 'MEDIA',
  `descripcion` TEXT NOT NULL,
  `fecha_apertura` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_cierre` DATETIME,
  `tecnico_asignado` VARCHAR(100),
  `observaciones` TEXT,
  CONSTRAINT `fk_incidencia_habitacion` FOREIGN KEY (`id_habitacion`) 
    REFERENCES `habitaciones` (`id`) ON DELETE CASCADE
);

CREATE INDEX `idx_incidencia__id_habitacion` ON `incidencias` (`id_habitacion`);

-- Datos de prueba: incidencias (mínimo 5, distintos estados y prioridades)
INSERT INTO `incidencias`
    (id_habitacion, estado_incidencia, prioridad, descripcion,
     fecha_apertura, tecnico_asignado, observaciones)
VALUES
(9, 'ABIERTA',   'ALTA',  'Fuga de agua en el baño',
    '2025-04-20 09:00:00', NULL, NULL),
(5, 'EN_CURSO',  'MEDIA', 'Aire acondicionado no enfría correctamente',
    '2025-04-18 14:30:00', 'Pedro Ruiz', 'Pendiente de repuesto'),
(2, 'CERRADA',   'BAJA',  'Bombilla fundida en la mesilla derecha',
    '2025-04-15 11:00:00', 'Ana Torres', 'Reemplazada'),
(5, 'ABIERTA',   'ALTA',  'Cerradura de la puerta no funciona',
    '2025-04-21 08:00:00', NULL, NULL),
(1, 'EN_CURSO',  'MEDIA', 'Persiana atascada',
    '2025-04-19 16:00:00', 'Pedro Ruiz', 'Se revisará mañana');