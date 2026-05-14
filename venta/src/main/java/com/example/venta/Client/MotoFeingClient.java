package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.venta.Model.Dto.MotoDto;

@FeignClient(
        name="Moto",
        url="localhost:8082"
)
public interface MotoFeingClient {
    @GetMapping("/api/motos/{id}")
     MotoDto obtenerMoto(@PathVariable Long id);

}

