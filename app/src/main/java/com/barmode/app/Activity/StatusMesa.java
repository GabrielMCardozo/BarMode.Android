package com.barmode.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.IntentBundleMesa;
import com.barmode.app.Model.Mesa;
import com.barmode.app.R;
import com.barmode.app.Service.MesaService;

public class StatusMesa extends ActionBarActivity {

    private TextView tv_titulo_mesa;
    private Mesa activity_mesa;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_status_mesa);

        tv_titulo_mesa = (TextView) findViewById(R.id.tv_titulo_mesa);

        loadMesa();
    }

    private void loadMesa() {

        Mesa mesa = IntentBundleMesa.GetBundleMesa(this);

        if( mesa.id == null) {
            Receiver receiver = new Receiver(new Handler());
            MesaService.startPutMesa(this, mesa, receiver);

            dialog = ProgressDialog.show(this, "", getString(R.string.carregando_mesa), true);
        }else{
            activity_mesa = mesa;
            loadComponents();
        }
    }

    private void loadComponents() {

        tv_titulo_mesa.setText("Nome:" + activity_mesa.nome + " Senha: " + activity_mesa.senha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.status_mesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_fazer_pedido) {
            IntentBundleMesa.StartActivity(this, FazerPedido.class, activity_mesa.id);
        }
        return super.onOptionsItemSelected(item);
    }

    private class Receiver extends ResultReceiver {

        public Receiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            activity_mesa = (Mesa) resultData.getSerializable(MesaService.RESULT_KEY);
            dialog.hide();
            loadComponents();
        }
    }

}
