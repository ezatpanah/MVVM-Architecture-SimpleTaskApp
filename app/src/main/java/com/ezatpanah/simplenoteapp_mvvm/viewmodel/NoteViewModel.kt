package com.ezatpanah.simplenoteapp_mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.simplenoteapp_mvvm.db.NoteEntity
import com.ezatpanah.simplenoteapp_mvvm.repository.DbRepository
import com.ezatpanah.simplenoteapp_mvvm.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: DbRepository) : ViewModel() {

    val catList = MutableLiveData<MutableList<String>>()
    val prList = MutableLiveData<MutableList<String>>()
    var notesData = MutableLiveData<DataStatus<List<NoteEntity>>>()
    val noteDetail = MutableLiveData<DataStatus<NoteEntity>>()

    fun loadCatData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(WORK, EDUCATION, HOME, HEALTH)
        catList.postValue(data)
    }

    fun loadPrData() = viewModelScope.launch(Dispatchers.IO) {
        val data = mutableListOf(HIGH, NORMAL, LOW)
        prList.postValue(data)
    }

    fun saveEditNote(isEdite: Boolean, noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        if (isEdite) {
            repository.updateNote(noteEntity)
        } else {
            repository.saveNote(noteEntity)
        }
    }

    fun getAllNotes() = viewModelScope.launch(Dispatchers.Main) {
        repository.getAllNotes().collect() {
            notesData.postValue(DataStatus.sucess(it, it.isEmpty()))
        }
    }

    fun getSearchNotes(search: String) = viewModelScope.launch {
        repository.searchNote(search).collect() {
            notesData.postValue(DataStatus.sucess(it, it.isEmpty()))
        }
    }

    fun getFilterNotes(filter: String) = viewModelScope.launch {
        repository.filterNote(filter).collect() {
            notesData.postValue(DataStatus.sucess(it, it.isEmpty()))
        }
    }

    fun deleteNote(entity: NoteEntity) = viewModelScope.launch {
        repository.deleteNote(entity)
    }

    fun getDetailsNote(id: Int) = viewModelScope.launch {
        repository.getDetailsNote(id).collect {
            noteDetail.postValue(DataStatus.sucess(it, false))
        }
    }


}