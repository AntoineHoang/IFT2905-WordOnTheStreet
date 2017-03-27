package com.example.csanchez.ift2905_wordonthestreet;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.xml.datatype.Duration;

public class NewsActivity extends AppCompatActivity {
    private String src[] = {"gaming"};
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        src = getIntent().getExtras().getStringArray("sources");
        System.out.println("HOOOO: "+src[0]);

        list = (ListView) findViewById(R.id.listView_news);

        NewsFetcher news = new NewsFetcher();
        news.execute();
    }

    public class NewsFetcher extends AsyncTask<Object, Object, News[]> {

        @Override
        protected News[] doInBackground(Object... params) {

            News[] lineups = new News[1];

            try {
                lineups = NewsAPI.getNews(src);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return lineups;
        }

        @Override
        protected void onPostExecute(final News[] news) {

            list.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return news.length;
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    if(convertView == null)
                        convertView = getLayoutInflater().inflate(R.layout.single_news, parent, false);

                    TextView title = (TextView) convertView.findViewById(R.id.title);
                    TextView description = (TextView) convertView.findViewById(R.id.description);
                    ImageView image = (ImageView) convertView.findViewById(R.id.image);

                    title.setText(news[position].title);
                    description.setText(news[position].description);

                    Picasso.with(getApplicationContext())
                            .load(news[position].image)
                            .into(image);

                    return convertView;
                }
            });

        }
    }
}