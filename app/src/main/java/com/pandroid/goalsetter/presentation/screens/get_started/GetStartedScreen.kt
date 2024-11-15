package com.pandroid.goalsetter.presentation.screens.get_started

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.pandroid.goalsetter.R
import com.pandroid.goalsetter.presentation.component.CustomButton
import com.pandroid.goalsetter.presentation.navigation.Routes
import com.pandroid.goalsetter.presentation.permission.RequestNotificationPermission
import com.pandroid.goalsetter.presentation.splash.SplashViewModel
import com.pandroid.goalsetter.presentation.utils.Dimens
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumRoundedCorner
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSize
import com.pandroid.goalsetter.ui.theme.Manrope
import com.pandroid.goalsetter.ui.theme.PrimaryColor
import com.pandroid.goalsetter.ui.theme.PrimaryDarkColor
import com.pandroid.goalsetter.ui.theme.SecondaryColor
import org.koin.androidx.compose.getViewModel

@Composable
fun GetStartedScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = getViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color.White)
                )
            )
    ) {

        RequestNotificationPermission { isGranted ->
            if (isGranted) {
                // Permission granted
            } else {
                // Show a message to the user
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(.05f))

        /*Icon image*/
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(MediumSize)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.05f))

        /*Hero image*/
        Image(
            painter = painterResource(id = R.drawable.hero),
            contentDescription = "background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(.75f)
                .fillMaxHeight(.4f)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.1f))

        /*Welcome Text*/
        Text(
            text = "Welcome to GoalSetter!",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = PrimaryDarkColor,
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(
                    start = Dimens.SmallMediumPadding,
                    end = Dimens.SmallMediumPadding
                )
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.11f))


        /*Quote Text*/
        Text(
            text = "\"Stay focused, and every goal is within reach.\"",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = PrimaryColor,
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(
                    start = Dimens.MediumLargePadding,
                    end = Dimens.MediumLargePadding
                )
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.fillMaxHeight(.5f))

        /*Get Started Button*/
        CustomButton(
            onClick = {
                splashViewModel.completeOnboarding()
                navController.navigate(Routes.Login.route)
            },
            text = "Get Started",
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(
                    start = Dimens.MediumSmallPadding,
                    end = Dimens.MediumSmallPadding
                )
                .height(Dimens.MediumHeight)
                .clip(RoundedCornerShape(MediumRoundedCorner))
                .background(Brush.horizontalGradient(colors = listOf(PrimaryColor, SecondaryColor)))
        )

    }


}
