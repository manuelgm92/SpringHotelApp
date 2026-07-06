-- 4. huéspedes
CREATE TABLE `huespedes` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `apellidos` VARCHAR(150) NOT NULL,
  `dni_pasaporte` VARCHAR(50) UNIQUE NOT NULL,
  `telefono` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) UNIQUE NOT NULL,
  `direccion` VARCHAR(255)
);

-- Datos de prueba: huéspedes (mínimo 8 de distintos tipos)
INSERT INTO `huespedes` (`nombre`, `apellidos`, `dni_pasaporte`, `telefono`, `email`, `direccion`) VALUES
('JUAN', 'PÉREZ GÓMEZ', '12345678A', '600111222', 'juan.perez@email.com', 'Calle Mayor 1, Madrid'),
('MARÍA', 'RODRÍGUEZ SAEZ', '23456789B', '600333444', 'm.rodriguez@email.com', 'Av. Libertad 15, Barcelona'),
('JOHN', 'DOE SMITH', 'Z98765432', '600555666', 'john.doe@test.com', 'Baker Street 221B, London'),
('LAURA', 'GIMÉNEZ CANO', '34567890C', '600777888', 'laura.g@email.com', 'Plaza España 5, Sevilla'),
('ROBERTO', 'FERNÁNDEZ LIMA', '45678901D', '600999000', 'roberto.f@email.com', 'Calle Real 10, Valencia'),
('CARMEN', 'ORTIZ RUIZ', '56789012E', '611222333', 'carmen.ortiz@email.com', 'Paseo del Prado 2, Madrid'),
('DAVID', 'VÁZQUEZ MURILLO', '67890123F', '622333444', 'david.v@email.com', 'Calle Luna 8, Bilbao'),
('PATRICIA', 'SANZ OCAÑA', '78901234G', '633444555', 'psanz@email.com', 'Av. Constitución 40, Granada'),
('ALBERTO', 'MÉNDEZ POZO', '89012345H', '644555666', 'alberto.m@email.com', 'Calle Sierpes 12, Sevilla'),
('ISABEL', 'LUNA CASTRO', '90123456I', '655666777', 'isabel.luna@email.com', 'Gran Vía 50, Madrid');