package group11.cse110.com.serviceforservice;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private final static String key = "MySharedData";
    private String user;
    private Intent homePage;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(key,0);
        try{
            sp = getSharedPreferences(key,0);
            user = sp.getString("username",null);
        }
        catch(Exception e){

        }
        Log.e("HMM","okay");
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
