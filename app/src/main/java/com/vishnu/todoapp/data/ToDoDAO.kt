package com.vishnu.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vishnu.todoapp.data.models.ToDoData
import java.lang.StringBuilder

@Dao //Data access object
interface ToDoDAO {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoData>> //The live data object enables us to see changes via the fragment simultaneously

    @Insert(onConflict = OnConflictStrategy.IGNORE) //Conflict strategy tells ROOM what to do when duplicate data is received
    suspend fun insertData(toDoData: ToDoData) //suspend keyword tells the compiler that the function is running in a co-routine

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>
}