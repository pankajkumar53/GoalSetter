package com.pandroid.goalsetter.presentation.screens.goal

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.pandroid.goalsetter.R
import com.pandroid.goalsetter.data.resource.State
import com.pandroid.goalsetter.domain.model.GoalModel
import com.pandroid.goalsetter.presentation.component.AppBar
import com.pandroid.goalsetter.presentation.component.CustomButton
import com.pandroid.goalsetter.presentation.component.CustomCircularIndicator
import com.pandroid.goalsetter.presentation.component.CustomTextField
import com.pandroid.goalsetter.presentation.utils.Dimens
import com.pandroid.goalsetter.ui.theme.PrimaryColor
import org.koin.androidx.compose.getViewModel
import java.util.Calendar

@Composable
fun CreateGoalScreen(navController: NavController, viewModel: GoalViewModel = getViewModel()) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var targetDate by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Initialize Calendar for DatePickerDialog
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Format date as "YYYY-MM-DD"
            targetDate = "$year-${month + 1}-$dayOfMonth"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AppBar(navController = navController, text = "Create Your Goal!")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumSmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        /*Hero image*/
        Image(
            painter = painterResource(id = R.drawable.hero),
            contentDescription = "Hero image"
        )

        // Title input field
        CustomTextField(
            value = title,
            onValueChange = { title = it },
            label = "Enter goal title",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(Dimens.SmallHeight))

        // Description input field
        CustomTextField(
            value = description,
            onValueChange = { description = it },
            label = "Enter goal description",
            keyboardType = KeyboardType.Text,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(Dimens.SmallMediumHeight))

        // Date picker button
        CustomButton(
            onClick = { datePickerDialog.show() },
            text = if (targetDate.isEmpty()) "Pick Target Date" else "Date: $targetDate",
            color = PrimaryColor
        )

        Spacer(modifier = Modifier.height(Dimens.MediumLargeLargeHeight))

        // Create goal button
        CustomButton(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty() && targetDate.isNotEmpty()) {
                    val goal = GoalModel(
                        title = title,
                        description = description,
                        targetDate = targetDate
                    )
                    viewModel.createGoal(goal)
                } else {
                    Toast.makeText(context, "Please fill All fields", Toast.LENGTH_SHORT).show()
                }
            },
            text = "Create Goal",
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.MediumHeight)
        )

        Spacer(modifier = Modifier.height(Dimens.SmallMediumHeight))

    }

    // Loading, Success, and Error states
    when (state) {
        is State.Loading -> CustomCircularIndicator()
        is State.Success -> {
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }

        is State.Error -> {
            Text(
                "Error: ${(state as State.Error).message}",
                color = Color.Red
            )
        }

        else -> Unit
    }

}

