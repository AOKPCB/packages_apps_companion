package com.cr5315.AOKPCB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.cr5315.companion.R;

public class Main extends Activity {
	
	ProgressDialog dialog;	
	String tag = "AOKPCB";
	String uName;
	Boolean logo = false;
	
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
                        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        uName = prefs.getString("userName", "user");
        TextView w = (TextView) findViewById(R.id.welcomeText);
        w.setText(getResources().getString(R.string.hello) + " " + uName + getResources().getString(R.string.hello2));
        Log.i(tag, "Set name in welcome message to" + " " + uName);
        
        Log.i(tag, "Set up ListView");
        
        Devs devs_data[] = new Devs[] {
        	new Devs(R.drawable.dev_scar45, getResources().getString(R.string.scar45), "Motto here"),
        	new Devs(R.drawable.dev_remicks, getResources().getString(R.string.remicks), "Motto here"),
        	new Devs(R.drawable.dev_cr5315, getResources().getString(R.string.cr5315), "I won\'t eat until when I\'m explode"),
        	new Devs(R.drawable.dev_sixstringsg, getResources().getString(R.string.sixstringsg), "Motto here")
        };
        
        DevsAdapter adapter = new DevsAdapter(this, R.layout.listitem, devs_data);

        ListView listView = (ListView) findViewById(R.id.listView1);
        
        View header = (View)getLayoutInflater().inflate(R.layout.listheader, null);
        
        listView.addHeaderView(header);
        
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                                
                devClick(position);
                
            }
        });
        }
    }
    
    public void devClick(int position) {
    	if (position == 0) {
    		showLogo();
    	} else if (position == 1) {
    		devDialog(R.drawable.dev_scar45, R.string.scar45, R.string.bio_scar45, "https://twitter.com/scar45", "http://goo.gl/F2DEX");
    	} else if (position == 2) {
    		devDialog(R.drawable.dev_remicks, R.string.remicks, R.string.bio_remicks, "https://twitter.com/remicksTweetz", "http://goo.gl/hYCD8");
    	} else if (position == 3) {
    		devDialog(R.drawable.dev_cr5315, R.string.cr5315, R.string.bio_cr5315, "https://twitter.com/cr5315", "http://goo.gl/XVlvd");
    	} else if (position == 4) {
    		devDialog(R.drawable.dev_sixstringsg, R.string.sixstringsg, R.string.bio_sixstringsg, "https://twitter.com/sixstringsg", "http://goo.gl/ak698");
    	} else {
    		Toast.makeText(getApplicationContext(), "Oops, you broke it", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void showLogo() {
    	logo = true;
    	setContentView(R.layout.about);
        ImageButton logo = (ImageButton) findViewById(R.id.logoImage);
        
        Toast.makeText(getApplicationContext(), getModVersion(), Toast.LENGTH_LONG).show();
        
        logo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent restart = new Intent (Main.this, Main.class);
	            startActivity(restart);
	            finish();
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
    
    public void devDialog(int icon, int name, int bio, final String twitter, final String donate) {
    	AlertDialog.Builder b = new AlertDialog.Builder(this);
    	b.setTitle(name);
    	b.setIcon(icon);
    	b.setMessage(bio);
    	b.setPositiveButton("Donate", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent d = new Intent(Intent.ACTION_VIEW);
				d.setData(Uri.parse(donate));
				startActivity(d);
			}
		});
    	b.setNeutralButton("Twitter", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(twitter));
				startActivity(i);
			}
		});
    	b.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			dialog.cancel();	
			}
		});
    	b.create();
    	b.show();
    }
    
    @SuppressLint("CommitPrefEdits")
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
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle(R.string.about);
                b.setMessage("AOKPCB Companion\nBeta 1\n\nDeveloped by cr5315\n\nIf you have this and aren't cr5315, remicks, scar45, or sixstringsg, you shouldn't have this");
                b.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
                b.create();
                b.show();                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	  @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK) && logo == true) {
	            Intent restart = new Intent (Main.this, Main.class);
	            startActivity(restart);
	            finish();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}