package com.example.venta.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.venta.Client.InventarioFeingClient;
import com.example.venta.Client.MotoFeingClient;
import com.example.venta.Client.PagoFeingClient;
import com.example.venta.Model.venta;
import com.example.venta.Model.Dto.InventarioDTO;
import com.example.venta.Model.Dto.MotoDto;
import com.example.venta.Model.Dto.PagoDTO;
import com.example.venta.Model.Dto.VentaSolicitudDTO;
import com.example.venta.Repository.ventaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ventaService {
    private final ventaRepository repository;
    private final MotoFeingClient motoClient;
    private final InventarioFeingClient inventarioClient;
    private final PagoFeingClient pagoClient;
public venta crearVenta(VentaSolicitudDTO dto){    


    MotoDto moto=motoClient.obtenerMoto(dto.getIdMoto());
    InventarioDTO inventario = inventarioClient.obtenerInventario(dto.getIdMoto());
      if(inventario.getStock() <= 0){

            throw new RuntimeException(
                    "No hay stock"
            );
        }
    PagoDTO pago = new PagoDTO();
    pago.setMonto(moto.getPrecio());
    pagoClient.procesar(pago);
      venta venta = new venta();
      
        venta.setIdCliente(dto.getIdCliente()
        );

        venta.setIdMoto(dto.getIdMoto()
        );

        venta.setTotal(moto.getPrecio()
        );

        venta.setEstado("PAGADO"
        );

        venta.setFechaVenta(LocalDate.now()
        );

return repository.save(venta);
}

}

