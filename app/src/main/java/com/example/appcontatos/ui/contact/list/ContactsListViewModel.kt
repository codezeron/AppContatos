package com.example.appcontatos.ui.contact.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcontatos.data.Contact
import com.example.appcontatos.data.ContactDatasource
import com.example.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel() {
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
        private set

    private val datasource: ContactDatasource = ContactDatasource.instance

    init {
        loadContacts()
    }

    fun loadContacts() {
        uiState = uiState.copy(
            isLoading = true,
            hasError = false
        )

        viewModelScope.launch {
            delay(2000)
            uiState = uiState.copy(
                contacts = datasource.findAll().groupByInitial(),
                isLoading = false
            )
        }
    }

    fun toggleFavorite(pressedContact: Contact) {
        val newMap: MutableMap<String, List<Contact>> = mutableMapOf()
        uiState.contacts.keys.forEach { key ->
            val contactsOfKey: List<Contact> = uiState.contacts[key]!!
            val newContacts = contactsOfKey.map {
                if (it.id == pressedContact.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
            newMap[key] = newContacts
        }
        uiState = uiState.copy(
            contacts = newMap.toMap()
        )
    }
}
