package com.example.javier.customsearchview;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements InteractionListener{

    private static final String TAG = "MainActivity";
    private MenuItem mSearchMenuItem;

    private SearchView mSearchView;
    private String mSearchString;
    private static final String SEARCH_KEY = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // if you saved something on outState you can recover them here
        if (savedInstanceState != null) {
            mSearchString = savedInstanceState.getString(SEARCH_KEY);
        }
        replaceFragment();

    }

    // This is called before the activity is destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSearchString = mSearchView.getQuery().toString();
        outState.putString(SEARCH_KEY, mSearchString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        mSearchMenuItem = menu.findItem(R.id.menu_main_action_search);

        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);

        customSearView();

        focusSearView();

        return super.onCreateOptionsMenu(menu);
    }

    public void customSearView() {
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        View searchPlate = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(R.drawable.background_search);

        mSearchView.setIconifiedByDefault(false);
    }

    public void focusSearView(){
        if (mSearchString != null && !mSearchString.isEmpty()) {
            mSearchMenuItem.expandActionView();
            mSearchView.setQuery(mSearchString, true);
            mSearchView.clearFocus();
        }
    }

    public void replaceFragment() {
        Fragment OurFragment = new OurFragment().newInstance();

        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, OurFragment, "tag").commit();

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

    }

    @Override
    public void onFragmentInteraction(String string) {
        mSearchString = string;
        focusSearView();
    }
}
