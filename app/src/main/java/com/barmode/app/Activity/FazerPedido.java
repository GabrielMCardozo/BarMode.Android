package com.barmode.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.IntentBundleMesa;
import com.barmode.app.Model.Cliente;
import com.barmode.app.Model.Pedido;
import com.barmode.app.Model.Produto;
import com.barmode.app.R;
import com.barmode.app.Service.MesaService;

import java.util.ArrayList;

public class FazerPedido extends ActionBarActivity {

    private ArrayList<Cliente> clientes;
    private EditText txt_nome_produto;
    private EditText txt_valor_produto;
    private EditText txt_nome_cliente;
    private ListView lv_clientes;
    private Button btn_adicionar_clientes;
    private Button btn_salvar_pedido;

    private String idMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_fazer_pedido);

        clientes = new ArrayList<Cliente>();

        idMesa = IntentBundleMesa.GetIdMesa(this);

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

        btn_salvar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FazerPedido();
            }
        });

    }

    private void AdicionarCliente() {
        Cliente cliente = new Cliente();
        cliente.nome = txt_nome_cliente.getText().toString();

        clientes.add(cliente);

        txt_nome_cliente.setText("");
    }

    private void UpdateClientes() {
        ArrayAdapter adapter = new ArrayAdapter<Cliente>(FazerPedido.this, android.R.layout.simple_list_item_1, clientes);
        lv_clientes.setAdapter(adapter);
    }


    private void FazerPedido() {
        Produto produto = new Produto();
        produto.nome = txt_nome_produto.getText().toString();
        produto.preco = Double.parseDouble(txt_valor_produto.getText().toString());

        Pedido pedido = new Pedido();
        pedido.produto = produto;
        pedido.clientes = clientes;

        Receiver resultReceiver = new Receiver(new Handler());

        MesaService.startPostPedido(this, idMesa, pedido, resultReceiver);

        ProgressDialog dialog = ProgressDialog.show(this, "",getString(R.string.salvando_pedido), true);

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

    private class Receiver extends ResultReceiver {

        public Receiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String idMesa = resultData.getString(MesaService.RESULT_KEY);

            IntentBundleMesa.StartActivity(FazerPedido.this, StatusMesa.class, idMesa);
        }
    }

}
