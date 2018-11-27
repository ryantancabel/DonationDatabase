package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DonationHistoryActivity extends Activity {

    DonationHistoryFragment donationHistoryFragment;
    int arrayNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_layout);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        arrayNo = b.getInt("id");

        donationHistoryFragment = donationHistoryFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.donation_history_fragment_container, donationHistoryFragment).commit();

    }
}
