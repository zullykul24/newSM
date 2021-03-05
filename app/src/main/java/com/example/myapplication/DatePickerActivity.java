package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.myapplication.FragmentSignIn.database;

public class DatePickerActivity extends AppCompatActivity {

    EditText startDate;
    EditText endDate;
    EditText codeVoucher;
    Button btn_ok;
    Button btn_cancel;
    int lastSelectedYear  ;
    int lastSelectedDayOfMonth;
    int lastSelectedMonth;
    int voucherID;
    long startDateTime;
    long endDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        Calendar cal = Calendar.getInstance();
        lastSelectedYear = cal.get(Calendar.YEAR);
        lastSelectedDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        // vì là trong Java thì tháng nó sẽ lấy từ ) tới 11 nên là  phải cộng thêm 1 để lấy đúng với giá trị hiện tại
        lastSelectedMonth = cal.get(Calendar.MONTH);

        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        codeVoucher = (EditText) findViewById(R.id.add_code_voucher);
        btn_ok = (Button) findViewById(R.id.btn_oke_date);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        lastSelectedYear = year;
                        lastSelectedMonth = month;
                        lastSelectedDayOfMonth = dayOfMonth;

                    }
                };
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(DatePickerActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
                datePickerDialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        lastSelectedYear = year;
                        lastSelectedMonth = month;
                        lastSelectedDayOfMonth = dayOfMonth;

                    }
                };
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(DatePickerActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
                datePickerDialog.show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
               finish();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check ddax
                if(isEmpty(codeVoucher) == true || isEmpty(startDate) == true || isEmpty(endDate) == true)
                {
                    Toast.makeText(DatePickerActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    voucherID = getIntent().getIntExtra("voucherID", 1);
                    // insert nốt dữ liệu vào db
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        startDateTime = simpleDateFormat.parse(startDate.getText().toString()).getTime();
                        endDateTime = simpleDateFormat.parse(endDate.getText().toString()).getTime();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (startDateTime> endDateTime){
                        Toast.makeText(DatePickerActivity.this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
                    }else{
                        database.QueryData("update vouchers set vouchercode = '"+codeVoucher.getText().toString()+"',  startdate ="+startDateTime+" , enddate = "+endDateTime+" WHERE voucherid = "+voucherID+";");
                        setResult(RESULT_OK, null);
                        finish();
                    }

                }

            }
        });
    }
    private boolean isEmpty(EditText editText){
        if(editText.getText().toString().trim().length() > 0){
            return false;
        }
        return true;
    }
}