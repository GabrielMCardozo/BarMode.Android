package com.barmode.app.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gabriel on 24/05/2014.
 */
public class Mesa implements Serializable {
    public String id;
    public String nome;
    public String senha;

    public ArrayList<Cliente> clientes;
    public ArrayList<Pedido> pedidos;

    @Override
    public String toString(){
        return id;
    };

    public Mesa(){
        clientes = new ArrayList<Cliente>();
        pedidos = new ArrayList<Pedido>();
    }
}
