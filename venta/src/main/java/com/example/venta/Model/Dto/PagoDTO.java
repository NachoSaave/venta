package com.example.venta.Model.Dto;

import lombok.Data;
@Data
public class PagoDTO {
    private Long id;
    private Long saleId;
    private Double monto;
    private String metodoPago;
    private String estado;

    private ClienteDTO cliente;
    private Long clienteId;
    private String clienteNombre;
    private String clienteTelefono;
}


