package ar.com.ada.hoteltresvagos.entities.reportes;

import java.math.BigDecimal;

import javax.persistence.*;

import ar.com.ada.hoteltresvagos.entities.Huesped;
import ar.com.ada.hoteltresvagos.entities.Reserva;

@Entity
public class ReporteImportesHuesped extends Reporte {

    @Id
    @Column(name = "huesped_id")
    private int huespedId;
    private int cantidadReservas;
    private BigDecimal importeReserva;
    private BigDecimal importeTotal;
    private BigDecimal importePagado;
    private Huesped huesped;
    private Reserva reserva;

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

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
}