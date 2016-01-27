package group11.cse110.com.serviceforservice;

        import java.io.ByteArrayOutputStream;
        import java.net.URL;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import android.annotation.SuppressLint;
        import android.annotation.TargetApi;
        import android.app.ActionBar;
        import android.app.Activity;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.graphics.Rect;
        import android.graphics.Typeface;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.View.OnFocusChangeListener;
        import android.view.View.OnKeyListener;
        import android.view.View.OnTouchListener;
        import android.view.ViewTreeObserver.OnGlobalLayoutListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Toast;



        /*
        import com.facebook.Request;
        import com.facebook.Response;
        import com.facebook.Session;
        import com.facebook.SessionState;
        import com.facebook.model.GraphUser;
        import com.parse.FindCallback;
        */
        import com.parse.FindCallback;
        import com.parse.Parse;
        import com.parse.ParseException;
        import com.parse.ParseFile;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;
        import com.parse.SaveCallback;


@TargetApi(Build.VERSION_CODES.CUPCAKE)
@SuppressLint("NewApi")
public class SignUp extends Activity implements OnClickListener,
        OnTouchListener, OnFocusChangeListener, OnKeyListener {
    Dialog dialogTwo;
    EditText name;
    boolean nameLong = false;
    EditText email;
    Bitmap bit;
    EditText username;
    EditText password;
    ImageButton picture;
    ProgressDialog dialog;
    ActionBar actionBar;
    Button toNext;
    Button galleryB;
    Button take;
    Button yes;
    ParseFile file;
    public static String key = "MySharedData";

    ProgressDialog progressDialog;
    boolean letterNum = false;
    Button no;
    boolean clickedImage = false;
    private Intent finish;
    private boolean fb = true;
    public static final int TAKEPHOTO = 3;
    public static final int CROPPED = 5;
    public static final int SELECT_PICTURE = 2;
    private static final int DIALOG_ALERT = 10;
    byte[] bytearray;
    ParseQuery<ParseObject> query;
    Typeface type;
    EditText passTwo;
    TextWatcher textwatcher;
    Users user;
    int numPeople;
    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("Here", "hereee");
        ParseObject.registerSubclass(Users.class);

 //       Parse.initialize(this, "NiuPkTEZo0wn61fxUEPZyre3yAmHm1WNLKTKFfTp",
 //               "acYSMbJ34seEJ95supo5ji4aIIFzz9PN0wOdHadb");
        type = Typeface.createFromAsset(getAssets(),"fonts/oswald.otf");

        dialog = new ProgressDialog(SignUp.this);
        dialog.setTitle("Loading");
        dialog.show();
        Log.e("Here here2", "hereee");

        setContentView(R.layout.fblogin);
        setActionBar();
        Log.e("Here2", "hereee");

        query = ParseQuery.getQuery("Users");
        passTwo = (EditText) findViewById(R.id.retypeET);
        name = (EditText) findViewById(R.id.nameET);
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);
        email = (EditText) findViewById(R.id.emailET);

        picture = (ImageButton) findViewById(R.id.ppic);
        passTwo.setOnFocusChangeListener(this);
        passTwo.setOnKeyListener(this);

        picture.setOnClickListener(this);
        name.setOnFocusChangeListener(this);
        username.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        name.setOnKeyListener(this);
        username.setOnKeyListener(this);
        password.setOnKeyListener(this);
        email.setOnKeyListener(this);

        Log.e("Here3", "hereee");

        finish = new Intent(this,HomePage.class);
        // start Facebook Login
        /*
        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state,
                             Exception exception) {
                if (session.isOpened()) {

                    // make request to the /me API
                    Request.executeMeRequestAsync(session,
                            new Request.GraphUserCallback() {
                                @Override
                                public void onCompleted(GraphUser user,
                                                        Response response) {
                                    if (user != null) {
                                        name.setText(user.getName());
                                        FBTask task = new FBTask();
                                        task.execute(user.getUsername());
                                        dialog.dismiss();
                                        fb = false;
                                    }
                                }
                            });
                }
            }
        });
*/

        Log.e("Here4","hereee");

        final View activityRootView = findViewById(R.id.activityRoot2);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();

                        activityRootView.getWindowVisibleDisplayFrame(r);

                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - (r.bottom - r.top);
                        if (heightDiff > 100 || heightDiff <-100) {
                            String length = name.getText().toString();
                            String pass = password.getText().toString();
                            String mail = email.getText().toString();
                            String user = username.getText().toString();
                            if (length.length() >= 3 && mail.length() >= 3
                                    && user.length() >= 1 && pass.length() >= 1)
                                nameLong = true;

                            else
                                nameLong = false;

                            if (nameLong)
                                toNext.setTextColor(Color.parseColor("#FFFFFF"));

                            else
                                toNext.setTextColor(Color.parseColor("#a0a0a0"));

                        }
                    }
                });

        Log.e("Here5", "hereee");

        textwatcher = new TextWatcher() {
            int counter = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ShowToast")
            @Override
            public void afterTextChanged(Editable editable) {
                //here, after we introduced something in the EditText we get the string from it
                String answerString = username.getText().toString();
                int length = answerString.length();
                username.setSelection(length, length);
                String pattern = "[^a-zA-Z0-9]";
                Pattern check = Pattern.compile(pattern);
                Matcher regex = check.matcher(answerString);

                if (regex.find() && counter <= 2){
                    counter++;
                    int start = regex.start();
                    int end = regex.end();
                    Toast.makeText(SignUp.this,"Username can only be letters and numbers", Toast.LENGTH_SHORT).show();
                }


                //and now we make a Toast
                //modify "yourActivity.this" with your activity name .this
            }
        };
        Log.e("Here6","hereee");



        username.addTextChangedListener(textwatcher);

        TextWatcher textWatcherTwo = new TextWatcher() {
            int namec = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ShowToast")
            @Override
            public void afterTextChanged(Editable editable) {
                //here, after we introduced something in the EditText we get the string from it
                String answerString = name.getText().toString();
                int length = answerString.length();
                name.setSelection(length, length);
                String pattern = "[^a-zA-Z\\s]";
                Pattern check = Pattern.compile(pattern);
                Matcher regex = check.matcher(answerString);

                if (regex.find() && namec <= 2){
                    namec++;
                    int start = regex.start();
                    int end = regex.end();
                    Toast.makeText(SignUp.this,"Name can only contain letters", Toast.LENGTH_SHORT).show();
                }


                //and now we make a Toast
                //modify "yourActivity.this" with your activity name .this
            }

        };

        Log.e("Here7","hereee");

        name.addTextChangedListener(textWatcherTwo);
        user = new Users();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.whereExists("objectId");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                numPeople = scoreList.size();
            }
        });


        TextWatcher textWatcherThree = new TextWatcher() {
            int namec = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ShowToast")
            @Override
            public void afterTextChanged(Editable editable) {
                //here, after we introduced something in the EditText we get the string from it
                if (passTwo.getText().toString().length() == password.getText().toString().length()){
                    if (!passTwo.getText().toString().equals(password.getText().toString()) && namec%3 == 0){
                        namec++;
                        Toast.makeText(SignUp.this,"Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
                //and now we make a Toast
                //modify "yourActivity.this" with your activity name .this
            }

        };
        passTwo.addTextChangedListener(textWatcherThree);

        TextWatcher textWatcherFour = new TextWatcher() {
            int namec = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ShowToast")
            @Override
            public void afterTextChanged(Editable editable) {
                //here, after we introduced something in the EditText we get the string from it
                String mail = email.getText().toString();
                if (mail.contains(".") && mail.indexOf(".") != mail.length()-1){
                    String length = name.getText().toString();
                    String pass = password.getText().toString();
                    String user = username.getText().toString();
                    String re = passTwo.getText().toString();
                    if (length.length() >= 2 && mail.length() >= 3
                            && user.length() >= 1 && pass.length() >= 1 && re.equals(pass))
                        nameLong = true;

                    else
                        nameLong = false;

                    if (nameLong)
                        toNext.setTextColor(Color.parseColor("#FFFFFF"));

                    else
                        toNext.setTextColor(Color.parseColor("#a0a0a0"));

                    //and now we make a Toast
                    //modify "yourActivity.this" with your activity name .this
                }
                else{
                    nameLong = false;
                    toNext.setTextColor(Color.parseColor("#a0a0a0"));
                }
            }

        };
        email.addTextChangedListener(textWatcherFour);


        Log.e("Here8", "hereee");

    }




    @SuppressLint("NewApi")
    private void setActionBar() {
        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        // displaying custom ActionBar
        View mActionBarView = getLayoutInflater()
                .inflate(R.layout.topbar, null);
        toNext = (Button) mActionBarView.findViewById(R.id.next);
        toNext.setTypeface(type);
        toNext.setOnClickListener(this);
        toNext.setOnTouchListener(this);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fb == true) {
        /*    Session.getActiveSession().onActivityResult(this, requestCode,
                    resultCode, data);*/
        }

        if (resultCode == RESULT_OK && requestCode == TAKEPHOTO) {
            Uri imageUri = null;
            imageUri = data.getData();
            performCrop(imageUri);
            dialogTwo.dismiss();

        }

        if (resultCode == RESULT_OK && requestCode == CROPPED) {
            Bundle ex = data.getExtras();
            bit = (Bitmap) ex.get("data");
            picture.setImageBitmap(bit);
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 100, outstream);

            // get byte array here
            bytearray= outstream.toByteArray();
            clickedImage = true;
        }

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {

            Uri selectedImageUri = data.getData();
            performCrop(selectedImageUri);
            dialogTwo.dismiss();

        }
    }

    public class FBTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap mIcon1;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            URL img_value = null;
            Uri uri =  Uri.parse("http://graph.facebook.com/" + params[0]
                    + "/picture?type=large");
            try {


                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                // indicate image type and Uri
                cropIntent.setDataAndType(uri, "image/*");
                // set crop properties
                cropIntent.putExtra("crop", "true");
                // indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                // indicate output X and Y
                cropIntent.putExtra("outputX", 128);
                cropIntent.putExtra("outputY", 128);
                // retrieve data on return
                cropIntent.putExtra("return-data", true);
                // start the activity - we handle returning in onActivityResult

                startActivityForResult(cropIntent, CROPPED);
            }
            // respond to users whose devices do not support the crop action
            catch (ActivityNotFoundException anfe) {
                // display an error message
            }


            return null;
        }

    }

    private void performCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult

            startActivityForResult(cropIntent, CROPPED);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast
                    .makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (arg0.getId() == R.id.next) {
            if (nameLong && clickedImage == true){
                String answerString = username.getText().toString();
                String pattern = "[^a-zA-Z0-9]";
                Pattern check = Pattern.compile(pattern);
                Matcher regex = check.matcher(answerString);

                if (regex.find()){
                    Toast.makeText(SignUp.this,"Username can only be letters and numbers", Toast.LENGTH_SHORT).show();
                }

                else if (password.getText().toString().length() < 6 || password.getText().toString().length() > 20)
                    Toast.makeText(this, "Password must be 6-20 characters", Toast.LENGTH_LONG).show();

                else{
                    progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setCancelable(false);

                    progressDialog.show();

                   file = new ParseFile(username.getText().toString() + ".jpg", bytearray);
                    file.saveInBackground(new SaveCallback() {

                        public void done(ParseException e) {
                            if (e != null) {
                                Toast.makeText(getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                user.setPhotoFile(file);
                                user.setName(name.getText().toString());

                                // Associate the meal with the current user
                                user.setUser(username.getText().toString());

                                // Add the rating
                                user.setPass(password.getText().toString());
                                user.setEmail(email.getText().toString());
                                userId = user.getObjectId();

                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            progressDialog.dismiss();
                                            finish.putExtra("num",numPeople);
                                            SharedPreferences sp = getSharedPreferences(key, 0);
                                            Editor edit = sp.edit();
                                            edit.putString("username", username.getText().toString());
                                            edit.putString("userId", userId);
                                            edit.commit();
                                            String file_Url = user.getParseFile("profilepic").getUrl();
                                            user.put("profileurl", file_Url);
                                            user.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    progressDialog.dismiss();
                                                    finish.putExtra("num", numPeople);
                                                    startActivity(finish);
                                                    finish();
                                                }
                                            });

                                        } else {
                                            Toast.makeText(
                                                    getApplicationContext().getApplicationContext(),
                                                    "Error saving: " + e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                            }
                        }
                    });

                }
            }
        }

        if (arg0.getId() == R.id.ppic) {
            photoDialog();
        }
    }

    public void photoDialog() {
        // Create custom dialog object
        dialogTwo = new Dialog(SignUp.this,
                R.style.myBackgroundStyle);
        // Include dialog.xml file
        dialogTwo.setContentView(R.layout.dialog);
        // Set dialog title
        dialogTwo.setTitle("Profile Photo");

        // set values for custom dialog components - text, image and button

        dialogTwo.show();

        galleryB = (Button) dialogTwo.findViewById(R.id.gallery);
        // if decline button is clicked, close the custom dialog
        galleryB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);
            }
        });

        take = (Button) dialogTwo.findViewById(R.id.takephoto);
        // if decline button is clicked, close the custom dialog

        galleryB.setOnTouchListener(this);
        take.setOnTouchListener(this);
        take.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                Intent i = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, TAKEPHOTO);
            }
        });
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        // TODO Auto-generated method stub
        if (arg0.getId() == R.id.next && nameLong) {
            switch (arg1.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    toNext.setBackgroundColor(Color.parseColor("#b11327"));
                    break;
                case (MotionEvent.ACTION_UP):
                    toNext.setBackgroundColor(Color.parseColor("#d52c45"));
                    break;
            }
        }

        if (arg0.getId() == R.id.gallery) {
            switch (arg1.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    galleryB.setBackgroundColor(Color.parseColor("#34c5fa"));
                    break;
                case (MotionEvent.ACTION_UP):
                    galleryB.setBackgroundColor(Color.parseColor("#FF000000"));
                    break;
            }
        }

        if (arg0.getId() == R.id.takephoto) {
            switch (arg1.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    take.setBackgroundColor(Color.parseColor("#34c5fa"));
                    break;
                case (MotionEvent.ACTION_UP):
                    take.setBackgroundColor(Color.parseColor("#FF000000"));
                    break;
            }
        }

        if (arg0.getId() == R.id.now) {
            switch (arg1.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    yes.setBackgroundColor(Color.parseColor("#34c5fa"));
                    break;
                case (MotionEvent.ACTION_UP):
                    yes.setBackgroundColor(Color.parseColor("#FF000000"));
                    break;
            }
        }

        if (arg0.getId() == R.id.later) {
            switch (arg1.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    no.setBackgroundColor(Color.parseColor("#34c5fa"));
                    break;
                case (MotionEvent.ACTION_UP):
                    no.setBackgroundColor(Color.parseColor("#FF000000"));
                    break;
            }
        }
        return super.onTouchEvent(arg1);
    }

    @Override
    public void onFocusChange(View arg0, boolean arg1) {
        // TODO Auto-generated method stub
        String length = name.getText().toString();
        String pass = password.getText().toString();
        String mail = email.getText().toString();
        String user = username.getText().toString();
        if (length.length() >= 3 && mail.length() >= 3
                && user.length() >= 1 && pass.length() >= 1)
            nameLong = true;

        else
            nameLong = false;

        if (nameLong)
            toNext.setTextColor(Color.parseColor("#FFFFFF"));

        else
            toNext.setTextColor(Color.parseColor("#a0a0a0"));

        if (arg0.getId() == R.id.passwordET && arg1 == false && (pass.length() < 6 || pass.length() > 20)){
            Toast.makeText(this, "Password must be 6-20 characters", Toast.LENGTH_LONG).show();
        }
        if (arg0.getId() == R.id.usernameET && arg1 == false && username.getText().toString().length() >= 3){

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
            query.whereEqualTo("username", username.getText().toString());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (scoreList.size() != 0){
                        Toast.makeText(getApplicationContext(), "Username " + username.getText().toString() + " is taken. Please choose different username", Toast.LENGTH_LONG).show();
                        nameLong = false;
                    }

                    else
                        nameLong = true;
                }
            });
        }

        if (arg0.getId() == R.id.usernameET && arg1 == true
                && username.getText().toString().length() >= 3) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
            query.whereEqualTo("username", username.getText().toString());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (scoreList.size() != 0) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Username "
                                        + username.getText().toString()
                                        + " is taken. Please choose different username",
                                Toast.LENGTH_LONG).show();
                        nameLong = false;
                    }

                    else
                        nameLong = true;
                }
            });
        }

        if (arg0.getId() == R.id.retypeET && arg1 == false){
            if (!passTwo.getText().toString().equals(password.getText().toString()))
                Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        if ((arg2.getAction() == KeyEvent.ACTION_DOWN)
                && (arg1 == KeyEvent.KEYCODE_ENTER)) {
            String length = name.getText().toString();
            String pass = password.getText().toString();
            String mail = email.getText().toString();
            String user = username.getText().toString();
            String re = passTwo.getText().toString();


            if (length.length() >= 2 && mail.length() >= 3
                    && user.length() >= 1 && pass.length() >= 1 && re.equals(pass))
                nameLong = true;

            else
                nameLong = false;

            if (nameLong)
                toNext.setTextColor(Color.parseColor("#FFFFFF"));

            else
                toNext.setTextColor(Color.parseColor("#a0a0a0"));

            return true;
        }
        return false;
    }
}
