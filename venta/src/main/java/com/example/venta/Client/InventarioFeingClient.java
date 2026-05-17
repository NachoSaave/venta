package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.venta.Model.Dto.InventarioDTO;

@FeignClient(
        name="inventory",
        url="localhost:8083"
)
public interface InventarioFeingClient {
    @GetMapping("/api/v1/inventory/{idMoto}")
    InventarioDTO obtenerInventario(@PathVariable Long idMoto);
}
