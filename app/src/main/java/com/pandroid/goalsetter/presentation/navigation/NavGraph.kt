package com.pandroid.goalsetter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pandroid.goalsetter.presentation.auth.ForgotPasswordScreen
import com.pandroid.goalsetter.presentation.auth.LoginScreen
import com.pandroid.goalsetter.presentation.auth.SignupScreen
import com.pandroid.goalsetter.presentation.screens.get_started.GetStartedScreen
import com.pandroid.goalsetter.presentation.screens.goal.CreateGoalScreen
import com.pandroid.goalsetter.presentation.screens.home.HomeScreen
import com.pandroid.goalsetter.presentation.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier, startDestination: String) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Routes.Splash.route) {
            SplashScreen()
        }

        composable(Routes.GetStarted.route) {
            GetStartedScreen(navController = navController)
        }

        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.SignUp.route) {
            SignupScreen(navController = navController)
        }

        composable(Routes.Forgot.route) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.CreateGoalScreen.route) {
            CreateGoalScreen(navController = navController)
        }

        /*composable(Routes.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.AboutUs.route) {
            AboutUs(navController = navController)
        }

        composable(Routes.PrivacyPolicy.route) {
            PrivacyPolicy(navController = navController)
        }

        composable(Routes.UpdateProfile.route) {
            UpdateScreen(navController = navController)
        }*/

    }

}