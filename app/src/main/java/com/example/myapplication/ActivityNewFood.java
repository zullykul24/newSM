package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.FragmentSignIn.database;

public class ActivityNewFood extends AppCompatActivity {
        ListView listview ;
        ImageButton backToMain;
        ArrayList<MenuFoodItem> arrayNewFood;
        MenuFoodItemAdapter menuFoodItemAdapter;
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food2);
        listview = (ListView) findViewById(R.id.new_food);
        backToMain = (ImageButton) findViewById(R.id.back_to_main_from_newest_foods);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrayNewFood = new ArrayList();
        //Cursor cursor = database.getData("select * from dish ");
       /* Cursor cursor = database.getData("select * from dish where strftime('%d', 'now') - strftime('%d', date) < 30 order by dishId DESC;");
        while (cursor.moveToNext()){
            arrayNewFood.add(new MenuFoodItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getBlob(4)

            ));

        }*/

        menuFoodItemAdapter = new MenuFoodItemAdapter(ActivityNewFood.this, R.layout.menu_food_item, arrayNewFood);
        listview.setAdapter(menuFoodItemAdapter);
    }
}