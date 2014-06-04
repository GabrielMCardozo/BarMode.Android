package com.barmode.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.IntentBundleMesa;
import com.barmode.app.Model.Mesa;
import com.barmode.app.R;
import com.barmode.app.Service.MesaService;


public class AbrirMesa extends ActionBarActivity {

    private EditText txt_nome_mesa;
    private EditText txt_senha_mesa;
    private Button btn_entrar_mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_abrir_mesa);

        txt_nome_mesa = (EditText)findViewById(R.id.txt_nome_mesa);
        txt_senha_mesa = (EditText)findViewById(R.id.txt_senha_mesa);

        btn_entrar_mesa = (Button)findViewById(R.id.btn_entrar_mesa);
        btn_entrar_mesa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EntrarMesa();
            }
        });
    }

    public void EntrarMesa(){

        Mesa mesa = new Mesa();
        mesa.nome = txt_nome_mesa.getText().toString().trim();
        mesa.senha = txt_senha_mesa.getText().toString().trim();

        Receiver receiver = new Receiver(new Handler());

        MesaService.startPutMesa(this,mesa,receiver);

        btn_entrar_mesa.setClickable(false);
    }

    private class Receiver extends ResultReceiver {

        public Receiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            btn_entrar_mesa.setClickable(true);

            Mesa mesa= (Mesa)resultData.getSerializable(MesaService.RESULT_KEY);

            Toast toast = Toast.makeText(AbrirMesa.this,"Criado: mesa "+ mesa.toString(),Toast.LENGTH_LONG);
            toast.show();

           /* Bundle bundle = new Bundle();
            bundle.putSerializable("Mesa", mesa);

            Intent intent = new Intent(AbrirMesa.this,StatusMesa.class);
            intent.putExtra("Bundle",bundle);

            startActivity(intent);*/

            IntentBundleMesa.StartActivity(AbrirMesa.this,StatusMesa.class,mesa);
        }
    }
}
