package com.example.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ContactInfo(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    value: String,
    enabled: Boolean = true,
    onPressed: () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onPressed,
        ),
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = value,
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun ContactInfoPreview() {
    AppContatosTheme {
        ContactInfo(
            imageVector = Icons.Filled.Phone,
            value = "(46) 99999-9999",
            enabled = true,
            onPressed = {}
        )
    }
}