package com.jmoore.aeft;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    public String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.sendB);
        button.setOnClickListener(this);
    }

    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.editText3);
        IP = et.getText().toString();
        Intent i;
        i = new Intent(this, OpenFileActivity.class);
        this.setContentView(R.layout.activity_open_file);
        this.startActivityForResult(i, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String fileName = null;
        if (resultCode == 2) {
            fileName = data.getStringExtra("fileName");
        } else {
            Toast.makeText(this,"No File Selected, Cancel Or Back Pressed",Toast.LENGTH_SHORT).show();
        }
        sendTheFile ooh = new sendTheFile();
        ooh.execute(fileName,IP);
    }
}

class sendTheFile extends AsyncTask<String, Void, String> {
    public OutputStream out = null;
    public Socket socket = null;
    public File myFile = null;
    public byte[] buffer;
    @Override
    protected String doInBackground(String[] params) {
        String IP = params[1];
        myFile = new File(params[0]);
        try {
            socket = new Socket(IP, 25000);
            FileInputStream fis = new FileInputStream(myFile);
            buffer = new byte[(int) myFile.length()];
            BufferedInputStream in = new BufferedInputStream(fis);
            in.read(buffer, 0, buffer.length);
            out = socket.getOutputStream();
            out.write(buffer,0,buffer.length);
            out.flush();
            out.close();
            in.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("TAG","Failed to send file");
        }
        return "";
    }
}