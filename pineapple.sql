DROP DATABASE IF EXISTS Pineapple;
CREATE DATABASE Pineapple;
USE pineapple;
 
--CLIENTE--
CREATE TABLE Cliente(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DNI INT NOT NULL unique,
    Nombre VARCHAR(50),
    Apellido VARCHAR(50),
    Mail VARCHAR(50),
    Direccion VARCHAR(50),
    C_Postal INT,
    Telefono INT unique
);

--MEDIOS DE PAGO--
CREATE TABLE Medios_de_pago(
    ID_PAGO INT PRIMARY KEY,
    Tipo_pago VARCHAR (30)
);

--PRODUCTOS--
CREATE TABLE Productos(
    Numero_serie INT PRIMARY KEY,
    Tipo varchar (20),
    Modelo VARCHAR(50),
    Precio INT,
    Color VARCHAR(50),
    Almacenamiento VARCHAR(50),
    Fecha_Lanzamiento date,
    Procesador varchar (10)  
);

--STOCK--
CREATE TABLE Stock(
    COD_ST INT PRIMARY KEY,
    CANTIDAD_ST INT,
    Num_serie_prod INT,
    FOREIGN KEY (Num_serie_prod) REFERENCES Productos(Numero_serie)
);

--PRESUPUESTOS--
CREATE TABLE presupuestos(
    nro_cli_comp INT,
    COD_ST_comp INT,
    ID_pago_comp INT,
    monto INT,
    fecha date,
    cantidad INT,
    FOREIGN KEY (nro_cli_comp) REFERENCES Cliente(ID),
    FOREIGN KEY (COD_ST_comp) REFERENCES Stock(COD_ST),
    FOREIGN KEY (ID_pago_comp) REFERENCES Medios_de_pago(ID_PAGO)
);

--CLIENTE--
INSERT INTO Cliente (DNI, Nombre, Apellido, Mail, Direccion, C_Postal, Telefono) VALUES 
    (12345678, 'Juan', 'Perez', 'juan.perez@email.com', 'Calle Principal 123', 12345, 555555555),
    (23456789, 'Maria', 'Lopez', 'maria.lopez@email.com', 'Avenida Secundaria 456', 54321, 666666666),
    (34567890, 'Carlos', 'Gomez', 'carlos.gomez@email.com', 'Calle Principal 789', 67890, 777777777),
    (45678901, 'Laura', 'Martinez', 'laura.martinez@email.com', 'Avenida Secundaria 012', 9876, 888888888),
    (56789012, 'Pedro', 'Rodriguez', 'pedro.rodriguez@email.com', 'Calle Principal 234', 23456, 999999999),
    (123456789, 'Juan', 'Pérez', 'juan@example.com', 'Calle Principal 123', 12345, 987654321);

--MEDIOS DE PAGO--
INSERT INTO Medios_de_pago (ID_PAGO, Tipo_pago) VALUES
    (1, 'Tarjeta de crédito'),
    (2, 'Tarjeta de débito'),
    (3, 'Efectivo'),
    (4, 'Transferencia bancaria'),
    (5, 'Cheque'),
    (6, 'PayPal');

--PRODUCTOS--
INSERT INTO Productos (Numero_serie, Tipo, Modelo, Precio, Color, Almacenamiento, Fecha_Lanzamiento, Procesador) VALUES
    (8319131, 'Celular', 'PinePhone', 199, 'Negro', '64GB', '2022-05-15', 'ARM'),
    (8497232, 'Tablet', 'PineTouch', 249, 'Gris', '128GB', '2022-08-20', 'ARM'),
    (3432145, 'Computadora', 'PineMac', 899, 'Plateado', '256GB', '2022-11-30', 'Intel'),
    (4827138, 'Reloj Inteligente', 'PineTime', 79, 'Blanco', '32MB', '2023-02-10', NULL),
    (5929290, 'Auriculares', 'Pods', 149, 'Blanco', NULL, '2023-03-05', NULL),
    (2365123, 'Accesorio', 'Teclados', 49, 'Negro', NULL, '2023-04-20', NULL);

--STOCK--
INSERT INTO Stock (COD_ST, CANTIDAD_ST, Num_serie_prod) VALUES
    (1, 10, 8319131),
    (2, 8, 8497232),
    (3, 5, 3432145),
    (4, 3, 4827138),
    (5, 6, 5929290),
    (6, 9, 2365123);

--PRESUPUESTOS--
INSERT INTO presupuestos (nro_cli_comp, COD_ST_comp, ID_pago_comp, monto, fecha, cantidad) VALUES
    (1, 1, 1, 199, '2023-11-07', 1),
    (2, 2, 2, 249, '2023-11-07', 2),
    (3, 3, 3, 899, '2023-11-07', 3),
    (4, 4, 4, 79, '2023-11-08', 1),
    (5, 5, 5, 149, '2023-11-08', 2),
    (1, 6, 6, 49, '2023-11-08', 3);
