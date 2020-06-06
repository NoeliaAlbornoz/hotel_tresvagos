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

            ABMHuesped.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

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
                        listarPorNombre();
                        break;

                    case 6:
                        seleccionarMenuReserva();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMHuesped.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Huesped huesped = new Huesped();
        System.out.println("Ingrese el nombre:");
        huesped.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        int dni = Teclado.nextInt();
        Teclado.nextLine();
        
        Huesped huesped1 = ABMHuesped.readByDNI(dni);

        if(huesped1 != null){

            throw new HuespedDNIException(huesped, "DNI ya existe");

        }

        huesped.setDni(dni);
        
        System.out.println("Ingrese la domicilio:");
        huesped.setDomicilio(Teclado.nextLine());

        System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            huesped.setDomicilioAlternativo(domAlternativo);

        // Vamos a generar una reserva.
        Reserva reserva = new Reserva();

        BigDecimal importeReserva = new BigDecimal(1000);
        reserva.setImporteReserva(importeReserva); // Forma 1

        reserva.setImporteTotal(new BigDecimal(3000)); // Forma 2

        reserva.setImportePagado(new BigDecimal(0));

        reserva.setFechaReserva(new Date()); // Fecha actual

        System.out.println("Ingrese la fecha de ingreso(dd/mm/yy)");

        Date fechaIngreso = null;
        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        // Alternativa de leer fecha con try catch
        try {
            fechaIngreso = dFormat.parse(Teclado.nextLine());

        } catch (Exception ex) {
            System.out.println("Ingreso una fecha invalida.");
            System.out.println("Vuelva a e empezar");
            return;
        }

        // Alternativa de leer fecha a los golpes(puede tirar una excepcion)
        System.out.println("Ingrese la fecha de egreso(dd/mm/yy)");
        fechaEgreso = dFormat.parse(Teclado.nextLine());

        reserva.setFechaIngreso(fechaIngreso);
        reserva.setFechaEgreso(fechaEgreso); // por ahora 1 dia.
        reserva.setTipoEstadoId(11); // En mi caso, 11 significa pagado.
        reserva.setHuesped(huesped); // Esta es la relacion bidireccional

        // Actualizo todos los objeto
        ABMHuesped.create(huesped);

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Huesped generada con exito.  " + huesped.getHuespedId);
         */

        System.out.println("Huesped generada con exito.  " + huesped);

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMHuesped.delete(huespedEncontrado);
                System.out
                        .println("El registro del huesped " + huespedEncontrado.getHuespedId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una huesped. Error: " + e.getCause());
            }

        }
    }

    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Huesped:");
        int dni = Teclado.nextInt();
        Huesped huespedEncontrado = ABMHuesped.readByDNI(dni);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {
            ABMHuesped.delete(huespedEncontrado);
            System.out.println("El registro del DNI " + huespedEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la huesped a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la huesped a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        if (huespedEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(huespedEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la huesped desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    huespedEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    huespedEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    huespedEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMHuesped.update(huespedEncontrado);

            System.out.println("El registro de " + huespedEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Huesped no encontrado.");
        }

    }

    public void listar() {

        List<Huesped> todos = ABMHuesped.buscarTodos();
        for (Huesped c : todos) {
            mostrarHuesped(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrarHuesped(huesped);
        }
    }

    public void mostrarHuesped(Huesped huesped) {

        System.out.print("Id: " + huesped.getHuespedId() + " Nombre: " + huesped.getNombre() + " DNI: "
                + huesped.getDni() + " Domicilio: " + huesped.getDomicilio());

        if (huesped.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + huesped.getDomicilioAlternativo());
        else
            System.out.println();
    }

    public void seleccionarMenuReserva() throws Exception {

        try {

            ABMReserva.setup();

            printOpcionesReserva();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:
                        altaReserva();
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
                        listarReservasPorNombreHuesped();
                        break;

                    case 6:
                        listarReservasPorDNIHuesped();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpcionesReserva();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMHuesped.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del menú Reservas.");

        }

    }

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un huesped.");
        System.out.println("2. Para eliminar un huesped.");
        System.out.println("3. Para modificar un huesped.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un huesped por nombre especifico(SQL Injection)).");
        System.out.println("6. Reservas.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionesReserva() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar una reserva.");
        System.out.println("2. Para eliminar una reserva.");
        System.out.println("3. Para modificar una reserva.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Para ver el listado de reservas de un huesped por su nombre.");
        System.out.println("6. Para ver el listado de reservas de un huesped por su dni.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public void altaReserva() throws Exception {

        System.out.println("Introducir el DNI del huésped: ");
        int dni = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huesped = ABMHuesped.readByDNI(dni);

        if(huesped == null){

            System.out.println("No existe");
            return;

        }

        Reserva reserva = new Reserva();

        reserva.setHuesped(huesped);

        reserva.setFechaReserva(new Date());

        Date fechaIngreso = null;
        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        System.out.println("Ingrese la fecha de ingreso(dd/mm/yy)");
        fechaIngreso = dFormat.parse(Teclado.nextLine());

        reserva.setFechaIngreso(fechaIngreso);

        System.out.println("Ingrese la fecha de egreso(dd/mm/yy)");
        fechaEgreso = dFormat.parse(Teclado.nextLine());

        reserva.setFechaEgreso(fechaEgreso);

        System.out.println("Ingrese habitación: ");
        reserva.setHabitacion(Teclado.nextInt());
        Teclado.nextLine();

        System.out.println("Introducir el importe de la reserva: ");
        reserva.setImporteReserva(Teclado.nextBigDecimal());
        Teclado.nextLine();

        System.out.println("Introducir el importe total: ");
        reserva.setImporteTotal(Teclado.nextBigDecimal());
        Teclado.nextLine();

        System.out.println("Introducir el importe pagado: ");
        reserva.setImportePagado(Teclado.nextBigDecimal());
        Teclado.nextLine();

        System.out.println("Ingrese el estado de pago: ");
        reserva.setTipoEstadoId(Teclado.nextInt());
        Teclado.nextLine();

        ABMReserva.create(reserva);

        System.out.println("Reserva generada con exito.  " + reserva);

    }

    public void bajaReserva() {

        System.out.println("Ingrese el ID de Huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        System.out.println("Ingrese el ID de Reserva:");
        int idR = Teclado.nextInt();
        Teclado.nextLine();
        Reserva reservaEncontrada = ABMReserva.read(idR);

        if (huespedEncontrado == null) {
            System.out.println("Huesped no encontrado.");

        } else {

            try {

                ABMReserva.delete(reservaEncontrada);
                System.out
                        .println("La reserva " + reservaEncontrada.getReservaId() + " ha sido eliminada.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una reserva.");
            }

        }

    }

    public void modificaReserva() throws Exception {

        Date fechaIngreso = null;
        Date fechaEgreso = null;

        DateFormat dFormat = new SimpleDateFormat("dd/MM/yy");

        // System.out.println("Ingrese el nombre de la huesped a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la huesped:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Huesped huespedEncontrado = ABMHuesped.read(id);

        System.out.println("Ingrese el ID de la reserva:");
        int idR = Teclado.nextInt();
        Teclado.nextLine();
        Reserva reservaEncontrada = ABMReserva.read(idR);


        if (huespedEncontrado != null && reservaEncontrada != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(huespedEncontrado.toString());

            System.out.println(
                    "Elija qué dato de la reserva desea modificar: \n1: fecha de reserva, \n2: fecha de ingreso, \n3: fecha de egreso, \n4: habitación, \n5: importe total, \n6: importe de reserva, \n7: importe pagado, \n8: estado de pago");
            int selecDato = Teclado.nextInt();

            switch (selecDato) {
                case 1:
                    System.out.println("Ingrese nueva fecha de reserva:");
                    Teclado.nextLine();
                    reservaEncontrada.setFechaReserva(new Date());

                    break;
                case 2:

                System.out.println("Ingrese nueva fecha de ingreso(dd/mm/yy)");
                Teclado.nextLine();
                fechaIngreso = dFormat.parse(Teclado.nextLine());
        
                reservaEncontrada.setFechaIngreso(fechaIngreso);

                    break;
                case 3:
                    System.out.println("Ingrese nueva fecha de egreso(dd/mm/yy):");
                    Teclado.nextLine();
                    fechaEgreso = dFormat.parse(Teclado.nextLine());

                    reservaEncontrada.setFechaEgreso(fechaEgreso);

                    break;
                case 4:
                    System.out.println("Ingrese habitación:");
                    Teclado.nextLine();
                    reservaEncontrada.setHabitacion(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                
                case 5:
                    System.out.println("Ingrese nuevo importe total:");
                    Teclado.nextLine();
                    reservaEncontrada.setImporteTotal(Teclado.nextBigDecimal());
                    Teclado.nextLine();

                    break;
                
                case 6:
                    System.out.println("Ingrese nuevo importe de reserva:");
                    Teclado.nextLine();
                    reservaEncontrada.setImporteReserva(Teclado.nextBigDecimal());
                    Teclado.nextLine();

                    break;
                
                case 7:
                    System.out.println("Ingrese nuevo importe pagado:");
                    Teclado.nextLine();
                    reservaEncontrada.setImportePagado(Teclado.nextBigDecimal());
                    Teclado.nextLine();

                    break;

                case 8:
                    System.out.println("Ingrese estado:");
                    Teclado.nextLine();
                    reservaEncontrada.setTipoEstadoId(Teclado.nextInt());
                    Teclado.nextLine();

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMReserva.update(reservaEncontrada);

            System.out.println("La reserva " + reservaEncontrada.getReservaId() + " ha sido modificada.");

        } else {
            System.out.println("Huesped/Reserva no encontrados.");
        }

    }

    public void listarReservas() {

        List<Reserva> todas = ABMReserva.buscarTodas();

        for(Reserva reserva : todas){

            mostrarReserva(reserva);

        }
    }

    public void listarReservasPorNombreHuesped() {

        System.out.println("Ingrese el nombre del huesped:");
        String nombre = Teclado.nextLine();

        List<Reserva> reservas = ABMHuesped.buscarReservasPor(nombre);
        for (Reserva reserva : reservas) {
            mostrarReserva(reserva);
        }
    }

    public void listarReservasPorDNIHuesped() {

        System.out.println("Ingrese el dni del huesped:");
        int dni = Teclado.nextInt();

        List<Reserva> reservas = ABMHuesped.buscarReservasPor(dni);
        for (Reserva reserva : reservas) {
            mostrarReserva(reserva);
        }
    }

    public void mostrarReserva(Reserva reserva) {

        System.out.print("\nReserva: \nId: " + reserva.getReservaId() + "\nFecha Reserva: " + reserva.getFechaReserva()
                + "\nFecha de Ingreso: " + reserva.getFechaIngreso() + "\nFecha de Egreso: " + reserva.getFechaEgreso()
                + "\nImporte de la reserva: " + reserva.getImporteReserva() + "\nHabitación: " + reserva.getHabitacion()
                + "\nEstado de pago: " + reserva.getTipoEstadoId() + "\n");

    }

}