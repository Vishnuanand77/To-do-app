package com.vishnu.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.models.Priority
import com.vishnu.todoapp.data.models.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>){
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean { //Returns false if the fields are empty
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriorityObj(priority: String): Priority {//Converts string to Priority enum
        return when(priority) {
            "High priority" -> {
                Priority.HIGH
            }
            "Medium priority" -> {
                Priority.MEDIUM
            }
            "Low priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }


}