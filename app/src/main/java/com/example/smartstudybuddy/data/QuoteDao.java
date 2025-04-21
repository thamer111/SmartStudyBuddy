package com.example.smartstudybuddy.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface QuoteDao {
    @Query("SELECT * FROM quotes")
    LiveData<List<Quote>> getAllQuotes();

    @Insert
    void insertQuote(Quote quote);
}
