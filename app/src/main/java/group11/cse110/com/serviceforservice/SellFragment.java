package group11.cse110.com.serviceforservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by Kim on 1/18/16.
 */
public class SellFragment extends Fragment {

    //Keeps track of which radio button was selected
    static int sellCategory = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sell, container, false);


        Button next= (Button)rootView.findViewById(R.id.submitButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new sellPageTwo();

                Bundle bundle = new Bundle();
                //Not really sure what to do here, since we'd be getting our info from
                //onActvityCreated right? Also, would I be putting twice?? Once for
                //what to sell, and once for what they are willing to exchange for?
                bundle.putInt(sell, sellCategory);
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
