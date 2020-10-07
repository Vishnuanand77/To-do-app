package com.vishnu.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vishnu.todoapp.data.ToDoDatabase
import com.vishnu.todoapp.data.models.ToDoData
import com.vishnu.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) { //ViewModel provides data to the UI and acts as a comms center b/w the repo and UI

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository

    private val getAllData: LiveData<List<ToDoData>> //Variable that holds the list of data from the repo.

    init { //Initializing the view model
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData) //Running the insert data function in a background thread
        }
    }
}