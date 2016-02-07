package group11.cse110.com.serviceforservice;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;


import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {

    private final static String key = "MySharedData";
    private String user;
    private Intent homePage;
    protected void onCreate(Bundle savedInstanceState){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this) ;
// Add your initialization code here Parse.initialize( this) ; ParseUser.enableAutomaticUser( ); ParseACL defaultACL = new ParseACL(); // Optionally enable public read access. // defaultACL.setPublicReadAccess﴾true﴿;

        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true) ;
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(key,0);
        try{
            sp = getSharedPreferences(key,0);
            user = sp.getString("username",null);
        }
        catch(Exception e){

        }
        Log.e("HMM", "okay");



        if(user != null){
            Log.e("not null","not null");

            homePage = new Intent(this,HomePage.class);
            startActivity(homePage);
            finish();
        }
        else{
            Log.e("this is null","is null");

            homePage = new Intent(this,StartScreen.class);
            startActivity(homePage);
            finish();
        }
    }
}
