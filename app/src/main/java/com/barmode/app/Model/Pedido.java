package com.barmode.app.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gabriel on 24/05/2014.
 */
public class Pedido implements Serializable {

    public String id;
    public Produto produto;
    public ArrayList<Cliente> clientes;

    public Pedido(){
        clientes = new ArrayList<Cliente>();
    }

}
