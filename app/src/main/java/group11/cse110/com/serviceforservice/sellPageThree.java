package group11.cse110.com.serviceforservice;

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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Phung on 1/18/16.
 */
public class sellPageThree extends Fragment {
    static int buffer = 0;
    View rootView;
    EditText description;
    int sellSelection;
    boolean sold;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("sell Page Three");


        rootView = inflater.inflate(R.layout.sellpagethree, container, false);
        description = (EditText) sellPageThree.this.rootView.findViewById(R.id.textEditForAdditionalInfo) ;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        //actionBar.setTitle("Sell Form");
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
        }

        Button submit= (Button)rootView.findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BuyFragment();
                ParseObject sellObject = new ParseObject("Selling");
                sellObject.put("sellCategory", sellSelection);
                sellObject.put("wantCategory", buffer);
                sellObject.put("sold", sold);
                List<String> listBuyers = new ArrayList<String>();
                sellObject.add("listofbuyers",listBuyers);
                //ParseQuery<ParseObject> query = ParseQuery.getQuery("Selling");

                sellObject.put("description", description.getText().toString());

                sellObject.saveInBackground();

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FrameLayout layout = (FrameLayout) rootView.findViewById(R.id.sellPageThree);
                layout.removeAllViewsInLayout();
                fragmentTransaction.replace(R.id.sellPageThree, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });
        return rootView;
    }

}
