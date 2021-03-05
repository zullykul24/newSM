package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;

public class FragmentTableOrder extends Fragment {
    TextView tableTitle;
    GridView gridViewTable;
    TableItemAdapter tableItemAdapter;
    int accoutID ;
    public void getInfor(int accoutID){
        this.accoutID = accoutID;
    }
    public Integer getAccountID (){
        return this.accoutID;
    }
    ArrayList<TableItem> tableItemArrayList;
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.set_table_status, menu);
        menu.setHeaderTitle("Đặt trạng thái");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.status_booked:
                if (tableItemArrayList.get(info.position).getStatus().equals("Empty")) {
                    database.QueryData("update group_table set status = 'Booked'  where tableId = " + tableItemArrayList.get(info.position).getId() + ";");
                    Toast.makeText(getActivity().getApplicationContext(), "Đã đặt", Toast.LENGTH_SHORT).show();
                    tableItemAdapter.notifyDataSetChanged();
                    ///reload fragment
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,new FragmentTableOrder()).addToBackStack(null).commit();
                    return true;
                }
                else return false;
                // ??? : sau nay se phai sua cai nayf
            case R.id.status_empty:
                if (tableItemArrayList.get(info.position).getStatus().equals("Booked")) {
                    database.QueryData("update group_table set status = 'Empty'  where tableId = " + tableItemArrayList.get(info.position).getId() + ";");
                    tableItemAdapter.notifyDataSetChanged();
                    ///reload fragment
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,new FragmentTableOrder()).addToBackStack(null).commit();
                    return true;
                }
                else return false;
            default:
                return super.onContextItemSelected(item);
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tableorder, container,false);
        tableTitle = rootView.findViewById(R.id.textViewTableTitle);
        //tableTitle.setText("#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorBookedTable)));



        gridViewTable = (GridView) rootView.findViewById(R.id.gridViewTable);
        tableItemArrayList = new ArrayList<>();
         /////Edit
     /*  Cursor  cursor = database.getData("SELECT * from group_table");
        while  (cursor.moveToNext()){
            tableItemArrayList.add( new TableItem(
                    cursor.getInt(0),
                    "Bàn số "+cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2)
                    )
            );
        }*/

        for(TableItem i:tableItemArrayList){
            if(i.getStatus().equals("Not Empty")){
                i.setColor("#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorFilledTable)));
            }
            else if(i.getStatus().equals("Booked")){
                i.setColor("#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorBookedTable)));
            }
            else
            {
                i.setColor("#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorEmptyTable)));
            }
        }
        tableItemAdapter = new TableItemAdapter(getContext(), R.layout.table_item,tableItemArrayList );
        tableItemAdapter.notifyDataSetChanged();
        gridViewTable.setAdapter(tableItemAdapter);
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity().getApplicationContext(), tableItemArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                String status = tableItemArrayList.get(position).getStatus();
                // chỉnh sửa chỗ này để trạng thái nào cũng có thể vào chỉnh sửa món ăn
                //if(status.equals("Empty")) {
                    Intent intentToOrder = new Intent(getActivity(), OrderActivity.class);
                    intentToOrder.putExtra("Tên bàn", tableItemArrayList.get(position).getName());
                    intentToOrder.putExtra("Bàn id", tableItemArrayList.get(position).getId());
                    intentToOrder.putExtra("Bàn Status", tableItemArrayList.get(position).getStatus());
                    intentToOrder.putExtra("AccountID",  getAccountID());
                    startActivityForResult(intentToOrder, 114);
               // }
            }
        });
        registerForContextMenu(gridViewTable);
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==114&& resultCode == 291){
            for(TableItem i:tableItemArrayList){
                if(i.getId() == data.getIntExtra("banId", 1)){
                    i.setColor("#"+Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorFilledTable)));
                }
            }
            ///reload fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,new FragmentTableOrder()).addToBackStack(null).commit();
        }
            }
}
