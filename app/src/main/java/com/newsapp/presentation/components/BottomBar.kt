package com.newsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newsapp.R
import com.newsapp.presentation.navigation.BottomBarNav

@Composable
fun BottomBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomBarNav.HomeScreen,
        BottomBarNav.FavouriteScreen,
        BottomBarNav.ProfileScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .width(300.dp)
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(colorResource(id = R.color.bottom_navbar))
                .drawWithContent {
                    drawContent()

                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = size.width * 0.1f
                        )
                    )

                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.05f)
                            ),
                            startX = size.width * 0.90f,
                            endX = size.width
                        )
                    )

                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = size.height * 0.2f
                        )
                    )

                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.05f)
                            ),
                            startY = size.height * 0.3f,
                            endY = size.height
                        )
                    )
                }
        ) {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(dimensionResource(id = R.dimen.bottom_nav_bar)),
                containerColor = Color.Transparent
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(BottomBarNav.HomeScreen.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Column(
                                modifier = Modifier.wrapContentHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (currentRoute == item.route) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.navbar_line),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.light_blue),
                                        modifier = Modifier
                                            .width(24.dp)
                                            .height(3.dp)
                                            .padding(bottom = 2.dp)
                                    )
                                }

                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title),
                                    modifier = Modifier.size(28.dp)
                                )

                                Text(
                                    text = stringResource(id = item.title),
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        label = null,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(R.color.light_blue),
                            unselectedIconColor = colorResource(R.color.bottom_navbar_icon_unselected),
                            selectedTextColor = colorResource(R.color.black),
                            unselectedTextColor = colorResource(R.color.text_color2),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}