package com.barmode.app.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.barmode.app.ExceptionHandler;
import com.barmode.app.IntentBundleMesa;
import com.barmode.app.Model.Mesa;
import com.barmode.app.R;


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

        IntentBundleMesa.StartActivity(AbrirMesa.this,StatusMesa.class, mesa);
    }


}
