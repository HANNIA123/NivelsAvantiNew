package com.example.curdfirestore.NivelPresentacion.PresentacionPasajero

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.NivelAplicacion.TituloPantalla
import com.example.curdfirestore.NivelAplicacion.pruebaMenuPasajero
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.mh

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViajePasajero(
    navController: NavController,
    correo: String

) {

    BoxWithConstraints {
        mh = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {
                pruebaMenuPasajero(navController, correo)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(mh)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TituloPantalla(Titulo = "Viaje", navController,)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(mh)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            238, 236, 239
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                        navController.navigate(route = "registrar_viaje_pasajero/$correo")
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color(137, 13, 88),
                    )
                    Text(
                        text = "Registrar viaje",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }


                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            238, 236, 239
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(route = "ver_itinerario_pasajero/$correo")

                    }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color(137, 13, 88),
                    )
                    Text(
                        text = "Visualizar itinerario",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }


                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            238, 236, 239
                        )
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {


                    }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color(137, 13, 88),
                    )
                    Text(
                        text = "Cancelar viaje",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }

            }
        }
    }
}







