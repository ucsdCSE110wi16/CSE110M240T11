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

import com.parse.ParseException;
import com.parse.ParseObject;

import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Phung on 1/18/16.
 */
public class sellPageThree extends Fragment {
    static int buffer;
    View rootView;
    EditText description;
    int sellSelection;
    boolean sold;
    boolean descriptionCheck;
    String str;
    String username;
    ParseQuery userQuery;
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
        buffer = 0;
        System.out.println("sell Page Three");


        rootView = inflater.inflate(R.layout.sellpagethree, container, false);
        description = (EditText) sellPageThree.this.rootView.findViewById(R.id.textEditForAdditionalInfo);

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Sell Form</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        Bundle bundle = this.getArguments();
        sellSelection = bundle.getInt("SellDecision");


        int inputMax = 6;
        sold = false;

        for(int i = 0; i < inputMax; i++) {
            String label = "SD" + i;
            buffer = buffer << 1;
            int sdSelection = bundle.getInt(label);
            buffer = buffer | sdSelection;
            System.out.println("BUFFER " + buffer);
        }

        SharedPreferences sp = getActivity().getSharedPreferences(key, 0);
        username = sp.getString("username",null);

        Button submit= (Button)rootView.findViewById(R.id.submitButton);

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
                    final ParseObject sellObject = new ParseObject("Selling");
                    sellObject.put("sellCategory", sellSelection);
                    sellObject.put("wantCategory", buffer);
                    sellObject.put("sold", sold);
                    sellObject.put("username",username);
                    List<String> listBuyers = new ArrayList<String>();
                    sellObject.add("listofbuyers", listBuyers);
                    //ParseQuery<ParseObject> query = ParseQuery.getQuery("Selling");

                    sellObject.put("description", description.getText().toString());

                    sellObject.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                userQuery = ParseQuery.getQuery("Users");
                                userQuery.whereEqualTo("username", username);
                                try {
                                    ParseObject userObj = userQuery.getFirst();
                                    List<String> list = userObj.getList("myPosts");
                                    list.add(sellObject.getObjectId());
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
                    FrameLayout layout = (FrameLayout) rootView.findViewById(R.id.sellPageThree);
                    layout.removeAllViewsInLayout();
                    fragmentTransaction.replace(R.id.sellPageThree, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else {
                    alertDialog.show();
                }
            }

        });
        return rootView;
    }

}
