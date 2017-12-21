package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marci.autoradar.AutoDetailActivity;
import com.example.marci.autoradar.R;

import java.text.SimpleDateFormat;
import java.util.List;

import entities.Auto;

/**
 * Created by marci on 09.12.2017.
 */

public class AutoListAdapter extends ArrayAdapter<Auto> {

    private Context context;
    private List<Auto> autos;

    public AutoListAdapter(Context context, List<Auto> autos)
    {
        super(context, R.layout.auto_list_layout, autos);
        this.context = context;
        this.autos = autos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Auto auto = getItem(position);

       // LayoutInflater layoutInflater = LayoutInflater.from(context);
      //  View view = layoutInflater.inflate(R.layout.auto_list_layout, parent, false);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_list_layout, parent, false);
        }
        //Auto auto = autos.get(position);


//        TextView textViewId = convertView.findViewById(R.id.textViewIdList);
//        textViewId.setText(auto.getIdAuto().toString());
//       // Toast.makeText(getContext(),auto.getIdAuto().toString(), Toast.LENGTH_SHORT);
//        //Log.v("E:" + auto.getIdAuto().toString(), "DDDDDD");
//
//
//        TextView textViewModel = convertView.findViewById(R.id.textViewModelLIst);
//        textViewModel.setText(auto.getModel());
//        //Log.v("E:" + auto.getCarBrand(), "WWWW");
////
//        TextView textViewCarBrand = convertView.findViewById(R.id.textViewCarBrandList);
//        textViewCarBrand.setText(auto.getCarBrand());

        TextView textViewDesc = convertView.findViewById(R.id.textViewTitleListLayout);
        textViewDesc.setText(auto.getTitle());

        TextView textViewCity = convertView.findViewById(R.id.textViewCityListLayout);

        if(auto.getUser()!=null)
        {
            textViewCity.setText(auto.getUser().getCity());
        }else {
            textViewCity.setText("Brak");
        }

        String date = new SimpleDateFormat("dd-MM-yyyy").format(auto.getCreatedAt());

        TextView textViewData = convertView.findViewById(R.id.textViewDataListLayout);
        textViewData.setText(date);


        ImageView image = convertView.findViewById(R.id.imageViewListLayout);
        if(auto.getImage()!=null){

            Bitmap bitmap = BitmapFactory.decodeByteArray(auto.getImage(), 0, auto.getImage().length);
            image.setImageBitmap(bitmap);
        }else {
            image.setImageResource(R.drawable.photo_camera);
        }





        ConstraintLayout constraintLayout = convertView.findViewById(R.id.constraintLayoutListLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AutoDetailActivity.class);
                intent.putExtra("AutoS", auto);


                context.startActivity((intent));

            }
        });

        return convertView;

    }
}
