package com.vishnu.todoapp.fragments.update

import android.app.AlertDialog
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
import com.vishnu.todoapp.databinding.FragmentUpdateBinding
import com.vishnu.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.row_layout.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        //Set menu
        setHasOptionsMenu(true)

        //Change colour of spinner based on priority
        binding.UpdateSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = Update_title_et.text.toString()
        val description = Update_description_et.text.toString()
        val getPriority = Update_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
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
            Toast.makeText(requireContext(), "Oops! You missed something!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun confirmItemRemoval() { //Alert dialogue to confirm removal of task
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Task '${args.currentItem.title}' has been removed!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _,_ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove task '${args.currentItem.title}'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}