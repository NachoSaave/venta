package com.example.venta.Model.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class VentaSolicitudDTO {

    private Long idCliente;

    private Long idMoto;

    private Double total;
}
