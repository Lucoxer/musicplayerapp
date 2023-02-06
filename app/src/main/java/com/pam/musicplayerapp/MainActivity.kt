package com.pam.musicplayerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pam.musicplayerapp.ui.theme.MusicplayerappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicplayerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginForm()
                }
            }
        }
    }
}

internal fun doAuth(
    username: String,
    password: String,
): Boolean {
    return (username.equals("admin") && password.equals("admin"))
}

@Composable
fun LoginForm() {
    val lContext = LocalContext.current

    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),

    ) {
        Text(
            text = "Login Form",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 120.dp, end = 128.dp, bottom = 20.dp)

        )
        TextField(
            value = usernameInput.toString(),
            onValueChange = { usernameInput = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth(),
        )
        TextField(
            value = passwordInput.toString(),
            onValueChange = { passwordInput = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 120.dp, end = 128.dp, bottom = 20.dp),
            onClick = {
                val isAuthtenticated = doAuth(usernameInput, passwordInput)
                if (isAuthtenticated) {
                    lContext.startActivity(
                        Intent(lContext, HomeActivity::class.java)
                            .apply {
                                putExtra("username", usernameInput)
                            }
                    )
                }
            }
        ) {
            Text(text = "Login")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginFormPreview(){
    MusicplayerappTheme{
        LoginForm()
    }
}