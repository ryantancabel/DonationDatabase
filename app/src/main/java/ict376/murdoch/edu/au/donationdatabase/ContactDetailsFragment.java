package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Hamid on 1/09/2017.
 */

public  class ContactDetailsFragment extends Fragment {

    private DBHelper mydb ;

    View mLayoutView;

    TextView name ;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;

    int id_To_Update = 0;

    // the buttons
    Button mEditButton;
    Button mSaveButton;
    Button mDeleteButton;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static ContactDetailsFragment newInstance(int index) {

        ContactDetailsFragment f = new ContactDetailsFragment();

        // Supply index input as an argument.
        // Google recommends using bundles to pass in arguments
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    // Retrieve the index of the contact that will be displayed
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        mLayoutView = inflater.inflate(R.layout.contact_details_layout, null);

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (mLayoutView == null)
            return;

        name   = (TextView) mLayoutView.findViewById(R.id.editTextName);
        phone  = (TextView) mLayoutView.findViewById(R.id.editTextPhone);
        street = (TextView) mLayoutView.findViewById(R.id.editTextStreet);
        email  = (TextView) mLayoutView.findViewById(R.id.editTextEmail);
        place  = (TextView) mLayoutView.findViewById(R.id.editTextCity);

        mydb = new DBHelper(getActivity());

        mSaveButton = (Button)mLayoutView.findViewById(R.id.button1);
        mEditButton = (Button)mLayoutView.findViewById(R.id.button_edit);
        mEditButton.setVisibility(View.INVISIBLE);

        mDeleteButton = (Button)mLayoutView.findViewById(R.id.button_delete);
        mDeleteButton.setVisibility(View.INVISIBLE);

        //  Bundle extras = getIntent().getExtras();

        int Value = getShownIndex();

        // Toast.makeText(getActivity(), Integer.toString(Value), Toast.LENGTH_SHORT).show();

        if(Value>0){
            //means this is in view mode - not the add contact part.

            // Toast.makeText(getActivity(), Integer.toString(Value), Toast.LENGTH_SHORT).show();

            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();

            String nam   = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
            String phon  = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
            String emai  = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
            String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
            String plac  = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

            if (!rs.isClosed()) {
                rs.close();
            }

            // Toast.makeText(getActivity(), nam, Toast.LENGTH_SHORT).show();
            setButtonsToViewMode();

            name.setText((CharSequence)nam);
            name.setFocusable(false);
            name.setClickable(false);
            name.setFocusableInTouchMode(false);
            name.setEnabled(false);

            phone.setText((CharSequence)phon);
            phone.setFocusable(false);
            phone.setClickable(false);
            phone.setFocusableInTouchMode(false);
            phone.setEnabled(false);

            email.setText((CharSequence)emai);
            email.setFocusable(false);
            email.setClickable(false);
            email.setFocusableInTouchMode(false);
            email.setEnabled(false);

            street.setText((CharSequence)stree);
            street.setFocusable(false);
            street.setClickable(false);
            street.setFocusableInTouchMode(false);
            street.setEnabled(false);

            place.setText((CharSequence)plac);
            place.setFocusable(false);
            place.setClickable(false);
            place.setFocusableInTouchMode(false);
            place.setEnabled(false);

        }

        // setting the listeners for the buttons
        mEditButton   = (Button) getActivity().findViewById(R.id.button_edit);
        mSaveButton   = (Button) getActivity().findViewById(R.id.button1);
        mDeleteButton = (Button) getActivity().findViewById(R.id.button_delete);

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setButtonsToEditMode();
                onEditContactClick();

            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
                setButtonsToViewMode();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteContactClick();
            }
        });
    }

    private void setButtonsToEditMode(){
        mSaveButton.setVisibility(View.VISIBLE);
        mEditButton.setVisibility(View.INVISIBLE);
        mDeleteButton.setVisibility(View.INVISIBLE);
    }
    private void setButtonsToViewMode(){
        mSaveButton.setVisibility(View.INVISIBLE);
        mEditButton.setVisibility(View.VISIBLE);
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    public void onDeleteContactClick(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.deleteContact)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteContact(id_To_Update);
                        Toast.makeText(getActivity().getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                        startActivity(intent);  // back to main activity
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle("Are you sure?");
        d.show();

    }

    public void onEditContactClick(){

        name.setEnabled(true);
        name.setFocusableInTouchMode(true);
        name.setClickable(true);
        name.setFocusable(true);


        phone.setEnabled(true);
        phone.setFocusableInTouchMode(true);
        phone.setClickable(true);
       // phone.setFocusable(true);

        email.setEnabled(true);
        email.setFocusableInTouchMode(true);
        email.setClickable(true);
       // email.setFocusable(true);

        street.setEnabled(true);
        street.setFocusableInTouchMode(true);
        street.setClickable(true);
      //  street.setFocusable(true);

        place.setEnabled(true);
        place.setFocusableInTouchMode(true);
        place.setClickable(true);
     //   place.setFocusable(true);

    }

    public void run()     {

        int Value = getShownIndex();
        if(Value>0){

            // Updating a contact
            if(mydb.updateContact(id_To_Update,name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), place.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            // inserting a new contact
            if(mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), place.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Contact inserted!", Toast.LENGTH_SHORT).show();

                // disable inserting  the same contact again


            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Contact NOT inserted. ", Toast.LENGTH_SHORT).show();
            }

            // I don't want to start the same activity in which the fragment is running
            // I want just to refresh the display of a fragment


            //Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
            //startActivity(intent);
        }

        // Refresh the fragment in which the list of data is re-displayed
        ContactListFragment contactListFragment = (ContactListFragment)getFragmentManager().findFragmentById(R.id.contactlist_fragment_container);
        if (contactListFragment!=null){
            contactListFragment.refresh();
        }


    }

}
