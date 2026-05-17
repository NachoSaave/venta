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
    public ResponseEntity<?> crearVenta(
            @RequestBody VentaSolicitudDTO dto){

        try{
            venta nuevaVenta = service.crearVenta(dto);
            return ResponseEntity.ok(nuevaVenta);

        } catch (RuntimeException e){

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // LISTAR VENTAS
    @GetMapping
    public ResponseEntity<List<venta>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long id){

        venta venta = service.buscarPorId(id);

        if(venta == null){
            return ResponseEntity
                    .badRequest()
                    .body("Venta no encontrada");
        }

        return ResponseEntity.ok(venta);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id){

        service.eliminar(id);

        return ResponseEntity.ok("Venta eliminada");
    }
}