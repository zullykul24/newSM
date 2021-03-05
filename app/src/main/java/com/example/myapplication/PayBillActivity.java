package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;

public class PayBillActivity extends AppCompatActivity {
    int tableId;
    TextView staffId;
    TextView paymentId;
    TextView date_paymented;
    TextView totalPriceText, discount_money, money_paid;
    TextView tablename;
    ImageView btn_voucher;
    TextView use_voucher;
    Button btn_pay;
    int orderId;
    double total;
    double money_discount=0;
    int voucherId = 0;
    double so_tien =0;

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return this.total;
    }

    long millis = System.currentTimeMillis();
    java.sql.Date date =new java.sql.Date(millis);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paybill);
        ListView listViewPayBill;
        ArrayList<PayBillItem> payBillItemArrayList;
        PayBillItemAdapter payBillItemAdapter;
        btn_voucher = (ImageView) findViewById(R.id.btn_voucher_add);
        totalPriceText = (TextView)findViewById(R.id.total_price);
        discount_money = (TextView)findViewById(R.id.discount_total);
        money_paid = (TextView)findViewById(R.id.money_paid);
        staffId = (TextView) findViewById(R.id.idNhanVien);
        paymentId = (TextView) findViewById(R.id.paymentId);
        date_paymented = (TextView) findViewById(R.id.date_paymented);
        tablename = (TextView)  findViewById(R.id.text_paybill_table);
        btn_pay = (Button) findViewById(R.id.btn_thanh_toan);
        use_voucher = (TextView) findViewById(R.id.use_voucher);
        total = 0;
        tableId = getIntent().getIntExtra("BanTen", 1);
//        // làm paymentId trước nhá
        Cursor cursor1  = database.getData("select max(paymentid) from payments;");
        if(cursor1.getCount()>0){
            while (cursor1.moveToNext()){
                paymentId.setText(""+ (cursor1.getInt(0)+1));
            }
        }else{
            paymentId.setText("1");
        }
////        // date payment
        date_paymented.setText(""+ date);
//
//        // staffId : lấy ra tên nhân viên.
        Cursor cursor2 = database.getData("select * from account where accountId = " + getIntent().getIntExtra("accountId", 1) );
        while (cursor2.moveToNext()){
            staffId.setText(cursor2.getString(3));
        }
        listViewPayBill = (ListView)findViewById(R.id.listview_pay_bill);
        payBillItemArrayList = new ArrayList<>();

        tablename.setText("Bàn số "+ tableId);
        Cursor cursor = database.getData("select dishname, quantityOrder, price, orderdetails.orderId as oId  from orderdetails join orders  on orderdetails.orderId = orders.orderid " +
                " join dish on orderdetails.dishid = dish.dishid " +
                "where oId = (select max(orderId) from orders where tableId = "+tableId+")");
        while(cursor.moveToNext()){
            payBillItemArrayList.add(new PayBillItem(  cursor.getString(0), cursor.getInt(1), cursor.getDouble(2) ));
            orderId = cursor.getInt(3);
        }

        for(int i=0; i< payBillItemArrayList.size();i++){
        payBillItemArrayList.get(i).setSTT(i+1);
        so_tien = so_tien + payBillItemArrayList.get(i).getPriceTotal();
        }
        total = so_tien;

        payBillItemAdapter = new PayBillItemAdapter(PayBillActivity.this, R.layout.bill_item, payBillItemArrayList);
        listViewPayBill.setAdapter(payBillItemAdapter);
        btn_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(PayBillActivity.this, ViewVoucherActivity.class);
                startActivityForResult(intent, 1122);
            }
        });
        DecimalFormat df= new DecimalFormat("###,###,###");
        String priceString = String.valueOf(df.format(Double.valueOf(so_tien)));
        totalPriceText.setText(priceString);
        money_paid.setText(String.valueOf(df.format(Double.valueOf(so_tien))));
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert db vao trong co so du lieu
                    database.QueryData("insert into payments values (null, "+getIntent().getIntExtra("accountId", 1)+" , "+tableId+","+orderId+" ,null, "+total+", "+millis+", 'Paid')");
                // update lai cai ban la con trong la oke
                database.QueryData("update group_table set status = 'Empty'  where tableId = "+tableId+";");
                database.QueryData("update orders set paid = 1 where orderId = " + orderId);
                // thoat ra khoi man hinh chinh
                setResult(RESULT_OK, null);
                finish();
            }
        });
        Log.d("checkdate", ""+ date);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if (resultCode == Activity.RESULT_OK && data!= null && requestCode==1122) {
               VoucherItem item = (VoucherItem) data.getSerializableExtra("voucher");
                use_voucher.setText(item.getTitle());
                money_discount = so_tien * item.getDiscount() / 100;
                total = (so_tien -  money_discount) ;
                this.setTotal(total);
               /// định dạng tiền 1500000 -> 1,500,000
               DecimalFormat df= new DecimalFormat("###,###,###");
               String priceString = String.valueOf(df.format(Double.valueOf(total)));
               totalPriceText.setText(priceString);
               discount_money.setText(String.valueOf(df.format(Double.valueOf(money_discount))));
               money_paid.setText(String.valueOf(df.format(Double.valueOf(so_tien))));
               voucherId = item.getVoucherId();

               btn_pay.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       // insert db vao trong co so du lieu
                           database.QueryData("insert into payments values (null, "+getIntent().getIntExtra("accountId", 1)+" , "+tableId+","+orderId+" ,"+voucherId+", "+total+", "+millis+", 'Paid')");
                       // update lai cai ban la con trong la oke
                       database.QueryData("update group_table set status = 'Empty'  where tableId = "+tableId+";");
                       database.QueryData("update orders set paid = 1 where orderId = " + orderId);
                       // thoat ra khoi man hinh chinh
                       setResult(RESULT_OK, null);
                       finish();
                   }
               });
            }
    }
}
