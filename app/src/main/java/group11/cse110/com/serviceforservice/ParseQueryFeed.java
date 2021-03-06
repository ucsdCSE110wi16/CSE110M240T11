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
    ArrayList<String> objectIds;
    ArrayList<String> number;
    ArrayList<String> email;
    ArrayList<ParseQuery<ParseObject>> uQ;
    int counter;
    BuyFragment b;
    BuyFeed buy;
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
        number = new ArrayList<String>();
        email = new ArrayList<String>();
        objectIds = new ArrayList<String>();
        frag.username = username;
        frag.descriptions = descriptions;
        frag.imageUrls = imageUrls;
        frag.sellCategory = sellCategory;
        frag.wantCategory = wantCategory;
        frag.numbers = number;
        frag.email = email;
        frag.objectIds = objectIds;
        lv = listView;
    }
    ParseQueryFeed(BuyFeed frag, Context c, ParseQuery<ParseObject> pq, ListView listView){
        query = pq;
        buy = frag;
        this.context = c;
        counter = 0;
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        number = new ArrayList<String>();
        email = new ArrayList<String>();
        objectIds = new ArrayList<String>();
        frag.username = username;
        frag.descriptions = descriptions;
        frag.imageUrls = imageUrls;
        frag.sellCategory = sellCategory;
        frag.wantCategory = wantCategory;
        frag.numbers = number;
        frag.email = email;
        frag.objectIds = objectIds;
        lv = listView;
    }


    public void load(){
        new Load().execute();
    }

    public void update(){
        new Update().execute();
    }

    public void search(ArrayList<String> id){
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        if(buy == null) {
            b.username = username;
            b.descriptions = descriptions;
            b.imageUrls = imageUrls;
            b.sellCategory = sellCategory;
            b.wantCategory = wantCategory;
            b.numbers = number;
            b.email = email;
            objectIds = id;
            b.objectIds = objectIds;
        }
        else{
            buy.username = username;
            buy.descriptions = descriptions;
            buy.imageUrls = imageUrls;
            buy.sellCategory = sellCategory;
            buy.wantCategory = wantCategory;
            buy.numbers = number;
            buy.email = email;
            objectIds = id;
            buy.objectIds = objectIds;
        }
        new Search().execute();
    }





    public void queryMethod(final boolean first){
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjectList, ParseException e) {

                for (ParseObject o : parseObjectList) {
                    if (o.getBoolean("sold") == false) {
                        objectIds.add(o.getObjectId());
                        username.add(o.getString("username"));
                        descriptions.add(o.getString("description"));
                        sellCategory.add(o.getInt("sellCategory"));
                        wantCategory.add(o.getInt("wantCategory"));
                    }
                }
                uQ = new ArrayList();
                for (int i = 0; i < username.size(); i++) {
                    uQ.add(ParseQuery.getQuery("Users"));
                    uQ.get(i).whereEqualTo("username", username.get(i));
                    try {
                        ParseObject obj = uQ.get(i).getFirst();
                        imageUrls.add(obj.getString("profileurl"));
                        number.add(obj.getString("number"));
                        email.add(obj.getString("email"));

                    } catch (Exception ex) {

                    }
                }

                if (first == true) {
                    cardsAdapter = new CardsAdapter(context, imageUrls, sellCategory, wantCategory);
                    lv.setAdapter(cardsAdapter);
                } else {
                    cardsAdapter.notifyDataSetChanged();
                }
                if(b != null)
                    b.updated = false;
                else
                    buy.updated = false;


            }
        });

    }

    public void searchMethod() throws ParseException {
        for(int i = 0; i < objectIds.size();i++){
            ParseObject o = query.get(objectIds.get(i));
            if(o.getBoolean("sold") == false) {
                username.add(o.getString("username"));
                descriptions.add(o.getString("description"));
                sellCategory.add(o.getInt("sellCategory"));
                wantCategory.add(o.getInt("wantCategory"));
            }

        }

        for (int i = 0; i < username.size(); i++) {
            uQ.add(ParseQuery.getQuery("Users"));
            uQ.get(i).whereEqualTo("username", username.get(i));
            try {
                ParseObject obj = uQ.get(i).getFirst();
                imageUrls.add(obj.getString("profileurl"));
                number.add(obj.getString("number"));
                email.add(obj.getString("email"));
            } catch (Exception ex) {

            }
        }
    }

    public class Search extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params)  {
            query.setLimit(10);
            try {
                searchMethod();
            }
            catch(Exception e) {

            }
            return null;

        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            cardsAdapter = new CardsAdapter(context, imageUrls, sellCategory, wantCategory);
            lv.setAdapter(cardsAdapter);
        }
    }

    public class Load extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            query.setLimit(10);
            try {
                Thread.sleep(1500);
            }
            catch(Exception e){

            }
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
            query.setSkip(10 + 5 * counter++);
            query.setLimit(5);
            queryMethod(false);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }
    }

}
