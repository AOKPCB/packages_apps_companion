package com.cr5315.AOKPCB;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

public class Preferences extends PreferenceActivity {
	 
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
 
        
        super.onCreate(savedInstanceState);
 
        //add the prefernces.xml layout
        addPreferencesFromResource(R.xml.prefs);  
        
        ActionBar bar = getActionBar();
        bar.setTitle(R.string.settings);
        bar.setDisplayHomeAsUpEnabled(true);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent hIntent = new Intent (Preferences.this, Main.class);
	            startActivity(hIntent);
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public boolean onKeyDown (int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent hIntent = new Intent (Preferences.this, Main.class);
            startActivity(hIntent);
            finish();
		}
		return super.onKeyDown(keyCode, event);
	}
} 
