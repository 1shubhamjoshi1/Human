package com.jiit.minor2.shubhamjoshi.human;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.authentication.Constants;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.jiit.minor2.shubhamjoshi.human.modals.Chat;

public class CommentAct extends AppCompatActivity {

    String user="";
    ToneAnalyzer service;
    String Res="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_comment);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.rview);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(new LinearLayoutManager(this));



       final Firebase mref = new Firebase(com.jiit.minor2.shubhamjoshi.human.Utils.Constants.BASE_URL).child("commment").child(getIntent().getStringExtra("URL").replace(".",","));
        final Chat[] msg = new Chat[1];


        final String[] s = {""};
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot k : dataSnapshot.getChildren())
                {
                    Chat c = k.getValue(Chat.class);
                    s[0] +=c.getMessage();

                }


                Res = s[0];
                Log.e("SJS",s[0]);
                Res=s[0];
                new  Watson().execute();

                // Call the service and get the tone

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        Button b = (Button)findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText e = (EditText) findViewById(R.id.a);
                    msg[0] = new Chat(user,e.getText().toString(),"");
                mref.push().setValue(msg[0]);
                }
        });

        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Chat, ChatHolder>(Chat.class, android.R.layout.two_line_list_item, ChatHolder.class, mref) {
            @Override
            public void populateViewHolder(ChatHolder chatMessageViewHolder, Chat chatMessage, int position) {
                chatMessageViewHolder.setName(chatMessage.getName());
                chatMessageViewHolder.setText(chatMessage.getMessage());
            }
        };
        recycler.setAdapter(mAdapter);



    }


    public static class ChatHolder extends RecyclerView.ViewHolder {
        private final TextView mNameField;
        private final TextView mTextField;

        public ChatHolder(View itemView) {
            super(itemView);
            mNameField = (TextView) itemView.findViewById(android.R.id.text1);
            mTextField = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setName(String name) {
            mNameField.setText(name);
        }

        public void setText(String text) {
            mTextField.setText(text);
        }
    }

    class Watson extends AsyncTask<Void, String, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
            service.setUsernameAndPassword("fab6aea0-062b-470b-a0f7-a6fca4bd00c8", "JqPjpNdoSNfn");

            String text =
                    "I know the times are difficult! Our sales have been "
                            + "disappointing for the past three quarters for our data analytics "
                            + "product suite. We have a competitive data analytics product "
                            + "suite in the industry. But we need to do our job selling it! "
                            + "We need to acknowledge and fix our sales challenges. "
                            + "We can’t blame the economy for our lack of execution! "
                            + "We are missing critical sales opportunities. "
                            + "Our product is in no way inferior to the competitor products. "
                            + "Our clients are hungry for analytical tools to improve their "
                            + "business outcomes. Economy has nothing to do with it.";

// Call the service and get the tone
            Log.e("TTT","happy");
            if(Res=="")Res = "happy";

            ToneAnalysis tone = service.getTone(Res, null).execute();
      System.out.print(tone.toString());
            return null;
        }
    }
}
