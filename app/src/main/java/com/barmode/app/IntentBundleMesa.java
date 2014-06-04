package com.barmode.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.barmode.app.Model.Mesa;

/**
 * Created by Gabriel on 04/06/2014.
 */
public class IntentBundleMesa {

    public static void StartActivity(Context context,Class someClass, Mesa mesa) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Mesa", mesa);

        Intent intent = new Intent(context, someClass);
        intent.putExtra("Bundle", bundle);

        context.startActivity(intent);
    }

    public static Mesa GetBundleMesa(Activity activity){
        Intent intent = activity.getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");

        Mesa mesa =(Mesa) bundle.getSerializable("Mesa");

        return mesa;
    }
}
