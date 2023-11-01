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


--TIPOS--
CREATE TABLE Tipo_producto(
    Codigo INT(50) PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(50)
);
 
--PRODUCTOS--
CREATE TABLE Productos(
    Numero_serie INT PRIMARY KEY,
    Modelo VARCHAR(50),
    Precio INT,
    Color VARCHAR(50),
    Almacenamiento VARCHAR(50),
    Fecha_Lanzamiento date,
    Codigo_tipo_producto INT(50),
    FOREIGN KEY (Codigo_tipo_producto) REFERENCES Tipo_producto(Codigo)
);


--COMPRAS--
CREATE TABLE compras(
    nro_cli_comp INT,
    num_ser_comp INT,
    cost_comp INT,
    cant_comp INT,
    fec_comp date,
    FOREIGN KEY (nro_cli_comp) REFERENCES Cliente(ID),
    FOREIGN KEY (num_ser_comp) REFERENCES Productos(Numero_serie)
);
