package com.example.actorlist;

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

public class MainActivity extends AppCompatActivity implements ActorListFragment.OnListFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(ActorEntry item) {
        Log.d(TAG, "onListFragmentInteraction() called. (CLICK)");
        Toast.makeText(this,item.name,Toast.LENGTH_LONG).show();
    }
}
