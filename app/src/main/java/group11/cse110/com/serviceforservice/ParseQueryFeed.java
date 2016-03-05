package group11.cse110.com.serviceforservice;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kim on 2/23/16.
 */
public class ParseQueryFeed {
    ParseQuery<ParseObject> query;
    CardsAdapter cardsAdapter;
    Context context;
    ArrayList<String> username;
    ArrayList<String> descriptions;
    ArrayList<String> imageUrls;
    ArrayList<Integer> sellCategory;
    ParseQuery<ParseObject> userQuery;
    ArrayList<Integer> wantCategory;
    ArrayList<ParseQuery<ParseObject>> uQ;
    int counter;
    BuyFragment b;
    ListView lv;

    ParseQueryFeed(BuyFragment frag, Context c, ParseQuery<ParseObject> pq, ListView listView){
        query = pq;
        b = frag;
        this.context = c;
        counter = 0;
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        lv = listView;
    }

    public void load(){
        new Load().execute();
    }

    public void update(){
        new Update().execute();
    }


    public void queryMethod(final boolean first){
        query.orderByDescending("updatedAt");
        if(query == null){
            System.out.println("QUERY IS NULL");
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjectList, ParseException e) {
                for(ParseObject o : parseObjectList){
                    if(o == null){
                        System.out.println("IS NULL");
                    }
                    System.out.println("USERNAME " + o.getString("username"));
                    username.add(o.getString("username"));
                    System.out.println("t1");
                    descriptions.add(o.getString("description"));
                    System.out.println("t2");

                    sellCategory.add(o.getInt("sellCategory"));
                    System.out.println("t3");

                    wantCategory.add(o.getInt("wantCategory"));
                }
                uQ = new ArrayList();
                for(int i = 0; i < username.size();i++){
                    uQ.add(ParseQuery.getQuery("Users"));
                    uQ.get(i).whereEqualTo("username", username.get(i));
                    try {
                        ParseObject obj = uQ.get(i).getFirst();
                        imageUrls.add(obj.getParseFile("profilepic").getUrl());
                    }
                    catch(Exception ex) {

                    }
                }

                if(first == true) {
                    cardsAdapter = new CardsAdapter(context, imageUrls, sellCategory, wantCategory);
                    lv.setAdapter(cardsAdapter);
                }
                else{
                    cardsAdapter.notifyDataSetChanged();
                }

            }
        });


    }

    public class Load extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            long l = 1500;
            try {
                Thread.sleep(l);
            }
            catch(Exception e){

            }
            query.setLimit(10);
            queryMethod(true);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }
    }

    public class Update extends AsyncTask<Integer,Void,Void> {
        protected Void doInBackground(Integer... params) {
            System.out.println("update");
            counter++;
            query.setSkip(10 * counter);
            queryMethod(false);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            b.updated = false;
        }
    }

}
