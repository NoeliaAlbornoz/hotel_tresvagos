package ar.com.ada.hoteltresvagos.entities;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class ReporteEstado extends Reporte {

    @Id
    @Column(name = "estado_id")
    private int estadoId;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidad_reservas")
    private int cantidadReservas;
    @Column(name = "total_importe_reserva")
    private BigDecimal importeReserva;
    @Column(name = "total_importe_pagado")
    private BigDecimal importePagado;
    @Column(name = "total_importe")
    private BigDecimal importeTotal;

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}