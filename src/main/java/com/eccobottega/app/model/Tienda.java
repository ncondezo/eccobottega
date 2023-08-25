package com.eccobottega.app.model;

import com.eccobottega.app.model.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.eccobottega.app.model.TipoAplicacion.MULTIUSO;
import static com.eccobottega.app.model.TipoAplicacion.ROPA;

public class Tienda {

    public static final String CATEGORIA_BEBIDA = "Bebida";
    public static final String CATEGORIA_ENVASADO = "Envasado";
    public static final String CATEGORIA_LIMPIEZA = "Limpieza";
    private static final int MAX_CANTIDAD_POR_VENTA = 10;
    private static final int MAX_PRODUCTOS_POR_VENTA = 3;
    private String nombre;
    private int maxStock;
    private double saldoCaja;
    private Map<String, List<Producto>> productosStock;

    public Tienda(String nombre, int maxStock, double saldoCaja) {
        this.nombre = nombre;
        this.maxStock = maxStock;
        this.saldoCaja = saldoCaja;
        this.productosStock = new HashMap<>();
        productosStock.put(CATEGORIA_BEBIDA, new ArrayList<>());
        productosStock.put(CATEGORIA_ENVASADO, new ArrayList<>());
        productosStock.put(CATEGORIA_LIMPIEZA, new ArrayList<>());
    }

    public List<Producto> getProductosPorCategoria(String categoria) {
        List<Producto> productosPorCategoria = new ArrayList<>();
        if (productosStock.containsKey(categoria)) {
            productosPorCategoria.addAll(productosStock.get(categoria));
        }
        return productosPorCategoria;
    }
    public void agregarProducto(String tipo, Producto producto) {
        List<Producto> listaProductos = productosStock.get(tipo);

        int stockTotal = productosStock.values().stream()
                .flatMap(List::stream)
                .mapToInt(Producto::getCantStock)
                .sum();

        int espacioDisponible = maxStock - stockTotal; // Calcula el espacio disponible en el stock

        int cantidadAgregar = Math.min(espacioDisponible, producto.getCantStock()); // Determina la cantidad a agregar
        producto.setCantStock(cantidadAgregar);

        if (cantidadAgregar > 0) {
            double totalProducto = producto.getCostoUnidad() * cantidadAgregar;

            if (totalProducto <= saldoCaja) {
                listaProductos.add(producto);
                saldoCaja -= totalProducto;
                System.out.println("Se agregaron " + cantidadAgregar + " unidades del producto " + producto.getDescripcion() + " con éxito.");
            } else {
                System.out.println(producto.getDescripcion() + " no puede ser agregado a la tienda por saldo insuficiente.");
            }
        } else {
            System.out.println("No hay suficiente espacio en el stock para agregar el producto " + producto.getDescripcion());
        }
    }

    public void venderProducto(Map<Producto, Integer> productosVenta) {
        boolean ventaRealizada = false;

        // VERIFICA QUE NO SE VENDAN MÁS DE 3 PRODUCTOS
        if (productosVenta.size() > MAX_PRODUCTOS_POR_VENTA) {
            System.out.println("No se pueden vender más de tres productos en una sola venta");
            return;
        }

        if(!estanTodasLasListasVacias(productosVenta)){
            System.out.println("Debe ingresar al menos un producto.");
            return;
        }

        if (!hayStock(productosVenta)) {
            return;
        }

        double totalVenta = 0.0;
        //boolean ventaRealizada = false;
        List<String> detalleVenta = new ArrayList<>();

        for (Map.Entry<Producto, Integer> entry : productosVenta.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidadVenta = entry.getValue();

            if (cantidadVenta <= 0) {
                continue;
            }

            // SE VERIFICA QUE EL PRODUCTO ESTE HABILITADO PARA LA VENTA
            if (!estaDisponible(producto, cantidadVenta)) {
                continue;
            }
            // SE VERIFICA PORCENTAJE DE GANANCIA SEA EL PERMITIDO
            if (!porcentaje(producto)) {
                System.out.println("La compra del producto " + producto.getId() + " ha sido cancelada debido a que el porcentaje de ganancia no está dentro de los límites permitidos.");
                continue;
            }
            // APLICAR DESCUENTO
            producto.aplicarDescuento();

            Integer unidades = Math.min(producto.getCantStock(), cantidadVenta);

            // VERIFICA QUE NO SE VENDAN MÁS DE LAS UNIDADES DISPONIBLES


            if (producto.getCantStock() < cantidadVenta) {
                producto.setDisponible(false);
                System.out.println("Hay productos con stock disponible menor al solicitado");
            }

            Double precioVenta = calcularPrecioVenta(producto, cantidadVenta);
            double subtotal = unidades * precioVenta;
            totalVenta += subtotal;

            String detalleProducto = obtenerFormatoDetalle(producto, unidades, precioVenta);
            detalleVenta.add(detalleProducto);

            ventaRealizada = true;

            producto.setCantStock(producto.getCantStock() - unidades); //movi red aca
        }

        if (ventaRealizada) {
            saldoCaja += totalVenta;
            imprimirDetalles(detalleVenta, totalVenta);
        }
    }

