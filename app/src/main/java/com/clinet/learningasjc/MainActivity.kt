package com.clinet.learningasjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinet.learningasjc.ui.theme.LearningASJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningASJCTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember() { mutableStateOf(1) }
    var squeezeCount by remember() { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(color = 0xFFF5E568)
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when(currentStep){
                1 -> {
                    LemonTextWithImage(
                        onImageClick = {
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        },
                        text = R.string.lemon_tree,
                        image = R.drawable.lemon_tree,
                        contentDescription = R.string.lemon_tree_content_description
                    )
                }
                2 -> {
                    LemonTextWithImage(
                        onImageClick = {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        },
                        text = R.string.lemon_squeeze,
                        image = R.drawable.lemon_squeeze,
                        contentDescription = R.string.lemon_content_description
                    )
                }
                3 -> {
                    LemonTextWithImage(
                        onImageClick = {
                            currentStep = 4
                        },
                        text = R.string.lemon_drink,
                        image = R.drawable.lemon_drink,
                        contentDescription = R.string.lemonade_content_description
                    )
                }
                4 -> {
                    LemonTextWithImage(
                        onImageClick = {
                            currentStep = 1
                        },
                        text = R.string.lemon_restart,
                        image = R.drawable.lemon_restart,
                        contentDescription = R.string.empty_glass_content_description
                    )
                }
            }
        }
    }
}

@Composable
fun LemonTextWithImage(
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    @DrawableRes image: Int,
    @StringRes contentDescription: Int

    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color = 0xFFCBEBD4)
            )
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = stringResource(id = contentDescription),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.button_image_with))
                    .height(dimensionResource(id = R.dimen.button_image_height))
                    .padding(dimensionResource(id = R.dimen.button_interior_padding))
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
        Text(
            text = stringResource(id = text),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LearningASJCTheme {
        LemonadeApp()
    }
}