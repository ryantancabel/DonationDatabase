package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.KeyEvent;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
// import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity{//AppCompatActivity {

    ContactListFragment contactListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        if (savedInstanceState == null) {
            contactListFragment = ContactListFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.contactlist_fragment_container, contactListFragment).commit();
        }else{
            contactListFragment = (ContactListFragment) getFragmentManager().findFragmentById(R.id.contactlist_fragment_container);
        }
    }

}

