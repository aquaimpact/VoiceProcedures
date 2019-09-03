package com.example.voiceprocedures.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class VoiceRecordingDBFragment extends Fragment {

    DatabaseHelper mDatabasehelper;
    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private TextView createnewV;

    SharedPreferences VoiceDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.frag_voicerecording, container, false);

        mDatabasehelper = new DatabaseHelper(getActivity());

        getActivity().setTitle("Voice Clips In DB");

//        mDatabasehelper.DELETE_DB();

        VoiceDetail = getActivity().getSharedPreferences("voiceCreationDetails", Context.MODE_PRIVATE);

        listView = (ExpandableListView) RootView.findViewById(R.id.expListsVoice);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SharedPreferences.Editor editor = VoiceDetail.edit();
                editor.putString("voiceName", listDataHeader.get(groupPosition));
                editor.commit();
//                    mDatabasehelper.resetrows();

//                System.out.println("LOL");
//                System.out.println(String.valueOf(groupPosition + 1));
//                System.out.println("LOL");

                if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Details"){
//                    Intent intent = new Intent(getActivity(), SubChapterDetails.class);
//                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit"){

//                    Intent intent = new Intent(getActivity(), SubChapterEdit.class);
//                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Delete"){
//                    Intent intent = new Intent(getActivity(), SubChapterDelete.class);
//                    startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        createnewV = (TextView) RootView.findViewById(R.id.createnewVoice);

        createnewV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreation();
            }
        });
        return RootView;
    }

    private void userCreation(){
//        Intent intent = new Intent(getActivity(), SubChapterCreate.class);
//        startActivity(intent);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        Cursor cursor = mDatabasehelper.allvoiceData();
        // DELETE AND REPLACE WITH DB INFO

        cursor.moveToFirst();
//        System.out.println(cursor.getString(1));

        int counter = 0;
        try {
            listDataHeader.add(cursor.getString(cursor.getColumnIndex("recordingName")));
            List<String> LOL2 = new ArrayList<>();
            LOL2.add("Details");
            LOL2.add("Edit");
            LOL2.add("Delete");

            listHash.put(listDataHeader.get(0), LOL2);

            while (cursor.moveToNext()) {
                listDataHeader.add(cursor.getString(cursor.getColumnIndex("recordingName")));

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

    }

}
