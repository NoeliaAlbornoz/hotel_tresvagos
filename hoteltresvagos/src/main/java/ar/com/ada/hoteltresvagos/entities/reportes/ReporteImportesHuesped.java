package ar.com.ada.hoteltresvagos.entities.reportes;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class ReporteImportesHuesped extends Reporte {

    @Id
    @Column(name = "huesped_id")
    private int huespedId;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad_reservas")
    private int cantidadReservas;
    @Column(name = "importe_reserva")
    private BigDecimal importeReserva;
    @Column(name = "importe_total")
    private BigDecimal importeTotal;
    @Column(name = "importe_pagado")
    private BigDecimal importePagado;

    public int getHuespedId() {
        return huespedId;
    }

    public void setHuespedId(int huespedId) {
        this.huespedId = huespedId;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }

    public BigDecimal getImporteReserva() {
        return importeReserva;
    }

    public void setImporteReserva(BigDecimal importeReserva) {
        this.importeReserva = importeReserva;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(BigDecimal importePagado) {
        this.importePagado = importePagado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}