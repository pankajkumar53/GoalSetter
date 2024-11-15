package com.pandroid.goalsetter.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pandroid.goalsetter.R
import com.pandroid.goalsetter.presentation.utils.Dimens.LargeSize
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumLargeSmallPadding

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "icon",
            modifier = Modifier.padding(bottom = MediumLargeSmallPadding).size(LargeSize)
        )
    }
}