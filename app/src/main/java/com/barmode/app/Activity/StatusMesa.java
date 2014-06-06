package com.barmode.app.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.IntentBundleMesa;
import com.barmode.app.Model.Mesa;
import com.barmode.app.R;

public class StatusMesa extends ActionBarActivity {

    private TextView tv_titulo_mesa;
    private Mesa activity_mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_status_mesa);

        tv_titulo_mesa = (TextView) findViewById(R.id.tv_titulo_mesa);

        LoadMesa();

    }

    private void LoadMesa() {
        Mesa mesa = IntentBundleMesa.GetBundleMesa(this);



        SetMesa(mesa);
    }

    private void SetMesa(Mesa mesa) {
        activity_mesa = mesa;

        tv_titulo_mesa.setText("Nome:" + mesa.nome + " Senha: " + mesa.senha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status_mesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_fazer_pedido) {
            IntentBundleMesa.StartActivity(this, FazerPedido.class,activity_mesa.id);
        }
        return super.onOptionsItemSelected(item);
    }


}
