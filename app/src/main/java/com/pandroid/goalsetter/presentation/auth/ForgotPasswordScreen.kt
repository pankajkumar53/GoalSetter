package com.pandroid.goalsetter.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.presentation.component.CustomButton
import com.pandroid.goalsetter.presentation.component.CustomTextField
import com.pandroid.goalsetter.presentation.utils.Dimens
import com.pandroid.goalsetter.presentation.utils.Dimens.ExtraSmallHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.ExtraSmallWidth
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeSmallPadding
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallPadding
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallRoundedCorner
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallSize
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallMediumRoundedCorner
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallPadding
import com.pandroid.goalsetter.ui.theme.Manrope
import com.pandroid.goalsetter.ui.theme.PrimaryColor
import com.pandroid.goalsetter.ui.theme.PrimaryDarkColor
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: AuthViewModel = getViewModel()
) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "back",
            tint = PrimaryDarkColor,
            modifier = Modifier
                .padding(start = MediumSmallPadding, top = MediumLargeSmallPadding)
                .size(MediumSmallSize)
                .border(
                    width = ExtraSmallWidth,
                    color = LightGray,
                    shape = RoundedCornerShape(MediumSmallRoundedCorner)
                )
                .clickable {
                    navController.popBackStack()
                }
                .padding(SmallPadding)
        )


        Column(
            modifier = Modifier
                .padding(MediumSmallPadding)
                .align(Alignment.Center)
        ) {

            Text(
                text = "Forget Password ?",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = Manrope
                )
            )

            Spacer(modifier = Modifier.height(ExtraSmallHeight))

            Text(
                text = "To ensure secure account recovery, we have sent a password reset link to your registered email address. Please check your inbox and follow the instructions to reset your password. If you don't receive the email, kindly check your spam folder or contact our support team for further assistance. Thank you.",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                    fontFamily = Manrope
                )
            )

            Spacer(modifier = Modifier.height(SmallHeight))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Enter email"
            )


            Spacer(modifier = Modifier.height(SmallLargeHeight))

            CustomButton(
                onClick = {
                    if (email != "") {
                        viewModel.forgotPassword(email)
                        email = ""
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MediumSmallHeight),
                shape = RoundedCornerShape(SmallMediumRoundedCorner),
                text = "Confirm Email"
            )

            Spacer(modifier = Modifier.height(MediumLargeHeight))
        }
    }

    when (state) {
        is State.Success -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Password reset email sent.", Toast.LENGTH_SHORT).show()
            }
        }

        is State.Error -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, (state as State.Error).message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        else -> Unit
    }

}


