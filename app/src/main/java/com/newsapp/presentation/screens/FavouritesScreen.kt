package com.newsapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.newsapp.R
import com.newsapp.domain.model.Article
import com.newsapp.presentation.components.BottomBar
import com.newsapp.presentation.navigation.Screen
import com.newsapp.presentation.viewmodels.FavouritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = koinViewModel(),
    navController: NavController
) {
    val favoriteArticles by viewModel.favoriteArticles.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.page_margin))
                        .padding(bottom = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(R.color.text_color),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )

                    Text(
                        text = "Favourites",
                        color = colorResource(R.color.light_blue),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )

                    Spacer(modifier = Modifier.size(24.dp))
                }
            }

            if (favoriteArticles.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No favorite articles yet",
                                color = colorResource(R.color.text_color),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Start adding articles to your favorites!",
                                color = colorResource(R.color.text_color2),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            } else {
                items(favoriteArticles) { article ->
                    FavoriteArticleItem(
                        article = article,
                        onClick = {
                            navController.navigate(Screen.ArticleDetail.createRoute(article.url))
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomBar(
                navController = navController
            )
        }
    }
}

@Composable
fun FavoriteArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val maxChars = 150

    val description = article.description ?: article.content ?: "No description available"

    val displayText = if (expanded) {
        buildAnnotatedString {
            append(description)
            append(" ")
            pushStringAnnotation(tag = "SHOW_LESS", annotation = "show_less")
            withStyle(style = SpanStyle(color = Color(0xFF00BCD4))) {
                append("Show Less")
            }
            pop()
        }
    } else {
        buildAnnotatedString {
            val short = if (description.length > maxChars) {
                description.take(maxChars) + "..."
            } else {
                description
            }
            append(short)

            if (description.length > maxChars) {
                append(" ")
                pushStringAnnotation(tag = "READ_MORE", annotation = "read_more")
                withStyle(style = SpanStyle(color = Color(0xFF00BCD4))) {
                    append("Read More")
                }
                pop()
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.page_margin))
            .padding(bottom = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = article.urlToImage ?: R.drawable.profile_img
                ),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = article.sourceName,
                    color = colorResource(R.color.text_color),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = article.title,
                    color = colorResource(R.color.text_color3),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2
                )

                ClickableText(
                    text = displayText,
                    style = androidx.compose.ui.text.TextStyle(
                        color = colorResource(R.color.text_color),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
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
        }
    }
}
