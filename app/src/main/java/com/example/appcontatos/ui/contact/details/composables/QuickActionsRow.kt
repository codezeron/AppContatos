package com.example.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appcontatos.data.Contact
import com.example.appcontatos.ui.theme.AppContatosTheme

@Composable
fun QuickActionsRow(

    modifier: Modifier = Modifier,
    contact: Contact,
    enabled: Boolean = true,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        QuickAction(
            imageVector = Icons.Default.Phone,
            text = "Ligar",
            enabled = enabled && contact.phoneNumber.isNotBlank(),
            onPressed = {  }
        )
        QuickAction(
            imageVector = Icons.Filled.Sms,
            text = "Mensagem",
            onPressed = {},
            enabled = enabled && contact.phoneNumber.isNotBlank(),
        )
        QuickAction(
            imageVector = Icons.Default.Videocam,
            text = "Video",
            enabled = enabled && contact.phoneNumber.isNotBlank(),
            onPressed = {  }
        )
        QuickAction(
            imageVector = Icons.Default.Email,
            text = "E-mail",
            enabled = enabled && contact.email.isNotBlank(),
            onPressed = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuickActionsRowPreview() {
    AppContatosTheme {
        QuickActionsRow(
            contact = Contact(
                phoneNumber = " (11) 99999-9999",
            )
        )
    }
}