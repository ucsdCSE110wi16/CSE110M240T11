package group11.cse110.com.serviceforservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

/**
 * Created by jennifer on 2/23/16.
 */
public class sellPageTwo extends Fragment {
    static int[] exchangeDecision = new int[6];
    static View root;
    int sellDecision;
    boolean canCont;

    private boolean checkSelection(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        System.out.println("sell Page Two");

        View rootView = inflater.inflate(R.layout.sellpagetwo, container, false);
        root = rootView;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Sell Form</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int halfWidth = width/2;

        Bundle bundle = this.getArguments();
        sellDecision = bundle.getInt("SellDecision");

        Button cont2= (Button)rootView.findViewById(R.id.cont2Button);

        final CheckBox food = (CheckBox)rootView.findViewById(R.id.foodCheck);
        final CheckBox housing = (CheckBox)rootView.findViewById(R.id.housingCheck);
        final CheckBox entertainment = (CheckBox)rootView.findViewById(R.id.entertainmentCheck);
        final CheckBox money = (CheckBox)rootView.findViewById(R.id.moneyCheck);
        final CheckBox language = (CheckBox)rootView.findViewById(R.id.langCheck);
        final CheckBox transportation = (CheckBox)rootView.findViewById(R.id.transportationCheck);

        System.out.println("HALF WIDTH " + halfWidth);
        food.getLayoutParams().height = halfWidth;
        food.getLayoutParams().width = halfWidth;
        language.getLayoutParams().height = halfWidth;
        language.getLayoutParams().width = halfWidth;
        money.getLayoutParams().height = halfWidth;
        money.getLayoutParams().width = halfWidth;
        entertainment.getLayoutParams().height = halfWidth;
        entertainment.getLayoutParams().width = halfWidth;
        housing.getLayoutParams().height = halfWidth;
        housing.getLayoutParams().width = halfWidth;
        transportation.getLayoutParams().height = halfWidth;
        transportation.getLayoutParams().width = halfWidth;


        for(int i = 0; i < exchangeDecision.length; i++) {
            exchangeDecision[i] = 0;
        }

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.isChecked()) {
                    exchangeDecision[0] = 1;
                }
                else {
                    exchangeDecision[0] = 0;
                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.isChecked()) {
                    exchangeDecision[1] = 1;
                }
                else {
                    exchangeDecision[1] = 0;
                }
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money.isChecked()) {
                    exchangeDecision[2] = 1;
                }
                else {
                    exchangeDecision[2] = 0;
                }
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entertainment.isChecked()) {
                    exchangeDecision[3] = 1;
                }
                else {
                    exchangeDecision[3] = 0;
                }
            }
        });

        housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (housing.isChecked()) {
                    exchangeDecision[4] = 1;
                }
                else {
                    exchangeDecision[4] = 0;
                }
            }
        });

        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transportation.isChecked()) {
                    exchangeDecision[5] = 1;
                } else {
                    exchangeDecision[5] = 0;
                }
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Oh no!");
        alertDialog.setMessage("Please make at least one selection.");
        alertDialog.setButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        cont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canCont = checkSelection(exchangeDecision);

                if (canCont) {
                    Fragment fragment = new sellPageThree();
                    Bundle bundle = new Bundle();
                    bundle.putInt("SellDecision", sellDecision);
                    fragment.setArguments(bundle);

                    for (int i = 0; i < exchangeDecision.length; i++) {
                        String label = "SD" + i;
                        bundle.putInt(label, exchangeDecision[i]);
                    }

                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FrameLayout layout = (FrameLayout) root.findViewById(R.id.sellPageTwo);
                    layout.removeAllViewsInLayout();
                    fragmentTransaction.replace(R.id.sellPageTwo, fragment);
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
