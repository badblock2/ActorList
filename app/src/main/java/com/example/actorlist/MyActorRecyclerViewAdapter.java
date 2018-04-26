package com.example.actorlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.actorlist.ActorListFragment.OnListFragmentInteractionListener;
import com.example.actorlist.databinding.FragmentActorBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ActorEntry} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyActorRecyclerViewAdapter extends RecyclerView.Adapter<MyActorRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MyActorRecyclerViewAdapter.class.getSimpleName();

    private final List<ActorEntry> mValues;
    private final OnListFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private int mExpandedPosition = -1;

    public MyActorRecyclerViewAdapter(List<ActorEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        Log.d(TAG,"Set the Adapter to use stable_id");
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mValues.get(position).id.hashCode();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        int position = holder.getAdapterPosition();
        Log.d(TAG,"onViewAttachedFromWindow() ViewHolder(" + position + ")");

        if(position == mExpandedPosition) {
            holder.binding.detailLayout.setVisibility(View.VISIBLE);
            holder.itemView.setActivated(true);
            Log.d(TAG,"Selection ON ViewHolder (" + position +")");
        }else{
            holder.binding.detailLayout.setVisibility(View.GONE);
            holder.itemView.setActivated(false);
            Log.d(TAG,"Selection OFF ViewHolder (" + position +")");
        }

        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int itemCount = getItemCount();
        int poolCount = mRecyclerView.getRecycledViewPool().getRecycledViewCount(0);

        Log.d(TAG,"onViewDetachedFromWindow() ViewHolder(" + holder.getAdapterPosition() + ")"
                + "ItemCount:" + itemCount
             + "ViewHolderPool:" + poolCount );
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        Log.d(TAG,"onAttachedToRecyclerView() called.");
        mRecyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Create new ViewHolder. viewType:" + viewType);

        //View view = LayoutInflater.from(parent.getContext())
        //        .inflate(R.layout.fragment_actor, parent, false);
        //return new ViewHolder(view);
        FragmentActorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.fragment_actor,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG,"Bind ViewHolder (" + position + ")");

        holder.bind(mValues.get(position));

        if(position == mExpandedPosition) {
            holder.binding.detailLayout.setVisibility(View.VISIBLE);
            holder.itemView.setActivated(true);
            Log.d(TAG,"Selection ON ViewHolder (" + position +")");
        }else{
            holder.binding.detailLayout.setVisibility(View.GONE);
            holder.itemView.setActivated(false);
            Log.d(TAG,"Selection OFF ViewHolder (" + position +")");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                Log.d(TAG,"Click ViewHolder (" + position + ")");

                if(mExpandedPosition>-1 && mExpandedPosition!=position) {
                    Log.d(TAG,"Toggle previous ViewHolder (" + mExpandedPosition + ")");
                    MyActorRecyclerViewAdapter.ViewHolder prevViewHolder;

                    long itemId = getItemId(mExpandedPosition);
                    prevViewHolder = (MyActorRecyclerViewAdapter.ViewHolder) mRecyclerView.findViewHolderForItemId(itemId);
                    Log.d(TAG,"findViewHolderForItemId(" + itemId + ") viewHolder:" + (prevViewHolder!=null) );

                    prevViewHolder =
                            (MyActorRecyclerViewAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mExpandedPosition);
                    if(prevViewHolder==null){

                        for(int i=0 ; i<mRecyclerView.getChildCount() ; i++){
                            View child = mRecyclerView.getChildAt(i);
                            ViewHolder viewHolder = (MyActorRecyclerViewAdapter.ViewHolder)mRecyclerView.getChildViewHolder(child);
                            int position = viewHolder.getAdapterPosition();
                            Log.d(TAG,"searching ViewHolder(" + position + ")");
                            if( position == mExpandedPosition){
                                Log.d(TAG,"Found!!!!!!!! Unbounded ViewHold (" + mExpandedPosition + ")");
                            }
                        }
                    }
                    toggleExpandableItem(prevViewHolder,mExpandedPosition);
                }

                if(toggleExpandableItem(holder,position)){
                    Log.d(TAG,"Toggle current ViewHolder (" + position + ")");
                    mExpandedPosition = position;
                }else{
                    mExpandedPosition = -1;
                }

            }

            private boolean toggleExpandableItem(final MyActorRecyclerViewAdapter.ViewHolder viewHolder, int position){
                boolean expanded = false;
                boolean shouldExpand;

                if(viewHolder==null){
                    Log.d(TAG,"Not found ViewHolder (" + position + ")");
                    return false;
                }

                shouldExpand = viewHolder.binding.detailLayout.getVisibility() == View.GONE;

                ChangeBounds transition = new ChangeBounds();
                transition.setDuration(150);
                TransitionManager.beginDelayedTransition(mRecyclerView, transition);

                viewHolder.itemView.setActivated(shouldExpand);

                if (shouldExpand) {
                    viewHolder.binding.detailLayout.setVisibility(View.VISIBLE);
                    expanded = true;
                    Log.d(TAG,"Selection ON ViewHolder (" + position +")");
                }else{
                    viewHolder.binding.detailLayout.setVisibility(View.GONE);
                    Log.d(TAG,"Selection OFF ViewHolder (" + position +")");
                }

                return expanded;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.item);
                    Log.d(TAG,"Long Click ViewHolder (" + position + ")");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues!=null? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ActorEntry item;
        public FragmentActorBinding binding;

        public ViewHolder(FragmentActorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ActorEntry item){
            binding.id.setText(item.id);
            binding.photo.setBackgroundResource(item.photo);
            binding.photo.setClipToOutline(true);
            binding.name.setText(item.name);
            binding.gender.setText(item.gender);
            binding.email.setText(item.email);
            binding.address.setText(item.address);
            binding.mobile.setText(item.mobile);
            binding.home.setText(item.home);
            binding.office.setText(item.office);

            this.item = item;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + binding.name.getText() + "'";
        }
    }
}
