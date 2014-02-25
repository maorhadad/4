package com.study.googleapi;

//import userActivity.LoginActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import googleplacesandmaps.*;
import userActivity.RegisterActivity;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
/*	
	 // flag for Internet connection status
    Boolean isInternetPresent = false;     
    // Connection detector class
    ConnectionDetector cd;*/
	Button btnOpenMap, SignIn, btnOpenList,btnOpenNearPlaces,btnFbLogin;

	 // Your Facebook APP ID
    private static String APP_ID = "252438914924347"; //  App ID here
 
    // Instance of Facebook Class
    private Facebook facebook;
    @SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartTheApp();

    }
	@SuppressWarnings("deprecation")
	private void StartTheApp() {
		btnOpenMap = (Button) findViewById(R.id.btnOpenMap);
		SignIn= (Button) findViewById(R.id.btnSignIn);
		btnOpenList= (Button) findViewById(R.id.btnOpenList);
		btnOpenNearPlaces= (Button) findViewById(R.id.btnOpenNearPlaces);
		btnFbLogin=(Button) findViewById(R.id.btnFbLogin);
		btnOpenMap.setOnClickListener(this);
		SignIn.setOnClickListener(this);
		btnOpenList.setOnClickListener(this);
		btnOpenNearPlaces.setOnClickListener(this);
		btnFbLogin.setOnClickListener(this);
	    facebook = new Facebook(APP_ID);
	    mAsyncRunner = new AsyncFacebookRunner(facebook);
	    if(mPrefs!=null)
	    {
	    	btnFbLogin.setText("Logout from FaceBook");
	    }

	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i;
		switch (v.getId())
		{
		case R.id.btnOpenMap:
			 i = new Intent (getApplication(), OpenMap.class);
			startActivity(i);
			break;
		case R.id.btnSignIn:
			i = new Intent (getApplication(), RegisterActivity.class);
			startActivity(i);
			break;
		case R.id.btnOpenList:
			i = new Intent (getApplication(), SpinnerList.class);
			startActivity(i);
			break;
		case R.id.btnOpenNearPlaces:
			 i = new Intent (getApplication(), googleplacesandmaps.MainActivity2.class);
			startActivity(i);
			break;
		case R.id.btnFbLogin:
			{
			    if(mPrefs!=null)
			    {
			    	logoutFromFacebook();
			    	mPrefs=null;
			    	btnFbLogin.setText("Logoin FaceBook");
			    }
			    else
			    {	
			    	loginToFacebook();
			    	btnFbLogin.setText("Logout FaceBook");
			    }
				break;
			}
		}		
	} 
	
	@SuppressWarnings("deprecation")
	public void loginToFacebook() {
	    mPrefs = getPreferences(MODE_PRIVATE);
	    String access_token = mPrefs.getString("access_token", null);
	    long expires = mPrefs.getLong("access_expires", 0);
	 
	    if (access_token != null) {
	        facebook.setAccessToken(access_token);
	    }
	 
	    if (expires != 0) {
	        facebook.setAccessExpires(expires);
	    }
	 
	    if (!facebook.isSessionValid()) {
	        facebook.authorize(this,
	                new String[] { "email", "publish_stream" },
	                new DialogListener() {
	 
	                    @Override
	                    public void onCancel() {
	                        // Function to handle cancel event
	                    }
	 
	                    @Override
	                    public void onComplete(Bundle values) {
	                        // Function to handle complete event
	                        // Edit Preferences and update facebook acess_token
	                        SharedPreferences.Editor editor = mPrefs.edit();
	                        editor.putString("access_token",
	                                facebook.getAccessToken());
	                        editor.putLong("access_expires",
	                                facebook.getAccessExpires());
	                        editor.commit();
	                    }
	 
	                    @Override
	                    public void onError(DialogError error) {
	                        // Function to handle error
	                    }
	                    
	                    @Override
	                    public void onFacebookError(FacebookError fberror) {
	                        // Function to handle Facebook errors
	                    }
	                });
	    }
	}
	
	@SuppressWarnings("deprecation")
	public void logoutFromFacebook() {
	    mAsyncRunner.logout(this, new RequestListener() {
	        @Override
	        public void onComplete(String response, Object state) {
	            Log.d("Logout from Facebook", response);
	            if (Boolean.parseBoolean(response) == true) {
	                // User successfully Logged out
	            }
	        }
	 
	        @Override
	        public void onIOException(IOException e, Object state) {
	        }
	 
	        @Override
	        public void onFileNotFoundException(FileNotFoundException e,
	                Object state) {
	        }
	 
	        @Override
	        public void onMalformedURLException(MalformedURLException e,
	                Object state) {
	        }
	        
	        @Override
	        public void onFacebookError(FacebookError e, Object state) {
	        }
	    });
	}
}
