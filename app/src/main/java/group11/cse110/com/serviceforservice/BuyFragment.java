package group11.cse110.com.serviceforservice;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

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
    ListView lv;
    ParseQueryFeed parseQueryFeed;
    CardsAdapter cardsAdapter;
    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int totalItem;
    int counter = 0;
    boolean updated;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy, container, false);
        lv = (ListView) rootView.findViewById(R.id.listview);
        lv.setOnScrollListener(this);

        android.support.v7.app.ActionBar actionBar = ((HomePage)getActivity()).getSupportActionBar();
        //actionBar.setTitle("Sell Form");
        actionBar.setTitle(Html.fromHtml("<font color=@colors/white>SELL FEED</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        query = ParseQuery.getQuery("Selling");
        parseQueryFeed = new ParseQueryFeed(this,getActivity(),query, lv);
        parseQueryFeed.load();
        updated = false;
        return rootView;
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
