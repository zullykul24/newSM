package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.myapplication.FragmentSignIn.database;

public class MainActivity extends AppCompatActivity {

    // dau  tien la phai show dc danh sach cac mon
    BottomNavigationView navbar;


    //public static Database database;

    protected void AnhXa(){
        navbar = (BottomNavigationView) findViewById(R.id.navbar);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        AnhXa();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentHomePage fragment1 = new FragmentHomePage();
        final FragmentTableOrder fragment2 = new FragmentTableOrder();
        final FragmentMenu fragment3 = new FragmentMenu();
        final FragmentAccount fragment4 = new FragmentAccount();
        final ManagerFragmentHomePage managerFragmentHomePage = new ManagerFragmentHomePage();
        fragment4.getInfor(intent.getStringExtra("name"), intent.getStringExtra("account_type"));
        fragment2.getInfor(intent.getIntExtra("accountId", 0));
        fragment1.getInfor(intent.getIntExtra("accountId", 0));
        managerFragmentHomePage.getInfor(intent.getIntExtra("accountId", 0));
       // final String accountType = intent.getStringExtra("account_type");
        final String accountType = "Quản lý";
        if(accountType.equals("Nhân viên")){
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment1, "1").addToBackStack("1").commit();}
        else {
            /// nếu là quản lý thì...
            fragmentManager.beginTransaction().add(R.id.fragment_container, managerFragmentHomePage, "5").addToBackStack("5").commit();
        }

        navbar.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()){
                    case R.id.nav_1:
                        if(accountType.equals("Nhân viên")) {
                            fragmentManager.beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                    .replace(R.id.fragment_container, fragment1,"1")
                                    .addToBackStack(null)
                                    .commit();

                        }
                        else {
                            /// nếu là quản lý
                            fragmentManager.beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                    .replace(R.id.fragment_container, managerFragmentHomePage, "5")
                                    .addToBackStack(null)

                                    .commit();

                        }

                        break;

                    case R.id.nav_2:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container, fragment2, "2")
                                .addToBackStack(null)

                                .commit();

                        break;
                    case R.id.nav_3:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container,fragment3, "3")
                                .addToBackStack(null)

                                .commit();

                        break;
                    case R.id.nav_4:
                        fragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                                .replace(R.id.fragment_container,fragment4, "4")
                                .addToBackStack(null)

                                .commit();

                        break;
                }

                return true;
            }
        });



    }
    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navbar);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_1 != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_1);
    }
}