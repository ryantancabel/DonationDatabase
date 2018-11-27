package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */


import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import 	android.util.Pair;

/**
 * Created by Hamid on 4/10/2017.
 */

public class ContactListFragment extends Fragment {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    public final static int REQUEST_CODE_NEW_CONTACT = 1;


    // Views
    View    mLayoutView;
    Button  mNewContactButton;
    private ListView obj;

    // Database
    DBHelper mydb = null;
    ArrayList mArrayList;  // the list of all contacts
    TextView tv;

    public static ContactListFragment newInstance(){

        ContactListFragment f = new ContactListFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mLayoutView = inflater.inflate(R.layout.contact_list_layout, null);
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        tv = mLayoutView.findViewById(R.id.total_amount);
        // initialise the DB
        refresh();

        // At the click on an item, start a new activity that will display the content of the database
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                // Here either start a new activity or display it on the same window

                // The index of the Contact that will be shown in the new activity DislayActivity.java
                Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(position);
                int id_To_Search = p.first; // position + 1;

                Bundle dataBundle = new Bundle();

                dataBundle.putInt("id", id_To_Search);
                Intent intent = new Intent(getActivity().getApplicationContext(), DonationHistoryActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });


        // set the onclick listener for the button
        mNewContactButton = getActivity().findViewById(R.id.new_donor_button);

        mNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", 0);

                    Intent intent = new Intent(getActivity().getApplicationContext(), DisplayContact.class);
                    intent.putExtras(dataBundle);

                    startActivity(intent);
            }
        });



    }

    @Override
    public void onResume() {

        super.onResume();

        refresh();
    }

    public void refresh(){

        if (mydb == null)
            mydb = new DBHelper(getActivity());

        // Get all the contacts from the database
        mArrayList = mydb.getAllContacts();

        ArrayList<String> array_list = new  ArrayList<String>();

        for (int i=0; i<mArrayList.size(); i++){
            Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(i);
            array_list.add(p.second);
        }
        // Put all the contacts in an array
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

        // Display the contacts in the ListView object
        obj = (ListView)mLayoutView.findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);

        tv.setText("Total donations = $" + getAmountDonated());

    }

    public int getAmountDonated() {

        if (mydb == null)
            mydb = new DBHelper(getActivity());

        int total = mydb.getAllDonorTotal();

        return total;

    }



}