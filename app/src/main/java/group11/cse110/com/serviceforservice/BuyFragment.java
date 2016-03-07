package group11.cse110.com.serviceforservice;

import org.w3c.dom.Text;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends Fragment implements AbsListView.OnScrollListener{
    SharedPreferences sp;
    public static String key = "MySharedData";
    ParseQuery<ParseObject> query;
    ParseQuery<ParseObject> userQuery;
    ArrayList<String> username;
    ArrayList<String> numbers;
    ArrayList<String> email;
    ArrayList<Integer> sellCategory;
    ArrayList<Integer> wantCategory;
    ArrayList<String> descriptions;
    ArrayList<String> imageUrls;
    ArrayList<String> objectIds;
    ArrayList<String> posts;
    String myUsername;

    ListView lv;
    ParseQueryFeed parseQueryFeed;
    CardsAdapter cardsAdapter;
    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int totalItem;
    int currentPosition;
    BuyFragment frag;
    int counter = 0;
    boolean bookmarked = false;
    boolean updated;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy, container, false);
        lv = (ListView) rootView.findViewById(R.id.listview);
        lv.setOnScrollListener(this);

        SharedPreferences sp = getActivity().getSharedPreferences(key,0);
        myUsername = sp.getString("username",null);
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                System.out.println(position + " " + id + " " + "SELECTED");
                System.out.println(username.get(position));
                System.out.println(descriptions.get(position));

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


                ScrollView inforow = (ScrollView) dialog.findViewById(R.id.listinfo);
                LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.linear);

                TextView descriptionV = (TextView) dialog.findViewById(R.id.description);

                new DownloadImageTask(profile).execute(imageUrls.get(position));
                name.setText(username.get(position));
                number.setText(numbers.get(position));
                emailView.setText(email.get(position));
                descriptionV.setText(descriptions.get(position));

                switch (sellCategory.get(position)) {
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

                int val = wantCategory.get(position);
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
                        if(bookmarked == false) {
                            bookmarked = true;
                            System.out.println("CLICKED");
                            userQuery.whereEqualTo("username", myUsername);
                            Toast.makeText(getContext(), "Bookmarked", Toast.LENGTH_LONG).show();

                            try {
                                System.out.println("USERNAME " + myUsername);
                                ParseObject obj = userQuery.getFirst();
                                System.out.println(obj.getString("name"));

                                List<String> list = obj.getList("bookmarks");
                                list.add(objectIds.get(currentPosition));
                                obj.put("bookmarks", list);
                                obj.saveInBackground();
                            } catch (Exception e) {

                            }
                            bookmarked = false;
                        }
                    }
                });

                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + numbers.get(position)));
                        startActivity(intent);
                    }
                });


                dialog.show();

            }

        });
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        numbers = new ArrayList<String>();
        email = new ArrayList<String>();
        objectIds = new ArrayList<String>();
        query = ParseQuery.getQuery("Selling");
        parseQueryFeed = new ParseQueryFeed(this,getActivity(),query, lv);
        parseQueryFeed.load();
        updated = false;
        frag = this;

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.newsfeed_action_bar, null);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        android.support.v7.app.ActionBar.LayoutParams layout = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);

        actionBar.setCustomView(v, layout);

        SearchView search = (SearchView)v.findViewById(R.id.action_search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {
              @Override
              public boolean   onQueryTextChange( String newText ) {
                  return true;
              }

              @Override
              public boolean   onQueryTextSubmit(String query) {
                  SearchEngine search = new SearchEngine(frag);
                  username = new ArrayList<String>();
                  descriptions = new ArrayList<String>();
                  imageUrls = new ArrayList<String>();
                  sellCategory = new ArrayList<Integer>();
                  wantCategory = new ArrayList<Integer>();
                  objectIds = new ArrayList<String>();
                  numbers = new ArrayList<String>();
                  email = new ArrayList<String>();
                  search.create("Selling");
                  search.search("description",query);
                  updated = true;
                  return true;
              }
          }
        );

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        return true;
    }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        final int last = firstVisibleItem + visibleItemCount;

        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
        this.totalItem = totalItemCount;

    }


    @Override
    public void onScrollStateChanged(AbsListView arg0, int scrollState) {
        if (updated == false && totalItem - currentFirstVisibleItem == currentVisibleItemCount
                && totalItem >= 10 && scrollState == SCROLL_STATE_IDLE) {
            parseQueryFeed.update();
            updated = true;
        }
    }
}
