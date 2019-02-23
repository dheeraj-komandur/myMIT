package com.dheeraj.mitver1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;



public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String,String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    HashMap<String, String> resultp1 = new HashMap<String, String>();
    LayoutInflater inflter;

    public CustomAdapter(Context context,ArrayList<HashMap<String,String>> arrayList) {
        this.context = context;
        data=arrayList;

        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.customlayout, null);



        TextView subject = view.findViewById(R.id.listview_subject);
        TextView present = view.findViewById(R.id.listview_present);
        TextView total = view.findViewById(R.id.listview_total);
        TextView percentage = view.findViewById(R.id.listview_percentage);

        resultp = data.get(i);
        String token = resultp.get(AttandanceListView.MAIN_ROW);


        if(token.equals("1"))//Main row
        {

            subject.setText(resultp.get(AttandanceListView.SUBJECT) + " (" + resultp.get(AttandanceListView.TYPE) + ")");
            present.setText(resultp.get(AttandanceListView.PRESENT));
            total.setText(resultp.get(AttandanceListView.TOTAL));
            percentage.setText(resultp.get(AttandanceListView.PERCENTAGE));
        }
        else if(token.equals("0"))//sub row
        {
            //resultp = data.get(i);
            //error for -1
            resultp1 = data.get(i - 1);

            subject.setText(resultp1.get(AttandanceListView.SUBJECT) + " (" + resultp.get(AttandanceListView.TYPE) + ")");
            present.setText(resultp.get(AttandanceListView.PRESENT));
            total.setText(resultp.get(AttandanceListView.TOTAL));
            percentage.setText(resultp.get(AttandanceListView.PERCENTAGE));
        }
        else if(token.equals("2"))//last 4 rows
        {
            subject.setText(resultp.get(AttandanceListView.SUBJECT));
            present.setText(resultp.get(AttandanceListView.PRESENT));
            total.setText(resultp.get(AttandanceListView.TOTAL));
            percentage.setText(resultp.get(AttandanceListView.PERCENTAGE));
        }


        return view;
    }

}
