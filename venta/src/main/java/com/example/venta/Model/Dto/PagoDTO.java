package com.example.venta.Model.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoDTO {
    private Long id;
    private Long saleId;
    private Double monto;
    private String metodoPago;
    private String estado;
}

