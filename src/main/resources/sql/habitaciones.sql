-- 3. habitaciones
CREATE TABLE `habitaciones` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `numero_habitacion` VARCHAR(10) UNIQUE NOT NULL,
  `tipo_habitacion` ENUM('INDIVIDUAL', 'DOBLE', 'SUITE') NOT NULL,
  `precio_noche` DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
  `capacidad` TINYINT UNSIGNED NOT NULL,
  `estado_habitacion` ENUM('LIBRE', 'OCUPADA', 'MANTENIMIENTO', "LIMPIEZA") NOT NULL DEFAULT 'LIBRE',
  `adaptada_discapacidad` BOOLEAN NOT NULL DEFAULT 0,
  CONSTRAINT `check_precio` CHECK (`precio_noche` >= 0),
  CONSTRAINT `check_capacidad` CHECK (`capacidad` > 0)
);
-- Datos de prueba: habitaciones (mínimo 8 de distintos tipos)
INSERT INTO `habitaciones` VALUES
(1,  '101', 'INDIVIDUAL', 59.00,  1, 'LIBRE',        0),
(2,  '102', 'INDIVIDUAL', 59.00,  1, 'OCUPADA',       0),
(3,  '103', 'INDIVIDUAL', 65.00,  1, 'LIBRE',        1),
(4,  '201', 'DOBLE',      95.00,  2, 'LIBRE',        0),
(5,  '202', 'DOBLE',      95.00,  2, 'LIMPIEZA',     0),
(6,  '203', 'DOBLE',     105.00,  2, 'OCUPADA',      1),
(7,  '204', 'DOBLE',      105.00,  2, 'LIBRE',        0),
(8,  '301', 'SUITE',     180.00,  3, 'LIBRE',        0),
(9,  '302', 'SUITE',     210.00,  4, 'MANTENIMIENTO', 0),
(10,  '303', 'SUITE', 210.00,  4, 'LIBRE',        1);