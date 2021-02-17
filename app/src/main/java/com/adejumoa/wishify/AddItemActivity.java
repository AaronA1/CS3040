package com.adejumoa.wishify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class AddItemActivity extends AppCompatActivity {

    private TextInputLayout itemName;
    private TextInputLayout itemDescription;
    private TextInputLayout itemPrice;
    private Spinner itemCategory;

    public static final String EXTRA_REPLY =
            "com.example.android.roomitem.REPLY";
    public static final String EXTRA_REPLY_2 =
            "com.example.android.roomitem2.REPLY";
    public static final String EXTRA_REPLY_3 =
            "com.example.android.roomitem3.REPLY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.item_name);
        itemDescription = findViewById(R.id.item_desc);
        itemPrice = findViewById(R.id.item_price);
        itemCategory = findViewById(R.id.spinner_category);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        itemCategory.setAdapter(adapter);

        findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(itemName.getEditText().getText())) {
                    itemName.setError("You must enter an item name");
                } else {
                    String name = itemName.getEditText().getText().toString();
                    String desc = itemDescription.getEditText().getText().toString();
                    double price = Double.parseDouble(itemPrice.getEditText().getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY, name);
                    replyIntent.putExtra(EXTRA_REPLY_2, desc);
                    replyIntent.putExtra(EXTRA_REPLY_3, price);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

    }

}