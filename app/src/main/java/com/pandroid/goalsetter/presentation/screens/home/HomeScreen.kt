package com.pandroid.goalsetter.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pandroid.goalsetter.R
import com.pandroid.goalsetter.domain.model.GoalModel
import com.pandroid.goalsetter.presentation.component.CustomButton
import com.pandroid.goalsetter.presentation.component.CustomTextField
import com.pandroid.goalsetter.presentation.navigation.Routes
import com.pandroid.goalsetter.presentation.screens.goal.GoalViewModel
import com.pandroid.goalsetter.presentation.utils.Dimens
import com.pandroid.goalsetter.ui.theme.AccentColor
import com.pandroid.goalsetter.ui.theme.Manrope
import com.pandroid.goalsetter.ui.theme.PrimaryColor
import com.pandroid.goalsetter.ui.theme.PrimaryDarkColor
import com.pandroid.goalsetter.ui.theme.TertiaryColor
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: GoalViewModel = getViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadGoal()
    }

    val allGoals = viewModel.goalList.collectAsState(initial = emptyList()).value
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val todayGoals = allGoals.filter { it.targetDate == todayDate }

    val images = listOf(
        R.drawable.hero,
        R.drawable.hero2,
        R.drawable.icon_splash
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TertiaryColor,
                        AccentColor,
                        Color.White
                    )
                )
            )
            .padding(Dimens.MediumSmallPadding)
    ) {

        Text(
            text = "Achieve your goal with us!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                color = PrimaryDarkColor
            )
        )

        Text(
            text = "Your Today Goals", style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            ),
            modifier = Modifier.padding(top = Dimens.MediumSmallPadding)
        )

        TodayGoal(todayGoals = todayGoals, images)

        Text(
            text = "Your All Goals", style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            ),
            modifier = Modifier.padding(top = Dimens.MediumSmallPadding)
        )

        AllGoals(
            allGoals = allGoals,
            images,
            onDeleteGoal = { viewModel.deleteGoal(it) },
            onUpdateGoal = { viewModel.updateGoal(it) })

        CustomButton(
            text = "CREATE NEW GOAL",
            onClick = { navController.navigate(Routes.CreateGoalScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.MediumHeight)
        )
    }
}

@Composable
fun TodayGoal(todayGoals: List<GoalModel>, images: List<Int>) {

    if (todayGoals.isEmpty()) {
        Text(
            text = "No goals for today!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(Dimens.MediumSmallPadding)
        )
    } else {
        LazyRow {
            items(todayGoals) { goal ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(Dimens.SmallPadding),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimens.MediumElevation)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFFE0F7FA), Color.White)
                                )
                            )
                            .padding(Dimens.SmallPadding)
                    ) {
                        Image(
                            painter = painterResource(images.random()),
                            contentDescription = "dummy image",
                            modifier = Modifier
                                .height(Dimens.MediumLargeHeight)
                                .width(Dimens.LargeWidth)
                        )
                        Text(
                            text = goal.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = Manrope
                            ),
                            modifier = Modifier.align(Alignment.BottomStart)
                        )

                    }
                }
            }
        }
    }
}


@Composable
fun AllGoals(
    allGoals: List<GoalModel>,
    images: List<Int>,
    onUpdateGoal: (GoalModel) -> Unit,
    onDeleteGoal: (String) -> Unit
) {
    var selectedGoal by remember { mutableStateOf<GoalModel?>(null) }

    // Show custom dialog if a goal is selected
    selectedGoal?.let { goal ->
        CustomPopup(
            goal = goal,
            onDismiss = { selectedGoal = null },
            onUpdate = { updatedTitle, updatedDescription ->
                val updatedGoal = goal.copy(title = updatedTitle, description = updatedDescription)
                onUpdateGoal(updatedGoal)
                selectedGoal = null
            },
            onDelete = {
                onDeleteGoal(goal.goalId)
                selectedGoal = null
            }
        )
    }

    if (allGoals.isEmpty()) {
        Text(
            text = "You have no goals yet.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(Dimens.MediumSmallPadding)
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f),
            columns = GridCells.Fixed(2)
        ) {
            items(allGoals) { goal ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(Dimens.SmallPadding)
                        .clickable { selectedGoal = goal }, // Open custom dialog on click
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimens.MediumElevation)
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFE0F7FA),
                                        Color.White
                                    )
                                )
                            )
                            .padding(Dimens.SmallPadding)
                    ) {
                        Image(
                            painter = painterResource(images.random()),
                            contentDescription = "Goal image",
                            modifier = Modifier.size(width = Dimens.LargeMediumWidth, height = Dimens.LargeHeight)
                        )
                        Text(
                            text = goal.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = Manrope
                            )
                        )
                        Text(
                            text = goal.description,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = Manrope
                        )
                        Text(
                            text = "Target Date: ${goal.targetDate}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = Manrope
                            )
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun CustomPopup(
    goal: GoalModel,
    onDismiss: () -> Unit,
    onUpdate: (String, String) -> Unit,
    onDelete: (String) -> Unit
) {

    var title by remember { mutableStateOf(goal.title) }
    var discreption by remember { mutableStateOf(goal.description) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.2f),
                shape = RoundedCornerShape(Dimens.MediumSmallRoundedCorner)
            )
            .clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(Dimens.MediumSmallPadding),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimens.MediumElevation)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE0F7FA),
                                Color.White
                            )
                        )
                    )
                    .padding(Dimens.MediumSmallPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Manage Goal",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = Manrope
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.SmallHeight))

                CustomTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Title"
                )

                Spacer(modifier = Modifier.height(Dimens.SmallHeight))

                CustomTextField(
                    value = discreption,
                    onValueChange = { discreption = it },
                    label = "Description"
                )


                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.MediumSmallPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomButton(
                        text = "Update",
                        onClick = {
                            onUpdate(title, discreption)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CustomButton(
                        text = "Delete",
                        onClick = { onDelete(goal.goalId) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.SmallHeight))
                CustomButton(
                    text = "Cancel",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


