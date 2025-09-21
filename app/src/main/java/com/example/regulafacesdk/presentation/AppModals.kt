package com.example.regulafacesdk.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun OptionsFaceComparisonModal(
    show: Boolean = false,
    onDismiss: () -> Unit = {},
    onOptionSelected: (String) -> Unit = {}
){
    if(!show){
        return
    }
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = { onOptionSelected("Camara FaceSdk") },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Camara FaceSdk"
                )
            }
            Button(
                onClick = { onOptionSelected("Galeria") },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Galeria"
                )
            }
        }
    }
}

@Preview
@Composable
fun OptionsFaceComparisonModalPreview(){
    OptionsFaceComparisonModal(
        show = true
    )
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Fondo semitransparente para simular una capa modal
            .background(Color.Black.copy(alpha = 0.4f))
            // Esto es CRUCIAL: Intercepta todos los clics y evita que pasen
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center
    ) {
        // El indicador de progreso de Material 3
        CircularProgressIndicator()
    }
}