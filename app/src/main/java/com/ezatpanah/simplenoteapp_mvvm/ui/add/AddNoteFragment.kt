package com.ezatpanah.simplenoteapp_mvvm.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ezatpanah.simplenoteapp_mvvm.R
import com.ezatpanah.simplenoteapp_mvvm.databinding.ActivityMainBinding
import com.ezatpanah.simplenoteapp_mvvm.databinding.FragmentAddNoteBinding
import com.ezatpanah.simplenoteapp_mvvm.db.NoteEntity
import com.ezatpanah.simplenoteapp_mvvm.utils.*
import com.ezatpanah.simplenoteapp_mvvm.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var entity: NoteEntity

    private val viewModel: NoteViewModel by viewModels()

    private var category = ""
    private var priority = ""
    //private var noteId = 0
    private var type = ""
    private var isEdit = false
    private val categoriesList: MutableList<String> = mutableListOf()
    private val prioriesList: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var noteId = arguments?.getInt(BUNDLE_ID) ?: 0

        if (noteId > 0) {
            type = EDIT
            isEdit = true
        } else {
            isEdit = false
            type = NEW
        }

        binding?.apply {
            imgClose.setOnClickListener { dismiss() }
            viewModel.loadCatData()
            viewModel.catList.observe(viewLifecycleOwner) {
                categoriesList.addAll(it)
                categoriesSpinner.setupList(it) { itItem -> category = itItem }
            }
            viewModel.loadPrData()
            viewModel.prList.observe(viewLifecycleOwner) {
                prioriesList.addAll(it)
                prioritySpinner.setupList(it) { itItem -> priority = itItem }
            }
            if (type == EDIT) {
                viewModel.getDetailsNote(noteId)
                viewModel.noteDetail.observe(viewLifecycleOwner) { itData ->
                    itData.data?.let {
                        titleEdt.setText(it.title)
                        descEdt.setText(it.desc)
                        categoriesSpinner.setSelection(categoriesList.getIndexFromList(it.cat))
                        prioritySpinner.setSelection(prioriesList.getIndexFromList(it.pr))
                    }
                }
            }
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteId
                entity.title = title
                entity.desc = desc
                entity.cat = category
                entity.pr = priority

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    viewModel.saveEditNote(isEdit, entity)
                }

                dismiss()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }
}