    private boolean estanTodasLasListasVacias(Map<Producto,Integer> map) {
        for (Integer cantidad : map.values()) {
            System.out.println(cantidad);
            if (cantidad == 0) {
                System.out.println("Todo vacio");
                return true;

            }
        }
        return false;
    }










private boolean estaDisponible(Producto producto, int cantidad) {

        if (!producto.estaDisponible()) {
            System.out.println("El producto " + producto.getId() + " no está disponible para la venta.");
            return false;
        }
        if (cantidad > MAX_CANTIDAD_POR_VENTA) {
            System.out.println("No se pueden vender más de diez unidades del producto " + producto.getDescripcion());
            return false;
        }

        return true;
    }

    private double calcularPrecioVenta(Producto producto, int cantidadVenta) {
        double precioVenta = producto.getPrecioUnidad();
        if (producto instanceof Envasado && ((Envasado) producto).isImportado()) {
            precioVenta *= 1.1;
        } else if (producto instanceof BebidaAlcoholica && ((BebidaAlcoholica) producto).isEsImportado()) {
            precioVenta *= 1.1;
        }
        return precioVenta;
    }

    private boolean porcentaje(Producto producto) {

        if (producto instanceof Comestible) {
            Comestible comestible = (Comestible) producto;
            double porcentajeGanancia = calcularPorcentajeGanancia(producto);
            return !(porcentajeGanancia > 20);

        } else if (producto instanceof Limpieza) {
            Limpieza limpieza = (Limpieza) producto;
            double porcentajeGanancia = calcularPorcentajeGanancia(limpieza);
            if (limpieza.getTipo() == ROPA || limpieza.getTipo() == MULTIUSO) {
                return porcentajeGanancia <= 25;
            } else {
                return porcentajeGanancia >= 10 && porcentajeGanancia <= 25;
            }
        }
        return true;
    }

    private double calcularPorcentajeGanancia(Producto producto) {
        double precioVenta = producto.getPrecioUnidad();
        double costoVenta = producto.getCostoUnidad();
        return ((precioVenta - costoVenta) / costoVenta * 100);
    }

    DecimalFormat formatVenta = new DecimalFormat("0.00");

    private void imprimirDetalles(List<String> detalleVenta, double totalVenta) {
        System.out.println("Detalle de la venta: ");
        detalleVenta.forEach(System.out::println);
        System.out.println("TOTAL VENTA: " + formatVenta.format(totalVenta).replace(".",","));
    }



    private String obtenerFormatoDetalle(Producto producto, int unidades, double precioVenta) {
        if (producto instanceof AplicableDescuento && ((AplicableDescuento) producto).getPorcentajeDescuento() > 0) {
            double porcentajeDescuento = ((AplicableDescuento) producto).getPorcentajeDescuento();
            return String.format("%s %s %d x %.2f - DESCUENTO: %.2f%% -",
                    producto.getId(), producto.getDescripcion(), unidades, precioVenta, porcentajeDescuento);
        } else {
            return String.format("%s %s %d x %.2f",
                    producto.getId(), producto.getDescripcion(), unidades, precioVenta);
        }
    }

    public void detallesProductos() {

        boolean tiendaVacia = true;

        for (Map.Entry<String, List<Producto>> entrada : productosStock.entrySet()) {
            String tipo = entrada.getKey();
            List<Producto> listaProductos = entrada.getValue();

            for (Producto producto : listaProductos) {
                if (producto.getCantStock() > 0) {
                    tiendaVacia = false;
                    System.out.println(producto.getDescripcion() + " " + producto.getCantStock() + " unidades en stock.");
                }
            }
        }
        if (tiendaVacia) {
            System.out.println("Tienda vacia");
        }

    }

    public boolean hayStock(Map<Producto, Integer> productosVenta) {

        for (Map.Entry<String, List<Producto>> entrada : productosStock.entrySet()) {
            String tipo = entrada.getKey();
            List<Producto> listaProductos = entrada.getValue();

            for (Producto producto : listaProductos) {
                if (producto.getCantStock() > 0) {
                    return true;

                }
            }
        }
        System.out.println("No hay stock stock");

        return false;
    }






    public void mostrarProductosPorCategoria() {
        for (Map.Entry<String, List<Producto>> entry : productosStock.entrySet()) {
            String categoria = entry.getKey();
            List<Producto> productos = entry.getValue();

            System.out.println("Categoría: " + categoria);
            for (Producto producto : productos) {
                System.out.println("- " + producto.getDescripcion());
            }
        }
    }
    public List<Producto> getProductosDisponiblesPorCategoria(String categoria) {
        return getProductosPorCategoria(categoria).stream()
                .filter(producto -> producto.getCantStock() > 0)
                .collect(Collectors.toList());
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public double getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

    public Map<String, List<Producto>> getProductosStock() {
        return productosStock;
    }

    public void setProductosStock(Map<String, List<Producto>> productosStock) {
        this.productosStock = productosStock;

   }
}



