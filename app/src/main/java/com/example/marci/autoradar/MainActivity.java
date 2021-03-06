package com.example.marci.autoradar;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import adapters.AutoListAdapaterRecycler;
import adapters.AutoListAdapter;
import entities.Auto;
import entities.User;
import models.AutoRestClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private User mainUser;
   public static boolean ifUserAutos = false;
   private boolean ifFilterAutos = false;
   private MenuItem mMenu;
    private AdView mAdView;

   //
    List<Auto> listaAut;
    FloatingActionButton fab;

    //test
    private RecyclerView recyclerView;
    private AutoListAdapaterRecycler mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();

        //AdRequest.Builder.addTestDevice("E9D3F269C53676DDA1836BDDC5B7D4B5");

        mAdView.loadAd(adRequest);

        recyclerView = findViewById(R.id.recyclerView);

        mainUser = User.mUser;
        //Toast.makeText(MainActivity.this, String.valueOf(mainUser.getAutos().size()), Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, NewAutoActivity.class);
               // intent.putExtra("UserS",mainUser );
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView nameView = header.findViewById(R.id.textViewUserName);
        nameView.setText(mainUser.getName());

        TextView emailView = header.findViewById(R.id.textViewUserEmail);
        emailView.setText(mainUser.getEmail());

//        TextView mTitle = toolbar.getT
//
//                toolbar.






        new MainActivity.HttpRequestAsk().execute();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(ifUserAutos){
                ifUserAutos = false;
                fab.setVisibility(FloatingActionButton.VISIBLE);
                mMenu.setTitle("Moje oferty");
                new MainActivity.HttpRequestAsk().execute();

            }else if(ifFilterAutos){
                ifFilterAutos = false;
                new MainActivity.HttpRequestAsk().execute();


            }else {
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       // mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mMenu = item;

        if (id == R.id.new_offer) {
            Intent intent = new Intent(MainActivity.this, NewAutoActivity.class);
            //intent.putExtra("UserS",mainUser );
            startActivity(intent);
        } else if (id == R.id.get_all) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.search) {

//            Intent intent = new Intent(this, AutoDetailActivity.class);
//            Auto auto = listaAut.get(11);
//            auto.setImage(null);
//            intent.putExtra("AutoS", auto);
//
//
//            startActivity((intent));
            Intent intent = new Intent(this, FilterActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 1);

        } else if (id == R.id.settings) {

        } else if (id == R.id.my_autos){

            if (ifUserAutos == false){
                new MainActivity.HttpRequestAskUserAutos().execute();
                ifUserAutos = true;
                fab.setVisibility(FloatingActionButton.GONE);
                item.setTitle("Wróć");
            }else{
                new MainActivity.HttpRequestAsk().execute();
                ifUserAutos = false;
                fab.setVisibility(FloatingActionButton.VISIBLE);
                item.setTitle("Moje oferty");
            }




        }



       // item.setEnabled(false);
        item.setChecked(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {


                Bundle b = data.getExtras();
                if (b != null) {
                    ArrayList<Auto> list; // = b.getParcelableArrayList("selectedContacts");
                    list = (ArrayList<Auto>) b.getSerializable("autos");
                    Log.v("HEHE", String.valueOf(list.size()));
                    mAdapter = new AutoListAdapaterRecycler(list, MainActivity.this);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                    ifFilterAutos = true;


//                    mSelectedContacts.addAll(newContacts);
//                    mAdapter.notifyDataSetChanged();

            }

            }
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Auto>> {

        @Override
        protected List<Auto> doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
           // Auto auto = autoRestClient.find(12L);


           // return autoRestClient.finAllNoUser();
                return autoRestClient.finAllNoUser();
        }

        @Override
        protected void onPostExecute(List<Auto> autos) {
            //listaAut = autos;
//            ListView listViewAuto = findViewById(R.id.listViewAutoContentMain);
//            listViewAuto.setAdapter(new AutoListAdapter(MainActivity.this, autos));

            mAdapter = new AutoListAdapaterRecycler(autos, MainActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);



        }
    }

    private class HttpRequestAskUserAutos extends AsyncTask<Void, Void, List<Auto>> {

        @Override
        protected List<Auto> doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
            // Auto auto = autoRestClient.find(12L);
            return autoRestClient.findAllByUser(mainUser);

        }

        @Override
        protected void onPostExecute(List<Auto> autos) {
//            ListView listViewAuto = findViewById(R.id.listViewAutoContentMain);
//            listViewAuto.setAdapter(new AutoListAdapter(MainActivity.this, autos));

            mAdapter = new AutoListAdapaterRecycler(autos, MainActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);

        }
    }


}
