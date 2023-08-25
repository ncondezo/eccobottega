package com.eccobottega.app.util;

import com.eccobottega.app.model.GeneradorId;

public class GeneradorIdLimpieza implements GeneradorId {

    private int contador = 1;

    @Override
    public String generarId() {
        String id = String.format("AZ%03d",contador);
        contador++;
        return id;

    }
}
