package com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.NivelAplicacion.Conductor.GuardarViaje
import com.example.curdfirestore.R
import com.example.curdfirestore.NivelAplicacion.TituloNoBack
import com.example.curdfirestore.NivelAplicacion.ViajeData
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

/*Ya no la estoy ocupando*/
@Composable
fun RegistrarDestinoConductorFalse(
    navController: NavController,
    userid:String,
    dia:String,
    hora:String,
    origen:String,
    horad:String

){
    var latitud by remember {
        mutableStateOf("")
    }
    var longitud by remember {
        mutableStateOf("")
    }
    var u_origen by remember { mutableStateOf("0,0") }
    var ejecutado by remember { mutableStateOf(false) }
    var boton by remember { mutableStateOf(false) }
    val miUbic = LatLng(19.389816, -99.110234)
    val markerState = rememberMarkerState(position = miUbic)
    latitud=markerState.position.latitude.toString()
    longitud= markerState.position.longitude.toString()
    u_origen="$latitud,$longitud"
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
    }
    Box(
        modifier = Modifier
            .height(mh)
            .padding(15.dp)
    ){
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxHeight()
        )
        {
            Marker(
                title = "Origen",
                state = markerState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                snippet = "Punto de origen",
                draggable = true
            )

        }

        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier=Modifier.fillMaxSize().padding(20.dp,0.dp)
        ) {

            Button(colors = ButtonDefaults.buttonColors(containerColor = Color(194, 99, 157)),
                onClick = {
                    navController.navigate("home_viaje_conductor/$userid")
                }) {
                Text(
                    text = "Cancelar",
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(124, 80, 202)
                ),
                onClick = {

                    var ubicacionF = "$latitud,$longitud"
                    boton = true
                }) {

                Text(text = "Confirmar")
            }
        }

        Box(
            modifier= Modifier
                .width(300.dp)
                .background(Color.White)
                //.clip(RoundedCornerShape(50))
                .align(Alignment.TopStart)
                //.offset(y=20.dp)

                .border(0.7.dp, Color(104, 104, 104),)

        ){
            Column {
               TituloNoBack(Titulo = "Origen")
                Text(
                    text = "Arrastra el marcador a las paradas donde puedas encontrarte con los pasajeros", textAlign = TextAlign.Start,
                    modifier = Modifier.padding(8.dp),

                    style = TextStyle(
                        color = Color(104, 104, 104),
                        fontSize = 16.sp
                    )
                )
            }

        }

    }
    if (boton==true && ejecutado==false){
        /*val viajeData = ViajeData(
          viaje_origen = "$latitud,$longitud"
        )*/
        val ubicacion="$latitud,$longitud"
        val viajeData = ViajeData(
            usu_id = userid,
            viaje_dia = dia,
            viaje_hora_partida = hora,
            viaje_origen = origen,
            viaje_hora_llegada = horad,
            viaje_destino = ubicacion,
            viaje_trayecto = "1"
        )

        GuardarViaje(navController, userid, viajeData)
        //GuardarCoordenadas(navController, userid,viajeData)
        ejecutado=true
    }



}