package com.example.actorlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.actorlist.util.JsonUtil;
import com.example.actorlist.util.NetworkUtil;

import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ActorListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = ActorListFragment.class.getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private RecyclerView mRecyclerView;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActorListFragment() {
        Log.d(TAG, "ActosFragment() Default Constructor called.");
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ActorListFragment newInstance(int columnCount) {
        Log.d(TAG,"newInstance() called.");

        ActorListFragment fragment = new ActorListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate() called.");

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView() called.");

        View view = inflater.inflate(R.layout.fragment_actor_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        // Set the adapter
        if (mRecyclerView instanceof RecyclerView) {
            Context context = view.getContext();

            Log.d(TAG,"RecyclerView.setLayoutManager");
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //TODO: Start LoaderManager.initLoader
            getLoaderManager().initLoader(0,null,this);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG,"onAttach() called");

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                                               + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG,"onDetach() called");
        mListener = null;
    }

    @NonNull
    @Override
    public AsyncTaskLoader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(getContext()){

            private final String TAG = AsyncTaskLoader.class.getSimpleName();
            private String mData = null;

            @Override
            protected void onStartLoading() {
                Log.d(TAG,"onStartLoading() called.");

                if(mData != null){
                    deliverResult(mData);
                }else{
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable String data) {
                Log.d(TAG,"deliverResult() called.");

                mData = data;
                super.deliverResult(data);
            }

            @Nullable
            @Override
            public String loadInBackground() {
                Log.d(TAG,"loadInBackgroud() called.");

                return NetworkUtil.getResponseFromHttpUrl();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(TAG,"onLoadFinished() called.");

        if(data!=null && data.length()>0){
            List<ActorEntry> actors = JsonUtil.parseJsonResponse(data);
            Log.d(TAG,"RecyclerView.setAdapter() called");
            mRecyclerView.setAdapter(new MyActorRecyclerViewAdapter(actors,mListener));
        }else{
            Log.e(TAG,"Request call failed");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG,"onLoaderReset() called.");
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ActorEntry item);
    }
}
