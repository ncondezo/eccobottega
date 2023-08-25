package com.eccobottega.app.model;

import java.time.LocalDate;

public class BebidaAlcoholica extends Bebida{

    private double graduacionAlcoholica;
    private boolean esImportado;
    private double porcentajeDescuento;

    public BebidaAlcoholica(String id, String descripcion, int cantStock, double precioUnidad, double costoUnidad, double graduacionAlcoholica, boolean esImportado) {
        super(id, descripcion, cantStock, precioUnidad, costoUnidad);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.esImportado = esImportado;
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




    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public void setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    public boolean isEsImportado() {
        return esImportado;
    }

    public void setEsImportado(boolean esImportado) {
        this.esImportado = esImportado;
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

    /*@Override
    public boolean esAlcoholica() {
        return true;
    }*/
}
