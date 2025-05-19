package com.example.appcontatos.ui.contact

import com.example.appcontatos.data.Contact

data class ContactsListUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val contacts: Map<String, List<Contact>> = emptyMap(),
)
