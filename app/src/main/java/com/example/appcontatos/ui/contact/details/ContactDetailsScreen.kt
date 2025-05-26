package com.example.appcontatos.ui.contact.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcontatos.ui.contact.composables.DefaultErrorContent
import com.example.appcontatos.ui.contact.composables.DefaultLoadingContent
import com.example.appcontatos.ui.contact.details.composables.AppBar
import com.example.appcontatos.ui.contact.details.composables.ConfirmationDialog
import com.example.appcontatos.ui.contact.details.composables.ContactDetails

@Composable
fun ContactDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = viewModel(),
    onBackPressed: () -> Unit,
    onEditPressed: () -> Unit,
    onContactDeleted: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {

    LaunchedEffect(
        viewModel.uiState.contactDeleted
    ) {
        if(viewModel.uiState.contactDeleted) {
            onContactDeleted()
        }
    }

    LaunchedEffect(
        snackbarHostState, viewModel.uiState.hasErrorDeleting
    ) {
        if(viewModel.uiState.hasErrorDeleting){
            snackbarHostState.showSnackbar(
                message = "Não foi possível excluir o contato. Aguarde e tente novamente.",
            )
        }
    }

    val contentModifier: Modifier = modifier.fillMaxSize()
    if(viewModel.uiState.showConfirmationDialog){
        ConfirmationDialog(
            message = "Você tem certeza que deseja excluir este contato?",
            onDismiss = viewModel::hideConfirmationDialog ,
            onConfirm = viewModel::deleteContact
        )
    }
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
                    isDeleting = viewModel.uiState.isDeleting,
                    onBackPressed = onBackPressed,
                    onDeletePressed = viewModel::showConfirmationDialog,
                    onEditPressed = onEditPressed,
                    onFavoritePressed = viewModel::onFavoritePressed,
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            ContactDetails(
                modifier = Modifier.padding(innerPadding),
                contact = viewModel.uiState.contact,
                onContactInfoPressed = onEditPressed,
                enabled = !viewModel.uiState.isDeleting
            )

        }
    }
}