package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.example.venta.Model.Dto.ClienteDTO;

@FeignClient(
    name = "cliente-service",
    url = "http://localhost:8081"
)
public interface ClienteFeignClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerCliente(@PathVariable Long id);
}