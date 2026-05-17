package com.example.venta.Service;

import java.time.LocalDate;
import java.util.List;

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

    // CREAR VENTA
    public venta crearVenta(VentaSolicitudDTO dto){

        // CONSULTAR MOTO
        MotoDto moto = motoClient.obtenerMoto(dto.getIdMoto());

        // VALIDAR MOTO
        if(moto == null){
            throw new RuntimeException("Moto no encontrada");
        }

        // CONSULTAR INVENTARIO
        InventarioDTO inventario =
                inventarioClient.obtenerInventario(dto.getIdMoto());

        // VALIDAR INVENTARIO
        if(inventario == null){
            throw new RuntimeException("Inventario no encontrado");
        }

        // VALIDAR STOCK
        if(inventario.getStock() <= 0){
            throw new RuntimeException("No hay stock disponible");
        }

        // CREAR PAGO
        PagoDTO pago = new PagoDTO();
        pago.setMonto(moto.getPrecio());

        // ENVIAR A PAYMENT-SERVICE
        pagoClient.procesar(pago);

        // DESCONTAR STOCK
        inventario.setStock(inventario.getStock() - 1);

        // ACTUALIZAR INVENTARIO
        inventarioClient.actualizarInventario(
                inventario.getId(),
                inventario
        );

        // CREAR VENTA
        venta nuevaVenta = new venta();

        nuevaVenta.setIdCliente(dto.getIdCliente());
        nuevaVenta.setIdMoto(dto.getIdMoto());
        nuevaVenta.setTotal(moto.getPrecio());
        nuevaVenta.setEstado("PAGADO");
        nuevaVenta.setFechaVenta(LocalDate.now());

        // GUARDAR VENTA
        return repository.save(nuevaVenta);
    }

    // LISTAR
    public List<venta> listar(){
        return repository.findAll();
    }

    // BUSCAR POR ID
    public venta buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    // ELIMINAR
    public void eliminar(Long id){
        repository.deleteById(id);
    }
}