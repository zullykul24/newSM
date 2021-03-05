package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;

import static com.example.myapplication.FragmentSignIn.database;

public class FragmentMenu extends Fragment {


    MenuFoodItemAdapter menuFoodItemAdapter;
    ArrayList<MenuFoodItem> menuItemArrayList;
    ImageButton btnAdd;
    EditText searchText;
    ListView lvFood, listViewFood;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        //khai bao
        searchText = (EditText)rootView.findViewById(R.id.searchText);

        listViewFood = (ListView)rootView.findViewById(R.id.listViewFoodMenu);
        menuItemArrayList = new ArrayList<>();
        menuFoodItemAdapter =  new MenuFoodItemAdapter(getContext(),R.layout.menu_food_item, menuItemArrayList);
        // đổ dữ liệu trong database ra ne
        listViewFood.setAdapter(menuFoodItemAdapter);
     /*   Cursor cursor = database.getData("SELECT * from dish order by dishName asc");
        while (cursor.moveToNext()) {
            menuItemArrayList.add(new MenuFoodItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getBlob(4)
            ));
        }*/


        listViewFood.setTextFilterEnabled(true);

        searchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                menuFoodItemAdapter.getFilter().filter(arg0);


            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            public void afterTextChanged(Editable arg0) {
                menuFoodItemAdapter.getFilter().filter(arg0);

            }
        });
        menuFoodItemAdapter.notifyDataSetChanged();
        return rootView;
    }
}
