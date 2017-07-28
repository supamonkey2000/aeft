package com.jmoore.aeft;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    //private final ArrayList<String>itempath;
    private final ArrayList<String>itemname;

    public CustomListAdapter(Activity context,ArrayList<String>itemname){
        super(context,R.layout.custom_list,itemname);
        this.context=context;
        this.itemname=itemname;
        //this.itempath=itempath;
    }

    @NonNull
    public View getView(int position,View view,ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_list,null,true);

        TextView txtTitle=(TextView)rowView.findViewById(R.id.item);
        ImageView imageView=(ImageView)rowView.findViewById(R.id.icon);
        TextView extratxt=(TextView)rowView.findViewById(R.id.textView1);
        String[]split=(itemname.get((position))).split("/");
        //txtTitle.setText(itemname.get(position));
        txtTitle.setText(split[split.length-1]);
        try{
            imageView.setImageURI(Uri.parse(itemname.get(position)));
        }catch(Exception ex){
            System.out.println("Failed to get an image for: " + itemname.get(position));
        }
        extratxt.setText("Description "+itemname.get(position));
        return rowView;
    }
}