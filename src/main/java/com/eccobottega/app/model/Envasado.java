package com.eccobottega.app.model;

import java.time.LocalDate;

public class Envasado extends Producto implements Comestible {

    private static final double MAX_PORCENTAJE_DESCUENTO = 0.20;
    private TipoEnvase tipo;
    private boolean importado;
    private double porcentajeDescuento;



    public Envasado(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad, TipoEnvase tipo, boolean importado) {
        super(id, descripcion, cantStock, precioUnidad, costoUnidad);
        this.tipo = tipo;
        this.importado = importado;
    }



    public TipoEnvase getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnvase tipo) {
        this.tipo = tipo;
    }

    public boolean isImportado() {
        return importado;
    }

    public void setImportado(boolean importado) {
        this.importado = importado;
    }

    @Override
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public double getPrecioVentaConDescuento() {
        return getPrecioUnidad() * (1 - getPorcentajeDescuento());
    }

    @Override
    public void aplicarDescuento() {
        if(getPorcentajeDescuento()>MAX_PORCENTAJE_DESCUENTO){
            System.out.println("El descuento registrado para el producto "+getId()+" excede el límite permitido.");
            return;
        }
        double precioConDescuento = getPrecioUnidad() * (1 - getPorcentajeDescuento());
        if(precioConDescuento < getCostoUnidad()){
            System.out.println("El descuento registrado para el producto "+getId()+" no pudo ser aplicado.");
            return;
        }
        setPrecioUnidad(precioConDescuento);
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {

    }

    @Override
    public LocalDate getFechaVencimiento() {
        return null;
    }

    @Override
    public void setCalorias(int calorias) {

    }

    @Override
    public int getCalorias() {
        return 0;
    }

    @Override
    public boolean esImportado() {
        return this.importado;
    }
}
