package group11.cse110.com.serviceforservice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Kim on 1/18/16.
 */
public class SellFragment extends Fragment {

    //Keeps track of which radio button was selected
    static int sellCategory = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sell, container, false);


        Button next= (Button)rootView.findViewById(R.id.submitButton);
        RadioGroup group1 = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
        RadioGroup group2 = (RadioGroup)rootView.findViewById(R.id.radioGroup2);
        RadioGroup group3 = (RadioGroup)rootView.findViewById(R.id.radioGroup3);
        final RadioButton sellFood = (RadioButton)group1.getChildAt(0);
        final RadioButton sellHousing = (RadioButton)group1.getChildAt(1);
        final RadioButton sellEntertainment = (RadioButton)group2.getChildAt(0);
        final RadioButton sellMoney = (RadioButton)group2.getChildAt(1);
        final RadioButton sellLanguage = (RadioButton)group3.getChildAt(0);
        final RadioButton sellTransportation = (RadioButton)group3.getChildAt(1);

        final int sellDecision = 0;

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

        sellHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellHousing.setChecked(true);
                sellFood.setChecked(false);
                sellEntertainment.setChecked(false);
                sellMoney.setChecked(false);
                sellLanguage.setChecked(false);
                sellTransportation.setChecked(false);

                sellDecision = 2;
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

                sellDecision = 3;
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

                sellDecision = 4;
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

        final CheckBox food = (CheckBox)rootView.findViewById(R.id.foodCheck);
        final CheckBox housing = (CheckBox)rootView.findViewById(R.id.housingCheck);
        final CheckBox entertainment = (CheckBox)rootView.findViewById(R.id.entertainmentCheck);
        final CheckBox money = (CheckBox)rootView.findViewById(R.id.moneyCheck);
        final CheckBox language = (CheckBox)rootView.findViewById(R.id.langCheck);
        final CheckBox transportation = (CheckBox)rootView.findViewById(R.id.transportationCheck);
        final int[] exchangeDecision = new int[6];

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

        housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (housing.isChecked()) {
                    exchangeDecision[1] = 1;
                }
                else {
                    exchangeDecision[1] = 0;
                }
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entertainment.isChecked()) {
                    exchangeDecision[2] = 1;
                }
                else {
                    exchangeDecision[2] = 0;
                }
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money.isChecked()) {
                    exchangeDecision[3] = 1;
                }
                else {
                    exchangeDecision[3] = 0;
                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.isChecked()) {
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
                    exchangeDecision[0] = 1;
                }
                else {
                    exchangeDecision[0] = 0;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new sellPageTwo();

                Bundle bundle = new Bundle();
                //Not really sure what to do here, since we'd be getting our info from
                //onActvityCreated right? Also, would I be putting twice?? Once for
                //what to sell, and once for what they are willing to exchange for?
                bundle.putInt("Sell Decision", sellDecision);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.sell, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }
}
