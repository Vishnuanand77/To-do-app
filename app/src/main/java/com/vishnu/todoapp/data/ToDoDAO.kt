package com.vishnu.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoData>> //The live data object enables us to see changes via the fragment simultaneously

    @Insert(onConflict = OnConflictStrategy.IGNORE) //Conflict strategy tells ROOM what to do when duplicate data is received
    suspend fun insertData(toDoData: ToDoData) //suspend keyword tells the compiler that the function is running in a co-routine


}