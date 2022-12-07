package com.ezatpanah.simpletodoapp_mvvm.ui.deleteall

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ezatpanah.simpletodoapp_mvvm.databinding.FragmentDeleteAllBinding
import com.ezatpanah.simpletodoapp_mvvm.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllFragment : DialogFragment()  {

    private lateinit var binding: FragmentDeleteAllBinding


    private val taskViewModel: TaskViewModel by viewModels()

    companion object {
        const val TAG = "DeleteAllFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDeleteAllBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnPositive.setOnClickListener {
                taskViewModel.deleteAllNotes()
                dismiss()
            }
            btnNegative.setOnClickListener {
                dismiss()
            }
        }
    }
}