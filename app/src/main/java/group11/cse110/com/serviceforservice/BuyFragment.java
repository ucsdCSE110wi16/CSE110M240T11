package group11.cse110.com.serviceforservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
    ListView lv;
    AdapterClass ac;
    int counter = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy, container, false);
        lv = (ListView) rootView.findViewById(R.id.listview);
        lv.setOnScrollListener(this);
        username = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        imageUrls = new ArrayList<String>();
        sellCategory = new ArrayList<Integer>();
        wantCategory = new ArrayList<Integer>();
        query = ParseQuery.getQuery("Selling");
        new Load().execute();

        return rootView;
    }


    public void queryMethod(){
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjectList, ParseException e) {
                for(ParseObject o : parseObjectList){

                    username.add(o.getString("username"));
                    descriptions.add(o.getString("description"));
                    sellCategory.add(o.getInt("sellCategory"));
                    wantCategory.add(o.getInt("wantCategory"));
                }
                userQuery = ParseQuery.getQuery("Users");
                for(int i = 0; i < username.size();i++) {
                    userQuery.whereEqualTo("username",username.get(i));
                    userQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> parseObjectList, ParseException e) {
                            imageUrls.add(parseObjectList.get(0).getParseFile("profilepic").getUrl());
                            ac = new AdapterClass(getActivity(), username);
                            lv.setAdapter(ac);
                        }
                    });
                }
            }
        });


    }

    public class Load extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            query.setLimit(10);

            queryMethod();
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
            counter++;
            query.setSkip(10 * counter);
            queryMethod();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ac.notifyDataSetChanged();
        }
    }



    public class AdapterClass extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> name;

        public AdapterClass(Context c, ArrayList<String> t) {
            super(c, R.layout.row, R.id.name, t);
            this.context = c;
            this.name = t;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.row, parent, false);
            }

            TextView name = (TextView) row.findViewById(R.id.name);
            ImageView profilepic = (ImageView) row.findViewById(R.id.profilerow);
            ImageView sell = (ImageView) row.findViewById(R.id.sellrow);
            HorizontalScrollView sv = (HorizontalScrollView) row.findViewById(R.id.sellScroll);
            TextView description = (TextView) row.findViewById(R.id.descriptionrow);

            new DownloadImageTask(profilepic).execute(imageUrls.get(position));
            description.setText(descriptions.get(position));
            name.setText(username.get(position));


            switch(sellCategory.get(position)){
                case 1:
                    sell.setBackgroundResource(R.drawable.foodicon);
                    break;
                case 2:
                    sell.setBackgroundResource(R.drawable.housing);
                    break;
                case 3:
                    sell.setBackgroundResource(R.drawable.entertainment);
                    break;
                case 4:
                    sell.setBackgroundResource(R.drawable.moneyicon);
                    break;
                case 5:
                    sell.setBackgroundResource(R.drawable.language);
                    break;
                case 6:
                    sell.setBackgroundResource(R.drawable.transportation);
                    break;
            }

            int val = wantCategory.get(position);
            int [] want = new int[6];
            int counter = 0;

            while(val > 0){
                want[counter] = val & 1;
                val = val >> 1;
                counter++;
            }


            LinearLayout topLinearLayout = new LinearLayout(getActivity());
            topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            int tagCounter = 0;
            for (int i = 0; i < 6; i++){
                if(want[i] == 1){
                    ImageView imageView = new ImageView (getActivity());
                    imageView.setTag(tagCounter++);
                    if(i == 1)
                        imageView.setImageResource(R.drawable.transportation);
                    if(i == 2)
                        imageView.setImageResource(R.drawable.language);
                    if(i == 3)
                        imageView.setImageResource(R.drawable.moneyicon);
                    if(i == 4)
                        imageView.setImageResource(R.drawable.entertainment);
                    if(i == 5)
                        imageView.setImageResource(R.drawable.housing);
                    if(i == 6)
                        imageView.setImageResource(R.drawable.foodicon);

                    topLinearLayout.addView(imageView);
                    final float scale = getContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (50 * scale + 0.5f);
                    imageView.getLayoutParams().height = pixels;
                    imageView.getLayoutParams().width = pixels;
                    imageView.requestLayout();
                }
            }

            sv.addView(topLinearLayout);
            return row;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        final int last = firstVisibleItem + visibleItemCount;

       /* if(last == totalItemCount && totalItemCount >= 10){
            new Update().execute(last);

        }*/
    }


    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {

    }
}
