package com.example.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcontatos.R
import com.example.appcontatos.data.Contact

@Composable
fun CardContactInfo(modifier: Modifier = Modifier, contact: Contact, enabled: Boolean = true, onPressed: () -> Unit) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.info_do_contato),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        ContactInfo(
            imageVector = Icons.Default.Phone,
            value = contact.phoneNumber.ifBlank {
                stringResource(R.string.adicionar_n_telefone)
            },
            enabled = enabled && contact.phoneNumber.isBlank(),
            onPressed = onPressed
        )
        ContactInfo(
            imageVector = Icons.Default.Email,
            value = contact.email.ifBlank {
                stringResource(R.string.adicionar_email)
            },
            enabled = enabled && contact.email.isBlank(),
            onPressed = onPressed
        )
        Spacer(Modifier.size(8.dp))
    }

}

@Preview(showBackground = true)
@Composable
private fun CardContactInfoPreview() {
    CardContactInfo(
        contact = Contact(
            phoneNumber = " (11) 91234-5678",
        ),
        onPressed = {}
    )
}