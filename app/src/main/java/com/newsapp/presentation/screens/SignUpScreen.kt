package com.newsapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newsapp.R
import com.newsapp.presentation.navigation.BottomBarNav
import com.newsapp.presentation.navigation.OnboardingNav
import com.newsapp.presentation.screens.uistates.LoginUiState
import com.newsapp.presentation.viewmodels.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    var firstName by remember { mutableStateOf("") }
    var firstNameError by remember { mutableStateOf(false) }
    val firstNameFocus = remember { FocusRequester() }

    var lastName by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf(false) }
    val lastNameFocus = remember { FocusRequester() }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    val emailFocus = remember { FocusRequester() }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    val passwordFocus = remember { FocusRequester() }

    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }
    val confirmPasswordFocus = remember { FocusRequester() }

    val loginState by viewModel.loginUiState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginUiState.Success) {
            navController.navigate(BottomBarNav.HomeScreen.route) {
                popUpTo(OnboardingNav.SignUpScreen.route) { inclusive = true }
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.resetState()
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.signup_screen_back),
                        tint = colorResource(R.color.text_color2)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.signup_screen_signup),
                    color = colorResource(R.color.light_blue),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            AuthTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    if (it.isNotBlank()) firstNameError = false
                },
                label = stringResource(R.string.signup_screen_first_name),
                isError = firstNameError,
                isPassword = false,
                focusRequester = firstNameFocus
            )

            AuthTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    if (it.isNotBlank()) lastNameError = false
                },
                label = stringResource(R.string.signup_screen_last_name),
                isError = lastNameError,
                isPassword = false,
                focusRequester = lastNameFocus
            )

            AuthTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (it.isNotBlank()) emailError = false
                },
                label = stringResource(R.string.signup_screen_email),
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
                label = stringResource(R.string.signup_screen_password),
                isError = passwordError,
                isPassword = true,
                focusRequester = passwordFocus
            )

            AuthTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    if (it.isNotBlank()) confirmPasswordError = false
                },
                label = stringResource(R.string.signup_screen_confirm_password),
                isError = confirmPasswordError,
                isPassword = true,
                focusRequester = confirmPasswordFocus
            )

            Button(
                onClick = {
                    when {
                        firstName.isBlank() -> firstNameError = true
                        lastName.isBlank() -> lastNameError = true
                        email.isBlank() -> emailError = true
                        password.isBlank() -> passwordError = true
                        confirmPassword.isBlank() -> confirmPasswordError = true
                        password != confirmPassword -> {
                            confirmPasswordError = true
                        }
                        else -> viewModel.signUp(firstName, lastName, email, password)
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
                        text = stringResource(R.string.signup_screen_signup),
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
                text = stringResource(R.string.signup_screen_already_have_an_account),
                color = colorResource(R.color.text_color),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.signup_screen_login),
                color = colorResource(R.color.light_blue),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewModel.resetState()
                    navController.popBackStack()
                }
            )
        }
    }
}