package com.example.appcontatos.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcontatos.ui.contact.details.ContactDetailsScreen
import com.example.appcontatos.ui.contact.list.ContactsListScreen

@Composable
fun AppContacts(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "List"
    ) {
        composable(route = "List") {
            ContactsListScreen(
                onAddPressed = {},
                onContactPressed = { contact ->
                    navController.navigate("details/${contact.id}")
                }
            )
        }
        composable(
            route = "details/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
            ContactDetailsScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onEditPressed = {},
                onContactDeleted = {
                    navigateToList(navController)
                }
            )
        }
    }
}

private fun navigateToList(navHostController: NavHostController) {
    navHostController.navigate("List") {
        popUpTo(navHostController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}