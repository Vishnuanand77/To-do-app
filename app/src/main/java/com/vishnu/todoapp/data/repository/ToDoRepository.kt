package com.vishnu.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.vishnu.todoapp.data.ToDoDAO
import com.vishnu.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDAO: ToDoDAO) {

    val getAllData: LiveData<List<ToDoData>> = toDoDAO.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDAO.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDAO.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDAO.deleteItem(toDoData)
    }

}