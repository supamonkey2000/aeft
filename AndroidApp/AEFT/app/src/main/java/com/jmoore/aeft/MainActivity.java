package com.jmoore.aeft;

import android.app.ProgressDialog;
import android.content.Context;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button,button2;
    public String IP;
    String thefilename;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.sendB);
        button.setOnClickListener(this);
        button2=(Button)findViewById(R.id.realsendB);
        button2.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==findViewById(R.id.sendB).getId()){
            EditText et=(EditText)findViewById(R.id.editText3);
            IP=et.getText().toString();
            Intent i;
            i=new Intent(this,OpenFileActivity.class);
            this.startActivityForResult(i,2);
        }
        else if(v.getId()==findViewById(R.id.realsendB).getId()){
            sendTheFile stf=new sendTheFile(this);
            stf.execute(thefilename,IP);
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        String fileName=null;
        if(resultCode==2){
            fileName=data.getStringExtra("fileName");
            Toast.makeText(this,fileName,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No File Selected, Cancel Or Back Pressed",Toast.LENGTH_SHORT).show();
        }
        thefilename=fileName;
    }
}

class sendTheFile extends AsyncTask<String,Integer,String>{
    private int progress=0;
    private ProgressDialog mProgressDialog;

    sendTheFile(Context cxt){
        mProgressDialog=new ProgressDialog(cxt);
    }

    protected void onPreExecute(){
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("Uploading, please wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void onProgressUpdate(Integer...values){
        mProgressDialog.setProgress(progress);
    }

    @Override
    protected String doInBackground(String[]params){
        String IP=params[1];
        File myFile=new File(params[0]);
        int count;
        try{
            long total=0;
            Socket socket=new Socket(IP,25000);
            byte[] buffer=new byte[(int)myFile.length()];
            FileInputStream fis=new FileInputStream(myFile);
            BufferedInputStream in=new BufferedInputStream(fis);
            OutputStream out=socket.getOutputStream();
            while((count=in.read(buffer,0,buffer.length))!=-1){
                total+=count;
                out.write(buffer,0,buffer.length);
                Log.i("Upload progress",""+(int)((total*100)/buffer.length));
                progress=(int)((total*100)/buffer.length);
                publishProgress();
            }
            out.flush();
            out.close();
            in.close();
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
            Log.i("TAG","Failed to send file");
        }
        return "test";
    }

    protected void onPostExecute(String result){
        System.out.println("IT FINISHED: "+result);
    }
}