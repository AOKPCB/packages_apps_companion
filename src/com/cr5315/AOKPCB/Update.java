package com.cr5315.AOKPCB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Update extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(R.string.update_title);
		
		TextView u = (TextView) findViewById(R.id.userVersion);
		TextView l = (TextView) findViewById(R.id.latestVersion);
		
		u.setText("Current Version:\n" + getModVersion());
		l.setText("Latest Version:\nNot available right now");
		
		Button b = (Button) findViewById(R.id.updateButton);
		//enable this after download method is written
		b.setEnabled(false);		
		b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public static String getModVersion() {
        String modVer = getSystemProperty(Versions.RO_AOKPCB_VERSION);

        return (modVer == null || modVer.length() == 0 ? "Unknown" : modVer);
    }
    
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        }
        catch (IOException ex) {
            Log.e("AOKPCB", "Not able to read prop " + propName, ex);
            return null;
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    Log.e("AOKPCB", "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent h = new Intent (Update.this, Main.class);
			startActivity(h);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	  @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	            Intent homeIntent = new Intent (Update.this, Main.class);
	            startActivity(homeIntent);
	            finish();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }

}
