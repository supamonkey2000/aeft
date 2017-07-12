package com.jmoore.aeft;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public Uri selectedfile;
    public OutputStream out = null;
    public Socket socket = null;
    public File myFile = null;
    public byte[] buffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);

        Button buttonname;
        buttonname = (Button) findViewById(R.id.sendB);

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFile = new File(selectedfile.getPath());
                buffer = new byte[(int) myFile.length()];

                //NETWORK STUFF HERE
                try {
                    EditText et = (EditText) findViewById(R.id.editText3);
                    String IP = et.getText().toString();
                    socket = new Socket(IP, 25000);
                    FileInputStream fis = new FileInputStream(myFile);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    in.read(buffer, 0, buffer.length);
                    out.flush();
                    out.close();
                    in.close();
                    socket.close();
                } catch(Exception ex) {ex.printStackTrace();}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK) {
            selectedfile = data.getData(); //The uri with the location of the file
            System.out.println(selectedfile);
        }
    }
}
