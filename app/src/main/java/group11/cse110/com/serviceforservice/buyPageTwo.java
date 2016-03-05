package group11.cse110.com.serviceforservice;

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
 * Created by jennifer on 2/25/16.
 */
public class buyPageTwo extends Fragment{
    static int[] canGiveDecision = new int[6];
    static View root;
    int buyDecision;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        System.out.println("buy Page two");

        View rootView = inflater.inflate(R.layout.buypagetwo, container, false);
        root = rootView;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int halfWidth = width/2;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        //actionBar.setTitle("Buy Form");
        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Buy Form</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        Bundle bundle = this.getArguments();
        buyDecision = bundle.getInt("BuyDecision");

        Button cont2= (Button)rootView.findViewById(R.id.cont2BuyForm);

        final CheckBox food = (CheckBox)rootView.findViewById(R.id.provideFood);
        final CheckBox housing = (CheckBox)rootView.findViewById(R.id.provideHousing);
        final CheckBox entertainment = (CheckBox)rootView.findViewById(R.id.provideEntertainment);
        final CheckBox money = (CheckBox)rootView.findViewById(R.id.provideMoney);
        final CheckBox language = (CheckBox)rootView.findViewById(R.id.provideLang);
        final CheckBox transportation = (CheckBox)rootView.findViewById(R.id.provideTransportation);

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

        for(int i = 0; i < canGiveDecision.length; i++) {
            canGiveDecision[i] = 0;
        }

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.isChecked()) {
                    canGiveDecision[0] = 1;
                }
                else {
                    canGiveDecision[0] = 0;
                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.isChecked()) {
                    canGiveDecision[1] = 1;
                }
                else {
                    canGiveDecision[1] = 0;
                }
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money.isChecked()) {
                    canGiveDecision[2] = 1;
                }
                else {
                    canGiveDecision[2] = 0;
                }
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entertainment.isChecked()) {
                    canGiveDecision[3] = 1;
                }
                else {
                    canGiveDecision[3] = 0;
                }
            }
        });

        housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (housing.isChecked()) {
                    canGiveDecision[4] = 1;
                }
                else {
                    canGiveDecision[4] = 0;
                }
            }
        });

        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transportation.isChecked()) {
                    canGiveDecision[5] = 1;
                }
                else {
                    canGiveDecision[5] = 0;
                }
            }
        });

        cont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new buyPageThree();
                Bundle bundle = new Bundle();
                bundle.putInt("BuyDecision", buyDecision);
                fragment.setArguments(bundle);

                for (int i = 0; i < canGiveDecision.length; i++) {
                    String label = "GIVE" + i;
                    bundle.putInt(label, canGiveDecision[i]);
                }

                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FrameLayout layout = (FrameLayout) root.findViewById(R.id.buyPageTwo);
                layout.removeAllViewsInLayout();
                fragmentTransaction.replace(R.id.buyPageTwo, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }
}
