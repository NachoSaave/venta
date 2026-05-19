package com.example.venta.Model.Dto;

import lombok.Data;
@Data
public class MotoDto {
    private Long id;
    private String marca;
    private String modelo;
    private Double precio;
    private int ano;
    private String cc;
}
