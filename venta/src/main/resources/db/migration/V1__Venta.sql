CREATE TABLE ventas (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    id_cliente BIGINT NOT NULL,

    id_moto BIGINT NOT NULL,

    total DECIMAL(10,2) NOT NULL,

    estado VARCHAR(50),

    fecha_venta DATE DEFAULT (CURRENT_DATE)
);