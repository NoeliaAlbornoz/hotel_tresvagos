package ar.com.ada.hoteltresvagos.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.Huesped;
import ar.com.ada.hoteltresvagos.entities.Reserva;
import ar.com.ada.hoteltresvagos.excepciones.HuespedDNIException;
import ar.com.ada.hoteltresvagos.managers.HuespedManager;
import ar.com.ada.hoteltresvagos.managers.ReservaManager;

public class ReservaService {


    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();
    protected ReservaManager ABMReserva = new ReservaManager();
    
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

    public void mostrar(Reserva reserva) {

        System.out.print("Id: " + reserva.getReservaId() + "\nFecha Reserva: " + reserva.getFechaReserva()
                + "\nFecha de Ingreso: " + reserva.getFechaIngreso() + "\nFecha de Egreso: " + reserva.getFechaEgreso()
                + "\nImporte de la reserva: " + reserva.getImporteReserva() + "\nHabitación: " + reserva.getHabitacion()
                + "\nEstado de pago: " + reserva.getTipoEstadoId() + "\n");

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
    
}