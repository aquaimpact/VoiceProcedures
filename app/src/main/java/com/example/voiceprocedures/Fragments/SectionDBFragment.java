package com.example.voiceprocedures.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voiceprocedures.CRUD_SECTIONS.CreateSection;
import com.example.voiceprocedures.CRUD_SECTIONS.SectionDelete;
import com.example.voiceprocedures.CRUD_SECTIONS.SectionDetails;
import com.example.voiceprocedures.CRUD_SECTIONS.SectionEdit;
import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterCreate;
import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterDelete;
import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterDetails;
import com.example.voiceprocedures.CRUD_SUBCHAPTER.SubChapterEdit;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.ExpendableListAdapter;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionDBFragment extends Fragment {

    DatabaseHelper mDatabasehelper;
    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private TextView createnewu;

    SharedPreferences sectDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.frag_section, container, false);

        getActivity().setTitle("Sections In DB");

        mDatabasehelper = new DatabaseHelper(getActivity());

//        mDatabasehelper.DELETE_DB();

        sectDetail = getActivity().getSharedPreferences("sectionCreationDetails", Context.MODE_PRIVATE);

        listView = (ExpandableListView) RootView.findViewById(R.id.expListsSec);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SharedPreferences.Editor editor = sectDetail.edit();
                editor.putString("sectName", listDataHeader.get(groupPosition));
                editor.commit();
//                mDatabasehelper.DELETE_DB();

//                System.out.println("LOL");
//                System.out.println(String.valueOf(groupPosition + 1));
//                System.out.println("LOL");

                if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Details"){

                    Intent intent = new Intent(getActivity(), SectionDetails.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit"){

                    Intent intent = new Intent(getActivity(), SectionEdit.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Delete"){

                    Intent intent = new Intent(getActivity(), SectionDelete.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        createnewu = (TextView) RootView.findViewById(R.id.createnewSec);

        createnewu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreation();
            }
        });

        return RootView;
    }

    private void userCreation(){
        Intent intent = new Intent(getActivity(), CreateSection.class);
        startActivity(intent);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        Cursor cursor = mDatabasehelper.allsectData();
        // DELETE AND REPLACE WITH DB INFO

        cursor.moveToFirst();
//        System.out.println(cursor.getString(1));

        int counter = 0;
        try {
            listDataHeader.add(cursor.getString(cursor.getColumnIndex("sectionName")));
            List<String> LOL2 = new ArrayList<>();
            LOL2.add("Details");
            LOL2.add("Edit");
            LOL2.add("Delete");

            listHash.put(listDataHeader.get(0), LOL2);

            while (cursor.moveToNext()) {
                listDataHeader.add(cursor.getString(cursor.getColumnIndex("sectionName")));

                counter += 1;
                List<String> LOL = new ArrayList<>();
                LOL.add("Details");
                LOL.add("Edit");
                LOL.add("Delete");
                listHash.put(listDataHeader.get(counter), LOL);
            }
        }catch (Exception ex){
            System.out.println("Error!");
        }

//        List<String> Distress_Traffic = new ArrayList<>();
//        Distress_Traffic.add("Fire, Explosion");
//        Distress_Traffic.add("Flooding");
//        Distress_Traffic.add("Collision");
//        Distress_Traffic.add("Grounding");
//
//        List<String> UT = new ArrayList<>();
//        UT.add("Fire, Explosion");
//        UT.add("Flooding");
//        UT.add("Collision");
//        UT.add("Grounding");
//
//        List<String> SComm = new ArrayList<>();
//        SComm.add("Fire, Explosion");
//        SComm.add("Flooding");
//        SComm.add("Collision");
//        SComm.add("Grounding");


//        listHash.put(listDataHeader.get(2), SComm);

    }
}
