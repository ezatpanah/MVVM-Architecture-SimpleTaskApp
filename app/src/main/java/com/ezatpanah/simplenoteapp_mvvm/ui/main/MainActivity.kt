package com.ezatpanah.simplenoteapp_mvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.ezatpanah.simplenoteapp_mvvm.R
import com.ezatpanah.simplenoteapp_mvvm.adapter.NoteAdapter
import com.ezatpanah.simplenoteapp_mvvm.databinding.ActivityMainBinding
import com.ezatpanah.simplenoteapp_mvvm.db.NoteEntity
import com.ezatpanah.simplenoteapp_mvvm.ui.add.AddNoteFragment
import com.ezatpanah.simplenoteapp_mvvm.utils.*
import com.ezatpanah.simplenoteapp_mvvm.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var entity: NoteEntity

    private val noteViewModel: NoteViewModel by viewModels()

    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {

            setSupportActionBar(notesToolbar)

            btnAddNote.setOnClickListener {
                AddNoteFragment().show(supportFragmentManager, AddNoteFragment().tag)
            }

            noteViewModel.getAllNotes()
            noteViewModel.notesData.observe(this@MainActivity) {
                showEmpty(it.isEmpty)
                noteAdapter.setData(it.data!!)
                noteList.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = noteAdapter
                }

            }

            notesToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionFilter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }

            noteAdapter.setOnItemClickListener { noteEntity, type ->
                when (type) {
                    EDIT -> {
                        val noteFragment=AddNoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID,noteEntity.id)
                        noteFragment.arguments=bundle
                        noteFragment.show(supportFragmentManager,AddNoteFragment().tag)
                    }
                    DELETE -> {
                        entity.id = noteEntity.id
                        entity.title = noteEntity.title
                        entity.desc = noteEntity.desc
                        entity.cat = noteEntity.cat
                        entity.pr = noteEntity.pr
                        noteViewModel.deleteNote(entity)
                    }
                }
            }

        }
    }

    private fun showEmpty(isShown: Boolean) {
        binding?.apply {
            if (isShown) {
                emptyLay.visibility = View.VISIBLE
                noteList.visibility = View.GONE
            } else {
                emptyLay.visibility = View.GONE
                noteList.visibility = View.VISIBLE
            }
        }
    }

    private fun filterByPriority() {
        val builder = AlertDialog.Builder(this)
        val priories = arrayOf(ALL, HIGH, NORMAL, LOW)
        builder.setSingleChoiceItems(priories, selectedItem) { dialog, item ->
            when (item) {
                0 -> {
                    noteViewModel.getAllNotes()
                }
                in 1..3 -> {
                    noteViewModel.getFilterNotes(priories[item])
                }
            }
            selectedItem = item
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                noteViewModel.getSearchNotes(newText!!)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}