package com.example.lolita.halloword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button next = findViewById(R.id.bt_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });


        //imageView = findViewById(R.id.imageView);
        //Bitmap bitmap = BitmapFactory.decodeFile("sdcard/test.ipg");
        //imageView.setImageBitmap(bitmap);
        
        initUI();
        //setDate();
    }

    //private   setDate() {
    //}

    /**
     * fd
     */
    private void initUI() {

    }

    @SuppressLint("SimpleDateFormat")
    public void process(View v){
        imageView.invalidate();
//        XmlSerializer serializer = Xml.newSerializer();
        long l = System.currentTimeMillis();
//        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse("content://");
        getContentResolver().query(uri, new String[]{}, "id = %d", new String[]{"1"},null);

        WindowManager wd = getWindowManager();
        int height = wd.getDefaultDisplay().getHeight();
        int width = wd.getDefaultDisplay().getWidth();
        Toast.makeText(getApplicationContext(),height + "*" + width,Toast.LENGTH_LONG).show();

    }

    public void next(){
        Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
        startActivity(intent);
    }

}
