package com.eccobottega.app.model;

import java.time.LocalDate;

public interface Comestible {

    void setFechaVencimiento(LocalDate fechaVencimiento);
    LocalDate getFechaVencimiento();
    void setCalorias(int calorias);
    int getCalorias();


    boolean esImportado();
}
