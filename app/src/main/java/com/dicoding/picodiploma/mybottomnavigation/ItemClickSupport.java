package com.dicoding.picodiploma.mybottomnavigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemClickSupport {
    private final RecyclerView recyclerView;
    private OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null){
                RecyclerView.ViewHolder vHolder = recyclerView.getChildViewHolder(view);
                mOnItemClickListener.onItemClicked(recyclerView,vHolder.getAdapterPosition(),view);
            }
        }
    };

    private final RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {

            if (mOnItemClickListener !=null){
                view.setOnClickListener(mOnClick);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setTag(R.id.item_click_support,this);
        recyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo (RecyclerView view){
        ItemClickSupport clickSupport = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (clickSupport == null){
            clickSupport = new ItemClickSupport(view);
        }
        return clickSupport;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{

        void onItemClicked(RecyclerView recyclerView, int adapterPosition, View view);
    }
}
