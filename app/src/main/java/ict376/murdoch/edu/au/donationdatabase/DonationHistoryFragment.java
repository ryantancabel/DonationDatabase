package ict376.murdoch.edu.au.donationdatabase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DonationHistoryFragment extends Fragment {

    private DBHelper mydb ;

    View mLayoutView;
    Button mDonateButton;
    Button mEditButton;
    private ListView donationList;
    int arrayNo;
    Bundle saveBundle;

    ArrayList donationHistory;
    ArrayAdapter arrayAdapter;
    ListView listObj;
    TextView amountDonated;

    public static DonationHistoryFragment newInstance() {

        DonationHistoryFragment f = new DonationHistoryFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mLayoutView = inflater.inflate(R.layout.history_fragment_layout, container, false);

        if(arrayAdapter != null)
        {
            arrayAdapter.clear();
        }

        TextView tv = mLayoutView.findViewById(R.id.history_title);
        amountDonated = mLayoutView.findViewById(R.id.person_amount);
        Bundle b = getActivity().getIntent().getExtras();
        arrayNo = b.getInt("id");
        mydb = new DBHelper(getActivity());
        String name = mydb.getName(arrayNo);
        tv.setText("Hello " + name);

        mDonateButton = mLayoutView.findViewById(R.id.donate_button);
        mEditButton = mLayoutView.findViewById(R.id.edit_button);

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            donationHistory = (ArrayList) savedInstanceState.getSerializable("list");
        }
        else
        {
            if (mydb == null)
                mydb = new DBHelper(getActivity());

            donationHistory = mydb.getDonationHistory(arrayNo);
        }

        // initialise the DB
        refresh();

        mDonateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("au.edu.murdoch.ict376.donation10");
                Bundle b = new Bundle();
                b.putInt("num",arrayNo);
                launchIntent.putExtras(b);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }

            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", arrayNo);

                Intent intent = new Intent(getActivity().getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
            }
        });

    }

    public void refresh(){

        if(arrayAdapter != null) {
            arrayAdapter.clear(); }
        // Put all the contacts in an array
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, donationHistory);

        // Display the contacts in the ListView object
        listObj = (ListView) mLayoutView.findViewById(R.id.listView);
        listObj.setAdapter(arrayAdapter);

        amountDonated.setText("You have donated $" + getAmountDonated());

    }

    public int getAmountDonated() {

        if (mydb == null)
            mydb = new DBHelper(getActivity());

        int total = mydb.getDonorTotal(arrayNo);

        return total;

    }

    @Override
    public void onResume() {

        super.onResume();

        if (mydb == null)
            mydb = new DBHelper(getActivity());

        donationHistory = mydb.getDonationHistory(arrayNo);

        refresh();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable("list", donationHistory);
        super.onSaveInstanceState(bundle);
    }




}
