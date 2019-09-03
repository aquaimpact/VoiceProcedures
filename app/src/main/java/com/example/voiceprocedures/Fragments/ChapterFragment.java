package com.example.voiceprocedures.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.ExpendableListAdapter;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChapterFragment extends Fragment {

    DatabaseHelper mDatabasehelper;
    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.frag_chapter, container, false);

        listView = (ExpandableListView) RootView.findViewById(R.id.expLists);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        getActivity().setTitle("Chapter Selection");

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), listDataHeader.get(groupPosition) + " : " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mDatabasehelper = new DatabaseHelper(getActivity());

        return RootView;
    }

    // PLACE TO PUT DATA!!! USE TO INTEGRATE DB
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        // DELETE AND REPLACE WITH DB INFO
        listDataHeader.add("Distress Communications");
        listDataHeader.add("Urgency Traffic");
        listDataHeader.add("Safety Communicaions");

        List<String> Distress_Traffic = new ArrayList<>();
        Distress_Traffic.add("Fire, Explosion");
        Distress_Traffic.add("Flooding");
        Distress_Traffic.add("Collision");
        Distress_Traffic.add("Grounding");

        List<String> UT = new ArrayList<>();
        UT.add("Fire, Explosion");
        UT.add("Flooding");
        UT.add("Collision");
        UT.add("Grounding");

        List<String> SComm = new ArrayList<>();
        SComm.add("Fire, Explosion");
        SComm.add("Flooding");
        SComm.add("Collision");
        SComm.add("Grounding");

        listHash.put(listDataHeader.get(0), Distress_Traffic);
        listHash.put(listDataHeader.get(1), UT);
        listHash.put(listDataHeader.get(2), SComm);

    }
}
