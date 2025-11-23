package com.newsapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.newsapp.R
import com.newsapp.presentation.viewmodels.ArticleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArticleScreen(
    articleUrl: String,
    viewModel: ArticleViewModel = koinViewModel(),
    navController: NavController
) {
    val article by viewModel.article.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(articleUrl) {
        viewModel.loadArticle(articleUrl)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
        ) {
            if (article != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = article!!.urlToImage ?: R.drawable.profile_img
                            ),
                            contentDescription = article!!.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(16.dp)
                                .size(40.dp)
                                .background(
                                    color = colorResource(R.color.light_blue),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 16.dp)
                                .offset(y = 40.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = article!!.sourceName,
                                    color = colorResource(R.color.text_color),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = article!!.title,
                                    color = colorResource(R.color.text_color3),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 24.sp
                                )

                                if (!article!!.author.isNullOrEmpty()) {
                                    Text(
                                        text = article!!.author!!,
                                        color = colorResource(R.color.text_color2),
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 60.dp, bottom = 100.dp)
                    ) {
                        Text(
                            text = article!!.title,
                            color = colorResource(R.color.text_color3),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 28.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        if (!article!!.description.isNullOrEmpty()) {
                            Text(
                                text = article!!.description!!,
                                color = colorResource(R.color.text_color),
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        if (!article!!.content.isNullOrEmpty()) {
                            Text(
                                text = article!!.content!!,
                                color = colorResource(R.color.text_color),
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Text(
                            text = "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.",
                            color = colorResource(R.color.text_color),
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    }
                }

                FloatingActionButton(
                    onClick = {
                        article?.let { viewModel.toggleFavorite(it) }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                        .size(56.dp),
                    containerColor = if (isFavorite)
                        colorResource(R.color.light_blue)
                    else
                        Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite)
                            "Remove from favorites"
                        else
                            "Add to favorites",
                        tint = if (isFavorite)
                            Color.White
                        else
                            colorResource(R.color.text_color2),
                        modifier = Modifier.size(28.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(R.color.light_blue)
                    )
                }
            }
        }
    }
}