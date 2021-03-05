package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.example.myapplication.FragmentSignIn.database;
public class HistoryActivity extends AppCompatActivity {
    ListView payedList;
    ArrayList<HistoryItem> historyItemArrayList;
    HistoryAdapter historyAdapter;
    Spinner spinner ;
    EditText fromDate, toDate;
    ArrayList<String> banID;
    ImageButton back_btn;
    int lastSelectedYear;
    int lastSelectedMonth;
    int lastSelectedDayOfMonth;
    int Exit_Signal = 1111;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        Calendar cal = Calendar.getInstance();
        lastSelectedYear = cal.get(Calendar.YEAR);
        lastSelectedDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        // vì là trong Java thì tháng nó sẽ lấy từ 0 tới 11 nên là  phải cộng thêm 1 để lấy đúng với giá trị hiện tại
        lastSelectedMonth = cal.get(Calendar.MONTH);
        back_btn = (ImageButton)findViewById(R.id.history_back_btn);
        historyItemArrayList = new ArrayList<>();
        payedList = (ListView) findViewById(R.id.list_history_item);
        spinner = (Spinner) findViewById(R.id.select_table_paymented);
        fromDate = (EditText) findViewById(R.id.from_date);
        toDate = (EditText)findViewById(R.id.to_date);


        //back to main
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* Cursor cursor = database.getData("select * from payments order by paymentId desc");
        while (cursor.moveToNext()) {
            historyItemArrayList.add(new HistoryItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getDouble(5),
                    cursor.getLong(6),
                    cursor.getString(7)
            ));
            historyAdapter = new HistoryAdapter(HistoryActivity.this, R.layout.paymented_item, historyItemArrayList);
            Cursor cursor1 = database.getData("SELECT DISTINCT (tableId) from payments  order by tableId asc");
            banID = new ArrayList<>();
            banID.add("Tất cả bàn");
            while (cursor1.moveToNext()){
                banID.add("Bàn "+ cursor1.getInt(0));
            }
            // gan source cho spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,
                    R.layout.spinner_item,
                    banID
            );
            //phai goi lenh nay de hien thi danh sach cho spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // thiet lap adapter
            spinner.setAdapter(adapter);
            long millis = System.currentTimeMillis();
            java.sql.Date dateNow = new java.sql.Date(millis);
            fromDate.setText(String.valueOf(dateNow ));
            toDate.setText(String.valueOf(dateNow));
            fromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            fromDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            lastSelectedYear = year;
                            lastSelectedMonth = month;
                            lastSelectedDayOfMonth = dayOfMonth;

                        }
                    };
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(HistoryActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                            dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
                    datePickerDialog.show();
                }
            });
            toDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            toDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            lastSelectedYear = year;
                            lastSelectedMonth = month;
                            lastSelectedDayOfMonth = dayOfMonth;

                        }
                    };
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(HistoryActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                            dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
                    datePickerDialog.show();
                }
            });

            payedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HistoryActivity.this, ViewPaymentActivity.class );
                    intent.putExtra("paymentId", historyItemArrayList.get(position).getPaymentId());
                    startActivity(intent);
                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        historyAdapter.getFilter(spinner.getSelectedItem().toString(), fromDate.getText().toString(), toDate.getText().toString()).filter(Integer.toString(position));
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            fromDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        historyAdapter.getFilter(spinner.getSelectedItem().toString(),s.toString(), toDate.getText().toString()).filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                        historyAdapter.getFilter(spinner.getSelectedItem().toString(),s.toString(), toDate.getText().toString()).filter(s);
                }
            });
            toDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        historyAdapter.getFilter(spinner.getSelectedItem().toString(), fromDate.getText().toString(), s.toString()).filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                        historyAdapter.getFilter(spinner.getSelectedItem().toString(), fromDate.getText().toString(), s.toString()).filter(s);
                }
            });
            historyAdapter.notifyDataSetChanged();
            payedList.setAdapter(historyAdapter);
        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1111 & resultCode == -1){
            finish();
        }
    }
}