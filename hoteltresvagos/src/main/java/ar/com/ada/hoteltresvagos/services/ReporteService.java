package ar.com.ada.hoteltresvagos.services;

import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.managers.*;

public class ReporteService {

    public static Scanner Teclado = new Scanner(System.in);
    protected ReporteManager ABMReporte;

    public ReporteService(ReporteManager ABMReporte) {

        this.ABMReporte = ABMReporte;
        
	}

    public int ingresarID() {

        System.out.println("Ingrese el ID: ");

        int id = Teclado.nextInt();
        Teclado.nextLine();

        return id;

    }

    public void listarPorHuespedId() {

        int id = ingresarID();
        List<ReporteHuesped> reportes = ABMReporte.generarPorHuespedId(id);
        for (ReporteHuesped reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorEstadoId() {

        int id = ingresarID();
        List<ReporteEstado> reportes = ABMReporte.generarPorEstadoId(id);
        for (ReporteEstado reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorHuespedes() {

        List<ReporteHuesped> reportes = ABMReporte.generarPorHuespedes();
        for (ReporteHuesped reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorEstados() {

        List<ReporteEstado> reportes = ABMReporte.generarPorEstados();
        for (ReporteEstado reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void mostrar(ReporteHuesped reporte) {

        System.out.println("Id " + reporte.getHuespedId() + " Nombre " + reporte.getNombre() + " Cantidad de Reservas " + reporte.getCantidadReservas() +  " Importe de Reserva " + reporte.getImporteReserva() + " Importe Total " + reporte.getImporteTotal() + " Importe Pagado " + reporte.getImportePagado());

    }

    public void mostrar(ReporteEstado reporte) {

        System.out.println("Id " + reporte.getEstadoId() + " Descripcion " + reporte.getDescripcion() + " Cantidad de Reservas " + reporte.getCantidadReservas() +  " Importe de Reserva " + reporte.getImporteReserva() + " Importe Total " + reporte.getImporteTotal() + " Importe Pagado " + reporte.getImportePagado());

    }

}