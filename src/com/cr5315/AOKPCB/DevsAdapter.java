package com.cr5315.AOKPCB;

import com.cr5315.companion.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DevsAdapter extends ArrayAdapter<Devs> {
	
	Context context; 
    int layoutResourceId;    
    Devs data[] = null;
    
    public DevsAdapter(Context context, int layoutResourceId, Devs[] data) {
    	super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DevsHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new DevsHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtMotto = (TextView)row.findViewById(R.id.txtMotto);
            
            row.setTag(holder);
        }
        else
        {
            holder = (DevsHolder)row.getTag();
        }
        
        Devs devs= data[position];
        holder.txtTitle.setText(devs.title);
        holder.txtMotto.setText(devs.motto);
        holder.imgIcon.setImageResource(devs.icon);
        
        return row;
    }
    
    static class DevsHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtMotto;
    }
}
