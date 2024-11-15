package com.pandroid.goalsetter.presentation.auth

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.pandroid.goalsetter.R
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.domain.model.UserModel
import com.pandroid.goalsetter.presentation.component.CustomButton
import com.pandroid.goalsetter.presentation.component.CustomCircularIndicator
import com.pandroid.goalsetter.presentation.component.CustomTextField
import com.pandroid.goalsetter.presentation.navigation.Routes
import com.pandroid.goalsetter.presentation.utils.Dimens
import com.pandroid.goalsetter.presentation.utils.Dimens.ExtraSmallHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.ExtraSmallWidth
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeSmallHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeSmallPadding
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumMediumHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumMediumLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallRoundedCorner
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallSize
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallTextSize
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallExtraLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallLargeHeight
import com.pandroid.goalsetter.presentation.utils.Dimens.SmallPadding
import com.pandroid.goalsetter.ui.theme.Manrope
import com.pandroid.goalsetter.ui.theme.PrimaryColor
import com.pandroid.goalsetter.ui.theme.PrimaryDarkColor
import org.koin.androidx.compose.getViewModel

@Composable
fun SignupScreen(navController: NavController, viewModel: AuthViewModel = getViewModel()) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumSmallPadding)
    ) {

        /*Back Icon*/
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "back",
            tint = PrimaryDarkColor,
            modifier = Modifier
                .padding(SmallPadding)
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

        Spacer(modifier = Modifier.height(SmallExtraLargeHeight))

        /*Main Icon*/
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Icon Image",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colorFilter = ColorFilter.tint(PrimaryColor)
        )

        Spacer(modifier = Modifier.height(SmallLargeHeight))

        /*Supporting Text*/
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = Manrope,
                color = PrimaryDarkColor
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Create your new Account",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                color = Color.LightGray,
                fontFamily = Manrope
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(ExtraSmallHeight))


        // Name input field
        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name"
        )

        // Email input field
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardType = KeyboardType.Email
        )

        // Phone input field
        CustomTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Phone",
            keyboardType = KeyboardType.Phone
        )

        // Password input field
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true,
            isPasswordVisible = isPasswordVisible,
            onPasswordToggleClick = { isPasswordVisible = !isPasswordVisible }
        )

        Spacer(modifier = Modifier.height(SmallLargeHeight))

        // Sign Up Button
        CustomButton(
            text = "Register",
            onClick = {
                if (name != "" && email != "" && password != "" && phone!= "" ) {
                    viewModel.signUpUser(
                        userModel = UserModel(
                            name = name,
                            email = email,
                            phone = phone
                        ),
                        password
                    )
                } else {
                    Toast.makeText(context, "Please fill All fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .height(MediumHeight)
                .fillMaxWidth()
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "OR",
                fontSize = MediumSmallTextSize,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = MediumLargeSmallPadding)
            )
        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Already Have an Account?", fontWeight = FontWeight.SemiBold)
            Text(
                text = " Login.",
                color = PrimaryColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.Login.route)
                })
        }

    }

    // Display different UI based on the state
    when (state) {
        is State.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomCircularIndicator()
            }
        }

        is State.Success -> {
            LaunchedEffect(Unit) {
                name = ""
                email = ""
                phone = ""
                password = ""
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.SignUp.route) {
                        inclusive = true
                    }
                }
            }
        }

        is State.Error -> {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    (state as State.Error).message ?: "An error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> Unit
    }


}
