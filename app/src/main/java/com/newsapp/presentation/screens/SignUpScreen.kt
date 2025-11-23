package com.newsapp.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.newsapp.R
import com.newsapp.presentation.components.BottomBar
import com.newsapp.presentation.viewmodels.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SignUpScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToSignUp: () -> Unit
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

    val loginState by viewModel.loginState.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onNavigateToSignUp()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 50.dp, bottom = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                FloatingLabelText(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        if (it.isNotBlank()) firstNameError = false
                    },
                    label = "First Name",
                    isError = firstNameError,
                    isPassword = false,
                    focusRequester = firstNameFocus
                )


                FloatingLabelText(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        if (it.isNotBlank()) lastNameError = false
                    },
                    label = "Last Name",
                    isError = lastNameError,
                    isPassword = false,
                    focusRequester = lastNameFocus
                )


                FloatingLabelText(
                    value = email,
                    onValueChange = {
                        email = it
                        if (it.isNotBlank()) emailError = false
                    },
                    label = "Email",
                    isError = emailError,
                    isPassword = false,
                    focusRequester = emailFocus
                )

                FloatingLabelText(
                    value = password,
                    onValueChange = {
                        password = it
                        if (it.isNotBlank()) passwordError = false
                    },
                    label = "Password",
                    isError = passwordError,
                    isPassword = true,
                    focusRequester = passwordFocus
                )



                FloatingLabelText(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (it.isNotBlank()) confirmPasswordError = false
                    },
                    label = "Confirm Password",
                    isError = confirmPasswordError,
                    isPassword = true,
                    focusRequester = confirmPasswordFocus
                )

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(76.dp)
                        .padding(top = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.light_blue)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    enabled = loginState !is LoginState.Loading
                ) {
                    if (loginState is LoginState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Sign Up",
                            style = TextStyle(fontSize = 16.sp),
                            color = colorResource(R.color.white)
                        )
                    }
                }

                if (loginState is LoginState.Error) {
                    Text(
                        text = (loginState as LoginState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Already have an account? ",
                    color = colorResource(R.color.text_color)
                )
                Text(
                    text = "Login",
                    color = colorResource(R.color.light_blue)
                )
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



@Composable
private fun FloatingLabelText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    isPassword: Boolean = false,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val hasText = value.isNotEmpty()

    val labelFraction by animateFloatAsState(
        targetValue = if (isFocused || hasText) 1f else 0f,
        label = "labelTransition"
    )

    Box(
        modifier = Modifier
            .width(350.dp)
            .padding(top = 15.dp)
    ) {

        Text(
            text = label,
            fontSize = lerp(16.sp, 12.sp, labelFraction),
            color = if (isError) Color.Red else colorResource(R.color.text_color),
            modifier = Modifier
                .offset(y = lerp(40.dp, (-8).dp, labelFraction))
                .padding(start = 35.dp)
                .background(
                    color = if (isFocused || hasText) colorResource(R.color.background) else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
        )


        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .border(
                    width = 1.dp,
                    color = if (isError) {
                        Color.Red
                    } else if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .focusRequester(focusRequester),

                visualTransformation = if (isPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                interactionSource = interactionSource,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.surfaceVariant)
            )

            if (hasText) {
                IconButton(
                    onClick = { onValueChange("") },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear field",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}