package group11.cse110.com.serviceforservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by jennifer on 2/25/16.
 */
public class buyPageOne extends Fragment{
    //Keeps track of which radio button was selected
    static int buyDecision = 0;
    static View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buypageone, container, false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int halfWidth = width/2;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        actionBar.setTitle("Buy Form");

        root = rootView;

        Button cont1= (Button)rootView.findViewById(R.id.cont1BuyForm);

        RadioGroup group1 = (RadioGroup)rootView.findViewById(R.id.buyRG1);
        RadioGroup group2 = (RadioGroup)rootView.findViewById(R.id.buyRG2);
        final RadioButton buyFood = (RadioButton)group1.getChildAt(0);
        final RadioButton buyLanguage = (RadioButton)group1.getChildAt(1);
        final RadioButton buyMoney = (RadioButton)group1.getChildAt(2);
        final RadioButton buyEntertainment = (RadioButton)group2.getChildAt(0);
        final RadioButton buyHousing = (RadioButton)group2.getChildAt(1);
        final RadioButton buyTransportation = (RadioButton)group2.getChildAt(2);

        System.out.println("HALF WIDTH " + halfWidth);
        buyFood.getLayoutParams().height = halfWidth;
        buyFood.getLayoutParams().width = halfWidth;
        buyLanguage.getLayoutParams().height = halfWidth;
        buyLanguage.getLayoutParams().width = halfWidth;
        buyMoney.getLayoutParams().height = halfWidth;
        buyMoney.getLayoutParams().width = halfWidth;
        buyEntertainment.getLayoutParams().height = halfWidth;
        buyEntertainment.getLayoutParams().width = halfWidth;
        buyHousing.getLayoutParams().height = halfWidth;
        buyHousing.getLayoutParams().width = halfWidth;
        buyTransportation.getLayoutParams().height = halfWidth;
        buyTransportation.getLayoutParams().width = halfWidth;

        buyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFood.setChecked(true);
                buyHousing.setChecked(false);
                buyEntertainment.setChecked(false);
                buyMoney.setChecked(false);
                buyLanguage.setChecked(false);
                buyTransportation.setChecked(false);

                buyDecision = 1;
            }
        });

        buyLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyLanguage.setChecked(true);
                buyFood.setChecked(false);
                buyEntertainment.setChecked(false);
                buyMoney.setChecked(false);
                buyHousing.setChecked(false);
                buyTransportation.setChecked(false);

                buyDecision = 2;
            }
        });

        buyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMoney.setChecked(true);
                buyFood.setChecked(false);
                buyEntertainment.setChecked(false);
                buyHousing.setChecked(false);
                buyLanguage.setChecked(false);
                buyTransportation.setChecked(false);

                buyDecision = 3;
            }
        });

        buyEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyEntertainment.setChecked(true);
                buyFood.setChecked(false);
                buyHousing.setChecked(false);
                buyMoney.setChecked(false);
                buyLanguage.setChecked(false);
                buyTransportation.setChecked(false);

                buyDecision = 4;
            }
        });

        buyHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyHousing.setChecked(true);
                buyFood.setChecked(false);
                buyEntertainment.setChecked(false);
                buyMoney.setChecked(false);
                buyLanguage.setChecked(false);
                buyTransportation.setChecked(false);

                buyDecision = 5;
            }
        });

        buyTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyTransportation.setChecked(true);
                buyFood.setChecked(false);
                buyEntertainment.setChecked(false);
                buyMoney.setChecked(false);
                buyLanguage.setChecked(false);
                buyHousing.setChecked(false);

                buyDecision = 6;
            }
        });


        cont1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new buyPageTwo();
                Bundle bundle = new Bundle();
                bundle.putInt("BuyDecision", buyDecision);

                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FrameLayout layout = (FrameLayout) root.findViewById(R.id.buyPageOne);
                layout.removeAllViewsInLayout();
                fragmentTransaction.replace(R.id.buyPageOne, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }
}
