package com.barmode.app.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.Model.Cliente;
import com.barmode.app.R;

import java.util.ArrayList;

public class FazerPedido extends ActionBarActivity {

    private ArrayList<Cliente> clientes;
    private EditText txt_nome_produto;
    private EditText txt_valor_produto;
    private EditText txt_nome_cliente;
    private ListView lv_clientes;
    private Button btn_adicionar_clientes;
    private Button btn_salvar_pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_fazer_pedido);

        clientes = new ArrayList<Cliente>();

        txt_nome_produto = (EditText) findViewById(R.id.txt_nome_produto);
        txt_valor_produto = (EditText) findViewById(R.id.txt_valor_produto);
        txt_nome_cliente = (EditText) findViewById(R.id.txt_nome_cliente);
        lv_clientes = (ListView) findViewById(R.id.lv_clientes);
        btn_adicionar_clientes = (Button) findViewById(R.id.btn_adicionar_clientes);
        btn_salvar_pedido = (Button) findViewById(R.id.btn_salvar_pedido);

        btn_adicionar_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdicionarCliente();
                UpdateClientes();
            }
        });

    }

    private void AdicionarCliente() {
        Cliente cliente = new Cliente();
        cliente.nome = txt_nome_cliente.getText().toString();

        clientes.add(cliente);

        txt_nome_cliente.setText("");
    }

    private void UpdateClientes(){
        ArrayAdapter adapter = new ArrayAdapter<Cliente>(FazerPedido.this, android.R.layout.simple_list_item_1, clientes);
        lv_clientes.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fazer_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
