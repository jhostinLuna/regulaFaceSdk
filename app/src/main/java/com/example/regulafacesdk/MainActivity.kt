package com.example.regulafacesdk

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.regulafacesdk.presentation.FaceSdkCamera
import com.example.regulafacesdk.presentation.NavigationGraph
import com.example.regulafacesdk.ui.theme.RegulaFaceSdkTheme
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.configuration.LivenessConfiguration
import com.regula.facesdk.enums.LivenessType
import com.regula.facesdk.exception.InitException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeFaceSDK()
        setContent {
            RegulaFaceSdkTheme {
                NavigationGraph()
            }
        }
    }
    private fun initializeFaceSDK() {
        //Inicializando FaceSDK
        FaceSDK.Instance().initialize(this) { status: Boolean, e: InitException? ->
            if (!status) {
                Toast.makeText(
                    this,
                    "Init finished with error: " + if (e != null) e.message else "",
                    Toast.LENGTH_LONG
                ).show()
                return@initialize
            }
            Log.d("MainActivity", "FaceSDK init completed successfully")
        }
    }
}