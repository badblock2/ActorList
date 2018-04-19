package com.example.actorlist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActorDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActorDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActorDetailFragment extends DialogFragment {
    private static final String TAG = ActorDetailFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ActorEntry mActor;

    private OnFragmentInteractionListener mListener;

    public ActorDetailFragment() {
        // Required empty public constructor
        Log.d(TAG,"ActorDetailFragment Default Constructor called");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActorDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActorDetailFragment newInstance(ActorEntry actor) {
        Log.d(TAG,"newInstance() called");

        ActorDetailFragment fragment = new ActorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, actor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate() called");
        if (getArguments() != null) {
            mActor = (ActorEntry) getArguments().getSerializable(ARG_PARAM1);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView() called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actor_detail, container, false);

        ImageView imageView = view.findViewById(R.id.photo);
        imageView.setImageResource(mActor.photo);
        imageView.setClipToOutline(true);

        ((TextView)view.findViewById(R.id.name)).setText(mActor.name);
        ((TextView)view.findViewById(R.id.id)).setText(mActor.id);
        ((TextView)view.findViewById(R.id.gender)).setText(mActor.gender);
        ((TextView)view.findViewById(R.id.email)).setText(mActor.email);
        ((TextView)view.findViewById(R.id.address)).setText(mActor.address);
        ((TextView)view.findViewById(R.id.mobile)).setText(mActor.mobile);
        ((TextView)view.findViewById(R.id.home)).setText(mActor.home);
        ((TextView)view.findViewById(R.id.office)).setText(mActor.office);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getDialog()==null){
            return;
        }

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDetailFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach() called");

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                                               + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG,"onDetach() called");

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDetailFragmentInteraction(Uri uri);
    }
}
