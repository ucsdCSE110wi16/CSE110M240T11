package group11.cse110.com.serviceforservice;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Edward on 3/3/16.
 */
public class info extends Fragment {

    static View root;
    ParseUser parseUser;
    String em,telephone;
    static String user, desc;
    ParseQuery<ParseObject> sellObject;
    static int categoryNum;
    //String buyOrSell;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.info, container, false);


        root = rootView;

        /* initializing all the picture */
        ImageView profilePic = (ImageView) root.findViewById(R.id.profilePic);
        ImageView icon = (ImageView) root.findViewById(R.id.icon);

        /*initializing selling, tel and email */
        TextView selling = (TextView) root.findViewById(R.id.Selling);
        TextView tel = (TextView) root.findViewById(R.id.Tel);
        TextView email = (TextView) root.findViewById(R.id.email);

        /* initializing the listView */
        ListView listView = (ListView) root.findViewById(R.id.listView);

        /* initializing the description */
        TextView description = (TextView) root.findViewById(R.id.descriptionContent);

        /* set the profile pic from facebook, and put the user, email on the imageView */
        profilePic.setImageBitmap(getUserDetailsFromParse());
        parseUser = ParseUser.getCurrentUser();
        em = parseUser.getEmail();
        telephone = parseUser.getString("phoneNumber");

        tel.setText("Tel: "+telephone);
        email.setText("Email: "+em);
         /* get the description, and selling item */

        getDescription("Selling", description);
        getSelling();

        switch(categoryNum){
            case 1 :
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finalfood));
                break;
            case 2 :
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finalentertainment));
                break;
            case 3 :
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finallang));
                break;
            case 4 :
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finalhouse));
                break;
            case 5:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finalmoney));
                break;
            case 6:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.finaltran));
                break;
        }


        return root;





    }

    /* description part */
    public static boolean getDescription(String s,TextView description) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(s);
        query.whereEqualTo("username", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {
                        desc = o.getString("description");

                    }
                }
            }
        });
        description.setText(desc);
        return true;
    }
    public static boolean getSelling() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Selling");
        query.whereEqualTo("username", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {
                        categoryNum = o.getInt("sellCategory");

                    }
                }
            }
        });
        return true;
    }



    private Bitmap getUserDetailsFromParse() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        Bitmap bitmap = null;
        //Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
