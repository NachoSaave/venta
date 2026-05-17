package com.example.venta.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.venta.Model.venta;
import com.example.venta.Model.Dto.VentaSolicitudDTO;
import com.example.venta.Service.ventaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class ventaController {

    private final ventaService service;

    // CREAR VENTA
    @PostMapping
    public ResponseEntity<venta> crearVenta(@RequestBody VentaSolicitudDTO dto){
        return ResponseEntity.ok(service.crearVenta(dto));
    }

    // LISTAR VENTAS
    @GetMapping
    public ResponseEntity<List<venta>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}