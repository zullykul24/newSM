package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class FragmentAccount extends Fragment {
    Button logOutBtn;
    String name;
    String accountType;
    public void getInfor(String name, String accountType) {
        this.name = name;
        this.accountType = accountType;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container,false);

        ((TextView)rootView.findViewById(R.id.displayName)).setText(name);
        ((TextView)rootView.findViewById(R.id.role_name)).setText(accountType);
        logOutBtn = rootView.findViewById(R.id.logoutBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogOut = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLogOut);
            }
        });
        return  rootView;
    }


}
