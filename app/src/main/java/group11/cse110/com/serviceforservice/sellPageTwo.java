package group11.cse110.com.serviceforservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Phung on 1/18/16.
 */
public class sellPageTwo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("sell Page Two");


        View rootView = inflater.inflate(R.layout.sellpagetwo, container, false);


        Bundle bundle = this.getArguments();
        int sellSelection = bundle.getInt("SellDecision");

        int buffer = 0;
        int inputMax = 6;

        for(int i = 0; i < inputMax; i++) {
            String label = "SD" + i;
            int sdSelection = bundle.getInt(label);
            buffer = buffer | sdSelection;
            buffer = buffer << 1;
        }


        return rootView;
    }

}
