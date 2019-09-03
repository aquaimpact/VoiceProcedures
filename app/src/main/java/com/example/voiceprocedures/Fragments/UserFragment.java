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

import com.example.voiceprocedures.CRUD_STUDENT.StudentDelete;
import com.example.voiceprocedures.CRUD_STUDENT.StudentDetails;
import com.example.voiceprocedures.CRUD_STUDENT.StudentEdit;
import com.example.voiceprocedures.CRUD_STUDENT.CreateUser;
import com.example.voiceprocedures.DatabaseHelper;
import com.example.voiceprocedures.ExpendableListAdapter;
import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserFragment extends Fragment {

    DatabaseHelper mDatabasehelper;
    private ExpandableListView listView;
    private ExpendableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private TextView createnewu;

    SharedPreferences userDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.frag_createuser, container, false);

        mDatabasehelper = new DatabaseHelper(getActivity());

//        mDatabasehelper.DELETE_DB();

//        mDatabasehelper.resetrows();

        userDetails = getActivity().getSharedPreferences("userCreationDetails", Context.MODE_PRIVATE);

        listView = (ExpandableListView) RootView.findViewById(R.id.expLists);

        initData();

        listAdapter = new ExpendableListAdapter(getActivity(), listDataHeader, listHash);

        listView.setAdapter(listAdapter);

        getActivity().setTitle("Users In DB");

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putString("StudentName", listDataHeader.get(groupPosition));
                    editor.putString("StudentID", String.valueOf(groupPosition + 1));

//                    mDatabasehelper.resetrows();

                    System.out.println("LOL");
                    System.out.println(String.valueOf(groupPosition + 1));
                    System.out.println("LOL");

                    editor.commit();
                    if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Details"){
                        Intent intent = new Intent(getActivity(), StudentDetails.class);
                        startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                    }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Edit"){

                        Intent intent = new Intent(getActivity(), StudentEdit.class);
                        startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                    }else if (listHash.get(listDataHeader.get(groupPosition)).get(childPosition) == "Delete"){
                        Intent intent = new Intent(getActivity(), StudentDelete.class);
                        startActivity(intent);
//                        Toast.makeText(getActivity(), "User ID: " + String.valueOf(groupPosition) + " Clicked on: " + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                    }
                    return false;
                }
            });

        createnewu = (TextView) RootView.findViewById(R.id.createnewU);

        createnewu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreation();
            }
        });

        return RootView;
    }

    private void userCreation(){
        Intent intent = new Intent(getActivity(), CreateUser.class);
        startActivity(intent);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        Cursor cursor = mDatabasehelper.allUserData();
        // DELETE AND REPLACE WITH DB INFO

        cursor.moveToFirst();
//        System.out.println(cursor.getString(1));

        int counter = 0;
        try {
            listDataHeader.add(cursor.getString(cursor.getColumnIndex("studentName")));
            List<String> LOL2 = new ArrayList<>();
            LOL2.add("Details");
            LOL2.add("Edit");
            LOL2.add("Delete");

            listHash.put(listDataHeader.get(0), LOL2);

            while (cursor.moveToNext()) {
                listDataHeader.add(cursor.getString(cursor.getColumnIndex("studentName")));

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
