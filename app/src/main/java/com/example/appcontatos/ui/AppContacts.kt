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
import com.example.appcontatos.ui.contact.form.ContactFormScreen
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
                onAddPressed = { navController.navigate("form") },
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
        ) { navBackStackEntry ->
            var contactId: Int = navBackStackEntry.arguments?.getInt("id") ?: 0
            ContactDetailsScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onEditPressed = {
                    navController.navigate("form?id=$contactId")
                },
                onContactDeleted = {
                    navigateToList(navController)
                }
            )
        }
        composable (
            route = "form?id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ){
            ContactFormScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onContactSaved = {
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