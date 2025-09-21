package com.example.regulafacesdk.presentation.features

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.regulafacesdk.R
import com.example.regulafacesdk.data.FaceSdkRepositoryImpl
import com.example.regulafacesdk.data.ImageRepositoryImpl
import com.example.regulafacesdk.domain.CompareFacesUseCase
import com.example.regulafacesdk.domain.GetImageUseCase
import com.example.regulafacesdk.presentation.LoadingOverlay
import com.example.regulafacesdk.presentation.OptionsFaceComparisonModal
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.configuration.FaceCaptureConfiguration
import com.regula.facesdk.model.results.FaceCaptureResponse

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val similarityUiState by viewModel.similarityUiState.collectAsState()
    val imageOneUiState by viewModel.imageOneUiState.collectAsState()
    val imageTwoUiState by viewModel.imageTwoUiState.collectAsState()

    var showOptionsModalOne by remember { mutableStateOf(false) }
    var showOptionsModalTwo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Bienvenido a Regula Face SDK",
                style = MaterialTheme.typography.headlineMedium
            )
            if (imageOneUiState.bitmap == null){
                Image(
                    modifier = Modifier
                        .clickable {
                            showOptionsModalOne = true
                        }
                        .width(200.dp)
                        .height(150.dp),
                    painter = painterResource(R.drawable.reconocimiento_facial_1),
                    contentDescription = "Face Icon"
                )
            } else {
                Image(
                    modifier = Modifier
                        .clickable {
                            showOptionsModalOne = true
                        }
                        .width(200.dp)
                        .height(150.dp),
                    bitmap = imageOneUiState.bitmap!!.asImageBitmap(),
                    contentDescription = "Face Icon"
                )
            }
            Text(
                text = "Sube la imagen del primer rostro",
            )
            // Muestra el modal solo si showOptionsModal es true
            // Registers a photo picker activity launcher in single-select mode.
            val pickMediaOne = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { uri: Uri? ->
                // El callback se ejecuta después de que el usuario selecciona algo
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    viewModel.setImageUriImageOneUiState(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

            OptionsFaceComparisonModal(
                show = showOptionsModalOne,
                onDismiss = {
                    showOptionsModalOne = false
                },
                onOptionSelected = {

                    when (it) {
                        "Camara FaceSdk" -> {
                            showOptionsModalOne = false
                            val configuration = FaceCaptureConfiguration.Builder().setCameraSwitchEnabled(true).build()

                            FaceSDK.Instance().presentFaceCaptureActivity(
                                context, configuration
                            ) { faceCaptureResponse: FaceCaptureResponse? ->

                                faceCaptureResponse?.let { response ->
                                    viewModel.setBitmapImageOneUiState(response.image.bitmap)
                                }
                            }
                        }
                        "Galeria" -> {
                            showOptionsModalOne = false
                            pickMediaOne.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    }
                }
            )

            if (imageTwoUiState.bitmap == null){
                Image(
                    modifier = Modifier
                        .clickable {
                            showOptionsModalTwo = true
                        }
                        .width(200.dp)
                        .height(150.dp),
                    painter = painterResource(R.drawable.reconocimiento_facial_2),
                    contentDescription = "Face Icon"
                )
            } else {
                Image(
                    modifier = Modifier
                        .clickable {
                            showOptionsModalTwo = true
                        }
                        .width(200.dp)
                        .height(150.dp),
                    bitmap = imageTwoUiState.bitmap!!.asImageBitmap(),
                    contentDescription = "Face Icon"
                )
            }
            Text(
                text = "Sube la imagen del segundo rostro",
            )

            val pickMediaTwo = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { uri: Uri? ->
                // El callback se ejecuta después de que el usuario selecciona algo
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    viewModel.setImageUriImageTwoUiState(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
            // Modal de Imagen Dos
            OptionsFaceComparisonModal(
                show = showOptionsModalTwo,
                onDismiss = {
                    showOptionsModalTwo = false
                },
                onOptionSelected = {

                    when (it) {
                        "Camara FaceSdk" -> {
                            showOptionsModalTwo = false
                            val configuration = FaceCaptureConfiguration.Builder().setCameraSwitchEnabled(true).build()

                            FaceSDK.Instance().presentFaceCaptureActivity(
                                context, configuration
                            ) { faceCaptureResponse: FaceCaptureResponse? ->
                                faceCaptureResponse?.let { response ->
                                    viewModel.setBitmapImageTwoUiState(response.image.bitmap)
                                }
                            }
                        }
                        "Galeria" -> {
                            showOptionsModalTwo = false
                            pickMediaTwo.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    }
                }
            )


            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .width(200.dp),
                onClick = {
                    viewModel.compareFaces()
                }
            ) {
                Text(
                    text = "Comparar rostros"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .width(200.dp),
                onClick = {
                    viewModel.clear()
                }
            ) {
                Text(
                    text = "Limpiar"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Similitud:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = similarityUiState.similarity,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

            }

        }
        if (similarityUiState.isLoading) {
            LoadingOverlay()
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(
    device = Devices.PIXEL_4
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = HomeViewModel(
            getImageUseCase = GetImageUseCase(
                ImageRepositoryImpl(
                    LocalContext.current
                )
            ),
            compareFacesUseCase = CompareFacesUseCase(
                FaceSdkRepositoryImpl(
                    LocalContext.current,
                    FaceSDK.Instance()
                )
            )
        )
    )
}

