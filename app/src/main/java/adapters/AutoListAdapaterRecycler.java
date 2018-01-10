package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.marci.autoradar.AutoDetailActivity;
import com.example.marci.autoradar.MainActivity;
import com.example.marci.autoradar.R;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;



import entities.Auto;
import models.AutoRestClient;

/**
 * Created by marci on 06.01.2018.
 */

public class AutoListAdapaterRecycler extends RecyclerView.Adapter<AutoListAdapaterRecycler.MyViewHolder> {

    private List<Auto> autos;
    private  Context context;


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDesc;
        TextView textViewData;
        ImageView imageView;
        ProgressBar progress;
        ConstraintLayout constraintLayout;

        public MyViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageViewListLayout);
            progress = view.findViewById(R.id.progressBar3);
            textViewData = view.findViewById(R.id.textViewDataListLayout);
            textViewDesc = view.findViewById(R.id.textViewTitleListLayout);
            constraintLayout = view.findViewById(R.id.constraintLayoutListLayout);

        }




    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
       // Glide.with(context).clear(holder.imageView);


    }

    public AutoListAdapaterRecycler(List<Auto> list, Context context){
        this.autos = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


       final Auto auto = autos.get(position);




        holder.textViewDesc.setText(auto.getTitle());

        String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());
        holder.textViewData.setText(date);

      // Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
//        holder.imageView.setImageBitmap(bitmap);

        //holder.progress.setVisibility(ProgressBar.GONE);
        holder.imageView.setImageResource(0);
       new DownloadImageTask(holder.imageView, holder.progress, auto.getIdAuto()).execute(auto);

        //File file = new File(bitmap);

        //Picasso.with(context).load("http://www.gstatic.com/webp/gallery/1.webp").into(holder.imageView);








       // holder.progress.setVisibility(ProgressBar.GONE);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //auto.setImage(null);
                Intent intent = new Intent(context, AutoDetailActivity.class);
                auto.setUser(null);
                intent.putExtra("AutoS", auto);


                context.startActivity((intent));

            }
        });


    }

    @Override
    public int getItemCount() {
        return autos.size();
    }


    private class DownloadImageTask extends AsyncTask<Auto, Void, Bitmap> {
        ImageView bmImage;
        Long teges;
        ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage,ProgressBar progressBar,Long teges) {
            this.bmImage = bmImage;
            this.teges = teges;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bmImage.setTag(this.teges);
           // bmImage.setImageResource(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(Auto... urls) {
            Auto auto = urls[0];

            AutoRestClient autoRestClient = new AutoRestClient();

            auto.setImage(autoRestClient.getImageById(auto.getIdAuto()));

            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {


           // progressBar.setVisibility(View.GONE);

            Long lol = (Long) bmImage.getTag();

            if(lol != null && lol.equals(this.teges)){
               // Glide.with(context).load(result).into(bmImage);
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
