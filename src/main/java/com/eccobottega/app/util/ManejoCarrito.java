package com.eccobottega.app.util;

import com.eccobottega.app.model.Producto;

import java.util.Map;
import java.util.Scanner;

public class ManejoCarrito {
    public static void agregarProductoAlCarrito(Producto producto, Map<Producto, Integer> carrito, Scanner scanner) {
        int totalUnidades = producto.getCantStock();

        if (totalUnidades > 0) {
            boolean inputValido = false;

            do {
                System.out.print("Ingrese el número de unidades (Stock = " + totalUnidades + "): ");

                if (scanner.hasNextInt()) {
                    int unidades = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    int nuevoTotal = carrito.getOrDefault(producto, 0) + unidades;

                    if (unidades > 0 && nuevoTotal <= totalUnidades && nuevoTotal <= 10) {
                        carrito.put(producto, nuevoTotal);
                        System.out.println("Producto añadido al carrito");
                        inputValido = true;
                    } else if (nuevoTotal > totalUnidades) {
                        System.out.println("Hay productos con stock disponible menor al solicitado.");
                        carrito.put(producto, totalUnidades);
                        inputValido = true;
                    } else {
                        System.out.println("Valor inválido. Por favor, ingrese un valor entre 1 y " + totalUnidades);
                    }
                } else {
                    System.out.println("Entrada inválida. Ingrese solo números.");
                    scanner.nextLine(); // Consumir entrada inválida
                }
            } while (!inputValido);
        }
    }

}

