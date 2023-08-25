package com.eccobottega.app.model;

public abstract class Bebida extends Producto implements AplicableDescuento,Comestible {

    private static final double MAX_PORCENTAJE_DESCUENTO = 0.15;
    private boolean esAlchoholica;

    public Bebida(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad) {
        super(id, descripcion, cantStock, precioUnidad, costoUnidad);
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


}
