package com.eccobottega.app.util;

import com.eccobottega.app.model.Tienda;
import com.eccobottega.app.model.AplicableDescuento;
import com.eccobottega.app.model.Comestible;
import com.eccobottega.app.model.Producto;

import java.util.*;
import java.util.stream.Collectors;

public class GestorTienda implements Visualizador{

    private Scanner scanner = new Scanner(System.in);
    private static Tienda tienda;

    @Override                           //  SE LISTAN LOS PRODUCTOS DISPONIBLES
    public void mostrarProductosDisponibles(List<Producto> productosDisponibles) {

        System.out.println("Productos disponibles:");
        for (Producto producto : productosDisponibles) {
            System.out.println(producto.getId() + ". " + producto.getDescripcion() + " - Precio: " + producto.getPrecioUnidad());
            }

        }

        //  SE FILTRA AL PRODUCTO POR SU ID
    public static Producto seleccionarProducto(List<Producto> productosDisponibles, Scanner scanner) {
        System.out.print("Seleccione un producto por su ID: ");
        String idSeleccionado = scanner.nextLine().toUpperCase();

        return productosDisponibles.stream()
                .filter(producto -> producto.getId().equals(idSeleccionado))
                .findFirst()
                .orElse(null);
    }

    //  SE VALIDA QUE LA OPCION SELECCIONADA EN EL MENU (PARA EL SWITCH) SEA VÁLIDA
    public static int obtenerOpcionValida(Scanner scanner) {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Ingrese una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                entradaValida = opcion >= 1 && opcion <= 7;
            } else {
                scanner.nextLine(); // Consumir la entrada inválida
                System.out.println("Entrada inválida. Por favor, ingrese un número de opción válido.");
            }
        }

        return opcion;
    }



    // MÉTODO PARA FILTRAR LOS PRODUCTOS CON UTILIDADES INFERIORES A UN PORCENTAJE INGRESADO POR CONSOLA
    public List<String> listarProductosConUtilidadesInferiores(Scanner scanner,Map<String, List<Producto>> map) {
        System.out.println("Ingrese porcentaje de utilidad: ");
        if (scanner.hasNextInt()) {
            double porcentajeUtilidad = scanner.nextDouble();
            return map.values().stream()
                    .flatMap(List::stream) // Aplanar las listas de productos
                    .filter(producto -> calcularPorcentajeGanancia(producto) < porcentajeUtilidad)
                    .map(producto -> producto.getId() + " - " + producto.getDescripcion() + " - Stock: " + producto.getCantStock())
                    .collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }

    }

    // MÉTODO QUE CALCULA EL PORCENTAJE DE GANANCIA DE UN PRODUCTO (SE UTILIZA EN EL MÉTODO ANTERIOR, listarProductosConUtilidadesInferiores)
    private double calcularPorcentajeGanancia(Producto producto) {
        double precioVenta = producto.getPrecioUnidad();
        double costoVenta = producto.getCostoUnidad();
        return ((precioVenta - costoVenta) / costoVenta * 100);
    }

    // MÉTODO QUE LISTA LOS PRODUCTOS QUE IMPLEMENTAN LA INTERFAZ COMESTIBLE Y NO SON IMPORTADOS, SEGÚN EL PORCENTAJE INGRESADO

    public List<String> obtenerComestiblesConMenorDescuento(Scanner scanner,Map<String, List<Producto>> map) {
        System.out.println("Ingrese porcentaje de descuento: ");
        if(scanner.hasNextDouble()) {
            double porcentajeDescuento = scanner.nextDouble();
            List<Producto> comestibles = new ArrayList<>();
            for (Map.Entry<String, List<Producto>> entry : map.entrySet()) {
                String categoria = entry.getKey();
                List<Producto> productos = entry.getValue();

                for (Producto producto : productos) {
                    if (producto instanceof Comestible && !((Comestible) producto).esImportado()) {
                        Comestible comestible = (Comestible) producto;
                            AplicableDescuento aplicableDescuento = (AplicableDescuento) comestible;
                            if (aplicableDescuento.getPorcentajeDescuento() < porcentajeDescuento) {
                                comestibles.add(producto);
                            }


                    }
                }
            }
            return convertirMayusculasYOrdenar(comestibles);
        }
        else{
            return Collections.emptyList();
        }
    }

    // AQUÍ SE CONVIERTE LA LIST A MAYUSCULA Y SE ORDENA DE FORMA ACSENCENDENTE
    public static List<String> convertirMayusculasYOrdenar(List<Producto> lista) {
        ArrayList<String> listaMayusculas = new ArrayList<>();

        lista.sort(new Comparator<Producto>() {
            @Override
            public int compare(Producto p1, Producto p2) {
                return Double.compare(p1.getPrecioUnidad(), p2.getPrecioUnidad());
            }
        });

        for (Producto producto : lista) {
            listaMayusculas.add(producto.getDescripcion().toUpperCase()+ " " + String.format("%.0f%% OFF", producto.getPorcentajeDescuento() * 100));
        }

        return listaMayusculas;
    }

}










