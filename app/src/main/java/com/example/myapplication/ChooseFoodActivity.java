package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;

public class ChooseFoodActivity extends AppCompatActivity {
    ListView listViewFood;
    ImageButton backToOrderActivity;
    MenuFoodItemAdapter menuFoodItemAdapter;
    ArrayList<MenuFoodItem> menuItemArrayList;
    EditText searchText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_foods);
        searchText = (EditText)findViewById(R.id.searchText);
        backToOrderActivity = (ImageButton)findViewById(R.id.back_to_order_activity);

        listViewFood = (ListView)findViewById(R.id.listViewFoodMenu);
        menuItemArrayList = new ArrayList<>();
        Cursor cursor = database.getData("SELECT * from dish order by dishName asc");
        while (cursor.moveToNext()){
            menuItemArrayList.add(new MenuFoodItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getBlob(4)

            ));
        }
        menuFoodItemAdapter = new MenuFoodItemAdapter(ChooseFoodActivity.this, R.layout.menu_food_item, menuItemArrayList);

        listViewFood.setTextFilterEnabled(true);

        searchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                ChooseFoodActivity.this.menuFoodItemAdapter.getFilter().filter(arg0);


            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            public void afterTextChanged(Editable arg0) {
                ChooseFoodActivity.this.menuFoodItemAdapter.getFilter().filter(arg0);

            }
        });

        listViewFood.setAdapter(menuFoodItemAdapter);

        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSendFoodToOrderActivity = new Intent(ChooseFoodActivity.this, OrderActivity.class);
                MenuFoodItem item = new MenuFoodItem(menuItemArrayList.get(position).getDish_id(), menuItemArrayList.get(position).getDish_name(), menuItemArrayList.get(position).getGroup_id(),menuItemArrayList.get(position).getPrice(), menuItemArrayList.get(position).getImage());
                intentSendFoodToOrderActivity.putExtra("abc", (Serializable) item);
                setResult(Activity.RESULT_OK, intentSendFoodToOrderActivity);
                finish();
            }
        });
        backToOrderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackToOrderActivity = new Intent(ChooseFoodActivity.this, OrderActivity.class);
                setResult(999, intentBackToOrderActivity);
                finish();
            }
        });


    }
}
