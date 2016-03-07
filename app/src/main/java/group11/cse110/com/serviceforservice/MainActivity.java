package group11.cse110.com.serviceforservice;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    private final static String key = "MySharedData";
    private String user;
    private Intent homePage;
    protected void onCreate(Bundle savedInstanceState){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
    // Add your initialization code here

        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true) ;
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this);
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(key,0);
        try{
            sp = getSharedPreferences(key,0);
            user = sp.getString("username",null);
        }
        catch(Exception e){

        }


        if(/*ParseUser.getCurrentUser()*/ null != null){
            Log.d("start up","User" + " is login");

            homePage = new Intent(this,HomePage.class);
            startActivity(homePage);
            finish();
        }
        else{
            Log.e("start up","User is not login");

            homePage = new Intent(this,StartScreen.class);
            startActivity(homePage);
            finish();
        }
    }

}
