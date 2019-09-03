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

import com.example.voiceprocedures.CRUD_CHAPTER.ChapterDelete;
import com.example.voiceprocedures.CRUD_CHAPTER.ChapterDetails;
import com.example.voiceprocedures.CRUD_CHAPTER.ChapterEdit;
import com.example.voiceprocedures.CRUD_CHAPTER.CreateChapter;
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

public class SubChapterDBFragment extends Fragment {

    DatabaseHelper mDatabasehelper;
    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private TextView createnewu;

    SharedPreferences SubChapterDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.frag_subchapterdb, container, false);
        mDatabasehelper = new DatabaseHelper(getActivity());

        getActivity().setTitle("Sub Chapters In DB");

//        mDatabasehelper.DELETE_DB();

        SubChapterDetail = getActivity().getSharedPreferences("subchapterCreationDetails", Context.MODE_PRIVATE);

        listView = (ExpandableListView) RootView.findViewById(R.id.expListsSC);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SharedPreferences.Editor editor = SubChapterDetail.edit();
                editor.putString("subchaptName", listDataHeader.get(groupPosition));
                editor.commit();
//                    mDatabasehelper.resetrows();

//                System.out.println("LOL");
//                System.out.println(String.valueOf(groupPosition + 1));
//                System.out.println("LOL");

                if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Details"){
                    Intent intent = new Intent(getActivity(), SubChapterDetails.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit"){

                    Intent intent = new Intent(getActivity(), SubChapterEdit.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Delete"){
                    Intent intent = new Intent(getActivity(), SubChapterDelete.class);
                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        createnewu = (TextView) RootView.findViewById(R.id.createnewSC);

        createnewu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreation();
            }
        });

        return RootView;

    }

    private void userCreation(){
        Intent intent = new Intent(getActivity(), SubChapterCreate.class);
        startActivity(intent);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        Cursor cursor = mDatabasehelper.allSubChapterData();
        // DELETE AND REPLACE WITH DB INFO

        cursor.moveToFirst();
//        System.out.println(cursor.getString(1));

        int counter = 0;
        try {
            listDataHeader.add(cursor.getString(cursor.getColumnIndex("subchapterName")));
            List<String> LOL2 = new ArrayList<>();
            LOL2.add("Details");
            LOL2.add("Edit");
            LOL2.add("Delete");

            listHash.put(listDataHeader.get(0), LOL2);

            while (cursor.moveToNext()) {
                listDataHeader.add(cursor.getString(cursor.getColumnIndex("subchapterName")));

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
