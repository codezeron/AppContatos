package com.example.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcontatos.data.ContactTypeEnum
import com.example.appcontatos.ui.contact.composables.ContactAvatar
import com.example.appcontatos.ui.contact.form.FormState
import com.example.appcontatos.ui.theme.AppContatosTheme
import java.time.LocalDate

@Composable
fun FormContent(
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
    formState: FormState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onIsFavoriteChanged: (Boolean) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onSalaryChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(
            modifier = Modifier.padding(16.dp),
            firstName = formState.firstName.value,
            lastName = formState.lastName.value,
            size = 150.dp,
            textStyle = MaterialTheme.typography.displayLarge
        )
        val textFiledPadding = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
        FormFieldRow(
            imageVector = Icons.Default.Person
        ) {
            FormTextField(
                modifier = textFiledPadding,
                value = formState.firstName.value,
                onValueChange = onFirstNameChanged,
                label = "Nome",
                enabled = !isSaving,
                keyboardCapitalization = KeyboardCapitalization.Words,
                errorMessage = formState.firstName.errorMessage
            )
        }
        FormFieldRow {
            FormTextField(
                modifier = textFiledPadding,
                value = formState.lastName.value,
                onValueChange = onLastNameChanged,
                label = "Sobrenome",
                enabled = !isSaving,
                keyboardCapitalization = KeyboardCapitalization.Words,
                errorMessage = formState.lastName.errorMessage
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Phone
        ) {
            FormTextField(
                modifier = textFiledPadding,
                value = formState.phoneNumber.value,
                onValueChange = onPhoneChanged,
                label = "Telefone",
                enabled = !isSaving,
                keyboardType = KeyboardType.Phone,
                errorMessage = formState.phoneNumber.errorMessage
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Mail
        ) {
            FormTextField(
                modifier = textFiledPadding,
                value = formState.email.value,
                onValueChange = onEmailChanged,
                label = "E-mail",
                enabled = !isSaving,
                keyboardType = KeyboardType.Email,
                errorMessage = formState.email.errorMessage
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Cake,
        ) {
            FormDatePicker(
                modifier = textFiledPadding,
                value = if (formState.birthDate.value.isBlank()) {
                    LocalDate.now()
                } else {
                    LocalDate.parse(formState.birthDate.value)
                },
                errorMessage = formState.birthDate.errorMessage,
                label = "Aniversário",
                enabled = !isSaving,
                onValueChanged = { newValue ->
                    onBirthDateChanged(newValue.toString())
                }
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.MonetizationOn
        ) {
            FormTextField(
                modifier = textFiledPadding,
                value = formState.salary.value,
                label = "Salário",
                errorMessage = formState.salary.errorMessage,
                onValueChange = onSalaryChanged,
                enabled = !isSaving,
                keyboardType = KeyboardType.Number,
            )
        }
        val optionsModifier = Modifier
            .padding(vertical = 8.dp)

        FormFieldRow {
            FormCheckBox(
                modifier = optionsModifier,
                label = "Favoritar",
                value = formState.isFavorite.value.toBoolean(),
                onValueChanged = { newValue ->
                    onIsFavoriteChanged(newValue)
                },
                enabled = !isSaving
            )
        }

        FormFieldRow {
            val groupValue = if (formState.type.value.isBlank()){
                ContactTypeEnum.PERSONAL
            } else {
                ContactTypeEnum.valueOf(formState.type.value)
            }
            FormRadioButton(
                modifier = optionsModifier,
                label = "Pessoal",
                value = ContactTypeEnum.PERSONAL,
                groupValue = groupValue,
                enabled = !isSaving,
                onValueChanged = { newValue ->
                    onTypeChanged(newValue.toString())
                },
            )
            FormRadioButton(
                modifier = optionsModifier,
                label = "Profissional",
                value = ContactTypeEnum.PROFESSIONAL,
                groupValue = groupValue,
                enabled = !isSaving,
                onValueChanged = { newValue ->
                    onTypeChanged(newValue.toString())
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppContatosTheme {
        FormContent(
            formState = FormState(),
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onPhoneChanged = {},
            onEmailChanged = {},
            onIsFavoriteChanged = {},
            onBirthDateChanged = {},
            onTypeChanged = {},
            onSalaryChanged = {}
        )
    }
}