package com.barmode.app.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.barmode.app.HttpHelper;
import com.barmode.app.Model.Mesa;
import com.barmode.app.Model.Pedido;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;

public class MesaService extends IntentService {

    private static final String ACTION_GET_MESA = "com.barmode.app.Service.action.get.mesa";
    private static final String ACTION_PUT_MESA = "com.barmode.app.Service.action.put.mesa";
    private static final String ACTION_POST_PEDIDO = "com.barmode.app.Service.action.post.pedido";

    private static final String EXTRA_ID_MESA = "com.barmode.app.Service.extra.id.mesa";
    private static final String EXTRA_MESA = "com.barmode.app.Service.extra.mesa";
    private static final String EXTRA_PEDIDO = "com.barmode.app.Service.extra.pedido";
    public static final String EXTRA_RESULT_RECEIVER = "com.barmode.app.Service.extra.result.receiver";

    private static final String MESA_URL = "http://barmode.apphb.com/api/mesa";
    public static final String RESULT_KEY = "result.receiver.key";

    public static void startGetMesa(Context context, String idMesa, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, MesaService.class);
        intent.setAction(ACTION_GET_MESA);
        intent.putExtra(EXTRA_ID_MESA, idMesa);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    public static void startPutMesa(Context context, Mesa mesa, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, MesaService.class);
        intent.setAction(ACTION_PUT_MESA);
        intent.putExtra(EXTRA_MESA, mesa);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    public static void startPostPedido(Context context, String idMesa, Pedido pedido, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, MesaService.class);
        intent.setAction(ACTION_POST_PEDIDO);
        intent.putExtra(EXTRA_ID_MESA, idMesa);
        intent.putExtra(EXTRA_PEDIDO, pedido);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }


    public MesaService() {
        super("MesaService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);

            if (ACTION_GET_MESA.equals(action)) {
                final String idMesa = intent.getStringExtra(EXTRA_ID_MESA);
                handleActionGetMesa(idMesa, resultReceiver);
            }

            if (ACTION_PUT_MESA.equals(action)) {
                final Mesa mesa = (Mesa) intent.getSerializableExtra(EXTRA_MESA);
                handleActionPutMesa(mesa, resultReceiver);
            }

            if (ACTION_POST_PEDIDO.equals(action)) {
                final Pedido pedido = (Pedido) intent.getSerializableExtra(EXTRA_PEDIDO);
                final String idMesa = intent.getStringExtra(EXTRA_ID_MESA);

                handleActionPostPedido(idMesa, pedido, resultReceiver);
            }

        }
    }

    private void handleActionPostPedido(String idMesa, Pedido pedido, ResultReceiver resultReceiver) {
        String jsonPedido = new Gson().toJson(pedido);

        String urlPedido = String.format("%s/%s/pedido", MESA_URL, idMesa);

        HttpHelper.postStream(urlPedido, jsonPedido);

        Bundle bundle = new Bundle();
        bundle.putSerializable(RESULT_KEY, idMesa);

        resultReceiver.send(0, bundle);
    }

    private void handleActionGetMesa(String idMesa, ResultReceiver resultReceiver) {

        String url = MESA_URL + "/" + idMesa;

        Reader reader = new InputStreamReader(HttpHelper.retrieveStream(url));

        Mesa mesa = new Gson().fromJson(reader, new TypeToken<Mesa>() {
        }.getType());

        Bundle bundle = new Bundle();
        bundle.putSerializable(RESULT_KEY, mesa);

        resultReceiver.send(0, bundle);
    }

    private void handleActionPutMesa(Mesa mesa, ResultReceiver resultReceiver) {

        String jsonNewTable = new Gson().toJson(mesa);

        Reader reader = new InputStreamReader(HttpHelper.putStream(MESA_URL, jsonNewTable));

        Mesa receivedMesa = new Gson().fromJson(reader, Mesa.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(RESULT_KEY, receivedMesa);

        resultReceiver.send(0, bundle);
    }
}
