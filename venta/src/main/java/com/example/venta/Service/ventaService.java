package com.example.venta.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.venta.Client.*;
import com.example.venta.Model.Dto.*;
import com.example.venta.Model.venta;
import com.example.venta.Repository.ventaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ventaService {

    private final ventaRepository repository;
    private final MotoFeingClient motoClient;
    private final InventarioFeingClient inventarioClient;
    private final PagoFeingClient pagoClient;
    private final ClienteFeignClient clienteClient;

    public venta crearVenta(VentaSolicitudDTO dto) {

        // CLIENTE
        ClienteDTO cliente;

        try {
            cliente = clienteClient.obtenerCliente(dto.getIdCliente());
        } catch (Exception e) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // MOTO
        MotoDto moto = motoClient.obtenerMoto(dto.getIdMoto());

        if (moto == null) {
            throw new RuntimeException("Moto no encontrada");
        }

        // INVENTARIO
        InventarioDTO inventario =
                inventarioClient.obtenerInventario(dto.getIdMoto());

        if (inventario == null) {
            throw new RuntimeException("Inventario no encontrado");
        }

        if (inventario.getStock() <= 0) {
            throw new RuntimeException("Sin stock disponible");
        }

        // CREAR VENTA
        venta nuevaVenta = new venta();

        nuevaVenta.setIdCliente(dto.getIdCliente());
        nuevaVenta.setIdMoto(dto.getIdMoto());
        nuevaVenta.setTotal(moto.getPrecio());
        nuevaVenta.setEstado("PENDIENTE");
        nuevaVenta.setFechaVenta(LocalDate.now());

        venta ventaGuardada = repository.save(nuevaVenta);

        // CREAR PAGO 
        PagoDTO pago = new PagoDTO();

        pago.setSaleId(ventaGuardada.getId());
        pago.setMonto(moto.getPrecio());
        pago.setMetodoPago("TARJETA");

        pago.setClienteId(cliente.getId());
        pago.setClienteNombre(cliente.getNombre());
        pago.setClienteTelefono(cliente.getTelefono());

        PagoDTO respuestaPago = pagoClient.procesar(pago);

        if (!respuestaPago.getEstado().equals("APROBADO")) {
            throw new RuntimeException("Pago rechazado");
        }

        // DESCONTAR STOCK
        inventario.setStock(inventario.getStock() - 1);

        inventarioClient.actualizarInventario(
                inventario.getIdMoto(),
                inventario
        );

        //  FINALIZAR VENTA
        ventaGuardada.setEstado("PAGADO");

        return repository.save(ventaGuardada);
    }

    public List<venta> listar() {
        return repository.findAll();
    }

    public venta buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}