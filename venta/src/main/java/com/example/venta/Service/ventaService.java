package com.example.venta.Service;

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
    private ventaRepository repository;
    private MotoFeingClient motoClient;
    private InventarioFeingClient InventarioClient;
    private PagoFeingClient pagoClient;
public venta crearVenta(VentaSolicitudDTO dto){    


    MotoDto moto=motoClient.obtenerMoto(dto.getIdMoto());
    InventarioDTO inventario = InventarioClient.obtenerInventario(dto.getIdMoto());
    PagoDTO pago = new PagoDTO();
    pago.setMonto(moto.getPrecio());
    pagoClient.procesar(pago);
return null;
}

}

