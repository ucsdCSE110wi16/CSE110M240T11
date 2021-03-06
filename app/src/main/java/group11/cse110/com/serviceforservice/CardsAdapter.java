package group11.cse110.com.serviceforservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kim on 2/23/16.
 */
public class CardsAdapter extends ArrayAdapter<Integer> {

    Context context;
    ArrayList<String> imageUrls;
    ArrayList<Integer> sellCategory;
    ArrayList<Integer> wantCategory;




    public CardsAdapter(Context c,ArrayList<String> urls, ArrayList<Integer> sell, ArrayList<Integer> buy) {
        super(c, R.layout.row, R.id.buyrow, sell);
        this.context = c;
        this.imageUrls = urls;
        this.sellCategory = sell;
        this.wantCategory = buy;
    }

    public static void setGrayScale(ImageView v){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0); //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    public static void setColor(ImageView v){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1); //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row, parent, false);
        }

        ImageView profilepic = (ImageView) row.findViewById(R.id.profilerow);
        ImageView sell = (ImageView) row.findViewById(R.id.buyrow);

        ImageView money = (ImageView) row.findViewById(R.id.money);
        ImageView food = (ImageView) row.findViewById(R.id.food);
        ImageView language = (ImageView) row.findViewById(R.id.language);
        ImageView entertainment = (ImageView) row.findViewById(R.id.entertainment);
        ImageView transportation = (ImageView) row.findViewById(R.id.transportation);
        ImageView housing = (ImageView) row.findViewById(R.id.housing);


        new DownloadImageTask(profilepic).execute(imageUrls.get(position));


        switch(sellCategory.get(position)){
            case 1:
                sell.setBackgroundResource(R.drawable.finalfood);
                break;
            case 2:
                sell.setBackgroundResource(R.drawable.finalhouse);
                break;
            case 3:
                sell.setBackgroundResource(R.drawable.finalentertainment);
                break;
            case 4:
                sell.setBackgroundResource(R.drawable.finalmoney);
                break;
            case 5:
                sell.setBackgroundResource(R.drawable.finallang);
                break;
            case 6:
                sell.setBackgroundResource(R.drawable.finaltran);
                break;
        }

        int val = wantCategory.get(position);
        System.out.println(val);
        int [] want = new int[6];
        int counter = 0;

        while(val > 0){
            want[counter] = val & 1;
            val = val >> 1;
            counter++;
        }


        int tagCounter = 0;
        for (int i = 0; i < 6; i++){
            if(want[i] != 1) {
                switch (i) {
                    case 0:
                        setGrayScale(transportation);
                        break;
                    case 1:
                        setGrayScale(language);
                        break;
                    case 2:
                        setGrayScale(money);
                        break;
                    case 3:
                        setGrayScale(entertainment);
                        break;
                    case 4:
                        setGrayScale(housing);
                        break;
                    case 5:
                        setGrayScale(food);
                        break;
                }
            }
            else{
                switch (i) {
                    case 0:
                        setColor(transportation);
                        break;
                    case 1:
                        setColor(language);
                        break;
                    case 2:
                        setColor(money);
                        break;
                    case 3:
                        setColor(entertainment);
                        break;
                    case 4:
                        setColor(housing);
                        break;
                    case 5:
                        setColor(food);
                        break;
                }
            }
        }

        return row;
    }





}
