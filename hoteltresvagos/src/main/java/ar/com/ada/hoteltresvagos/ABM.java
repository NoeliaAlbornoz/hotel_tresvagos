package ar.com.ada.hoteltresvagos;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.exception.ConstraintViolationException;

import ar.com.ada.hoteltresvagos.entities.*;
import ar.com.ada.hoteltresvagos.excepciones.*;
import ar.com.ada.hoteltresvagos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();

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
                            alta();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI o el DNI ya existe.");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorDNI();
                        break;

                    case 6:
                        listarPorNombre();
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
                            altaReserva();
                        } catch (HuespedDNIException exdni) {
                            System.out.println("Error en el DNI o el DNI ya existe.");
                        }
                        break;

                    case 2:
                        bajaReserva();
                        break;

                    case 3:
                        modificaReserva();
                        break;

                    case 4:
                        listarReservas();
                        break;

                    case 5:
                        listarReservasPorDNI();
                        break;

                    case 6:
                        listarReservasPorNombre();
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

    public void buscarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrar(huesped);
        }
    }

    public void alta() throws Exception {

        Huesped huesped = new Huesped();

        String nombre = this.ingresarNombre();
        huesped.setNombre(nombre);

        int dni = this.ingresarDNI();

        existe(dni, huesped);
        huesped.setDni(dni);

        String domicilio = this.ingresarDomicilio();
        huesped.setDomicilio(domicilio);

        String domAlternativo = this.ingresarDomAlternativo();
        this.confirmarDomAlternativo(domAlternativo, huesped);

        ABMHuesped.create(huesped);

        System.out.println("Huesped generado con exito.");

    }

    public void baja() {

        int id = ingresarID();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMHuesped.delete(huespedEncontrado);
                System.out.println("El registro del huesped ha sido eliminado.");

            } catch (Exception e) {
                System.out.println("Error al eliminar huesped.");
            }

        }
    }

    public void bajaPorDNI(Huesped huesped) throws Exception {

        int dni = this.ingresarDNI();
        Huesped huespedEncontrado = ABMHuesped.readByDNI(dni);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {
            ABMHuesped.delete(huespedEncontrado);
            System.out.println("El registro del huesped ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {

        int id = this.ingresarID();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado != null) {

            System.out.println("Editar " + huespedEncontrado.toString());

            System.out.println("Editar: \n1: nombre \n2: DNI \n3: domicilio \n4: domicilio alternativo");

            int seleccionarDato = Teclado.nextInt();

            switch (seleccionarDato) {
                case 1:
                    String nombre = this.ingresarNombre();
                    huespedEncontrado.setNombre(nombre);
                    break;
                case 2:

                    int dni = this.ingresarDNI();

                    existe(dni, huespedEncontrado);
                    huespedEncontrado.setDni(dni);

                    break;
                case 3:
                    String domicilio = this.ingresarDomicilio();
                    huespedEncontrado.setDomicilio(domicilio);
                    break;
                case 4:
                    String domAlternativo = this.ingresarDomAlternativo();
                    this.confirmarDomAlternativo(domAlternativo, huespedEncontrado);
                    break;

                default:
                    break;
            }

            ABMHuesped.update(huespedEncontrado);

            System.out.println("El registro del huesped ha sido modificado.");

        } else {
            System.out.println("Huesped no encontrado.");
        }

    }

    public void listar() {

        List<Huesped> todos = ABMHuesped.buscarTodos();
        for (Huesped huesped : todos) {
            mostrar(huesped);
        }
    }

    public void listarPorDNI() {
        System.out.println("Ingrese el dni del huesped:");
        int dni = Teclado.nextInt();

        List<Huesped> huespedes = ABMHuesped.buscarPor(dni);
        for (Huesped huesped : huespedes) {
            mostrar(huesped);
        }

    }

    public void listarPorNombre() {
        System.out.println("Ingrese el nombre del huesped:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrar(huesped);
        }

    }

    public String ingresarNombre() {

        System.out.println("Ingrese el nombre:");

        return Teclado.nextLine();

    }

    public int ingresarDNI() throws Exception {

        System.out.println("Ingrese el DNI:");

        int dni = Teclado.nextInt();
        Teclado.nextLine();

        return dni;

    }

    public void existe(int dni, Huesped huesped) throws HuespedDNIException {

        huesped = ABMHuesped.readByDNI(dni);

        if (huesped != null) {

            throw new HuespedDNIException(huesped, "DNI ya existe");

        }

    }

    public String ingresarDomicilio() {

        System.out.println("Ingrese domicilio:");

        return Teclado.nextLine();

    }

    public String ingresarDomAlternativo() {

        System.out.println("Ingrese domicilio alternativo (OPCIONAL):");

        return Teclado.nextLine();

    }

    public void confirmarDomAlternativo(String domAlternativo, Huesped huesped) {

        if (domAlternativo != null)
            huesped.setDomicilioAlternativo(domAlternativo);

    }

    public int ingresarID() {

        System.out.println("Ingrese el ID: ");

        int id = Teclado.nextInt();
        Teclado.nextLine();

        return id;

    }

    public void existe(Huesped huesped) {

        if (huesped == null) {

            System.out.println("No existe");
            return;

        }

    }

    public void altaReserva() throws Exception {

        int dni = ingresarDNI();
        Huesped huespedEncontrado = ABMHuesped.readByDNI(dni);
        existe(huespedEncontrado);

        Reserva reserva = new Reserva();

        reserva.setHuesped(huespedEncontrado);

        reserva.setFechaReserva(new Date());

        Date fechaIngreso = this.ingresarFechaDeIngreso();
        reserva.setFechaIngreso(fechaIngreso);

        Date fechaEgreso = this.ingresarFechaDeEgreso();
        reserva.setFechaEgreso(fechaEgreso);

        int habitacion = this.ingresarHabitacion();
        reserva.setHabitacion(habitacion);

        BigDecimal importeReserva = this.ingresarImporteReserva();
        reserva.setImporteReserva(importeReserva);

        BigDecimal importeTotal = this.ingresarImporteTotal();
        reserva.setImporteTotal(importeTotal);

        BigDecimal importePagado = this.ingresarImportePagado();
        reserva.setImportePagado(importePagado);

        int estadoPago = this.ingresarEstadoPago();
        reserva.setTipoEstadoId(estadoPago);

        ABMReserva.create(reserva);

        System.out.println("Reserva generada con exito.");

    }

    public int ingresarEstadoPago() {

        System.out.println("Ingrese el estado de pago: ");

        int estadoPago = Teclado.nextInt();
        Teclado.nextLine();

        return estadoPago;

    }

    public BigDecimal ingresarImporteReserva() {

        System.out.println("Introducir el importe de la reserva: ");

        BigDecimal importeReserva = Teclado.nextBigDecimal();
        Teclado.nextLine();

        return importeReserva;

    }

    public BigDecimal ingresarImporteTotal() {

        System.out.println("Introducir el importe total: ");

        BigDecimal importeTotal = Teclado.nextBigDecimal();
        Teclado.nextLine();

        return importeTotal;

    }

    public BigDecimal ingresarImportePagado() {

        System.out.println("Introducir el importe pagado: ");

        BigDecimal importePagado = Teclado.nextBigDecimal();
        Teclado.nextLine();

        return importePagado;

    }

    public int ingresarHabitacion() {

        System.out.println("Ingrese habitación: ");
        int habitacion = Teclado.nextInt();
        Teclado.nextLine();

        return habitacion;
    }

    public Date ingresarFechaDeIngreso() throws Exception {

        Date fechaIngreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        System.out.println("Ingrese la fecha de ingreso(dd/mm/yy)");

        try {
            fechaIngreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {

            throw new Exception("Fecha invalida.");
        }
        return fechaIngreso;

    }

    public Date ingresarFechaDeEgreso() throws Exception {

        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        System.out.println("Ingrese la fecha de egreso(dd/mm/yy)");

        try {
            fechaEgreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {
            throw new Exception("Fecha invalida.");

        }
        return fechaEgreso;

    }

    public void bajaReserva() {

        int id = ingresarID();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        id = this.ingresarID();
        Reserva reservaEncontrada = ABMReserva.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMReserva.delete(reservaEncontrada);
                System.out.println("La reserva ha sido eliminada.");

            } catch (Exception e) {
                System.out.println("Error al eliminar una reserva.");
            }

        }

    }

    public void modificaReserva() throws Exception {

        int id = ingresarID();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        id = this.ingresarID();
        Reserva reservaEncontrada = ABMReserva.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            System.out.println("Editar reserva de: " + huespedEncontrado.toString());

            System.out.println(
                    "Editar: \n1: fecha de reserva \n2: fecha de ingreso \n3: fecha de egreso \n4: habitación \n5: importe total \n6: importe de reserva \n7: importe pagado \n8: estado de pago");
            int seleccionarDato = Teclado.nextInt();

            switch (seleccionarDato) {
                case 1:
                    reservaEncontrada.setFechaReserva(new Date());
                    break;
                case 2:
                    Date fechaIngreso = this.ingresarFechaDeIngreso();
                    reservaEncontrada.setFechaIngreso(fechaIngreso);
                    break;
                case 3:
                    Date fechaEgreso = this.ingresarFechaDeEgreso();
                    reservaEncontrada.setFechaEgreso(fechaEgreso);
                    break;
                case 4:
                    int habitacion = this.ingresarHabitacion();
                    reservaEncontrada.setHabitacion(habitacion);
                    break;

                case 5:
                    BigDecimal importeTotal = this.ingresarImporteTotal();
                    reservaEncontrada.setImporteTotal(importeTotal);
                    break;

                case 6:
                    BigDecimal importeReserva = this.ingresarImporteReserva();
                    reservaEncontrada.setImporteReserva(importeReserva);
                    break;

                case 7:
                    BigDecimal importePagado = this.ingresarImportePagado();
                    reservaEncontrada.setImportePagado(importePagado);
                    break;

                case 8:
                    int estadoPago = this.ingresarEstadoPago();
                    reservaEncontrada.setTipoEstadoId(estadoPago);
                    break;

                default:
                    break;
            }

            ABMReserva.update(reservaEncontrada);

            System.out.println("La reserva ha sido modificada.");
        }

    }

    public void listarReservas() {

        List<Reserva> todas = ABMReserva.buscarTodas();

        for (Reserva reserva : todas) {

            mostrar(reserva);

        }
    }

    public void listarReservasPorDNI() throws Exception {

        int dni = this.ingresarDNI();

        List<Reserva> reservas = ABMReserva.buscarPor(dni);
        for (Reserva reserva : reservas) {
            mostrar(reserva);
        }
    }

    public void listarReservasPorNombre() {

        String nombre = this.ingresarNombre();

        List<Reserva> reservas = ABMReserva.buscarPor(nombre);
        for (Reserva reserva : reservas) {
            mostrar(reserva);
        }
    }

    public void mostrar(Huesped huesped) {

        System.out.print("Id: " + huesped.getHuespedId() + " Nombre: " + huesped.getNombre() + " DNI: "
                + huesped.getDni() + " Domicilio: " + huesped.getDomicilio());

        if (huesped.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + huesped.getDomicilioAlternativo());
        else
            System.out.println();
    }

    public void mostrar(Reserva reserva) {

        System.out.print("Id: " + reserva.getReservaId() + "\nFecha Reserva: " + reserva.getFechaReserva()
                + "\nFecha de Ingreso: " + reserva.getFechaIngreso() + "\nFecha de Egreso: " + reserva.getFechaEgreso()
                + "\nImporte de la reserva: " + reserva.getImporteReserva() + "\nHabitación: " + reserva.getHabitacion()
                + "\nEstado de pago: " + reserva.getTipoEstadoId() + "\n");

    }

}