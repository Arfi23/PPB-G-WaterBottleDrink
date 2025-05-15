package com.example.waterbottle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaterBottle(
    modifier: Modifier = Modifier,
    totalWaterAmount: Int,
    unit: String,
    usedWaterAmount: Int,
    waterColor: Color = Color(0xff279eff),
    bottleColor: Color = Color.White,
    capColor: Color = Color(0xff0065b9)
){
    val waterPercentage = animateFloatAsState(
        targetValue = usedWaterAmount.toFloat() / totalWaterAmount.toFloat(),
        label = "Water Waves Animation",
        animationSpec = tween(durationMillis = 1000)
    ).value


    val usedWaterAmountAnimation = animateIntAsState(
        targetValue = usedWaterAmount,
        label = "Used Water Amount Animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    Box(modifier.width(200.dp).height(600.dp)){
        Canvas(modifier= Modifier.fillMaxSize()){
            val width = size.width // max width dari canvas
            val height = size.height // max height dari canvas

            val capWidth = size.width * 0.55f
            val capHeight = size.height * 0.13f

            val bottleBodyPath = Path().apply{
                moveTo(width * 0.3f, height * 0.1f)

                lineTo(width * 0.3f, height * 0.2f)

                quadraticTo(0f, height * 0.3f, 0f, height * 0.4f)

                lineTo(0f, height * 0.95f)

                quadraticTo(0f, height, width * 0.05f, height)

                lineTo(width * 0.95f, height)

                quadraticTo(width, height, width, height * 0.95f)

                lineTo(width, height * 0.4f)

                quadraticTo(width, height * 0.3f, width * 0.7f, height * 0.2f)

                lineTo(width * 0.7f, height * 0.2f)

                lineTo(width * 0.7f, height * 0.1f)

                close()
            }
            clipPath (
                bottleBodyPath
            ){
                drawRect(
                    color = bottleColor,
                    size = size,
                )

                val waterWavesYPosition = (1 - waterPercentage) * size.height
                val waterPath = Path().apply {
                    moveTo(
                        x = 0f,
                        y = waterWavesYPosition
                    )
                    lineTo(
                        x = size.width,
                        y = waterWavesYPosition
                    )
                    lineTo(
                        x = size.width,
                        y = size.height
                    )
                    lineTo(
                        x = 0f,
                        y = size.height
                    )
                    close()
                }
                drawPath(
                    path = waterPath,
                    color = waterColor,
                )
            }
            drawRoundRect(
                color = capColor,
                size = Size(capWidth, capHeight),
                topLeft = Offset(size.width / 2 - capWidth / 2f, 0f),
                cornerRadius = CornerRadius(45f, 45f)
            )
        }

        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = if(waterPercentage > 0.5f) bottleColor else waterColor,
                    fontSize = 44.sp
                )
            ){
                append(usedWaterAmountAnimation.toString())
            }
            withStyle(
                style = SpanStyle(
                    color = if(waterPercentage > 0.5f) bottleColor else waterColor,
                    fontSize = 22.sp
                )
            ){
                append(" ")
                append(unit)
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = text)
        }
    }
}

