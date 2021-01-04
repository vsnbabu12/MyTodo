package com.nag.todo

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nag.todo.adapters.TodoAdapter
import com.nag.todo.model.TodoEntity
import com.nag.todo.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*


const val ADD_NOTE_REQUEST = 1
const val EDIT_NOTE_REQUEST = 2

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setupRecyclerView()

        setUpListeners()

        viewModel = ViewModelProviders.of(this)[TodoViewModel::class.java]

        viewModel.getAllTodos().observe(this, Observer {
            Log.i("Todo observed", "$it")

            adapter.submitList(it)
        })


    }

    private fun setUpListeners(){
        button_add_note.setOnClickListener{
            val intent = Intent(this, EditTodoActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    private fun setupRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = TodoAdapter { clickedNote ->
            val intent = Intent(this, EditTodoActivity::class.java)
            intent.putExtra(EXTRA_TITLE, clickedNote.id)
            intent.putExtra(EXTRA_DESCRIPTION, clickedNote.title)
            startActivityForResult(intent, EDIT_NOTE_REQUEST)
        }
        recycler_view.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data.getStringExtra(EXTRA_TITLE)
            val content: String = data.getStringExtra(EXTRA_DESCRIPTION)

            viewModel.insert(TodoEntity(0, title, content))
            Toast.makeText(this, "Note inserted!", Toast.LENGTH_SHORT).show()

        } else if(data != null && requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if(id == -1) {
                Toast.makeText(this, "Note couldn't be updated!", Toast.LENGTH_SHORT).show()
                return
            }
            val title: String = data.getStringExtra(EXTRA_TITLE)
            val description: String = data.getStringExtra(EXTRA_DESCRIPTION)
            viewModel.update(TodoEntity(1, title, description))
            Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }
    }


}