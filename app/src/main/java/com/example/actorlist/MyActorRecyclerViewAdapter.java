package com.example.actorlist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.actorlist.ActorListFragment.OnListFragmentInteractionListener;

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

    public MyActorRecyclerViewAdapter(List<ActorEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        Log.d(TAG, "Set the Adapter to use stable_id");
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mValues.get(position).id.hashCode();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Create new ViewHolder. viewType:" + viewType);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_actor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG,"Bind ViewHolder (" + position + ")");

        holder.mItem = mValues.get(position);

        holder.mPhoto.setBackgroundResource(mValues.get(position).photo);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mGenderView.setText(mValues.get(position).gender);
        holder.mEmailView.setText(mValues.get(position).email);
        holder.mMobileView.setText(mValues.get(position).mobile);
        holder.mAddressView.setText(mValues.get(position).address);
        holder.mHomeView.setText(mValues.get(position).home);
        holder.mOfficeView.setText(mValues.get(position).office);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues!=null? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ActorEntry mItem;

        public final ImageView mPhoto;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mGenderView;
        public final TextView mMobileView;

        public final ViewGroup mDetailLayout;
        public final TextView mEmailView;
        public final TextView mAddressView;
        public final TextView mHomeView;
        public final TextView mOfficeView;

        public ViewHolder(View view) {
            super(view);

            mPhoto = (ImageView) view.findViewById(R.id.photo);
            mPhoto.setClipToOutline(true);

            mNameView = (TextView) view.findViewById(R.id.name);
            mIdView = (TextView) view.findViewById(R.id.id);
            mGenderView = (TextView) view.findViewById(R.id.gender);
            mMobileView = (TextView) view.findViewById(R.id.mobile);

            mDetailLayout = (ViewGroup) view.findViewById(R.id.detail_layout);
            mEmailView = (TextView) view.findViewById(R.id.email);
            mAddressView = (TextView) view.findViewById(R.id.address);
            mHomeView = (TextView) view.findViewById(R.id.home);
            mOfficeView = (TextView) view.findViewById(R.id.office);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
