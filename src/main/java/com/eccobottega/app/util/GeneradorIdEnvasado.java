package com.eccobottega.app.util;

import com.eccobottega.app.model.GeneradorId;

public class GeneradorIdEnvasado implements GeneradorId {

    private int contador = 1;

    @Override
    public  String generarId() {
        String id = String.format("AB%03d",contador);
        contador++;
        return id;

    }
}
