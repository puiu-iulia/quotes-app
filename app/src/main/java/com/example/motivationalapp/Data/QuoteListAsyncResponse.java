package com.example.motivationalapp.Data;

import com.example.motivationalapp.Model.Quote;

import java.util.ArrayList;

public interface QuoteListAsyncResponse {
    void processFinished(ArrayList<Quote> quotes);
}
