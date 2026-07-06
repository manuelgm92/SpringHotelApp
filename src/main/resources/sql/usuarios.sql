-- 2. usuarios
CREATE TABLE `usuarios` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `nombre_usuario` VARCHAR(50) UNIQUE NOT NULL,
  `password_usuario` VARCHAR(255) NOT NULL,
  `perfil` ENUM('RECEPCIONISTA', 'SUPERVISOR') NOT NULL,
  `nombre_completo` VARCHAR(100) NOT NULL
);

-- Datos de prueba: usuarios 
INSERT INTO `usuarios` (`nombre_usuario`, `password_usuario`, `perfil`, `nombre_completo`) VALUES
('admin_israel', '$2a$12$LPAQQIuQdON/gQcX3kMS2ulHjFpB1IoUZKRgacPk05WIuWUDE6pRK', 'SUPERVISOR', 'Israel Israel Israel'),
('recep_ana', '$2a$12$ig9vwgDBmJrKr.ERJATSReUs72Eg07tXSRKcNwD/YYGlipWBdiBAW', 'RECEPCIONISTA', 'Ana Laura Izquierdo Becerra'),
('recep_manuel', '$2a$12$O03f5BLtbBJMavnGpg.1oODpnLjwXmOirSl2d7sXQsEhOSyvKVViy', 'RECEPCIONISTA', 'Manuel González Martínez'),
('recepcionista', '$2a$12$Tnx7JLlJGHPOamzmoi720OqwKGm/LpPMOqCujV6NmsgJVySMuTnse', 'RECEPCIONISTA', 'Credencial de Prueba' ),
('supervisor', '$2a$12$AQKrZAbIPcDWDwLFDgSgUe5ugSbIrjf0njJikKHShUu6eIZZczxQa', 'SUPERVISOR', 'Credencial de Prueba');