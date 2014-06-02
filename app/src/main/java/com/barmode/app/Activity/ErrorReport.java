package com.barmode.app.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.barmode.app.R;

public class ErrorReport extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_report);


        TextView error = (TextView) findViewById(R.id.error);
        error.setText(getIntent().getStringExtra("error"));
    }
}
