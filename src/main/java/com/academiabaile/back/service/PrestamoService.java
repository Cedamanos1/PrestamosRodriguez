package com.academiabaile.back.service;

import com.academiabaile.back.entidades.Prestamo;
import com.academiabaile.back.entidades.CronogramaPagos;
import com.academiabaile.back.repository.PrestamoRepository;
import com.academiabaile.back.repository.CronogramaPagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private CronogramaPagosRepository cronogramaPagosRepository;

    public Prestamo guardarPrestamo(Prestamo prestamo) {
        Prestamo nuevoPrestamo = prestamoRepository.save(prestamo);
        generarCronogramaPagos(nuevoPrestamo);
        return nuevoPrestamo;
    }

    private void generarCronogramaPagos(Prestamo prestamo) {
        double montoCuota = (prestamo.getMonto() * (1 + prestamo.getInteres())) / prestamo.getPlazo();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prestamo.getFecha());

        for (int i = 0; i < prestamo.getPlazo(); i++) {
            calendar.add(Calendar.MONTH, 1);
            CronogramaPagos cronograma = new CronogramaPagos();
            cronograma.setPrestamo(prestamo);
            cronograma.setFechaVencimiento(calendar.getTime());
            cronograma.setMontoCuota(montoCuota);
            cronograma.setPagado(false);
            cronogramaPagosRepository.save(cronograma);
        }
    }

    public List<Prestamo> obtenerPrestamos() {
        return prestamoRepository.findAll();
    }
}
