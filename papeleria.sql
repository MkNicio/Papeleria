CREATE SCHEMA ciber_action AUTHORIZATION postgres;
SET search_path TO ciber_action;

-- Crear la tabla Proveedor
CREATE TABLE Proveedor (
    Id_prov SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Telefono VARCHAR(15),
    Correo_e VARCHAR(100),
    Direccion TEXT,
    RFC VARCHAR(13) NOT NULL
);

-- Crear la tabla Producto
CREATE TABLE Producto (
    Id_prod SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    Categoria VARCHAR(50),
    Precio DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla Empleado
CREATE TABLE Empleado (
    Id_em SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Telefono VARCHAR(15),
    Correo_e VARCHAR(100),
    Fecha_contratacion DATE NOT NULL
);

-- Crear la tabla Pedido
CREATE TABLE Pedido (
    Id_pedido SERIAL PRIMARY KEY,
    Id_prov INT NOT NULL,
    Estado VARCHAR(50),
    Fecha_P DATE NOT NULL,
    FOREIGN KEY (Id_prov) REFERENCES Proveedor(Id_prov) ON DELETE CASCADE
);

-- Crear la tabla Detalle_Pedido
CREATE TABLE Detalle_Pedido (
    Id_pedido INT NOT NULL,
    Id_prod INT NOT NULL,
    Cantidad INT NOT NULL,
    Monto_total DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (Id_pedido, Id_prod),
    FOREIGN KEY (Id_pedido) REFERENCES Pedido(Id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (Id_prod) REFERENCES Producto(Id_prod) ON DELETE CASCADE
);

-- Crear la tabla Venta
CREATE TABLE Venta (
    Folio_v SERIAL PRIMARY KEY,
    Fecha DATE NOT NULL,
    Id_em INT NOT NULL,
    FOREIGN KEY (Id_em) REFERENCES Empleado(Id_em) ON DELETE CASCADE
);

-- Crear la tabla Detalle_Venta
CREATE TABLE Detalle_Venta (
    Folio_v INT NOT NULL,
    Id_prod INT NOT NULL,
    Cantidad INT NOT NULL,
    Monto_total DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (Folio_v, Id_prod),
    FOREIGN KEY (Folio_v) REFERENCES Venta(Folio_v) ON DELETE CASCADE,
    FOREIGN KEY (Id_prod) REFERENCES Producto(Id_prod) ON DELETE CASCADE
);

-- Crear la tabla Inventario
CREATE TABLE Inventario (
    Id_prod INT PRIMARY KEY,
    Cantidad_inv INT NOT NULL,
    Stock_minimo INT NOT NULL,
    FOREIGN KEY (Id_prod) REFERENCES Producto(Id_prod) ON DELETE CASCADE
);

-- Crear la tabla Usuario
CREATE TABLE Usuario (
    Id_usuario SERIAL PRIMARY KEY,
    Id_em INT NOT NULL,  -- Clave foránea que referencia a la tabla Empleado
    Nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    Contraseña VARCHAR(255) NOT NULL,
    Rol VARCHAR(20) NOT NULL CHECK (Rol IN ('Gerente', 'Empleado')),
    FOREIGN KEY (Id_em) REFERENCES Empleado(Id_em) ON DELETE CASCADE  -- Relación con la tabla Empleado
);

-- Crear la tabla Tareas
CREATE TABLE Tareas (
    Id_tarea SERIAL PRIMARY KEY,
    Id_pedido INT NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    FOREIGN KEY (Id_pedido) REFERENCES Pedido(Id_pedido) ON DELETE CASCADE
);



