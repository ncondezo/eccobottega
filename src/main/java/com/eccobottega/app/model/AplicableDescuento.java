package com.eccobottega.app.model;

public interface AplicableDescuento {

    void setPorcentajeDescuento(double porcentajeDescuento);
    double getPorcentajeDescuento();
    double getPrecioVentaConDescuento();
   void aplicarDescuento();


}
