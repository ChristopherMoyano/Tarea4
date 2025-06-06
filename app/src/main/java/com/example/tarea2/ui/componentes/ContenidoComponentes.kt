package com.example.tarea2.ui.componentes

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.R

@Composable
fun CategoryDropdown(
    categories: List<Categoria>,
    selectedCategory: Categoria?,
    onCategorySelected: (Categoria) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.border(1.dp, Color(0xFFD84040), RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(selectedCategory?.nombre ?:"Selecciones una categoria")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 120.dp, y = 0.dp)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.nombre) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CommonFields(
    name: String,
    information: String,
    onNameChange: (String) -> Unit,
    onInformationChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Nombre") },
        modifier = Modifier.padding(10.dp)
    )
    OutlinedTextField(
        value = information,
        onValueChange = onInformationChange,
        label = { Text("Descripción") },
        modifier = Modifier.padding(10.dp)
    )

}

@Composable
fun ImagePickerSection(
    imageUri: String?,
    onPickImage: () -> Unit
) {
    Button(
        onClick = onPickImage,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Seleccionar Imagen")
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (imageUri != null) {
        AsyncImage(
            model = Uri.parse(imageUri),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.bajoconstruccion),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}