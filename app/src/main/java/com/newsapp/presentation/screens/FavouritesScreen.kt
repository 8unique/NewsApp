package com.newsapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.newsapp.R
import com.newsapp.presentation.components.BottomBar
import com.newsapp.presentation.viewmodels.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouritesScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToFavourites: () -> Unit
) {
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    val fullText = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"

    val maxChars = 300

    val displayText = if (expanded && fullText.length > maxChars) {
        buildAnnotatedString {
            append(fullText)

            append("... ")
            pushStringAnnotation(tag = "SHOW_LESS", annotation = "show_less")
            withStyle(style = SpanStyle(color = colorResource(R.color.light_blue))) {
                append("Show Less")
            }
            pop()
        }
    } else {
        buildAnnotatedString {
            val short = fullText.take(maxChars) + "..."
            append(short)

            pushStringAnnotation(tag = "READ_MORE", annotation = "read_more")
            withStyle(style = SpanStyle(color = colorResource(R.color.light_blue))) {
                append(" Read More")
            }
            pop()
        }
    }



    val loginState by viewModel.loginState.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(loginState)
    {
        if (loginState is LoginState.Success) {
            onNavigateToFavourites()
        }
    }

    Scaffold(
    modifier = Modifier.fillMaxSize()
    )
    {
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .padding(paddingValues)
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(bottom = 20.dp, top = 20.dp)
            ) {
                Text(
                    text = "Favourites",
                    color = colorResource(R.color.light_blue),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 50.dp, bottom = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.profile_img),
                    contentDescription = "article image",
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(horizontal = dimensionResource(R.dimen.page_img_margin))
                        .height(200.dp)
                        .width(500.dp)
                        .clip(RoundedCornerShape(5)),
                    contentScale = ContentScale.Crop
                )

                ClickableText(
                    text = displayText,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .padding(horizontal = dimensionResource(R.dimen.page_margin)),
                    style = androidx.compose.ui.text.TextStyle(
                        color = colorResource(R.color.black),
                        fontSize = 18.sp
                    )
                ) { offset ->
                    displayText.getStringAnnotations(
                        tag = "READ_MORE",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        expanded = true
                    }

                    displayText.getStringAnnotations(
                        tag = "SHOW_LESS",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        expanded = false
                    }
                }


            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                BottomBar(
                    navController = navController
                )
            }
        }
    }
}