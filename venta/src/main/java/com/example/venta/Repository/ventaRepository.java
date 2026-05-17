package com.example.venta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venta.Model.venta;
@Repository
public interface ventaRepository extends JpaRepository<venta,Long>{
}
