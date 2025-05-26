package com.example.appcontatos.ui.contact.details

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.appcontatos.data.ContactDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class ContactDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState : ContactDetailsUiState by mutableStateOf(ContactDetailsUiState())
        private set

    private val datasource: ContactDatasource = ContactDatasource.instance
    private val contactId: Int = savedStateHandle.get<Int>("id") ?: 0

    init {
        loadContact()
    }

    fun loadContact() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )

        viewModelScope.launch {
            delay(2000)
            val contact = datasource.findById(contactId)
            uiState = if(contact == null) {
                uiState.copy(
                    hasErrorLoading = true,
                    isLoading = false
                )
            } else {
                uiState.copy(
                    contact = contact,
                    isLoading = false
                )
            }
        }

    }

    fun deleteContact() {
        if(uiState.isDeleting) return

        uiState = uiState.copy(
            isDeleting = true,
            hasErrorDeleting = false,
            showConfirmationDialog = false,
        )
        viewModelScope.launch {
            delay(2000)
            val hasError = Random.nextBoolean()
            uiState = if(hasError)
            {
                uiState.copy(
                    isDeleting = false,
                    hasErrorDeleting = true,
                )
            } else {
                datasource.delete(contactId)
                uiState.copy(
                    isDeleting = false,
                    hasErrorDeleting = false,
                    contactDeleted = true
                )
            }
        }
    }

    fun onFavoritePressed() {
        val updatedContact = uiState.contact.copy(
            isFavorite = !uiState.contact.isFavorite
        )
        uiState = uiState.copy(
            contact = datasource.save(updatedContact)
        )
    }

    fun showConfirmationDialog() {
        uiState = uiState.copy(
            showConfirmationDialog = true
        )
    }

    fun hideConfirmationDialog() {
        uiState = uiState.copy(
            showConfirmationDialog = false
        )
    }
}