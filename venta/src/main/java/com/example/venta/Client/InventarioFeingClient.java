package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.example.venta.Model.Dto.InventarioDTO;

@FeignClient(
        name = "inventory",
        url = "http://localhost:8083"
)
public interface InventarioFeingClient {

    @GetMapping("/api/v1/inventory/{idMoto}")
    InventarioDTO obtenerInventario(@PathVariable Long idMoto);

    @PutMapping("/api/v1/inventory/{id}")
    InventarioDTO actualizarInventario(
            @PathVariable Long id,
            @RequestBody InventarioDTO inventario
    );
}