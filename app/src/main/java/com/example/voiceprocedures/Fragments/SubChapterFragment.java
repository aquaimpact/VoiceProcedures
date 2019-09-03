package com.example.voiceprocedures.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voiceprocedures.ExpendableListAdapter;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubChapterFragment extends Fragment {

    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View RootView = inflater.inflate(R.layout.frag_subchapter, container, false);

        listView = (ExpandableListView) RootView.findViewById(R.id.expList);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        getActivity().setTitle("Sub-Chapter Selection");

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), listDataHeader.get(groupPosition) + " : " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return RootView;
    }



    // PLACE TO PUT DATA!!! USE TO INTEGRATE DB
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        // DELETE AND REPLACE WITH DB INFO
        listDataHeader.add("Distress Traffic");
        listDataHeader.add("Search & Rescue Communications");
        listDataHeader.add("Requesting Medical Assistance");

        List<String> Distress_Traffic = new ArrayList<>();
        Distress_Traffic.add("Fire, Explosion");
        Distress_Traffic.add("Flooding");
        Distress_Traffic.add("Collision");
        Distress_Traffic.add("Grounding");

        List<String> SRComm = new ArrayList<>();
        SRComm.add("Fire, Explosion");
        SRComm.add("Flooding");
        SRComm.add("Collision");
        SRComm.add("Grounding");

        List<String> RMA = new ArrayList<>();
        RMA.add("Fire, Explosion");
        RMA.add("Flooding");
        RMA.add("Collision");
        RMA.add("Grounding");

        listHash.put(listDataHeader.get(0), Distress_Traffic);
        listHash.put(listDataHeader.get(1), SRComm);
        listHash.put(listDataHeader.get(2), RMA);

    }

}
