

package com.chuckylee.search;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private EditText mSearchField;
    private Button mAddBtn;
    private ImageButton mSearchBtn;
    private TextView mIdexView;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase,mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");


        mSearchField = (EditText) findViewById(R.id.search);
        mSearchBtn = (ImageButton) findViewById(R.id.button);
        mAddBtn = (Button) findViewById(R.id.addbutton);
        mResultList = (RecyclerView) findViewById(R.id.searchlit);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("index");
        mIdexView = (TextView) findViewById(R.id.text_index);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String index =dataSnapshot.getValue().toString();
                mIdexView.setText(index);
                checkIndex(index);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);

                //checkIndex(index);


            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

    }

////////////////////////////////////////////   SEARCH /////////////////////////////////////////////////////////////////
    private void firebaseUserSearch(String searchText) {

        Toast.makeText(MainActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
      //  Query fire1 = mUserDatabase.orderByChild("id").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<users, UsersViewHolder>(

                users.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

                //mUserDatabase

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, users model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getEmail(), model.getId(), model.getNumber());
               // check(model.getName());
                //    check2(model.getNumber());
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userEmail, String userId, String userNumber){


            TextView user_name = (TextView) mView.findViewById(R.id.view1);
            TextView user_email = (TextView) mView.findViewById(R.id.view2);
            TextView user_id = (TextView) mView.findViewById(R.id.view3);
            TextView user_number = (TextView) mView.findViewById(R.id.view4);


            user_name.setText(userName);
            user_email.setText(userEmail);
            user_id.setText(userId);
            user_number.setText(userNumber);


            //Glide.with(ctx).load(userImage).into(user_image);
        }
    }

    private void add(){
        String addname = mSearchField.getText().toString().trim();
        String addemail = mSearchField.getText().toString().trim();
        String addnumber = mSearchField.getText().toString().trim();


        if (!TextUtils.isEmpty(addname)){
            String addid = mUserDatabase.push().getKey();
           // String addemail = mUserDatabase.push().getKey();
           // String addnumber = mUserDatabase.push().getKey();
            users user = new users(addname, addid, addemail, addnumber );

            mUserDatabase.child(addid).setValue(user);
            Toast.makeText(this, "added", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "enter name", Toast.LENGTH_LONG).show();
        }
    }

    private void check(String get_id){
     //   TextView user_id = (TextView) findViewById(R.id.view3);
     //   user_id.setText(get_id);
        String a= "hey";
    //    if(get_id == a){
        if(get_id.equals(a)){
            Toast.makeText(this, "OMG, HERE YOU ARE", Toast.LENGTH_LONG).show();
        }
        else {
           // Toast.makeText(this, "HUHU, NOT YOU", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "it's not you" , Toast.LENGTH_LONG).show();
        }
    }


    private void check2(String get_number){
        float foo = Float.parseFloat(get_number);
        if (foo <= 16){
            Toast.makeText(this, "TOO COLD", Toast.LENGTH_LONG).show();
        }

        else if (foo > 16 && foo <=30){
            Toast.makeText(this, "COOL", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(this, "TOO HOT", Toast.LENGTH_LONG).show();
        }
    }

    private void checkIndex(String index){

        int foo = Integer.parseInt(index);
       // Toast.makeText(this, index, Toast.LENGTH_LONG).show();
        if (foo == 13){
            Toast.makeText(this, "YAHOO", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "huhu", Toast.LENGTH_LONG).show();
        }
    }

}

