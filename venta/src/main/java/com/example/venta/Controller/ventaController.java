package com.example.venta.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.Model.venta;
import com.example.venta.Model.Dto.VentaSolicitudDTO;
import com.example.venta.Service.ventaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class ventaController {

    private final ventaService ventaService;

    @PostMapping
    public ResponseEntity<venta> guardar(
            @RequestBody VentaSolicitudDTO dto) {

        return ResponseEntity.ok(
                ventaService.guardarVenta(dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<venta>> listar() {

        return ResponseEntity.ok(
                ventaService.listarVentas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<venta> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(
                ventaService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        ventaService.eliminarVenta(id);

        return ResponseEntity.ok("Venta eliminada");
    }
}
