package com.eccobottega.app.model;

public abstract class Producto implements AplicableDescuento  {

    private String id;
    private String descripcion;
    private int cantStock;
    private double precioUnidad;
    private double costoUnidad;
    private boolean disponible;

    public Producto(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantStock = cantStock;
        this.precioUnidad = precioUnidad;
        this.costoUnidad = costoUnidad;
        this.disponible = true;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantStock() {
        return cantStock;
    }

    public void setCantStock(int cantStock) {
        this.cantStock = cantStock;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public double getCostoUnidad() {
        return costoUnidad;
    }

    public void setCostoUnidad(double costoUnidad) {
        this.costoUnidad = costoUnidad;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


}
