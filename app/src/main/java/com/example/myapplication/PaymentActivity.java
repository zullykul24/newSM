package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;

public class PaymentActivity extends AppCompatActivity {
    int REQUEST_EXIT = 2818;
    ListView listViewPaymentTable;
    ImageButton backToHomeBtn;
    ArrayList<PaymentTableItem> paymentTableItemArrayList;
    PaymentTableItemAdapter paymentTableItemAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        paymentTableItemArrayList = new ArrayList<>();
        listViewPaymentTable = findViewById(R.id.list_payment_item);
        Load();

        backToHomeBtn = (ImageButton)findViewById(R.id.payment_backtohome_btn);
        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(PaymentActivity.this, MainActivity.class);
                setResult(65, intentToHome);
                finish();
            }
        });
        listViewPaymentTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToPayBill = new Intent(PaymentActivity.this, PayBillActivity.class);
                intentToPayBill.putExtra("BanTen", paymentTableItemArrayList.get(position).getTableName());
                intentToPayBill.putExtra("accountId", getIntent().getIntExtra("accountId", 1));
                startActivityForResult(intentToPayBill, REQUEST_EXIT);
            }
        });
    }
    // lấy tên các bàn mà đang được sử dụng
    private void Load() {
        paymentTableItemArrayList.clear();
       /* Cursor cursor = database.getData("select * from orders where paid = '0' order by orderId desc");
        while (cursor.moveToNext()){
            paymentTableItemArrayList.add(new PaymentTableItem( cursor.getInt(1)));
        }*/
        paymentTableItemAdapter = new PaymentTableItemAdapter(PaymentActivity.this,R.layout.payment_item,paymentTableItemArrayList);
        listViewPaymentTable.setAdapter(paymentTableItemAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                Load();
            }
        }
    }
}
