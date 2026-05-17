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

        if(moto == null){
            throw new RuntimeException("Moto no encontrada");
        }

        // CONSULTAR INVENTARIO
        InventarioDTO inventario =
                inventarioClient.obtenerInventario(dto.getIdMoto());

        if(inventario == null){
            throw new RuntimeException("Inventario no encontrado");
        }

        // VALIDAR STOCK
        if(inventario.getStock() <= 0){
            throw new RuntimeException("No hay stock disponible");
        }

        // CREAR VENTA EN ESTADO PENDIENTE
        venta nuevaVenta = new venta();

        nuevaVenta.setIdCliente(dto.getIdCliente());
        nuevaVenta.setIdMoto(dto.getIdMoto());
        nuevaVenta.setTotal(moto.getPrecio());
        nuevaVenta.setEstado("PENDIENTE");
        nuevaVenta.setFechaVenta(LocalDate.now());

        venta ventaGuardada = repository.save(nuevaVenta);

        // CREAR PAGO
        PagoDTO pago = new PagoDTO();
        pago.setMonto(moto.getPrecio());
        pago.setMetodoPago("TARJETA");
        pago.setSaleId(ventaGuardada.getId());

        PagoDTO respuestaPago = pagoClient.procesar(pago);

        if(!respuestaPago.getEstado().equals("APROBADO")){
            throw new RuntimeException("Pago rechazado");
        }

        // DESCONTAR STOCK
        inventario.setStock(inventario.getStock() - 1);

        inventarioClient.actualizarInventario(
                inventario.getIdMoto(),
                inventario
        );

        // ACTUALIZAR VENTA A PAGADA
        ventaGuardada.setEstado("PAGADO");

        return repository.save(ventaGuardada);
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