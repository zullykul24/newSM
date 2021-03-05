package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSignIn extends Fragment {
    public static Database database;
    Button logInBtn;
    CheckBox togglePassword;
    EditText  username, password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /*
        // create a new database sqlite
        database = new Database(getContext(), "ex4.sqlite", null,1);
        // create table account : Detail about Account
        database.QueryData("create table if not exists account (accountId integer primary key AUTOINCREMENT, phoneNumber VARCHAR(200) not null, account_type varchar(20) not null, userName varchar(50) not null unique, password varchar(20)  not null) " );
        // create table dish : Detail about dish
        database.QueryData("create table if not exists dish (dishId integer primary key AUTOINCREMENT, dishName varchar(200) not null, group_id integer not null, price double, image varchar(200), date datetime)");//database.QueryData("Insert into  staff_group values (0, 'Staff'), (1, 'Management')");
        // create table group_table : Thông tin về Group_table
        database.QueryData("create table if not exists group_table(tableId integer primary key autoincrement , chair_number integer not null, status varchar(255) not null )");
        // create table order : Detail about order
        database.QueryData("create table if not exists orders(orderId integer primary key autoincrement, tableId integer not null, note text , paid bool not null, foreign key (tableId) references group_table(tableId))");
//        // create table orderdetail :
       database.QueryData("create table if not exists orderdetails(orderId integer not null, dishId integer not null, accountId integer not null,  quantityOrder integer not null,date datetime not null,  primary key (orderId, dishId))");
//        // create table discount : Detail  about discount
       database.QueryData("create table if not exists  vouchers(voucherId integer primary key AUTOINCREMENT,voucherCode varchar(255),  title varchar(255) not null, discount integer not null, startdate long, enddate long) ");
// create conditions : một voucher có thể có nhiều điều kiện mà
       database.QueryData("create table if NOT EXISTS conditions (voucherId integer, condition text, FOREIGN KEY (voucherId) REFERENCES vouchers(voucherId));");
//        // create table payment  : Detail for payment
        database.QueryData("create table if not exists payments(paymentId integer primary key AUTOINCREMENT,accountId integer not null,tableId integer not null, orderId integer not null, discountId integer, amount double not null, date long not null, status varchar(255)," +
                "foreign key (accountId) references account(accountId), "+
                "foreign key (orderId) references orders(orderId), " +
                "foreign key (discountId) references vouchers(voucherId))");*/
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        logInBtn = rootView.findViewById(R.id.loginButton);
        username = (EditText) rootView.findViewById(R.id.usernameEditText);
        password = (EditText) rootView.findViewById(R.id.passwordEditText);
        togglePassword = (CheckBox) rootView.findViewById(R.id.show_hide_password_signin);
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                /*
              //  Cursor dataAccount = database.getData("SELECT * FROM account where userName = '"+username.getText().toString()+"' "); // trả về một cái dãy các account nè
               // Toast.makeText(getContext(), "" + dataAccount.getCount(), Toast.LENGTH_SHORT).show();
                if(dataAccount.getCount()>0)
                {
                    while (dataAccount.moveToNext()){
                        if(dataAccount.getString(4).equals(password.getText().toString()))
                        {
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("name", username.getText().toString());
                            intent.putExtra("account_type", dataAccount.getString(2));
                            intent.putExtra("accountId", dataAccount.getInt(0));
                            startActivity(intent);
                        }
                        else
                        {
                            password.setText(null);
                            Toast.makeText(getContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    password.setText(null);
                    username.setText(null);
                    Toast.makeText(getContext(), "UserName không tồn tại", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }
                */
            }
        });
        return  rootView;
    }
}
