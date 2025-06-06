package com.example.tarea2.ui.componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tarea2.Home
import com.example.tarea2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarConBackButton(
    titulo: String,
    navController: NavController,
    showBack: Boolean = true,
    onBack: (()->Unit)? = null
){
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically // alinea verticalmente al centro
            ) {
                if(showBack)
                {
                    FloatingActionButton(
                        onClick = {
                            val popped = navController.popBackStack()
                            if(!popped) navController.navigate(Home)
                            onBack?.invoke()
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.atras),
                            contentDescription = "Back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }



                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(12.dp))

                Text(text = titulo)
            }
        }
    )
}