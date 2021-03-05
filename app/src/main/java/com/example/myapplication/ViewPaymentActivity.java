package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;
public class ViewPaymentActivity extends AppCompatActivity {
    TextView name_employee, date_time_paid, paid_id, discount_name, discount_number, total_pay;
    ListView listViewPaid;
    ImageButton btn_back;
    int paymentId ;
    long date;
    int orderId;
    int count;
    PayBillItemAdapter payBillItemAdapter;
    ArrayList<PayBillItem> payBillItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);
        paymentId = getIntent().getIntExtra("paymentId", 1);
        name_employee = (TextView)  findViewById(R.id.nhanVienid);
        date_time_paid = (TextView)  findViewById(R.id.date_time);
        paid_id = (TextView)  findViewById(R.id.number_paid_id);
        discount_name = (TextView)  findViewById(R.id.discount_ma_code);
        discount_number = (TextView)  findViewById(R.id.so_giam);
        total_pay = (TextView)  findViewById(R.id.so_tien_paid);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        listViewPaid = (ListView) findViewById(R.id.list_view_paid) ;
        count = 1;
        paid_id.setText(""+ paymentId);
        Cursor cursor = database.getData("select account.userName, payments.date, vouchers.voucherCode, vouchers.discount, payments.amount,payments.orderId  from payments join account on account.accountId = payments.accountId" +
                " join vouchers on vouchers.voucherId = payments.discountId "+
                " where payments.paymentId = " + paymentId );
        while (cursor.moveToNext()){
            name_employee.setText("" + cursor.getString(0));
            date= cursor.getLong(1);
            discount_name.setText("" + cursor.getString(2));
            discount_number.setText(" -"+cursor.getInt(3)+ "%");
            total_pay.setText(cursor.getDouble(4)+"đồng");
            orderId = cursor.getInt(5);
        }
        java.sql.Date dateTime = new java.sql.Date(date);
        date_time_paid.setText(""+dateTime);
        payBillItems = new ArrayList<>();

        Cursor cursor1 = database.getData("Select dish.dishName, orderdetails.quantityOrder, dish.price  from orderdetails join dish on orderdetails.dishId = dish.dishId  where orderdetails.orderId = "+ orderId);
        while(cursor1.moveToNext()){
            payBillItems.add(new PayBillItem(
                    count,
                    cursor1.getString(0),
                    cursor1.getInt(1),
                    cursor1.getDouble(2)
            ));
            count++;
        }


       payBillItemAdapter = new PayBillItemAdapter(ViewPaymentActivity.this, R.layout.bill_item,payBillItems);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        payBillItemAdapter.notifyDataSetChanged();
        listViewPaid.setAdapter(payBillItemAdapter);

    }
}