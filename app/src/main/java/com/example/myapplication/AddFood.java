package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.myapplication.FragmentSignIn.database;

public class AddFood extends AppCompatActivity {
    Button btnAdd;
    ImageButton btnDele;
    ImageView image;
    EditText name, price;
    ImageButton camera, folder;
    int RE_CAMERA = 123;
    int RE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        AnhXa();
        // bat cam
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mo cam ne
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RE_CAMERA);
            }
        });
        // mo folder
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RE_FOLDER);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap(); // chuyen ve sang kieu bitmap
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray); // chuyen du lieu
                byte[] hinhanh = byteArray.toByteArray(); // chuyen ve kieu mang byte;
                // lấy price ở kiểu text phải chuyển sang lưu ở dạng double
                if(name.getText().toString().equals("")==true||price.getText().toString().equals("")==true)
                {
                    Toast.makeText(AddFood.this, "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    /*database.insertDish(name.getText().toString(), Double.parseDouble(price.getText().toString()),
                            hinhanh );
                            */

                       finish();
                }

            }
        });
        btnDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    // lay du lieu trong camera hoawcj folder
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // chon luu cai hinh do ne
        if(requestCode == RE_CAMERA && resultCode == RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
        // chon luu tam hinh trong folder
        if(requestCode==RE_FOLDER && resultCode == RESULT_OK && data!=null ){
            // lay dg dan trong bo nho
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public  void AnhXa(){
        btnAdd = (Button) findViewById(R.id.buttonThem);
        btnDele = (ImageButton) findViewById(R.id.image_btn_huy);
        image = (ImageView) findViewById(R.id.imageFood);
        name = (EditText) findViewById(R.id.nameFood);
        price = (EditText) findViewById(R.id.priceFood);
        camera = (ImageButton) findViewById(R.id.camera);
        folder = (ImageButton) findViewById(R.id.folder);

    }
}