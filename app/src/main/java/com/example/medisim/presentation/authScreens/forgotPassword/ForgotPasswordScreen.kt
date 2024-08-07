package com.example.medisim.presentation.authScreens.forgotPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medisim.R
import com.example.medisim.presentation.components.BackIcon
import com.example.medisim.presentation.components.ButtonClickOn
import com.example.medisim.presentation.components.EmailEditText
import com.example.medisim.presentation.components.TextLabel


@Composable
fun ForgotPassword(navController: NavHostController,forgotPasswordViewModel: ForgotPasswordViewModel) {

    val state = forgotPasswordViewModel.state.value
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(12.dp),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackIcon{ navController.popBackStack()}


        }

        TextLabel(
            text = stringResource(id = R.string.forgot_password),
            modifier = Modifier.padding(top = 20.dp),
            textFont = 28,
            textFontWight = FontWeight.Bold
        )
        TextLabel(
            text = stringResource(id = R.string.email),
            modifier = Modifier.padding(top = 60.dp,bottom = 20.dp),
            textFont = 18,
            textFontWight = FontWeight.Bold
        )
        EmailEditText(
            email = state.email,
            isErrorEmail = state.isErrorEmail,
            emailErrorMessage = state.emailErrorMessage,
            onValueChange = {newEmail->forgotPasswordViewModel.onEmailChange(newEmail)}
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonClickOn(
            buttonText = stringResource(R.string.send),
            paddingValue = 0) {
            forgotPasswordViewModel.onSendOtpToEmail(navController, context)
        }



    }
}