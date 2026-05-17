package com.example.venta.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.venta.Model.Dto.PagoDTO;

@FeignClient(
        name="Pago",
        url="http://localhost:8085/"
)
public interface PagoFeingClient {

    @PostMapping("/api/v1/pagos")
    PagoDTO procesar(
            @RequestBody PagoDTO pago
    );
}
