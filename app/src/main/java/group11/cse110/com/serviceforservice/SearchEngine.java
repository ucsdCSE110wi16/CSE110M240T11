package group11.cse110.com.serviceforservice;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.*;

/**
 * Created by Edward on 2/16/16
 * create an engine to search through the username and .
 */
public class SearchEngine {
    private ParseQuery<ParseObject> query;
    ArrayList<String> post;
    BuyFragment frag;
    public SearchEngine(BuyFragment b){
        post = new ArrayList<>();
        frag = b;
    }

    public void create(String className){
        this.query = ParseQuery.getQuery(className);
    }

    public ArrayList<String> getPosts(){
        return post;
    }

    public void search(String key ,String word){
        this.query.whereContains(key, word); // search for all the word in the key
        this.query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> description, com.parse.ParseException e) {
                if (e == null) {
                    for(ParseObject o: description){
                        String id = o.getObjectId();
                        post.add(id);
                    }
                    frag.parseQueryFeed.search(post);
                    Log.i("retrieved", "username already retrieved");
                } else {
                    Log.d("retrieved", "Error: " + e.getMessage());
                }
            }


        });

    }
}