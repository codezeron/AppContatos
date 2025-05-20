package com.example.appcontatos.ui.contact.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcontatos.ui.contact.composables.DefaultErrorContent
import com.example.appcontatos.ui.contact.composables.DefaultLoadingContent
import com.example.appcontatos.ui.contact.details.composables.AppBar
import com.example.appcontatos.ui.contact.details.composables.ContactDetails

@Composable
fun ContactDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = viewModel()
) {
    val contentModifier: Modifier = Modifier.fillMaxSize()
    if(viewModel.uiState.isLoading){
        DefaultLoadingContent(
            modifier = contentModifier,
        )
    }else if(viewModel.uiState.hasErrorLoading){
        DefaultErrorContent(
            modifier = contentModifier,
            onTryAgainPress = viewModel::loadContact
        )
    }else{
        Scaffold (
            modifier = contentModifier,
            topBar = {
                AppBar (
                    contact = viewModel.uiState.contact,
                    onBackPressed = { },
                    onDeletePressed = { },
                    onEditPressed = { },
                    onFavoritePressed = { }
                )
            }
        ) { innerPadding ->
            ContactDetails(
                modifier = Modifier.padding(innerPadding),
                contact = viewModel.uiState.contact,
                onContactInfoPressed = { }
            )

        }
    }
}