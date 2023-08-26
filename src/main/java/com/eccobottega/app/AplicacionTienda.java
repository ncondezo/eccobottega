package com.eccobottega.app;

import com.eccobottega.app.model.*;
import com.eccobottega.app.util.*;

import java.util.*;

import static com.eccobottega.app.model.Tienda.*;
import static com.eccobottega.app.model.TipoAplicacion.ROPA;
import static com.eccobottega.app.model.TipoEnvase.LATA;
import static com.eccobottega.app.model.TipoEnvase.VIDRIO;

import static com.eccobottega.app.util.GestorTienda.obtenerOpcionValida;

public class AplicacionTienda {

    public static void main(String[] args) {

        GestorTienda gestorProductos = new GestorTienda();
        // genera id únicos para cada categoría de producto
        GeneradorIdBebida idBebida = new GeneradorIdBebida();
        GeneradorIdEnvasado idEnvasado = new GeneradorIdEnvasado();
        GeneradorIdLimpieza idLimpieza = new GeneradorIdLimpieza();

        Tienda tienda = new Tienda("Coto", 1000000, 1000000000.00);
        Tienda tienda2 = new Tienda("Wemec", 100000, 10000.00);
        Set<Tienda> tiendas = new HashSet<>();
        tiendas.add(tienda);
        tiendas.add(tienda2);
        Producto coca = new BebidaNoAlcoholica(idBebida.generarId(), "Coca Cola", 8, 50.00, 49.00);
        Producto agua = new BebidaNoAlcoholica(idBebida.generarId(), "Villavicencio", 100, 20.00, 18.00);
        Producto cerveza = new BebidaAlcoholica(idBebida.generarId(),"Heineken",55,8.0,6.9,5.0,true);
        Producto atun = new Envasado(idEnvasado.generarId(),"Lata atún",50,12.00,11.5, TipoEnvase.LATA,false);
        Producto aceitunas = new Envasado(idEnvasado.generarId(),"Aceitunas",80,27.00,25.5,VIDRIO,true);
        Producto duraznos = new Envasado(idEnvasado.generarId(),"Duraznos",100,22.00,21.1,LATA,true);
        Producto salsaTomate = new Envasado(idEnvasado.generarId(),"Salsa de tomate",12,5.5,4.8,TipoEnvase.LATA,false);
        Producto cif = new Limpieza(idLimpieza.generarId(),"Detergente Cif",25,7.0,6.4,TipoAplicacion.MULTIUSO);
        Producto poet = new Limpieza(idLimpieza.generarId(),"Limpiapisos Poet",30,8.3,8.0,TipoAplicacion.PISOS);
        Producto ala = new Limpieza(idLimpieza.generarId(),"Jabón Líquido Ala",100,23.00,22.00,ROPA);

        // Se setean descuentos a algunos productos
        atun.setPorcentajeDescuento(0.1);
        aceitunas.setPorcentajeDescuento(0.1);
        duraznos.setPorcentajeDescuento(0.15);
        agua.setPorcentajeDescuento(0.1);

        // Se agregan los productos a la tienda
        tienda.agregarProducto(CATEGORIA_BEBIDA, coca);
        tienda.agregarProducto(CATEGORIA_BEBIDA, agua);
        tienda.agregarProducto(CATEGORIA_ENVASADO,atun);
        tienda.agregarProducto(CATEGORIA_ENVASADO,salsaTomate);
        tienda.agregarProducto(CATEGORIA_LIMPIEZA,cif);
        tienda.agregarProducto(CATEGORIA_LIMPIEZA,poet);
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        tienda.agregarProducto(CATEGORIA_ENVASADO,aceitunas);
        tienda.agregarProducto(CATEGORIA_ENVASADO,duraznos);

        Map<String, Tienda> tiendasMap = new HashMap<>();
        tiendas.forEach(t -> tiendasMap.put(t.getNombre().toUpperCase(), t));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Tiendas disponibles:");
        tiendas.forEach(tiendaActual -> System.out.println(tiendaActual.getNombre()));

        Tienda tiendaSeleccionada = null;
        Map<Producto, Integer> carrito = new HashMap<>();   // CARRITO DONDE SE AÑADIRÁN LOS PRODUCTOS A COMPRAR
        boolean seguirComprando = true;
        while (true) {
            System.out.print("Ingrese el nombre de la tienda: ");
            String nombreTienda = scanner.nextLine().toUpperCase();

            if (tiendasMap.containsKey(nombreTienda)) {
                tiendaSeleccionada = tiendasMap.get(nombreTienda);
                System.out.println(tiendaSeleccionada);
                System.out.println("Ha seleccionado la tienda: " + nombreTienda);
                break; // Salir del bucle si la selección es válida
            } else {
                System.out.println("Tienda no encontrada. Por favor, ingrese un nombre válido.");
            }
        }

        while (seguirComprando) {

            mostrarMenu();
            int opcion = obtenerOpcionValida(scanner);//scanner.nextInt();
            //scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:

                    List<Producto> bebidasDisponibles = tiendaSeleccionada.getProductosDisponiblesPorCategoria(CATEGORIA_BEBIDA);
                    if(!bebidasDisponibles.isEmpty()) {
                        gestorProductos.mostrarProductosDisponibles(bebidasDisponibles);
                        Producto bebidaSeleccionada = GestorTienda.seleccionarProducto(bebidasDisponibles, scanner);
                        ManejoCarrito.agregarProductoAlCarrito(bebidaSeleccionada, carrito, scanner);
                    }else {
                        System.out.println("    --- Nada por aquí ---");
                    }

                    break;

                case 2:

                    List<Producto> envasadosDisponibles = tiendaSeleccionada.getProductosDisponiblesPorCategoria(CATEGORIA_ENVASADO);
                    if(!envasadosDisponibles.isEmpty()) {
                        gestorProductos.mostrarProductosDisponibles(envasadosDisponibles);
                        Producto envasadoSeleccionado = GestorTienda.seleccionarProducto(envasadosDisponibles, scanner);
                        ManejoCarrito.agregarProductoAlCarrito(envasadoSeleccionado, carrito, scanner);
                    }else {
                        System.out.println("    --- Nada por aquí ---");
                    }

                    break;

                case 3:

                    List<Producto> limpiezaDisponibles = tiendaSeleccionada.getProductosDisponiblesPorCategoria(CATEGORIA_LIMPIEZA);
                    if(!limpiezaDisponibles.isEmpty()) {
                        gestorProductos.mostrarProductosDisponibles(limpiezaDisponibles);
                        Producto limpiezaSeleccionado = GestorTienda.seleccionarProducto(limpiezaDisponibles, scanner);
                        ManejoCarrito.agregarProductoAlCarrito(limpiezaSeleccionado, carrito, scanner);
                    }else{
                        System.out.println("    --- Nada por aquí ---");
                    }

                    break;

                case 4:

                    System.out.println("----------");
                    tiendaSeleccionada.venderProducto(carrito);
                    System.out.println("----------");
                    carrito.clear();


                    break;

                case 5:
                    List<String> productos = gestorProductos.listarProductosConUtilidadesInferiores(scanner,tiendaSeleccionada.getProductosStock());
                    if (!productos.isEmpty()) {
                        System.out.println("Productos con utilidades inferiores:");
                        for (String productoInfo : productos) {
                            System.out.println(productoInfo);
                        }
                    } else {
                        System.out.println("No hay productos con utilidades inferiores.");
                    }
                    break;

                case 6:
                    List<String> productosComestibles = gestorProductos.obtenerComestiblesConMenorDescuento(scanner,tiendaSeleccionada.getProductosStock());
                    if (!productosComestibles.isEmpty()) {
                        System.out.println("Productos comestibles no importados con menor descuento: ");
                        for (String productoInfo : productosComestibles) {
                            System.out.println(productoInfo);
                        }
                    } else {
                        System.out.println("No hay productos comestibles en la tienda.");
                    }
                    break;


                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }

            if (opcion == 7) {
                break;
            }
        }

        scanner.close();
    }

    public static void mostrarMenu(){                           // MENU DE LA APP
        System.out.println("Seleccione una opción: ");
        System.out.println("1. Añadir bebidas");
        System.out.println("2. Añadir productos envasados");
        System.out.println("3. Añadir productos de limpieza");
        System.out.println("4. Comprar");
        System.out.println("5. Listar productos con utilidad inferior");
        System.out.println("6. Listar productos comestibles con menor descuento.");
        System.out.println("7. SALIR");
    }


}














