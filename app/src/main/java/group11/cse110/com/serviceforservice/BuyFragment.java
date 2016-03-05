package group11.cse110.com.serviceforservice;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
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
    ArrayList<Integer> sellCategory;
    ArrayList<Integer> wantCategory;
    ArrayList<String> descriptions;
    ArrayList<String> imageUrls;
    ArrayList<String> posts;

    ListView lv;
    ParseQueryFeed parseQueryFeed;
    CardsAdapter cardsAdapter;
    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int totalItem;
    BuyFragment frag;
    int counter = 0;
    boolean updated;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy, container, false);
        lv = (ListView) rootView.findViewById(R.id.listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        lv.setOnScrollListener(this);
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
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
