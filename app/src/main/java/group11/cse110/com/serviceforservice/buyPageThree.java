package group11.cse110.com.serviceforservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * Created by jennifer on 2/25/16.
 */
public class buyPageThree extends Fragment {
    static int buffer = 0;
    View rootView;
    EditText description;
    int buySelection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("buy Page Three");


        rootView = inflater.inflate(R.layout.buypagethree, container, false);
        description = (EditText) buyPageThree.this.rootView.findViewById(R.id.additionalInfoForBuy) ;


        Bundle bundle = this.getArguments();
        buySelection = bundle.getInt("BuyDecision");


        int inputMax = 6;

        for(int i = 0; i < inputMax; i++) {
            String label = "GIVE" + i;
            buffer = buffer << 1;
            int buySelection = bundle.getInt(label);
            buffer = buffer | buySelection;
        }

        Button submit= (Button)rootView.findViewById(R.id.buySubmitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BuyFragment();
                ParseObject buyObject = new ParseObject("Buying");
                buyObject.put("sellCategory", buySelection);
                buyObject.put("wantCategory", buffer);
                List<String> listBuyers = new ArrayList<String>();
                buyObject.add("listofbuyers",listBuyers);
                //ParseQuery<ParseObject> query = ParseQuery.getQuery("Selling");

                buyObject.put("description", description.getText().toString());

                buyObject.saveInBackground();

                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FrameLayout layout = (FrameLayout) rootView.findViewById(R.id.buyPageThree);
                layout.removeAllViewsInLayout();
                fragmentTransaction.replace(R.id.buyPageThree, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });
        return rootView;
    }
}
