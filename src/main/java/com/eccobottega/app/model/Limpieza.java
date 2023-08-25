package com.eccobottega.app.model;

public class Limpieza extends Producto implements AplicableDescuento{

    private TipoAplicacion tipo;

    private double porcentajeDescuento;

    private static final double MAX_PORCENTAJE_DESCUENTO = 0.25;

    public Limpieza(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad,TipoAplicacion tipo) {
        super(id, descripcion, cantStock, precioUnidad, costoUnidad);
        this.tipo = tipo;
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

    public TipoAplicacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoAplicacion tipo) {
        this.tipo = tipo;
    }


}
