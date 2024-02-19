package com.seyda.composestatedemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seyda.composestatedemo.ui.theme.ComposeStateDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MyViewModel>()
            ComposeStateDemoTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /** var count by rememberSaveable { mutableStateOf(0) }
                    MyButton(count) {
                    count = it + 1  **/

                    val count = viewModel.count
                    MyButton(count) {
                        viewModel.increaseCount()
                    }
                }
            }
        }
    }
}


//During configuration changes,such as screen rotation , language changes and keyboard changes , android recreates the activity.
//If we want our state in a composable to preserve such configuration change, we should use  "rememberSaveable"


//This concept where the state goes down and events go up called unidirectional data flow
//State hoisting is the main pattern we use to build unidirectional data flow design in Jetpack Compose
// When we are applying state hoisting to a composable, we usually need to add two parameters to it.
//Current value to display and An event that request current value change
//We add the event as a functional parameter
//There are many benefits of state hoisting
//It decouples the state from the composable. That allows us to store the state somewhere like room data base or
// a remote server and update the composable when required
//State hoisting provides a single data source. We call it single source of truth.
//ıt helps to avoid bugs and it makes maintain of the code much easier.
//It also makes our states more secure by encapsulating states
//State hoisting makes states shareable between different composable
//It eliminates all the risks of sharing state

@Composable
fun MyButton(currentCount: Int, updateCount: (Int) -> Unit) {
    //I am going to define a function parameter for the click event
    //We are going to take this event up, implement this event inside the caller using a lambda
    //This function
    //var count by remember { mutableStateOf(0) }
    Button(
        onClick = {
            updateCount(currentCount)
        },
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(10.dp, Color.Black),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Count is : $currentCount",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(5.dp)
        )
        //So, that means, Even though state has changed it has not detected by compose runtime
        //So there was no Recomposition
        //Therefore we need to use mutable state now.
    }
}
