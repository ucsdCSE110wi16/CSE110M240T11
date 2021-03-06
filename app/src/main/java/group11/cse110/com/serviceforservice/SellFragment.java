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
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Kim on 1/18/16.
 */
public class SellFragment extends Fragment {

    //Keeps track of which radio button was selected
    static int sellDecision = 0;
    static View root;
    boolean selectionCheck;

    private boolean checkSelection(int num) {
        if (num > 0) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sell, container, false);
        root = rootView;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int halfWidth = width/2;



        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();

        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Sell Form</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        Button cont1= (Button)rootView.findViewById(R.id.cont1Button);

        RadioGroup group1 = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
        RadioGroup group2 = (RadioGroup)rootView.findViewById(R.id.radioGroup2);
        final RadioButton sellFood = (RadioButton)group1.getChildAt(0);
        final RadioButton sellLanguage = (RadioButton)group1.getChildAt(1);
        final RadioButton sellMoney = (RadioButton)group1.getChildAt(2);
        final RadioButton sellEntertainment = (RadioButton)group2.getChildAt(0);
        final RadioButton sellHousing = (RadioButton)group2.getChildAt(1);
        final RadioButton sellTransportation = (RadioButton)group2.getChildAt(2);

        System.out.println("HALF WIDTH " + halfWidth);
        sellFood.getLayoutParams().height = halfWidth;
        sellFood.getLayoutParams().width = halfWidth;
        sellLanguage.getLayoutParams().height = halfWidth;
        sellLanguage.getLayoutParams().width = halfWidth;
        sellMoney.getLayoutParams().height = halfWidth;
        sellMoney.getLayoutParams().width = halfWidth;
        sellEntertainment.getLayoutParams().height = halfWidth;
        sellEntertainment.getLayoutParams().width = halfWidth;
        sellHousing.getLayoutParams().height = halfWidth;
        sellHousing.getLayoutParams().width = halfWidth;
        sellTransportation.getLayoutParams().height = halfWidth;
        sellTransportation.getLayoutParams().width = halfWidth;

        sellFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellFood.setChecked(true);
                sellHousing.setChecked(false);
                sellEntertainment.setChecked(false);
                sellMoney.setChecked(false);
                sellLanguage.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 1;
            }
        });

        sellLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellLanguage.setChecked(true);
                sellFood.setChecked(false);
                sellEntertainment.setChecked(false);
                sellMoney.setChecked(false);
                sellHousing.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 2;
            }
        });

        sellMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellMoney.setChecked(true);
                sellFood.setChecked(false);
                sellEntertainment.setChecked(false);
                sellHousing.setChecked(false);
                sellLanguage.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 3;
            }
        });

        sellEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellEntertainment.setChecked(true);
                sellFood.setChecked(false);
                sellHousing.setChecked(false);
                sellMoney.setChecked(false);
                sellLanguage.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 4;
            }
        });

        sellHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellHousing.setChecked(true);
                sellFood.setChecked(false);
                sellEntertainment.setChecked(false);
                sellMoney.setChecked(false);
                sellLanguage.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 5;
            }
        });

        sellTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellTransportation.setChecked(true);
                sellFood.setChecked(false);
                sellEntertainment.setChecked(false);
                sellMoney.setChecked(false);
                sellLanguage.setChecked(false);
                sellHousing.setChecked(false);

                sellDecision = 6;
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Oh no!");
        alertDialog.setMessage("Please make a selection.");
        alertDialog.setButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        cont1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionCheck = checkSelection(sellDecision);

                if (selectionCheck) {
                    Fragment fragment = new sellPageTwo();
                    Bundle bundle = new Bundle();
                    bundle.putInt("SellDecision", sellDecision);

                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FrameLayout layout = (FrameLayout) root.findViewById(R.id.sell);
                    layout.removeAllViewsInLayout();
                    fragmentTransaction.replace(R.id.sell, fragment);
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
