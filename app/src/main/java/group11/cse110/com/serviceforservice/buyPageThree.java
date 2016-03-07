package group11.cse110.com.serviceforservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.parse.ParseObject;

import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennifer on 2/25/16.
 */
public class buyPageThree extends Fragment {
    static int buffer = 0;
    View rootView;
    EditText description;
    int buySelection;
    boolean bought;
    boolean descriptionCheck;
    String str;
    String username;
    ParseQuery<ParseObject> userQuery;
    private static final String key = "MySharedData";

    private boolean checkEmpty(String str) {
        if (str.length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("buy Page Three");


        rootView = inflater.inflate(R.layout.buypagethree, container, false);
        description = (EditText) buyPageThree.this.rootView.findViewById(R.id.additionalInfoForBuy) ;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Buy Form</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        Bundle bundle = this.getArguments();
        buySelection = bundle.getInt("BuyDecision");

        bought = false;
        int inputMax = 6;

        for(int i = 0; i < inputMax; i++) {
            String label = "GIVE" + i;
            buffer = buffer << 1;
            int buySelection = bundle.getInt(label);
            buffer = buffer | buySelection;
        }

        SharedPreferences sp = getActivity().getSharedPreferences(key, 0);
        username = sp.getString("username",null);

        Button submit= (Button)rootView.findViewById(R.id.buySubmitButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Oh no!");
        alertDialog.setMessage("Please enter a description with the specifics for your post.");
        alertDialog.setButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = description.getText().toString();
                descriptionCheck = checkEmpty(str);

                if (descriptionCheck) {
                    Fragment fragment = new BuyFragment();
                    final ParseObject buyObject = new ParseObject("Buying");
                    buyObject.put("sellCategory", buySelection);
                    buyObject.put("wantCategory", buffer);
                    buyObject.put("sold", false);
                    buyObject.put("username", username);
                    List<String> listBuyers = new ArrayList<String>();
                    buyObject.add("listofbuyers", listBuyers);
                    //ParseQuery<ParseObject> query = ParseQuery.getQuery("Selling");

                    buyObject.put("description", description.getText().toString());

                    buyObject.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                userQuery = ParseQuery.getQuery("Users");
                                userQuery.whereEqualTo("username", username);
                                try {
                                    ParseObject userObj = userQuery.getFirst();
                                    List<String> list = userObj.getList("myPosts");
                                    list.add(buyObject.getObjectId());
                                    userObj.put("myPosts",list);
                                    userObj.saveInBackground();
                                }
                                catch (Exception ex){

                                }
                            } else {

                            }
                        }
                    });

                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FrameLayout layout = (FrameLayout) rootView.findViewById(R.id.buyPageThree);
                    layout.removeAllViewsInLayout();
                    fragmentTransaction.replace(R.id.buyPageThree, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    alertDialog.show();
                }
            }

        });
        return rootView;
    }
}
