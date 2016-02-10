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
        View rootView = inflater.inflate(R.layout.buy, container, false);

        Bundle bundle = this.getArguments();
        int defaultValue = 0;
       // int sellSelection = bundle.getInt(sell, defaultValue);

        return rootView;
    }

}
