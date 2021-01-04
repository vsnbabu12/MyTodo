package com.nag.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_todo.*

const val EXTRA_ID = " com.nag.todo.EXTRA_ID"
const val EXTRA_TITLE = " com.nag.todo.EXTRA_TITLE"
const val EXTRA_DESCRIPTION = " com.nag.todo.EXTRA_DESCRIPTION"

class EditTodoActivity : AppCompatActivity() {

    private lateinit var mode : Mode
    private var todoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        todoId = intent.getIntExtra(EXTRA_ID,-1)
        mode = if(todoId == -1) Mode.AddTodo
                    else Mode.EditTodo

        when(mode){
            Mode.AddTodo -> "Add Todo"
            Mode.EditTodo -> {
                title = "Edit Note"
                edit_title.setText(intent.getStringExtra(EXTRA_TITLE))
                edit_desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_todo_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(){
        val title = edit_title.text.toString()
        val desc = edit_desc.text.toString()

        if(title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if(todoId != -1)
            data.putExtra(EXTRA_ID, todoId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddTodo : Mode()
        object EditTodo : Mode()

    }
}