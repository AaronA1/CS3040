package com.adejumoa.wishify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    private TextInputLayout itemName;
    private TextInputLayout itemDescription;
    private TextInputLayout itemPrice;
    private AutoCompleteTextView ac_category;

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
        ac_category = findViewById(R.id.ac_category);

        String[] categories = new String[] {"Bicycle", "Book", "Clothing", "Electronics", "Flowers", "Jewellery", "Other"};

        ArrayAdapter<String> adapterNew = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        ac_category.setAdapter(adapterNew);

        findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(Objects.requireNonNull(itemName.getEditText()).getText())) {
                    itemName.setError("You must enter an item name");
                } else {
                    String name = itemName.getEditText().getText().toString();
                    String desc = itemDescription.getEditText().getText().toString();
                    double price = 0.00;
                    if (!itemPrice.getEditText().getText().toString().equals("")) {
                        price = Double.parseDouble(itemPrice.getEditText().getText().toString());
                    }
                    String category = ac_category.getText().toString();
                    Item item = new Item(name, desc, price, category);
                    replyIntent.putExtra("Item", item);
//                    replyIntent.putExtra(EXTRA_REPLY, name);
//                    replyIntent.putExtra(EXTRA_REPLY_2, desc);
//                    replyIntent.putExtra(EXTRA_REPLY_3, price);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

    }

}