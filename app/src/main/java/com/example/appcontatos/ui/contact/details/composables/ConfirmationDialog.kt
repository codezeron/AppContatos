package com.example.appcontatos.ui.contact.details.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = title?.let { { Text(it) } },
        text = { Text(message) },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Ok") }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ConfirmationDialogPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        ConfirmationDialog(
            modifier = modifier,
            title = "Confirmação",
            message = "Você tem certeza que deseja excluir este contato?",
            onDismiss = {},
            onConfirm = {}
        )
    }
}