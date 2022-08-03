package com.hiyama.getfileandsendtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String dirPath = Environment.getExternalStorageDirectory().getPath() + "/GetFileAndSendTest/";
    File destDir = new File(dirPath);
    File[] dirFileName = destDir.listFiles();
    SendToServer sendToServer = new SendToServer();
    Button mSendBtn;
    Bitmap bitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int dirSize = dirFileName.length - 1;
        String lastFileName = dirFileName[dirSize].getName();
        imageView = findViewById(R.id.imageView);
        mSendBtn = findViewById(R.id.sendBtn);
        mSendBtn.setOnClickListener(this);
        try {
            InputStream inputStream = new FileInputStream(destDir + "/" + lastFileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn: {
                sendToServer.sendImageToServer(bitmap);
            }
        }
    }
}