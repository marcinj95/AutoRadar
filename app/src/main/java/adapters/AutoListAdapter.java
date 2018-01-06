package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.marci.autoradar.AutoDetailActivity;
import com.example.marci.autoradar.MainActivity;
import com.example.marci.autoradar.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.Auto;

/**
 * Created by marci on 09.12.2017.
 */

public class AutoListAdapter extends ArrayAdapter<Auto> {

    private Context context;
    private int positionheh;
    //private List<Auto> autos;


    public AutoListAdapter(Context context, List<Auto> autos)
    {
        super(context, R.layout.auto_list_layout, autos);
        this.context = context;
        //this.autos = autos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //final Auto auto = getItem(position);

        // LayoutInflater layoutInflater = LayoutInflater.from(context);
        //  View view = layoutInflater.inflate(R.layout.auto_list_layout, parent, false);
        Auto auto = getItem(position);
        ViewHolder viewHolder= null;


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_list_layout, parent, false);


            viewHolder = new ViewHolder();
            // viewHolder.position = position;
            viewHolder.imageView = convertView.findViewById(R.id.imageViewListLayout);
            viewHolder.progress = convertView.findViewById(R.id.progressBar3);
            viewHolder.textViewData = convertView.findViewById(R.id.textViewDataListLayout);
            viewHolder.textViewDesc =  convertView.findViewById(R.id.textViewTitleListLayout);
            convertView.setTag(viewHolder);

        }
        else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.textViewDesc.setText(auto.getTitle());
        String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());
        viewHolder.textViewData.setText(date);
        viewHolder.imageView.setImageResource(0);

        Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.progress.setVisibility(ProgressBar.GONE);

//        new DownloadImageTask(viewHolder.imageView,viewHolder.progress,
//                auto.getIdAuto()).execute(auto);


//        Glide.with(context).load(auto.getImage()).into(viewHolder.imageView);
//        viewHolder.progress.setVisibility(ProgressBar.GONE);





        //new lol(auto).execute(viewHolder);


//        String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());
//
//
//        ConstraintLayout constraintLayout = convertView.findViewById(R.id.constraintLayoutListLayout);
//        constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //auto.setImage(null);
//                Intent intent = new Intent(context, AutoDetailActivity.class);
//               // auto.setUser(null);
//              //  intent.putExtra("AutoS", auto);
//
//
//                context.startActivity((intent));
//
//            }
//        });

        return convertView;

    }






    static class ViewHolder {
        TextView textViewDesc;
        TextView textViewData;
        ImageView imageView;
        ProgressBar progress;
        // int position;
    }

//    private  class lol extends AsyncTask<ViewHolder, Void, Bitmap> {
//        private ViewHolder v;
//        private Auto auto;
//
//        public lol(Auto auto){
//            this.auto = auto;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            v.progress.setVisibility(ProgressBar.VISIBLE);
//            v.imageView.setVisibility(View.GONE);
//        }
//
//        @Override
//        protected Bitmap doInBackground(ViewHolder... params) {
//            v = params[0];
//            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
//           return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            super.onPostExecute(result);
//            if (v.position == positionheh) {
//                // If this item hasn't been recycled already, hide the
//                // progress and set and show the image
//                v.progress.setVisibility(View.GONE);
//                v.imageView.setVisibility(View.VISIBLE);
//                v.imageView.setImageBitmap(result);
//            }
//        }
//    }


    private class DownloadImageTask extends AsyncTask<Auto, Void, Bitmap> {
        ImageView bmImage;
        Long teges;
        ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage,ProgressBar progressBar, Long teges) {
            this.bmImage = bmImage;
            this.teges = teges;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bmImage.setTag(this.teges);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(Auto... urls) {
            Auto auto = urls[0];

            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {

            Long lol = (Long) bmImage.getTag();

            if(lol != null && lol.equals(this.teges)){
                bmImage.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            } else {
                // bmImage.setImageResource(R.drawable.photo_camera);
                bmImage.setImageResource(0);
                progressBar.setVisibility(View.GONE);
            }

        }

    }
}




