package ar.com.ada.hoteltresvagos.services;

import java.util.List;
import java.util.Scanner;

import ar.com.ada.hoteltresvagos.entities.Huesped;
import ar.com.ada.hoteltresvagos.excepciones.HuespedDNIException;
import ar.com.ada.hoteltresvagos.managers.HuespedManager;

public class HuespedService {

    public static Scanner Teclado = new Scanner(System.in);

    protected HuespedManager ABMHuesped = new HuespedManager();

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

    public void buscarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Huesped> huespedes = ABMHuesped.buscarPor(nombre);
        for (Huesped huesped : huespedes) {
            mostrar(huesped);
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