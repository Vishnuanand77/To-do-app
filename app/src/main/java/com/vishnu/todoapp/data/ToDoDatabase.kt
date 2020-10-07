package com.vishnu.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vishnu.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun toDoDao(): ToDoDAO

    companion object { //Similar to public static final class in java

        @Volatile //Writes to this field are immediately made visible to other database
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase { //Creating an instance of the database class.
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this) { //Only one thread can access the database class and multiple threads won't be creating an instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}