package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends Activity {

    ContactDetailsFragment contactDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_activity_layout);

        Bundle extras = getIntent().getExtras();
        int ix = -1;
        if(extras !=null)
            ix = extras.getInt("id", -1);

        if (savedInstanceState == null) {
            contactDetailsFragment = ContactDetailsFragment.newInstance(ix);
            getFragmentManager().beginTransaction().add(R.id.contactdetails_fragment_container, contactDetailsFragment).commit();
        }else{
            contactDetailsFragment = (ContactDetailsFragment)getFragmentManager().findFragmentById(R.id.contactdetails_fragment_container);
        }


    }

    /*
    public void onDeleteContactClick(View v){

        contactDetailsFragment.onDeleteContactClick(v);


    }

    public void onEditContactClick(View v){

        contactDetailsFragment.onEditContactClick(v);

    }

    public void run(View view)    {

        contactDetailsFragment.run(view);

        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent);

    }
    */
}