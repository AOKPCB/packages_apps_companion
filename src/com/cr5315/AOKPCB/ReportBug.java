package com.cr5315.AOKPCB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReportBug extends Activity {
	
	public  String deviceName;
	public String modVersion;
	public String userDesc;
	public String finalMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		
		ActionBar bar = getActionBar();
		bar.setTitle(R.string.reportMenu);
		bar.setDisplayHomeAsUpEnabled(true);
		
		TextView device = (TextView) findViewById(R.id.deviceText);
		TextView mod = (TextView) findViewById(R.id.modVersion);
		deviceName = getResources().getString(R.string.device) + " " + Build.DEVICE;
		modVersion = getResources().getString(R.string.rom_version) + " " + getModVersion();
		device.setText(deviceName);
		mod.setText(modVersion);
		
		Button submit = (Button) findViewById(R.id.submitButton);
		submit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText edit = (EditText) findViewById(R.id.descriptionText);
				userDesc = edit.getText().toString();
				if (userDesc.equals("")) {
					Toast.makeText(getApplicationContext(), R.string.describe, Toast.LENGTH_LONG).show();
				} else {
				String submitEmail[] = { "aokpcb@crypticlarity.net" }; 
				Intent eIntent = new Intent(Intent.ACTION_SEND);
				eIntent.setType("plain/text");
				eIntent.putExtra(Intent.EXTRA_EMAIL, submitEmail);
				eIntent.putExtra(Intent.EXTRA_SUBJECT, "AOKPCB Bug Report");
				eIntent.putExtra(Intent.EXTRA_TEXT, getBugMessage(deviceName, modVersion));
				startActivity(Intent.createChooser(eIntent, getResources().getString(R.string.via)));
				}
			}
		});
				
	}
	
	public String getBugMessage(String device, String mod) {
		EditText edit = (EditText) findViewById(R.id.descriptionText);
		userDesc = edit.getText().toString();
		finalMessage = device + "\n\n" + mod + "\n\n" + 
				getResources().getString(R.string.description) + " " + userDesc;
		Log.i("AOKPCB", finalMessage);
		return finalMessage;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.bug, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent hIntent = new Intent (ReportBug.this, Main.class);
	            startActivity(hIntent);
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public static String getModVersion() {
        String modVer = getSystemProperty(Customization.RO_AOKPCB_VERSION);

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
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	            Intent homeIntent = new Intent (ReportBug.this, Main.class);
	            startActivity(homeIntent);
	            finish();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
