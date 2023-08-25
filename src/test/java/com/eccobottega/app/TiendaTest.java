package com.eccobottega.app;

import com.eccobottega.app.model.Bebida;
import com.eccobottega.app.model.BebidaAlcoholica;
import com.eccobottega.app.model.Producto;
import com.eccobottega.app.model.Tienda;
import com.eccobottega.app.util.GeneradorIdBebida;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.eccobottega.app.model.Tienda.CATEGORIA_BEBIDA;
import static org.junit.jupiter.api.Assertions.*;

class TiendaTest {
    GeneradorIdBebida generadorIdBebida = new GeneradorIdBebida();
    Tienda tienda;
    Bebida cerveza;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        tienda = new Tienda("TiendaTest", 20, 1000.00);
        cerveza = new BebidaAlcoholica(generadorIdBebida.generarId(), "Heineken", 3, 5.0, 4.5, 5.0, false);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalSystemOut);
    }

    @Test
    @DisplayName("Agregar productos + actualización del saldo")
    void agregarProductoTest(){
        double saldoInicial = tienda.getSaldoCaja();
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Se agregaron "+cerveza.getCantStock()+" unidades del producto Heineken con éxito."));
        assertTrue(tienda.getSaldoCaja()== saldoInicial - cerveza.getCostoUnidad()*cerveza.getCantStock());
    }

    @Test
    @DisplayName("Agregar productos saldo insuficiente")
    void saldoInsuficienteTest(){
        tienda.setSaldoCaja(0.5);
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains(cerveza.getDescripcion()+" no puede ser agregado a la tienda por saldo insuficiente."));
    }

    void obtenerComestiblesConMenorDescuento(){

    }

    @Test
    @DisplayName("Añadir el máx productos a tienda")
    void agregarMaxStockTienda(){
        tienda.setMaxStock(2);
        double saldoAntes = tienda.getSaldoCaja();
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Se agregaron "+cerveza.getCantStock()+" unidades del producto "+cerveza.getDescripcion()+" con éxito."));
        assertTrue(tienda.getSaldoCaja() == saldoAntes - (cerveza.getCostoUnidad()*2));

    }

    @Test
    @DisplayName("Venta exitosa")
    void venderTest(){
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        Map<Producto, Integer> carrito = new HashMap<>();
        carrito.put(cerveza, 1);
        tienda.venderProducto(carrito);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Detalle de la venta:"));
        assertTrue(consoleOutput.contains(cerveza.getId()+" "+cerveza.getDescripcion()));
        assertTrue(consoleOutput.contains("TOTAL VENTA: 5,00"));

    }

    @ParameterizedTest
    @DisplayName("Tratar de vender + 10 unidades")
    @CsvSource({
            "11","13","14","15"
    })
    void venderMasDiezProductos(int cantidad){
        tienda.agregarProducto(CATEGORIA_BEBIDA,cerveza);
        Map<Producto, Integer> carrito = new HashMap<>();
        carrito.put(cerveza, cantidad);
        tienda.venderProducto(carrito);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("No se pueden vender más de diez unidades del producto "+cerveza.getDescripcion()));

    }

}