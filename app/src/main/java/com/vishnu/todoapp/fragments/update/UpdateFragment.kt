package com.vishnu.todoapp.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.models.Priority
import com.vishnu.todoapp.data.models.ToDoData
import com.vishnu.todoapp.data.viewmodel.ToDoViewModel
import com.vishnu.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.row_layout.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        //Set menu
        setHasOptionsMenu(true)

        view.Update_title_et.setText(args.currentItem.title)
        view.Update_description_et.setText(args.currentItem.description)
        view.Update_spinner.setSelection(mSharedViewModel.parsePrioritytoInt(args.currentItem.priority))

        //Change colour of spinner based on priority
        view.Update_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            updateItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = Update_title_et.text.toString()
        val description = Update_description_et.text.toString()
        val getPriority = Update_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation){
            //Update the current item
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriorityObj(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedItem)

            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Oops! You missed something!", Toast.LENGTH_SHORT).show()
        }
    }
}