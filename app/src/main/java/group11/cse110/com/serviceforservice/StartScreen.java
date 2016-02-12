package group11.cse110.com.serviceforservice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StartScreen extends Activity implements OnClickListener {
    Intent facebook;
    Intent signupIntent;
    ImageButton sign;
    LoginButton fb;
    Intent camera;
    CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        ParseObject userInfo = new ParseObject("Facebook User");
        userInfo.put("userID", "ge");
        userInfo.put("Name", "ge");
        userInfo.saveInBackground();
        signupIntent = new Intent(this, SignIn.class);
        facebook = new Intent(this, SignUp.class);
        sign = (ImageButton) findViewById(R.id.signup);
        fb = (LoginButton) findViewById(R.id.signfacebook);
        sign.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                StartScreen.this.fbInfoToParse();
                StartScreen.this.fbSignIn();
            }

            @Override
            public void onCancel() {
            //TODO: Create an cancel message
            }

            @Override
            public void onError(FacebookException error) {
            //TODO: Create an error message
            }
        });


    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case (R.id.signup):
                startActivity(signupIntent);
                break;
        }
    }
    public void fbSignIn(){
        Intent homepageIntent = new Intent(this,HomePage.class);
        startActivity(homepageIntent);
    }
    private void fbInfoToParse() {

        Profile user = Profile.getCurrentProfile();
        if (user == null) {
            Log.d("Test1", "ge");
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    mProfileTracker.stopTracking();
                    Log.d("Test3", "ge");
                    StartScreen.this.addToParse(currentProfile);
                }
            };
            mProfileTracker.startTracking();
        } else {
            Log.d("Test2", "ge");
            addToParse(user);
        }
    }

    private void addToParse(Profile user){
        Log.d("Tests", "ge");
        ParseObject userInfo = new ParseObject("Facebook User");
        userInfo.put("userID", user.getId());
        userInfo.put("Name", user.getFirstName()+" "+user.getLastName());
        //userInfo.put("Profile Picture", user.getProfilePictureUri(100,100));
        userInfo.saveInBackground();
    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


}
