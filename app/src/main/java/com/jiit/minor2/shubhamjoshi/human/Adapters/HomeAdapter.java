package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.modals.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Joshi on 31-08-2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  final Context mContext;

    private List<Items> mData;
    private static final int VIEW_HOLDER_LIST_TYPE_ONE = 0;
    private static final int VIEW_HOLDER_LIST_TYPE_TWO = 1;

    public HomeAdapter(Context context,List<Items> mData) {

        this.mContext = context;
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
    public int getItemViewType(int position) {
        if(mData.get(position).getType()==0)
            return VIEW_HOLDER_LIST_TYPE_ONE;
        else
            return VIEW_HOLDER_LIST_TYPE_TWO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_HOLDER_LIST_TYPE_ONE:
                return  new ViewHolderImage(inflater.inflate(R.layout.home_list_layout1,parent,false));
            case VIEW_HOLDER_LIST_TYPE_TWO:
                return  new ViewHolderVideo(inflater.inflate(R.layout.home_list_layout2,parent,false));
            default:
                return null;

        }
    }
    static int lastPosition=-1;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  ViewHolderImage) {
            ((ViewHolderImage) holder).tv1.setText(mData.get(position).getStr1());
            ((ViewHolderImage) holder).tv2.setText(mData.get(position).getStr2());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onClick(view, position);
                }
            });
        }


        if(holder instanceof  ViewHolderVideo)
        {
            ((ViewHolderVideo) holder).tv1.setText(mData.get(position).getStr1());
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,(position>lastPosition)?R.anim.up_from_bottom:R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = Math.max(lastPosition,position);
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

    public class ViewHolderImage extends RecyclerView.ViewHolder {
        protected TextView tv1;
        protected TextView tv2;
        public  ViewHolderImage(View itemView)
        {
            super(itemView);
            tv1 = (TextView)itemView.findViewById(R.id.txt1);
            tv2 = (TextView)itemView.findViewById(R.id.txt2);
        }
    }

    public class ViewHolderVideo extends RecyclerView.ViewHolder {
        protected TextView tv1;
        protected TextView tv2;
        public  ViewHolderVideo(View itemView)
        {
            super(itemView);
            tv1 = (TextView)itemView.findViewById(R.id.txt1);
        }
    }


}
