package com.pandroid.goalsetter.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumRoundedCorner
import com.pandroid.goalsetter.presentation.utils.Dimens.MediumSmallTextSize
import com.pandroid.goalsetter.ui.theme.Manrope
import com.pandroid.goalsetter.ui.theme.PrimaryColor

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    color: Color = PrimaryColor,
    textColor: Color = Color.White,
    shape: RoundedCornerShape = RoundedCornerShape(MediumRoundedCorner),
    modifier: Modifier = Modifier.fillMaxWidth(),
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = shape
    ) {
        Text(text = text, fontFamily = Manrope, fontSize = MediumSmallTextSize, color = textColor)
    }
}
