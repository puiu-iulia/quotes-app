package com.example.motivationalapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.motivationalapp.Data.QuoteData;
import com.example.motivationalapp.Data.QuoteListAsyncResponse;
import com.example.motivationalapp.Data.QuoteViewPagerAdapter;
import com.example.motivationalapp.Model.Quote;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuoteViewPagerAdapter quoteViewPagerAdapter;
    private ViewPager viewPager;
    private ArrayList<Quote> quoteList = new ArrayList();
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quoteViewPagerAdapter = new QuoteViewPagerAdapter(getSupportFragmentManager(), getFragments());

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(quoteViewPagerAdapter);
        shareButton = findViewById(R.id.share);

        shareButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_SEND);
                       myIntent.setType("text/plain");
                       String shareSub = "Quotes";
                       QuoteFragment fragment = (QuoteFragment) quoteViewPagerAdapter.getItem(viewPager.getCurrentItem());
                       String shareBody= fragment.getArguments().getString("quote") + " (" + fragment.getArguments().getString("author") + ") " ;
                       myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                       myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                       startActivity(Intent.createChooser(myIntent, "Share using"));
                   }
               });


    }

    private List<Fragment> getFragments() {
        //Log.i("tag", "Guten Tag");
        final List<Fragment> fragmentList = new ArrayList<>();

        new QuoteData().getQuotes(new QuoteListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Quote> quotes) {

                for (int i = 0; i < quotes.size(); i++) {
                    final QuoteFragment quoteFragment = QuoteFragment.newInstance(
                            quotes.get(i).getQuote(),
                            quotes.get(i).getAuthor()
                    );
                    fragmentList.add(quoteFragment);



                }


                quoteViewPagerAdapter.notifyDataSetChanged();/// very important!!
               //Log.i("tag", quoteList.toString());

            }

        });

        return fragmentList;
    }

    private Quote getAnswer(int i) {
        return quoteList.get(i);
    }

}
