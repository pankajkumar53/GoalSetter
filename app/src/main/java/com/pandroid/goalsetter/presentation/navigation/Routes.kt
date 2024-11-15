package com.pandroid.goalsetter.presentation.navigation


sealed class Routes(val route: String) {

    data object Splash : Routes("splash")

    data object GetStarted : Routes("get_started")

    data object SignUp : Routes("sign_up")

    data object Login : Routes("login")

    data object Forgot : Routes("forgot")

    data object Home : Routes("home")

    data object CreateGoalScreen : Routes("createGoalScreen")

    data object Profile : Routes("profile")

    data object UpdateProfile : Routes("updateProfile")

    data object Search : Routes("search")

    data object ShareApp : Routes("shareApp")

    data object Contact : Routes("contact")

    data object AboutUs : Routes("about")

    data object PrivacyPolicy : Routes("privacyPolicy")

}
