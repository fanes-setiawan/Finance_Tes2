package com.example.finance_tes2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finance_tes2.ui.detail.DetailScreen
import com.example.finance_tes2.ui.entry.EntryScreen
import com.example.finance_tes2.ui.customer.CustomerSelectionScreen
import com.example.finance_tes2.ui.home.CashInflowScreen
import com.example.finance_tes2.ui.home.HomeScreen

enum class AppScreen {
    Home,
    CashInflow,
    Entry,
    Detail
}

@Composable
fun FinanceApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.name,
        modifier = Modifier
    ) {
        composable(route = AppScreen.Home.name) {
            HomeScreen(
                navigateToCashInflow = { navController.navigate(AppScreen.CashInflow.name) }
            )
        }
        composable(route = AppScreen.CashInflow.name) {
            CashInflowScreen(
                navigateToEntry = { navController.navigate(AppScreen.Entry.name) },
                navigateToDetail = { itemId ->
                    navController.navigate("${AppScreen.Detail.name}/$itemId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "entry?itemId={itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            EntryScreen(
                itemId = itemId,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToCustomerSelection = { navController.navigate("customer_selection") },
                navController = navController
            )
        }
        
        composable(route = "customer_selection") {
            CustomerSelectionScreen(
                navigateBack = { navController.popBackStack() },
                onCustomerSelected = { customerName ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_customer", customerName)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${AppScreen.Detail.name}/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) {
            val itemId = it.arguments?.getInt("itemId") ?: 0
            DetailScreen(
                itemId = itemId,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id -> navController.navigate("entry?itemId=$id") }
            )
        }
    }
}
