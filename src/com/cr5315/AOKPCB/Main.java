package com.cr5315.AOKPCB;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	ProgressDialog dialog;	
	String tag = "AOKPCB";
	String uName;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(tag, "onCreate");
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        Boolean r = settings.getBoolean("firstRun", false);
        if (r == false) {
            Log.i(tag, "Run firstRun");
            firstRun();
        } else {
        	
        setContentView(R.layout.main);
        
        Log.i(tag, "Get ActionBar");
        ActionBar bar = getActionBar();
                
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        uName = prefs.getString("userName", "user");
        TextView w = (TextView) findViewById(R.id.welcomeText);
        w.setText(getResources().getString(R.string.hello) + " " + uName + getResources().getString(R.string.hell2));
        
        Log.i(tag, "Set name in welcome message to" + " " + uName);
        }
    }
    
    @SuppressWarnings("deprecation")
	public void firstRun() {
    	SharedPreferences settings = getSharedPreferences("PREFS", 0);
    	final SharedPreferences.Editor ed = settings.edit();
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	Boolean first = settings.getBoolean("firstRun", false);
    	Log.i("AOKPCB", "firstRun =" + " " + first);
    	if (first.equals(false)) {
    		
    		AlertDialog.Builder editalert = new AlertDialog.Builder(this);

    		editalert.setTitle("Name");
    		editalert.setMessage("Please enter your name");

    		final EditText input = new EditText(this);
    		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
    		        LinearLayout.LayoutParams.FILL_PARENT,
    		        LinearLayout.LayoutParams.FILL_PARENT );
    		input.setLayoutParams(lp);
    		editalert.setView(input);

    		editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		    public void onClick(DialogInterface dialog, int whichButton) {

    		    	if (input.getText().toString().equals("")) {
    		    		Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
    		    		firstRun();
    		    	} else {
    		    		editor.putString("userName", input.getText().toString());
    		    		editor.putString("device", Build.DEVICE);
    		    		editor.commit();
    		    		ed.putBoolean("firstRun", true);
    		    		ed.commit();
    		            Intent restart = new Intent (Main.this, Main.class);
    		            startActivity(restart);
    		            finish();
    		    	}
    		    }
    		});
    		editalert.show();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reportBug:
            	Intent rIntent = new Intent (Main.this, ReportBug.class);
            	startActivity(rIntent);
            	finish();
                return true;
            case R.id.settings:
            	Intent sIntent = new Intent (Main.this, Preferences.class);
            	startActivity(sIntent);
            	finish();
            	return true;
            case R.id.about:
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}