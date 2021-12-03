package com.moringaschool.eventsearch.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.eventsearch.Connection.YelpApi;
import com.moringaschool.eventsearch.Connection.YelpClient;
import com.moringaschool.eventsearch.Models.Event;
import com.moringaschool.eventsearch.Models.Location;
import com.moringaschool.eventsearch.Models.YelpEventSearchResponse;
import com.moringaschool.eventsearch.Adapters.MyRestaurantsArrayAdapter;
import com.moringaschool.eventsearch.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventSearch extends AppCompatActivity {
    @BindView(R.id.text2) TextView textView;
    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    List<Event> eventResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        String eventSearch=intent.getStringExtra("searchInput");
        textView.setText(eventSearch);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String restaurant = ((TextView)view).getText().toString();
                Toast.makeText(EventSearch.this,restaurant,Toast.LENGTH_LONG).show();
            }
        });
        YelpApi client = YelpClient.getClient();

        Call<YelpEventSearchResponse> call = client.getRestaurants(eventSearch);
        call.enqueue(new Callback<YelpEventSearchResponse>() {
            @Override
            public void onResponse(Call<YelpEventSearchResponse> call, Response<YelpEventSearchResponse> response) {
               hideProgressBar();
                if(response.isSuccessful()) {
                    eventResponse = response.body().getEvents();
                    String[] events = new String[eventResponse.size()];
//                    String[] location=new String[eventResponse.size()];
//                    for(int i=0;i<events.length;i++){
//                        events[i]=eventResponse.get(i).getName();
                //}
                    if(events.length==0){
                        showUnsuccessfulMessage();
                    }else{
                        ArrayAdapter adapter= new MyRestaurantsArrayAdapter(EventSearch.this,android.R.layout.simple_list_item_1,events);
                        listView.setAdapter(adapter);
                        showRestaurants();

                    }




                }
                else {
                    showUnsuccessfulMessage();

                }
            }

            @Override
            public void onFailure(Call<YelpEventSearchResponse> call, Throwable t) {
                        hideProgressBar();
                        showFailureMessage();
            }

        });

        }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("API Could not fetch data");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        listView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
