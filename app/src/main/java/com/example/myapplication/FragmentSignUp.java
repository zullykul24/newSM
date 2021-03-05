package com.example.myapplication;
// đã xong phần đăng kí tài khoản nè

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.FragmentSignIn.database;

//import static com.example.myapplication.MainActivity.database;

public class FragmentSignUp extends Fragment {
    EditText userName;
    EditText phoneNumber;
    EditText password;
    Spinner acc_type;
    CheckBox togglePassword;
    EditText retypePassword;
    Button signUp;
    String account_type = "Nhân viên";
    // kiểm tra userName
    private static final String USERNAME_PATTERN = "^[a-z0-9]{3,16}$";

    public static boolean verifyUsername(String username) {
        if (username == null) return false;
        return username.matches(USERNAME_PATTERN);
    }

    // kiem tra pass
    private static final String PASSWORD_PATTERN ="^[a-z0-9]{3,16}$";

    public static boolean verifyPassword(String password) {
        if (password == null) return false;
        return password.matches(PASSWORD_PATTERN);
    }

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_signup, container, false);

        acc_type = (Spinner) view.findViewById(R.id.account_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item , getResources().getStringArray(R.array.account_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userName  =(EditText) view.findViewById(R.id.usernameEditTextSignUp);
        password =  (EditText) view.findViewById(R.id.passwordEditTextSignUp);
        phoneNumber =  (EditText) view.findViewById(R.id.phoneNumber);
        retypePassword = (EditText) view.findViewById(R.id.retypePass);
        togglePassword = (CheckBox) view.findViewById(R.id.show_hide_password_signup);
        acc_type.setAdapter(adapter);
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    retypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    retypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signUp = (Button)  view.findViewById(R.id.signUpButtoncommit);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_type = acc_type.getSelectedItem().toString();
/*
                Cursor dataAccount = database.getData("SELECT * FROM account where userName = '"+userName.getText().toString()+"' "); // trả về một cái dãy các account nè
                if(dataAccount.getCount()>0)
                {
                    Toast.makeText(getContext(),"Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (verifyUsername(userName.getText().toString())== true && verifyPassword(password.getText().toString()) == true && password.getText().toString().equals(retypePassword.getText().toString()) == true) {

                        database.QueryData("INSERT INTO account VALUES(null, '"+phoneNumber.getText().toString()+"','"+account_type+"', '"+userName.getText().toString()+"', '"+password.getText().toString()+"' ) ");
                        Toast.makeText(getContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    } else if (verifyUsername(userName.getText().toString()) == false) {
                        userName.setText(null);
                        userName.setHint("UserName không hợp lệ");
                        userName.setHintTextColor(Color.RED);
                    } else if (verifyPassword(password.getText().toString()) == false) {
                        password.setText(null);
                        password.setHint("Password không hợp lệ");
                        password.setHintTextColor(Color.RED);
                    } else {
                        retypePassword.setText(null);
                       // retypePassword.setHint("Bạn chắc chứ ??");
                        //retypePassword.setHintTextColor(Color.RED);
                        Toast.makeText(getContext(), "Mật khẩu nhập lại không đúng\nVui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                    }
                }
                */
            }
        });
        return view;
    }


}
