package com.example.venta.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.Model.venta;
import com.example.venta.Model.Dto.VentaSolicitudDTO;
import com.example.venta.Service.ventaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class ventaController {

    private final ventaService service;

    @PostMapping
    public ResponseEntity<venta> crearVenta(
            @RequestBody VentaSolicitudDTO dto){

        return ResponseEntity.ok(
                service.crearVenta(dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<venta>> listar(){

        return ResponseEntity.ok(
                service.listar()
        );
    }
}
