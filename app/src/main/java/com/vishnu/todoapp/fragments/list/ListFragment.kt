package com.vishnu.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.models.ToDoData
import com.vishnu.todoapp.data.viewmodel.ToDoViewModel
import com.vishnu.todoapp.databinding.FragmentListBinding
import com.vishnu.todoapp.fragments.SharedViewModel
import com.vishnu.todoapp.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment() {

    private  val mToDoViewModel: ToDoViewModel by viewModels()
    private val msharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = msharedViewModel

        //Setup Recycler view
        setupRecyclerView()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {data -> //Checks notification from setData function if there are any changes and makes those changes in the recyclerView
            msharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })


        //Set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        //Swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]

                //Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
//                Toast.makeText(requireContext(), "Successfully Removed: '${deletedItem.title}'", Toast.LENGTH_SHORT).show()

                //Restore deleted data
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all){
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    //Alert dialog box for removal of all items
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Clean slate protocol activated!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _,_ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroyView() { //Avoids memory leaks
        super.onDestroyView()
        _binding = null
    }
}