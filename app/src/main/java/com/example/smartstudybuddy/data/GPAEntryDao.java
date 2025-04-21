package com.example.smartstudybuddy.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GPAEntryDao {
    @Query("SELECT * FROM gpa_entries")
    LiveData<List<GPAEntry>> getAllEntries();

    @Insert
    void insertEntry(GPAEntry entry);
}
