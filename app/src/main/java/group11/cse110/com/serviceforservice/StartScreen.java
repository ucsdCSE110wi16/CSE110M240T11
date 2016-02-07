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

import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StartScreen extends Activity implements OnClickListener {
    Intent facebook;
    Intent signupIntent;
    ImageButton sign;
    LoginButton fb;
    Intent camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        signupIntent = new Intent(this, SignIn.class);
        facebook = new Intent(this, SignUp.class);
        sign = (ImageButton) findViewById(R.id.signup);
        fb = (LoginButton) findViewById(R.id.signfacebook);
        sign.setOnClickListener(this);
        fb.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case (R.id.signup):
                startActivity(signupIntent);
                break;
            case (R.id.signfacebook):
                //startActivity(facebook);
                //break;
        }
    }

}
