package group11.cse110.com.serviceforservice;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;


public class Profile extends Fragment {
    public static String key = "MySharedData";
    String username;
    ParseQuery<ParseObject> userQuery;
    ParseQuery<ParseObject> buyQuery;
    ParseQuery<ParseObject> sellQuery;


    ArrayList<String> bookusername;
    ArrayList<String> booknumbers;
    ArrayList<String> bookemail;
    ArrayList<Integer> booksellCategory;
    ArrayList<Integer> bookwantCategory;
    ArrayList<String> bookdescriptions;
    ArrayList<String> bookimageUrls;
    ArrayList<ParseQuery<ParseObject>> uQ;
    ArrayList<ParseQuery<ParseObject>> myUQ;

    ArrayList<String> bookobjectIds;
    int currentPosition;
    CardsAdapter cardsAdapter;
    CardsAdapter myAdapter;
    ListView bookmarkList;
    ListView myPostsList;

    ArrayList<String> myUsername;
    ArrayList<String> myNumbers;
    ArrayList<String> myEmail;
    ArrayList<Integer> mySellCategory;
    ArrayList<Integer> myWantCategory;
    ArrayList<String> myDescriptions;
    ArrayList<String> myImageUrls;
    ArrayList<String> myObjectIds;
    ParseObject obj;
    List<String> posts;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences(key, 0);
        username = sp.getString("username", null);

        ImageView ppic = (ImageView) rootView.findViewById(R.id.profilepic);
        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView number = (TextView) rootView.findViewById(R.id.number);
        bookmarkList = (ListView) rootView.findViewById(R.id.bookmarklistview);


        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>Profile</font>"));

        userQuery = ParseQuery.getQuery("Users");
        userQuery.whereEqualTo("username", username);

        sellQuery = ParseQuery.getQuery("Selling");
        buyQuery = ParseQuery.getQuery("Buying");

        bookusername = new ArrayList<String>();
        booknumbers = new ArrayList<String>();
        bookemail = new ArrayList<String>();
        booksellCategory = new ArrayList<Integer>();
        bookwantCategory = new ArrayList<Integer>();
        bookdescriptions = new ArrayList<String>();
        bookimageUrls = new ArrayList<String>();
        bookobjectIds = new ArrayList<String>();

        myUsername = new ArrayList<String>();
        myNumbers = new ArrayList<String>();
        myEmail = new ArrayList<String>();
        mySellCategory = new ArrayList<Integer>();
        myWantCategory = new ArrayList<Integer>();
        myDescriptions = new ArrayList<String>();
        myImageUrls = new ArrayList<String>();
        myObjectIds = new ArrayList<String>();

        myPostsList = (ListView) rootView.findViewById(R.id.mypostslistview);


        uQ = new ArrayList<ParseQuery<ParseObject>>();
        try {
            obj = userQuery.getFirst();
            name.setText(obj.getString("username"));
            number.setText(obj.getString("number"));
            new DownloadImageTask(ppic).execute(obj.getString("profileurl"));

            List<String> bookmarks = obj.getList("bookmarks");
            for(int i = 0; i < bookmarks.size();i++){
                ParseObject newObj = sellQuery.get(bookmarks.get(i));
                if(newObj == null){
                    newObj = buyQuery.get(bookmarks.get(i));
                }
                System.out.println("NEW OBJECT SOLD " + newObj.getBoolean("sold"));
                if(newObj.getBoolean("sold") == false) {
                    bookusername.add(newObj.getString("username"));
                    booksellCategory.add(newObj.getInt("sellCategory"));
                    bookwantCategory.add(newObj.getInt("wantCategory"));
                    bookdescriptions.add(newObj.getString("description"));
                    uQ.add(ParseQuery.getQuery("Users"));

                    uQ.get(i).whereEqualTo("username", bookusername.get(i));
                    ParseObject host = uQ.get(i).getFirst();
                    bookimageUrls.add(host.getString("profileurl"));
                    booknumbers.add(host.getString("number"));
                    bookemail.add(host.getString("email"));
                }
                else{
                    posts.remove(i);
                    i--;
                }
                cardsAdapter = new CardsAdapter(getContext(), bookimageUrls, booksellCategory, bookwantCategory);
                bookmarkList.setAdapter(cardsAdapter);
            }
            System.out.println("HERE");
            System.out.println(bookimageUrls.size() + " " + booksellCategory.size());

        }
        catch(Exception e){

        }

