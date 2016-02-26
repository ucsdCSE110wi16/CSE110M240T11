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
    List<String> user;

    public SearchEngine(){
        user = new ArrayList<>();
    }

    public void create(String className){
        this.query = ParseQuery.getQuery(className);
    }


    /* search through the key and a specific word that needed to search through
    and put the username into the user array and return it
     */
    public void search(String key ,String word){
        this.query.whereContains(key, word); // search for all the word in the key
        this.query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> description, com.parse.ParseException e) {
                if (e == null) {
                    for(ParseObject o: description){
                        String username = o.getObjectId();
                        user.add(username);
                    }
                    Log.i("retrieved", "username already retrieved");
                } else {
                    Log.d("retrieved", "Error: " + e.getMessage());
                }
            }


        });

    }
}