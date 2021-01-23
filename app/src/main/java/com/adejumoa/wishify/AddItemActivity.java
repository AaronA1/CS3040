package com.adejumoa.wishify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

}