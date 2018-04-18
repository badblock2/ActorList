package com.example.actorlist;

import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/*
    ActorList Application

    1.RecyclerView를 기반으로 한 Dummy Actor List 구현
        - Add New Fragment(List) with Object Kind (ActorList)
        - Add Fragment XML element into activity_main.xml
        - Implement ActorListFragment.OnListFragmentInteractionListener on MainActivity

    2.http/json API를 통한 Actor 정보 조회

    3.Actor 상세 화면 구현

    4.UI Polishing
 */

public class MainActivity extends AppCompatActivity implements ActorListFragment.OnListFragmentInteractionListener, ActorDetailFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showDetailDialog(ActorEntry item){
        Log.d(TAG,"showDetailDialog() called.");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if(prev!=null){
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        DialogFragment dialog = ActorDetailFragment.newInstance(item);
        dialog.show(transaction,"dialog");
    }


    @Override
    public void onListFragmentInteraction(ActorEntry item) {
        Log.d(TAG, "onListFragmentInteraction() called. (CLICK)");

        showDetailDialog(item);
    }

    @Override
    public void onDetailFragmentInteraction(Uri uri) {
        Log.d(TAG, "onDetailFragmentInteraction() called. (CLICK)");

    }
}
