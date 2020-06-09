package ar.com.ada.hoteltresvagos;

import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;
import ar.com.ada.hoteltresvagos.services.HuespedService;
import ar.com.ada.hoteltresvagos.services.ReporteService;
import ar.com.ada.hoteltresvagos.services.ReservaService;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();
    protected ReporteManager ABMReporte = new ReporteManager();

    protected HuespedService huespedService = new HuespedService();
    protected ReservaService reservaService = new ReservaService();
    protected ReporteService reporteService = new ReporteService();

    public void iniciar() throws Exception {

        try {

            printMenu();

            int opcion = seleccionarOpcion();

            while (opcion > 0) {

                switch (opcion) {

                    case 1:
                        gestionarHuespedes();
                        break;

                    case 2:
                        gestionarReservas();
                        break;

                    case 3:
                        gestionarReportes();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;

                }

                printMenu();

                opcion = seleccionarOpcion();

            }

        } catch (Exception e) {
            System.out.println("Error.");
            throw e;
        } finally {
            System.out.println("Cerrando el sistema.");

        }

    }

    public static void printMenu() {

        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Huespedes.");
        System.out.println("2. Reservas.");
        System.out.println("3. Reportes.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");

    }

    public static void printPanelHuespedes() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Registrar.");
        System.out.println("2. Eliminar.");
        System.out.println("3. Modificar.");
        System.out.println("4. Listar.");
        System.out.println("5. Listar por DNI.");
        System.out.println("6. Listar por nombre.");
        System.out.println("0. Terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printPanelReservas() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Registrar.");
        System.out.println("2. Eliminar.");
        System.out.println("3. Modificar.");
        System.out.println("4. Listar.");
        System.out.println("5. Listar por DNI.");
        System.out.println("6. Listar por nombre.");
        System.out.println("0. Terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printPanelReportes() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Importes por Huespedes.");
        System.out.println("2. Importes por Huesped Id.");
        System.out.println("3. Importes por Estados.");
        System.out.println("4. Importes por Estado Id.");
        System.out.println("0. Terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public int seleccionarOpcion() {

        int opcion = Teclado.nextInt();
        Teclado.nextLine();

        return opcion;
    }

    public void gestionarHuespedes() throws Exception {

        try {

            ABMHuesped.setup();

            printPanelHuespedes();

            int opcion = seleccionarOpcion();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            huespedService.alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI o el DNI ya existe.");
                        }
                        break;

                    case 2:
                        huespedService.baja();
                        break;

                    case 3:
                        huespedService.modifica();
                        break;

                    case 4:
                        huespedService.listar();
                        break;

                    case 5:
                        huespedService.listarPorDNI();
                        break;

                    case 6:
                        huespedService.listarPorNombre();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printPanelHuespedes();

                opcion = seleccionarOpcion();
            }

            ABMHuesped.exit();

        } catch (Exception e) {

            System.out.println("Error.");
            throw e;

        } finally {
            System.out.println("Saliendo de Huespedes.");

        }

    }

    public void gestionarReservas() throws Exception {

        try {

            ABMReserva.setup();

            printPanelReservas();

            int opcion = seleccionarOpcion();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            reservaService.altaReserva();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI o el DNI ya existe.");
                        }
                        break;

                    case 2:
                        reservaService.bajaReserva();
                        break;

                    case 3:
                        reservaService.modificaReserva();
                        break;

                    case 4:
                        reservaService.listarReservas();
                        break;

                    case 5:
                        reservaService.listarReservasPorDNI();
                        break;

                    case 6:
                        reservaService.listarReservasPorNombre();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printPanelReservas();

                opcion = seleccionarOpcion();
            }

            ABMReserva.exit();

        } catch (Exception e) {

            System.out.println("Error.");
            throw e;
        } finally {
            System.out.println("Saliendo de Reservas.");

        }

    }

    public void gestionarReportes() throws Exception {

        try {

            ABMReporte.setup();

            printPanelReportes();

            int opcion = seleccionarOpcion();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:
                        reporteService.listarPorHuespedes();
                        ;
                        break;

                    case 2:
                        reporteService.listarPorHuespedId();
                        break;

                    case 3:
                        reporteService.listarPorEstados();
                        break;

                    case 4:
                        reporteService.listarPorEstadoId();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printPanelReportes();

                opcion = seleccionarOpcion();
            }

            ABMReporte.exit();

        } catch (Exception e) {

            System.out.println("Error.");
            throw e;

        } finally {
            System.out.println("Saliendo de Reportes.");

        }

    }

}