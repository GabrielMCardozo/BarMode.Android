package com.barmode.app.Model;

import java.io.Serializable;

/**
 * Created by Gabriel on 24/05/2014.
 */
public class Cliente implements Serializable {
    public String nome;

    public Cliente(){}

    @Override
    public String toString() {
        return nome;
    }
}
