package com.example.appcontatos.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcontatos.R
import com.example.appcontatos.data.Contact
import com.example.appcontatos.data.groupByInitial
import com.example.appcontatos.ui.contact.composables.ContactAvatar
import com.example.appcontatos.ui.theme.AppContatosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ContactsListScreen(modifier: Modifier = Modifier) {
    var isInitialComposition: Boolean by rememberSaveable { mutableStateOf(true) }
    var uiState: ContactsListUiState by rememberSaveable {
        mutableStateOf(ContactsListUiState())
    }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val loadContacts: () -> Unit = {
        uiState = uiState.copy(
            isLoading = true,
            hasError = false
        )

        coroutineScope.launch {
            delay(2000)
            uiState.copy(
                contacts = generateContacts().groupByInitial(),
                isLoading = false
            )
        }
    }

    val toggleFavorite: (Contact) -> Unit = { contact ->
        val newMap: MutableMap<String, List<Contact>> = mutableMapOf()
        uiState.contacts.keys.forEach { key ->
            val contactsOfKey: List<Contact> = uiState.contacts[key]!!
            val newContacts = contactsOfKey.map {
                if (it.id == contact.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
            newMap[key] = newContacts
        }
        uiState.copy(
            contacts = newMap.toMap()
        )
    }

    if (isInitialComposition) {
        loadContacts()
        isInitialComposition = false
    }

    if (uiState.isLoading) {
        LoadingContent()
    } else if (uiState.hasError) {
        ErrorContent(
            onTryAgainPress = loadContacts
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                AppBar(
                    onRefreshPressed = loadContacts
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.adicionar)
                    )
                }
            }
        ) { paddingValues ->
            if (uiState.contacts.isEmpty()) {
                EmptyList(
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                List(
                    modifier = Modifier.padding(paddingValues),
                    contacts = uiState.contacts,
                    onFavoritePressed = toggleFavorite
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onRefreshPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = {
            Text(
                text = stringResource(R.string.contatos),
                color = MaterialTheme.colorScheme.primary
            )
        },
        actions = {
            IconButton(
                onClick = onRefreshPressed,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.atualizar),
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppContatosTheme {
        AppBar(
            onRefreshPressed = {}
        )
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(60.dp)
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.carregando_contatos),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun LoadingContentPreview() {
    AppContatosTheme {
        LoadingContent()
    }
}

@Composable
fun ErrorContent(modifier: Modifier = Modifier, onTryAgainPress: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.CloudOff,
            contentDescription = stringResource(R.string.erro_ao_carregar),
            tint = MaterialTheme.colorScheme.primary,
            modifier = modifier.size(80.dp)
        )
        Text(
            text = stringResource(R.string.erro_ao_carregar),
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.aguarde_um_momento_e_tente_novamente),
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        ElevatedButton(
            onClick = onTryAgainPress,
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text(stringResource(R.string.tentar_novamente))
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun ErrorContentPreview() {
    AppContatosTheme {
        ErrorContent(onTryAgainPress = {})
    }
}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.no_data),
            contentDescription = stringResource(R.string.nenhum_contato)
        )
        Text(
            text = stringResource(R.string.nenhum_contato),
            modifier = modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(R.string.adicione_o_primeiro),
            modifier = modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun List(
    modifier: Modifier = Modifier,
    contacts: Map<String, List<Contact>>,
    onFavoritePressed: (Contact) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        contacts.forEach { (initial, contacts) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = initial,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            items(contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onFavoritePressed = onFavoritePressed
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun ListPreview() {
    AppContatosTheme {
        List(
            contacts = generateContacts().groupByInitial(),
            onFavoritePressed = {}
        )
    }
}

@Composable
fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    onFavoritePressed: (Contact) -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(contact.fullName)
        },
        leadingContent = {
            ContactAvatar(
                firstName = contact.firstName,
                lastName = contact.lastName,
                size = 24.dp,
            )
        },
        trailingContent = {
            IconButton(
                onClick = {
                    onFavoritePressed(contact)
                }
            ) {
                Icon(
                    imageVector = if (contact.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.favoritar),
                    tint = if (contact.isFavorite) {
                        Color.Red
                    } else {
                        LocalContentColor.current
                    },
                )
            }
        },
    )
}


private fun generateContacts(): List<Contact> {
    val firstNames = listOf(
        "João", "José", "Everton", "Marcos", "André", "Anderson", "Antônio",
        "Laura", "Ana", "Maria", "Joaquina", "Suelen", "Samuel"
    )
    val lastNames = listOf(
        "Do Carmo", "Oliveira", "Dos Santos", "Da Silva", "Brasil", "Pichetti",
        "Cordeiro", "Silveira", "Andrades", "Cardoso", "Souza"
    )
    val contacts: MutableList<Contact> = mutableListOf()
    for (i in 0..19) {
        var generatedNewContact = false
        while (!generatedNewContact) {
            val firstNameIndex = Random.nextInt(firstNames.size)
            val lastNameIndex = Random.nextInt(lastNames.size)
            val newContact = Contact(
                id = i + 1,
                firstName = firstNames[firstNameIndex],
                lastName = lastNames[lastNameIndex]
            )
            if (!contacts.any { it.fullName == newContact.fullName }) {
                contacts.add(newContact)
                generatedNewContact = true
            }
        }
    }
    return contacts
}