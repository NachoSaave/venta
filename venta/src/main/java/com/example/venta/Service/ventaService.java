package com.example.venta.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.venta.Model.venta;
import com.example.venta.Model.Dto.VentaSolicitudDTO;
import com.example.venta.Repository.ventaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ventaService {

    private final ventaRepository ventaRepository;

    public venta guardarVenta(VentaSolicitudDTO dto) {

        venta venta = new venta();

        venta.setIdCliente(dto.getIdCliente());
        venta.setIdMoto(dto.getIdMoto());
        venta.setTotal(dto.getTotal());
        venta.setEstado("PENDIENTE");
        venta.setFechaVenta(java.time.LocalDate.now());

        return ventaRepository.save(venta);
    }

    public List<venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}