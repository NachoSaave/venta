package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.venta.Model.Dto.PagoDTO;

@FeignClient(
        name="Pago",
        url="localhost:8085"
)
public interface PagoFeingClient {
    @GetMapping("/api/pagos")
    PagoDTO procesar(@PathVariable PagoDTO pago);
}
