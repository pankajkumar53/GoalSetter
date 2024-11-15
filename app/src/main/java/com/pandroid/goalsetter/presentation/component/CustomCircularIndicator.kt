package com.pandroid.goalsetter.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallLargeSize
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumWidth
import com.pandroid.goalsetter.ui.theme.PrimaryColor

@Composable
fun CustomCircularIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(MediumSmallLargeSize),
        color = PrimaryColor,
        strokeWidth = MediumWidth
    )
}