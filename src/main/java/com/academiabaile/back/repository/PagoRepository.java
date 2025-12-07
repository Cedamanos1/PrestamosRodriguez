package com.academiabaile.back.repository;

import com.academiabaile.back.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
