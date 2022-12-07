package com.ezatpanah.simpletodoapp_mvvm.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ezatpanah.simpletodoapp_mvvm.databinding.FragmentAddTaskBinding
import com.ezatpanah.simpletodoapp_mvvm.db.TaskEntity
import com.ezatpanah.simpletodoapp_mvvm.utils.*
import com.ezatpanah.simpletodoapp_mvvm.utils.Constants.BUNDLE_ID
import com.ezatpanah.simpletodoapp_mvvm.utils.Constants.EDIT
import com.ezatpanah.simpletodoapp_mvvm.utils.Constants.NEW
import com.ezatpanah.simpletodoapp_mvvm.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var entity: TaskEntity

    private val viewModel: TaskViewModel by viewModels()

    private var category = ""
    private var priority = ""
    private var noteId = 0
    private var type = ""
    private var isEdit = false
    private val categoriesList: MutableList<String> = mutableListOf()
    private val prioriesList: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt(BUNDLE_ID) ?: 0

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
                viewModel.taskDetail.observe(viewLifecycleOwner) { itData ->
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
                    viewModel.saveEditTask(isEdit, entity)
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