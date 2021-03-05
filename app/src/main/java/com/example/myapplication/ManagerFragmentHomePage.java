package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import static com.example.myapplication.FragmentSignIn.database;

public class ManagerFragmentHomePage extends Fragment {
    @Nullable

    ImageButton monmoi;
    ImageButton addFood;
    ImageButton addTable;
    ImageButton payment;
    ImageButton voucher;
    ImageButton addVoucher;
    ImageButton history, menu;
    BottomNavigationView navbar;
    int accountId;
    public void getInfor(int accountId) {
        this.accountId = accountId;
    }
    public  Integer getAccountId(){
        return this.accountId;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.manager_fragment_homepage, container, false);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        final FragmentManager fragmentManager = getFragmentManager();
        addFood = (ImageButton) rootView.findViewById(R.id.manager_add_food_btn);
        addTable = (ImageButton) rootView.findViewById(R.id.manager_add_table_btn);
        monmoi = (ImageButton) rootView.findViewById(R.id.manage_new_food);
        payment = (ImageButton) rootView.findViewById(R.id.manager_payment);
        addVoucher = (ImageButton) rootView.findViewById(R.id.add_voucher);
        voucher = (ImageButton) rootView.findViewById(R.id.voucher);
        history = (ImageButton) rootView.findViewById(R.id.history_btn);
        menu = (ImageButton) rootView.findViewById(R.id.menu);
        navbar = (BottomNavigationView) getActivity().findViewById(R.id.navbar);


        //center crop image btns
        Glide.with(this).load(R.drawable.new_food).circleCrop().into(monmoi);
        Glide.with(this).load(R.drawable.new_table2).circleCrop().into(addTable);
        Glide.with(this).load(R.drawable.new_offer).circleCrop().into(addVoucher);
        Glide.with(this).load(R.drawable.history).circleCrop().into(history);
        Glide.with(this).load(R.drawable.food_menu).circleCrop().into(menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .replace(R.id.fragment_container,new FragmentMenu(), "3")
                        .addToBackStack(null)

                        .commit();

                navbar.getMenu().findItem(R.id.nav_3).setChecked(true);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });
        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewVoucherActivity.class));
            }
        });
        addVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddVoucherActivity.class));
            }
        });
        monmoi.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent = new Intent(getActivity(), ActivityNewFood.class);
                                          startActivity(intent);
                                      }
                                  });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPayment = new Intent(getActivity().getApplicationContext(), PaymentActivity.class);
                intentToPayment.putExtra("accountId", getAccountId());
                Log.d("check", getAccountId()+" ");
                startActivityForResult(intentToPayment, 65);
            }
        });
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFood = new Intent(getActivity(), AddFood.class);
                startActivity(intentFood);
            }
        });
        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInsertTable();
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.manager_recycle_view_hot_items);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        ArrayList<MenuFoodItem> hotArrayList = new ArrayList<>();
       /* Cursor cursor = database.getData("SELECT * from dish where dishId in (Select dishId from orderdetails group by dishId order by sum(orderdetails.quantityOrder) desc) limit  5 ");
        while (cursor.moveToNext()) {
            hotArrayList.add(new MenuFoodItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getBlob(4)));
        }*/
        RecycleItemAdapter adapter = new RecycleItemAdapter(hotArrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

            private void DialogInsertTable() {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_table);
                // Ánh xạ xong r nè

                Button add = (Button) dialog.findViewById(R.id.button_ok);
                Button cancel = (Button) dialog.findViewById(R.id.button_cancel);
                final EditText number_chair = (EditText) dialog.findViewById(R.id.edit_chair_number);

                // sử dụng các button

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!number_chair.getText().toString().equals("")) {
                            int num = Integer.parseInt(number_chair.getText().toString()); // lay du lieu trong cai nay nay
                            if (num <= 0 || num > 100) {
                                Toast.makeText(getContext(), "Không hợp lệ", Toast.LENGTH_SHORT).show();
                            } else {
                               // database.QueryData("insert into group_table values (null, " + num + ", 'Empty')");
                                dialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getContext(), "Vui lòng điền số lượng ghế", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }
