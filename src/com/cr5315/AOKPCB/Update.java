package com.cr5315.AOKPCB;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.cr5315.companion.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Update extends Activity {
	
	String userVersion;
	int userCode;
	String latestVersion;
	int latestCode;
	String dlLink;
	WebView webView;
	Boolean button = false;
	ProgressDialog dialog;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);	
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
				
		Button b = (Button) findViewById(R.id.downloadButton);
		b.setEnabled(button);
		b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(dlLink));
				request.setDescription("AOKPCB");
				request.setTitle(latestVersion);
				// in order for this if to run, you must use the android 3.2 to compile your app
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				    request.allowScanningByMediaScanner();
				    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
				File f = new File ("" + dlLink);
				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, f.getName());

				// get download service and enqueue file
				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
			}
		});
		
		dialog = new ProgressDialog(Update.this);
		dialog.setMessage("Loading. Please wait...");
		
		new GetVersions().execute();
	}
	
	private class NightlyWebViewClient extends WebViewClient {
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
	
	
	public static String getModVersion(String prop) {
		
		String modVer = getSystemProperty(prop);

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
	            Intent homeIntent = new Intent (Update.this, Main.class);
	            startActivity(homeIntent);
	            finish();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	  
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		        case android.R.id.home:
		            Intent hIntent = new Intent(Update.this, Main.class);
		            startActivity(hIntent);
		            finish();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	  
	  public class GetVersions extends AsyncTask<Void, Void, Void> {
		  
		  @Override
		  protected void onPreExecute() {
			  super.onPreExecute();
			  
			  dialog.show();
		  }
		  
			@SuppressLint("SetJavaScriptEnabled")
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				webView = (WebView) findViewById(R.id.changelogWebView);
				WebSettings webSettings = webView.getSettings();
				webSettings.setJavaScriptEnabled(true);
				webView.setWebViewClient(new NightlyWebViewClient());
				webView.loadUrl("http://cr5315.com/AOKPCB/nightlyrss.html");
				
				Log.i("AOKPCB Update", "getUserVersions()");
				getUserVersions();
				return null;
			}
		
		public void getUserVersions() {
			
			//Get the version number for the app to see
			String userCodeString = getModVersion(Versions.RO_AOKPCB_APP_VERSION);
			Log.i("AOKPCB Update", "User Code(String):" + " " + userCodeString);
			try {
				userCode = Integer.parseInt(userCodeString);
				Log.i("AOKPCB Update", "User Code(int):" + " " + userCode);
			} catch (NumberFormatException nfe) {
				Toast.makeText(getApplicationContext(), "NumberFormatException", Toast.LENGTH_LONG).show();
				Log.e("AOKPCB Update", "NumberFormatException");
			}
			
			//Get the version name for the user to see
			userVersion = getModVersion(Versions.RO_AOKPCB_VERSION);
			Log.i("AOKPCB Update", "User Version:" + " " + userVersion);
			
			//Now that we have those
			//Let's get the latest versions
			getLatestVersions();
		}
		
		public void getLatestVersions() {
			String checkLink = "http://aokpcb.boomm.net/private/app/" + Build.DEVICE + ".txt";
			
			try {
				URL url = new URL(checkLink);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
				if ((str = in.readLine()) != null) {
					Log.i("AOKPCB Update", "Latest Version Code:" + " " + str);
					latestCode = Integer.parseInt(str);
					if (latestCode > userCode) {
						dlLink = in.readLine();
						Log.i("AOKPCB Update", "Download link:" + " " + dlLink);
						latestVersion = dlLink.substring(dlLink.lastIndexOf('/')+1, dlLink.lastIndexOf('.'));
						Log.i("AOKPCB Update", "Latest Version:" + " " + latestVersion);
						button = true;
					}
				}
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			TextView versionText = (TextView) findViewById(R.id.currentText);
			TextView latestText = (TextView) findViewById(R.id.latestText);
			versionText.setText(getResources().getString(R.string.current) + " " + userVersion);
			latestText.setText(getResources().getString(R.string.latest) + " " + latestVersion);
				Button d = (Button) findViewById(R.id.downloadButton);
				d.setEnabled(button);
			dialog.dismiss();
		}		  
	  }
}