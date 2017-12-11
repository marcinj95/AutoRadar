package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marci.autoradar.R;

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


        TextView textViewId = convertView.findViewById(R.id.textViewIdList);
        textViewId.setText(auto.getIdAuto().toString());
       // Toast.makeText(getContext(),auto.getIdAuto().toString(), Toast.LENGTH_SHORT);
        //Log.v("E:" + auto.getIdAuto().toString(), "DDDDDD");


        TextView textViewModel = convertView.findViewById(R.id.textViewModelLIst);
        textViewModel.setText(auto.getModel());
        //Log.v("E:" + auto.getCarBrand(), "WWWW");
//
        TextView textViewCarBrand = convertView.findViewById(R.id.textViewCarBrandList);
        textViewCarBrand.setText(auto.getCarBrand());

        return convertView;

    }
}
