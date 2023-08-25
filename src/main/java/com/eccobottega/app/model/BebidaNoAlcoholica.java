package com.eccobottega.app.model;

import java.time.LocalDate;

public class BebidaNoAlcoholica extends Bebida {

    private double porcentajeDescuento;

    private boolean esImportado;


    public BebidaNoAlcoholica(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad) {
        super(id, descripcion, cantStock, precioUnidad, costoUnidad);
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
        return this.esImportado;
    }


}
