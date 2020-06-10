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
        List<ReporteImportesHuesped> reportes = ABMReporte.generarPorHuespedId(id);
        for (ReporteImportesHuesped reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorEstadoId() {

        int id = ingresarID();
        List<ReporteImportesEstado> reportes = ABMReporte.generarPorEstadoId(id);
        for (ReporteImportesEstado reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorHuespedes() {

        List<ReporteImportesHuesped> reportes = ABMReporte.generarPorHuespedes();
        for (ReporteImportesHuesped reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void listarPorEstados() {

        List<ReporteImportesEstado> reportes = ABMReporte.generarPorEstados();
        for (ReporteImportesEstado reporte : reportes) {
            mostrar(reporte);
        }
    }

    public void mostrar(ReporteImportesHuesped reporte) {

        System.out.println("Id " + reporte.getHuespedId() + " Nombre " + reporte.getNombre() + " Cantidad de Reservas " + reporte.getCantidadReservas() +  " Importe de Reserva " + reporte.getImporteReserva() + " Importe Total " + reporte.getImporteTotal() + " Importe Pagado " + reporte.getImportePagado());

    }

    public void mostrar(ReporteImportesEstado reporte) {

        System.out.println("Id " + reporte.getEstadoId() + " Descripcion " + reporte.getDescripcion() + " Cantidad de Reservas " + reporte.getCantidadReservas() +  " Importe de Reserva " + reporte.getImporteReserva() + " Importe Total " + reporte.getImporteTotal() + " Importe Pagado " + reporte.getImportePagado());

    }

}