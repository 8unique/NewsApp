package com.newsapp.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newsapp.R
import com.newsapp.presentation.navigation.BottomBarNav
import com.newsapp.presentation.navigation.OnboardingNav
import com.newsapp.presentation.screens.uistates.LoginUiState
import com.newsapp.presentation.viewmodels.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    val emailFocus = remember { FocusRequester() }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    val passwordFocus = remember { FocusRequester() }

    val loginState by viewModel.loginUiState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginUiState.Success) {
            navController.navigate(BottomBarNav.HomeScreen.route) {
                popUpTo(OnboardingNav.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           Image(
                    painter = painterResource(id = R.drawable.profile_img),
                    contentDescription = "profile image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            AuthTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (it.isNotBlank()) emailError = false
                },
                label = stringResource(R.string.login_screen_email),
                isError = emailError,
                isPassword = false,
                focusRequester = emailFocus
            )

            AuthTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (it.isNotBlank()) passwordError = false
                },
                label = stringResource(R.string.login_screen_password),
                isError = passwordError,
                isPassword = true,
                focusRequester = passwordFocus
            )

            Button(
                onClick = {
                    when {
                        email.isBlank() -> emailError = true
                        password.isBlank() -> passwordError = true
                        else -> viewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(76.dp)
                    .padding(top = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.light_blue)
                ),
                shape = RoundedCornerShape(50.dp),
                enabled = loginState !is LoginUiState.Loading
            ) {
                if (loginState is LoginUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colorResource(R.color.light_blue)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.login_screen_login),
                        style = TextStyle(fontSize = 16.sp),
                        color = colorResource(R.color.text_color2)
                    )
                }
            }

            if (loginState is LoginUiState.Error) {
                Text(
                    text = (loginState as LoginUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 14.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.login_screen_do_not_have_an_account),
                color = colorResource(R.color.text_color),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.login_screen_signup),
                color = colorResource(R.color.light_blue),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewModel.resetState()
                    navController.navigate(OnboardingNav.SignUpScreen.route)
                }
            )
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    isPassword: Boolean = false,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val hasText = value.isNotEmpty()
    var passwordVisible by remember { mutableStateOf(false) }

    val labelFraction by animateFloatAsState(
        targetValue = if (isFocused || hasText) 1f else 0f,
        label = "labelTransition"
    )

    Box(
        modifier = Modifier
            .width(350.dp)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = lerp(16.sp, 12.sp, labelFraction),
            color = if (isError) Color.Red else colorResource(R.color.light_red),
            modifier = Modifier
                .offset(y = lerp(35.dp, (-8).dp, labelFraction))
                .padding(horizontal = 20.dp)
                .background(
                    color = if (isFocused || hasText) colorResource(R.color.background) else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 4.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(56.dp)
                .border(
                    width = 2.dp,
                    color = if (isError) Color.Red
                    else if (isFocused) colorResource(R.color.light_red)
                    else colorResource(R.color.text_color2),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 20.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                visualTransformation = if (isPassword && !passwordVisible)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_color3)
                ),
                interactionSource = interactionSource,
                cursorBrush = SolidColor(colorResource(R.color.light_blue))
            )

            if (isPassword) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = colorResource(R.color.text_color2),
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                if (hasText) {
                    IconButton(
                        onClick = { onValueChange("") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear field",
                            tint = colorResource(R.color.text_color2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}