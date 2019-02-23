package com.dheeraj.mitver1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AttandanceListView extends AppCompatActivity {

    ListView listview;
    ArrayList<HashMap<String, String>> arraylist;
    static String SUBJECT = "subject";
    static String TYPE = "type";
    static String PRESENT = "present";
    static String TOTAL = "total";
    static String PERCENTAGE = "percentage";
    static String MAIN_ROW = "main_row";
    static int totalrows = 0;
    static int webviewcalled = 0;
    //String url = "http://creationdevs.in/dheeraj/";
    String url = "https://erp.mitwpu.edu.in/STUDENT/SelfAttendence.aspx?MENU_CODE=MWEBSTUATTEN_SLF_ATTEN";
    DbService dbService = new DbService(AttandanceListView.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandancelistview);

        if(webviewcalled==0)
        {
           //
            String result = dbService.SessionFetch();

            if (result.equals("nodata")) {
                Intent intent = new Intent(AttandanceListView.this, MainActivity.class);
                startActivity(intent);
                webviewcalled++;
            }
        }

        new JsoupListView().execute();
    }

    public class JsoupListView extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listview = (ListView) findViewById(R.id.listview);
            CustomAdapter adapter = new CustomAdapter(AttandanceListView.this, arraylist);
            listview.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            arraylist =new ArrayList<HashMap<String, String>>();

            try{
                //Bundle bundle = getIntent().getExtras();
                //String session = bundle.getString("cook");
               // DbService dbService = new DbService(AttandanceListView.this);
                String session = dbService.SessionFetch();


                Document doc = Jsoup.connect(url).cookie("ASP.NET_SessionId",session).get();
                int rownum =0;
                //as per example----for (Element table : doc.select("table[class=worldpopulation]"))
                Element table = doc.select("table").get(3);
                //only change table number in wpu website
                {
                    for (Element row : table.select("tr:gt(0)"))
                    {
                        totalrows++;
                    }
                    for (Element row : table.select("tr:gt(0)"))
                    {
                        HashMap<String, String> map = new HashMap<String, String>();
                        Elements tds = row.select("td");

                        String token = tds.get(2).text();
                        if(token.equals("TH")||token.equals("TR")||token.equals("PR") )
                        {

                            map.put("subject", tds.get(1).text());
                            map.put("type", tds.get(2).text());
                            map.put("present", tds.get(3).text());
                            map.put("total", tds.get(4).text());
                            map.put("percentage", tds.get(5).text());
                            map.put("main_row","1");

                            arraylist.add(map);
                        }
                        else if(rownum<totalrows-4)
                        {
                            //map.put("subject", tds.get(1).text());
                            map.put("type", tds.get(0).text());
                            map.put("present", tds.get(1).text());
                            map.put("total", tds.get(2).text());
                            map.put("percentage", tds.get(3).text());
                            map.put("main_row","0");

                            arraylist.add(map);
                        }


                        else if(rownum>=totalrows-4)
                        {
                            map.put("subject", tds.get(0).text());
                            map.put("present", tds.get(1).text());
                            map.put("total", tds.get(2).text());
                            map.put("percentage", tds.get(3).text());
                            map.put("main_row","2");

                            arraylist.add(map);
                        }
                        rownum++;
                    }
                }
            }
            catch (IOException e)
            {
                Toast.makeText(AttandanceListView.this, e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return null;
        }
    }
}