        myUQ = new ArrayList<ParseQuery<ParseObject>>();
        try {
            posts = obj.getList("myPosts");
            System.out.println("POSTS SIZE " + posts.size());
            for(int i = 0; i < posts.size();i++){
                ParseObject newObj = sellQuery.get(posts.get(i));
                if(newObj == null){
                    newObj = buyQuery.get(posts.get(i));
                }
                if(newObj.getBoolean("sold") == false) {
                    myUsername.add(newObj.getString("username"));
                    mySellCategory.add(newObj.getInt("sellCategory"));
                    myWantCategory.add(newObj.getInt("wantCategory"));
                    myDescriptions.add(newObj.getString("description"));
                    myUQ.add(ParseQuery.getQuery("Users"));

                    myUQ.get(i).whereEqualTo("username", myUsername.get(i));
                    ParseObject host = myUQ.get(i).getFirst();
                    myImageUrls.add(host.getString("profileurl"));
                    myNumbers.add(host.getString("number"));
                    myEmail.add(host.getString("email"));
                }
                else{
                    posts.remove(i);
                    i--;
                }
                myAdapter = new CardsAdapter(getContext(), myImageUrls, mySellCategory, myWantCategory);
                myPostsList.setAdapter(myAdapter);
            }

        }
        catch(Exception e){

        }



