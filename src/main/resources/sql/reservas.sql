-- 5. reservas
CREATE TABLE `reservas` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `check_in` DATE NOT NULL,
  `check_out` DATE NOT NULL,
  `num_personas` TINYINT UNSIGNED NOT NULL,
  `estado_reserva` ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA', 'FINALIZADA') NOT NULL DEFAULT 'PENDIENTE',
  `fecha_creacion_reserva` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `id_huesped` INTEGER NOT NULL,
  `id_habitacion` INTEGER NOT NULL,
  CONSTRAINT `fk_reserva_id_habitacion` FOREIGN KEY (`id_habitacion`) REFERENCES `habitaciones` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_reserva_id_huesped` FOREIGN KEY (`id_huesped`) REFERENCES `huespedes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `checusuariosk_fechas` CHECK (`check_out` > `check_in`),
  CONSTRAINT `check_num_personas` CHECK (`num_personas` > 0)
);

CREATE INDEX `idx_reserva__fechas` ON `reservas` (`check_in`, `check_out`);
CREATE INDEX `idx_reserva__id_habitacion` ON `reservas` (`id_habitacion`);
CREATE INDEX `idx_reserva__id_huesped` ON `reservas` (`id_huesped`);
-- Datos de prueba: reservas
INSERT INTO reservas (check_in, check_out, num_personas, estado_reserva, id_huesped, id_habitacion)
VALUES
('2025-06-10', '2025-06-12', 1, 'PENDIENTE', 1, 1),   
('2025-06-15', '2025-06-20', 2, 'CONFIRMADA', 2, 2), 
('2025-07-01', '2025-07-05', 2, 'PENDIENTE', 3, 2),  
('2025-05-01', '2025-05-03', 1, 'FINALIZADA', 4, 1), 
('2025-08-10', '2025-08-15', 3, 'CONFIRMADA', 5, 4),
('2025-09-01', '2025-09-10', 4, 'PENDIENTE', 6, 5),  
('2025-04-20', '2025-04-22', 1, 'CANCELADA', 1, 3),  
('2025-10-05', '2025-10-08', 2, 'CONFIRMADA', 2, 4), 
('2025-11-12', '2025-11-14', 1, 'PENDIENTE', 3, 6),  
('2025-12-20', '2025-12-27', 2, 'PENDIENTE', 4, 4);