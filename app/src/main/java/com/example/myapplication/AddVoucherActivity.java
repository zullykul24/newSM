package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;
import static java.security.AccessController.getContext;

public class AddVoucherActivity extends AppCompatActivity {
    ImageButton btnAddConditions;
    EditText title ;
    EditText discount;
    Button onCreate;
    Button onCancel;
    ImageButton backToMain;
    LinearLayout linearLayout;
    int hint =0;
    int voucherID;
    int REQUEST_EXIT = 1234;
    ArrayList<String> conditions =  new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);
        linearLayout = (LinearLayout) findViewById(R.id.conditions);
        btnAddConditions = (ImageButton) findViewById(R.id.btn_add_conditions);
        title = (EditText) findViewById(R.id.title_voucher);
        discount = (EditText) findViewById(R.id.discount);
        backToMain = (ImageButton)findViewById(R.id.back_to_main_from_voucher);
        onCancel = (Button) findViewById(R.id.btn_cancel_create_voucher);
        onCreate = (Button) findViewById(R.id.btn_ok_create_voucher);
        onCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(isEmpty(title)==true || isEmpty(discount) == true){
                        Toast.makeText(AddVoucherActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else {
                        if(Integer.parseInt(discount.getText().toString()) > 100)
                        {
                            Toast.makeText(AddVoucherActivity.this, "Discount không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            for (int i = 0; i< linearLayout.getChildCount(); i++){
                                EditText editText = (EditText) linearLayout.getChildAt(i);
                                if(!isEmpty(editText)) {
                                    conditions.add(editText.getText().toString());
                                }
                            }
                            if (conditions.size() == 0){
                                DialogMakeSure();
                            }
                            else{
                                addVoucher();

                            }
                        }


                    }
            }
        });
        btnAddConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo nhiều nhất là 3 điều kiện thôi
                if(hint< 4) {
                    addTextConditions();
                }

            }
        });

    }
    private void addVoucher(){
        /*database.QueryData("insert into vouchers (voucherId, title, discount) values (null, '"+title.getText().toString()+"', '"+discount.getText().toString()+"');");
        Cursor cursor  = database.getData("select max(voucherid) from vouchers;");
        while(cursor.moveToNext()){
            voucherID = cursor.getInt(0);
        }
        if(conditions.size()>0){
            for( String item : conditions){
                database.QueryData("insert into conditions values ("+voucherID+", '"+item+"')");
            }
        }*/
        Intent intent = new Intent(AddVoucherActivity.this, DatePickerActivity.class);
        intent.putExtra("voucherID", voucherID);
        startActivityForResult(intent, REQUEST_EXIT);
    }
    private void DialogMakeSure(){
        final Dialog dialog = new Dialog(AddVoucherActivity.this);
        dialog.setContentView(R.layout.dialogmakesure);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok_not_condition);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel_not_conditions);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVoucher();
                //okeNotCondition = true;
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //okeNotCondition = false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private boolean isEmpty(EditText editText){
        if(editText.getText().toString().trim().length() > 0){
            return false;
        }
        return true;
    }
    private void addTextConditions() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(200,5,0,5);
        EditText edittTxt = new EditText(this);
        hint++;
        edittTxt.setHint("Điều kiện "+hint);
        edittTxt.setLayoutParams(params);
        edittTxt.setId(hint);
        linearLayout.addView(edittTxt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }
}