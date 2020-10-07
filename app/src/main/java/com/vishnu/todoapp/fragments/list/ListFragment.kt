package com.vishnu.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.viewmodel.ToDoViewModel
import com.vishnu.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private  val mToDoViewModel: ToDoViewModel by viewModels()
    private val msharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //Recycler view
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {data -> //Checks notification from setData function if there are any changes and makes those changes in the recyclerView
            msharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        msharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //Set Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.noDataImageView?.visibility = View.VISIBLE
            view?.noDataText?.visibility = View.VISIBLE
        } else {
            view?.noDataImageView?.visibility = View.INVISIBLE
            view?.noDataText?.visibility = View.INVISIBLE
        }
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
}