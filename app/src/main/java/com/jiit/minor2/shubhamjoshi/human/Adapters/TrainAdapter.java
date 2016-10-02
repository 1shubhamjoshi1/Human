package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Items;
import com.jiit.minor2.shubhamjoshi.human.modals.Twitter.TwitterData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham Joshi on 31-08-2016.
 */
public class TrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  final Context mContext;

    private List<TwitterData> mData;
    String email;


    public TrainAdapter(Context context,List<TwitterData> mData) {
        mContext = context;
        SharedPreferences prefs = mContext.getSharedPreferences("EMAIL", MODE_PRIVATE);
        email = prefs.getString("email", null);

        if (email!= null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }

        if(mData!=null)
            this.mData = mData;
        else
            mData = new ArrayList<>();

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return  new ViewHolderTrain(inflater.inflate(R.layout.twitter_train,parent,false));
        }

    static int lastPosition=-1;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            Picasso.with(mContext).load(mData.get(position).getImageUrl()).into( ((ViewHolderTrain) holder).mImageView1);

            ((ViewHolderTrain) holder).tv2.setText(mData.get(position).getTag());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Firebase f = new Firebase(Constants.BASE_URL);

                    //lets strip data

                    String str = mData.get(position).getTag();
                    List<String> list = new LinkedList<String>(Arrays.asList(str.split(" ")));
                    List<String> newList = new LinkedList<String>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).contains("#"))
                            newList.add(list.get(i));

                    }
                    Log.e("SJ",newList.toString());

                    for(int i=0;i<newList.size();i++)
                    {
                        f.child("interests").child(email.replace(".",",")).child(newList.get(i).replace("#"," ")).setValue("");
                    }
                  //  f.child("interests").child(email.replace(".",",")).push().setValue(mData.get(position).getTag());

                }
            });



      }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    ClickListener mClickListener;

    public  void setClickListener(ClickListener clickListener)
    {
        this.mClickListener = clickListener;
    }

    public interface  ClickListener{
        public void onClick(View v,int pos);
    }

    public class ViewHolderTrain extends RecyclerView.ViewHolder {
        protected ImageView mImageView1;
        protected TextView tv2;
        public  ViewHolderTrain(View itemView)
        {
            super(itemView);
            mImageView1 = (ImageView) itemView.findViewById(R.id.mImageView1);
            tv2 = (TextView)itemView.findViewById(R.id.txt2);
        }
    }




}
