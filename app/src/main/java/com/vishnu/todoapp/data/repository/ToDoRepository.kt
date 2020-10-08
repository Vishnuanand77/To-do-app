package com.vishnu.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.vishnu.todoapp.data.ToDoDAO
import com.vishnu.todoapp.data.models.ToDoData
import java.lang.StringBuilder

class ToDoRepository(private val toDoDAO: ToDoDAO) {

    val getAllData: LiveData<List<ToDoData>> = toDoDAO.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDAO.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDAO.sortByLowPriority()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDAO.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDAO.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDAO.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDAO.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDAO.searchDatabase(searchQuery)
    }

}