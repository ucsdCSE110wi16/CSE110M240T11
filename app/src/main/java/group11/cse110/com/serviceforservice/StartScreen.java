package group11.cse110.com.serviceforservice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StartScreen extends Activity implements OnClickListener {
    Intent facebook;
    Intent signupIntent;
    ImageButton sign;
    LoginButton fb;
    Intent camera;
    CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    String email, name;
    ParseUser parseUser;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        signupIntent = new Intent(this, SignIn.class);
        facebook = new Intent(this, SignUp.class);
        sign = (ImageButton) findViewById(R.id.signup);
        fb = (LoginButton) findViewById(R.id.signfacebook);
        sign.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
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
                ArrayList<String> mPermissions = new ArrayList<String>();
                mPermissions.add("public_profile");
                mPermissions.add("email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("Sign In", "The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("Sign In", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                        } else {
                            Log.d("Sign In", "User logged in through Facebook!");
                            getUserDetailsFromParse();
                        }
                    }
                });
        }
    }
    public void fbSignIn(){
        Intent homepageIntent = new Intent(this,HomePage.class);
        startActivity(homepageIntent);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserDetailsFromFB() {
            Bundle parameters = new Bundle();
            parameters.putString("fields", "email,name,picture");
            new GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me",
            parameters,
            HttpMethod.GET,
            new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                 /* handle the result */
                    try {
                        email = response.getJSONObject().getString("email");
                        name = response.getJSONObject().getString("name");
                        JSONObject picture = response.getJSONObject().getJSONObject("picture");
                        JSONObject data = picture.getJSONObject("data");
                        //  Returns a 50x50 profile picture
                        String pictureUrl = data.getString("url");
                        new ProfilePhotoAsync(pictureUrl).execute();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            ).executeAsync();
    }
    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(name);
        parseUser.setEmail(email);
//        Saving profile photo as a ParseFile
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] data = stream.toByteArray();
        String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
        final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                parseUser.put("profileThumb", parseFile);
                //Finally save all the user details
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(StartScreen.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
        String url;
        public ProfilePhotoAsync(String url) {
            this.url = url;
        }
        @Override
        protected String doInBackground(String... params) {
            // Fetching data from URI and storing in bitmap
            bitmap = DownloadImageBitmap(url);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            saveNewUser();
        }
    }
    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
    }
    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();
        //Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(StartScreen.this, "Welcome back " + parseUser.getUsername(), Toast.LENGTH_SHORT).show();
    }
}