        myPostsList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.mypostinfo);

                userQuery = ParseQuery.getQuery("Users");

                ImageView profile = (ImageView) dialog.findViewById(R.id.profilepic);
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView number = (TextView) dialog.findViewById(R.id.number);
                TextView emailView = (TextView) dialog.findViewById(R.id.email);

                ImageView selling = (ImageView) dialog.findViewById(R.id.itempic);
                TextView sellingdesc = (TextView) dialog.findViewById(R.id.itemdescription);


                LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.linear);

                TextView descriptionV = (TextView) dialog.findViewById(R.id.description);

                new DownloadImageTask(profile).execute(myImageUrls.get(position));
                name.setText(myUsername.get(position));
                number.setText(myNumbers.get(position));
                emailView.setText(myEmail.get(position));
                descriptionV.setText(myDescriptions.get(position));

                switch (mySellCategory.get(position)) {
                    case 1:
                        selling.setBackgroundResource(R.drawable.finalfood);
                        sellingdesc.setText("Food");
                        break;
                    case 2:
                        selling.setBackgroundResource(R.drawable.finalhouse);
                        sellingdesc.setText("House");
                        break;
                    case 3:
                        selling.setBackgroundResource(R.drawable.finalentertainment);
                        sellingdesc.setText("Entertainment");
                        break;
                    case 4:
                        selling.setBackgroundResource(R.drawable.finalmoney);
                        sellingdesc.setText("Money");
                        break;
                    case 5:
                        selling.setBackgroundResource(R.drawable.finallang);
                        sellingdesc.setText("Language");
                        break;
                    case 6:
                        selling.setBackgroundResource(R.drawable.finaltran);
                        sellingdesc.setText("Transportation");
                        break;
                }

                int val = myWantCategory.get(position);
                int[] want = new int[6];
                int counter = 0;

                while (val > 0) {
                    want[counter] = val & 1;
                    val = val >> 1;
                    counter++;
                }

                LayoutInflater inflater = LayoutInflater.from(getActivity());

                int tagCounter = 0;
                for (int i = 0; i < 6; i++) {
                    View custLayout = inflater.inflate(R.layout.inforow, null, false);
                    ImageView item;
                    TextView itemdesc;
                    if (want[i] == 1) {
                        switch (i) {
                            case 0:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finaltran);
                                itemdesc.setText("Transportation");
                                layout.addView(custLayout);
                                break;
                            case 1:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finallang);
                                itemdesc.setText("Language");
                                layout.addView(custLayout);

                                break;
                            case 2:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalmoney);
                                itemdesc.setText("Money");
                                layout.addView(custLayout);
                                break;
                            case 3:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalentertainment);
                                itemdesc.setText("Entertainment");
                                layout.addView(custLayout);
                                break;
                            case 4:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalhouse);
                                itemdesc.setText("Housing");
                                layout.addView(custLayout);
                                break;
                            case 5:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalfood);
                                itemdesc.setText("Food");
                                layout.addView(custLayout);
                                break;
                        }
                    }
                }
                currentPosition = position;
                Button delete = (Button) dialog.findViewById(R.id.sold);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ParseObject newObj = sellQuery.get(posts.get(currentPosition));
                            if (newObj == null) {
                                newObj = buyQuery.get(posts.get(currentPosition));
                            }
                            newObj.put("sold",true);
                            newObj.saveInBackground();
                            Toast.makeText(getContext(),"Deleted post", Toast.LENGTH_LONG).show();
                        }
                        catch(Exception e){

                        }
                    }
                });


                dialog.show();

            }

        });



        bookmarkList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.info);

                userQuery = ParseQuery.getQuery("Users");

                ImageView profile = (ImageView) dialog.findViewById(R.id.profilepic);
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView number = (TextView) dialog.findViewById(R.id.number);
                TextView emailView = (TextView) dialog.findViewById(R.id.email);

                ImageView selling = (ImageView) dialog.findViewById(R.id.itempic);
                TextView sellingdesc = (TextView) dialog.findViewById(R.id.itemdescription);


                LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.linear);

                TextView descriptionV = (TextView) dialog.findViewById(R.id.description);

                new DownloadImageTask(profile).execute(bookimageUrls.get(position));
                name.setText(bookusername.get(position));
                number.setText(booknumbers.get(position));
                emailView.setText(bookemail.get(position));
                descriptionV.setText(bookdescriptions.get(position));

                switch (booksellCategory.get(position)) {
                    case 1:
                        selling.setBackgroundResource(R.drawable.finalfood);
                        sellingdesc.setText("Food");
                        break;
                    case 2:
                        selling.setBackgroundResource(R.drawable.finalhouse);
                        sellingdesc.setText("House");
                        break;
                    case 3:
                        selling.setBackgroundResource(R.drawable.finalentertainment);
                        sellingdesc.setText("Entertainment");
                        break;
                    case 4:
                        selling.setBackgroundResource(R.drawable.finalmoney);
                        sellingdesc.setText("Money");
                        break;
                    case 5:
                        selling.setBackgroundResource(R.drawable.finallang);
                        sellingdesc.setText("Language");
                        break;
                    case 6:
                        selling.setBackgroundResource(R.drawable.finaltran);
                        sellingdesc.setText("Transportation");
                        break;
                }

                int val = bookwantCategory.get(position);
                int[] want = new int[6];
                int counter = 0;

                while (val > 0) {
                    want[counter] = val & 1;
                    val = val >> 1;
                    counter++;
                }

                LayoutInflater inflater = LayoutInflater.from(getActivity());

                int tagCounter = 0;
                for (int i = 0; i < 6; i++) {
                    View custLayout = inflater.inflate(R.layout.inforow, null, false);
                    ImageView item;
                    TextView itemdesc;
                    if (want[i] == 1) {
                        switch (i) {
                            case 0:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finaltran);
                                itemdesc.setText("Transportation");
                                layout.addView(custLayout);
                                break;
                            case 1:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finallang);
                                itemdesc.setText("Language");
                                layout.addView(custLayout);

                                break;
                            case 2:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalmoney);
                                itemdesc.setText("Money");
                                layout.addView(custLayout);
                                break;
                            case 3:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalentertainment);
                                itemdesc.setText("Entertainment");
                                layout.addView(custLayout);
                                break;
                            case 4:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalhouse);
                                itemdesc.setText("Housing");
                                layout.addView(custLayout);
                                break;
                            case 5:
                                item = (ImageView) custLayout.findViewById(R.id.itempic);
                                itemdesc = (TextView) custLayout.findViewById(R.id.itemdescription);
                                item.setBackgroundResource(R.drawable.finalfood);
                                itemdesc.setText("Food");
                                layout.addView(custLayout);
                                break;
                        }
                    }
                }
                currentPosition = position;
                Button bookmark = (Button) dialog.findViewById(R.id.bookmark);
                Button contact = (Button) dialog.findViewById(R.id.contactowner);

                bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toast.makeText(getContext(), "Already Bookmarked", Toast.LENGTH_LONG).show();

                    }
                });

                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + booknumbers.get(position)));
                        startActivity(intent);
                    }
                });


                dialog.show();

            }

        });



        return rootView;
    }

}
