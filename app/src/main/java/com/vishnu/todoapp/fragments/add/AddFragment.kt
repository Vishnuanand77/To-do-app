package com.vishnu.todoapp.fragments.add

import android.icu.text.CaseMap
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.models.Priority
import com.vishnu.todoapp.data.models.ToDoData
import com.vishnu.todoapp.data.viewmodel.ToDoViewModel
import com.vishnu.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private val mtoDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //set menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = title_et.text.toString()
        val mPriority = spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if(validation){ //Only insert data into database if title and description are not empty
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriorityObj(mPriority),
                mDescription
            )
            mtoDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            //To navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment) //Go back to list view once task has been added.
        } else {
            Toast.makeText(requireContext(), "Oops! You missed something", Toast.LENGTH_SHORT).show()
        }
    }


}