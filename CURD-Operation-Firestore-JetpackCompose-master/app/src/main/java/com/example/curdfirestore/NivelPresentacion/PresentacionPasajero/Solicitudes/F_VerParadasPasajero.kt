package com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Solicitudes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.curdfirestore.R
import com.example.curdfirestore.NivelAplicacion.ParadaData


import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.curdfirestore.NivelAplicacion.TextoMarker
import com.example.curdfirestore.NivelAplicacion.convertCoordinatesToAddress
import com.example.curdfirestore.NivelAplicacion.convertirStringALatLng
import com.example.curdfirestore.NivelAplicacion.HorarioData
import com.example.curdfirestore.NivelAplicacion.ViajeDataReturn
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.mh

var maxh = 0.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F_VerParadasPasajero(
    navController: NavController,
    correo: String,
    viajeData: List<ViajeDataReturn>,
    paradas: List<ParadaData>,
    horarioId: String,
    horario: HorarioData
) {
    BoxWithConstraints {
        mh = this.maxHeight - 50.dp
    }
    println("LLegaa---- $paradas")
    //Convertir String a coordenadas  -- origen

    var markerLatO by remember { mutableStateOf(0.0) }
    var markerLonO by remember { mutableStateOf(0.0) }
    var infparadas by remember { mutableStateOf<ParadaData?>(null) }

    val markerCoordenadasLatLngO = convertirStringALatLng(horario.horario_origen)

    if (markerCoordenadasLatLngO != null) {
        markerLatO = markerCoordenadasLatLngO.latitude
        markerLonO = markerCoordenadasLatLngO.longitude
        // Hacer algo con las coordenadas LatLng
        println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

//Destino
    var markerLatD by remember { mutableStateOf(0.0) }
    var markerLonD by remember { mutableStateOf(0.0) }

    val markerCoordenadasLatLngD = convertirStringALatLng(horario.horario_destino)

    if (markerCoordenadasLatLngD != null) {
        markerLatD = markerCoordenadasLatLngD.latitude
        markerLonD = markerCoordenadasLatLngD.longitude
        // Hacer algo con las coordenadas LatLng
        //  println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

//Lista de los markers
    val latLngs = mutableListOf<LatLng>()
    val paradasPorMarcador = mutableMapOf<String, ParadaData>()

    BoxWithConstraints {
        maxh = this.maxHeight
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
        ) {
            val origen = LatLng(markerLatO, markerLonO)
            val destino = LatLng(markerLatD, markerLonD)

            var markerPositions = listOf(
                origen, // Marker 1
                destino, // Marker 2

            )

            var paradasPositions = listOf<LatLng>()
            // Variable para almacenar la última ubicación de la cámara
            var ultimaUbicacionCamera: LatLng? = null

            for (parada in paradas) {

                var markerLat by remember { mutableStateOf(0.0) }
                var markerLon by remember { mutableStateOf(0.0) }

                val markerCoordenadasLatLng = convertirStringALatLng(parada.par_ubicacion)

                if (markerCoordenadasLatLng != null) {
                    markerLat = markerCoordenadasLatLng.latitude
                    markerLon = markerCoordenadasLatLng.longitude
                    // Hacer algo con las coordenadas LatLng
                    // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                } else {
                    // La conversión falló
                    println("Error al convertir la cadena a LatLng")
                }
                val ubiParada = LatLng(markerLat, markerLon)
                //  markerPositions = markerPositions + ubiParada
                paradasPositions = paradasPositions + ubiParada

            }
            val miUbic = LatLng(markerLatO, markerLonO)
            var markerState = rememberMarkerState(position = miUbic)
            var cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
            }
            var paradasClickeadas by remember { mutableStateOf(emptyList<ParadaData>()) }
            //Mapa
            var show by rememberSaveable { mutableStateOf(false) }
            var markerLat by remember { mutableStateOf(0.0) }
            var markerLon by remember { mutableStateOf(0.0) }
            val context = LocalContext.current
            MapViewContainer { googleMap: GoogleMap ->
                // Habilita los controles de zoom
                // googleMap.uiSettings.isZoomControlsEnabled = true
                // Agrega los marcadores
                for (parada in paradas) {
                    val markerCoordenadasLatLng = convertirStringALatLng(parada.par_ubicacion)
                    if (markerCoordenadasLatLng != null) {
                        markerLat = markerCoordenadasLatLng.latitude
                        markerLon = markerCoordenadasLatLng.longitude
                        // Hacer algo con las coordenadas LatLng
                        // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                    } else {
                        println("Error al convertir la cadena a LatLng")
                    }
                    val ubiParada = LatLng(markerLat, markerLon)
                    val markerOptions = MarkerOptions().position(ubiParada)
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                    val marker = googleMap.addMarker(markerOptions)
                    paradasPorMarcador[marker!!.id] = parada


                    // Actualiza la última ubicación de la cámara con la primera parada
                    ultimaUbicacionCamera = ubiParada

                    // Agregar un evento de clic al marcador
                    googleMap.setOnMarkerClickListener { marker ->
                        // Aquí puedes manejar el evento de clic en el marcador
                        // Por ejemplo, puedes mostrar un mensaje Toast con el título del marcador
                        //Toast.makeText(context, "Has hecho clic en el marcador: ${marker.title}", Toast.LENGTH_SHORT).show()
                        val paradaSeleccionada = paradasPorMarcador[marker.id]
                        if (paradaSeleccionada != null) {
                            infparadas = paradaSeleccionada
                            show = true
                        }


                        true

                    }


                }

                ultimaUbicacionCamera?.let { ultimaUbicacion ->
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(ultimaUbicacion, 12.0f)
                    googleMap.animateCamera(cameraUpdate)
                }

            }
            if (show) {
                VentanaMarker(
                    navController,
                    correo,
                    infparadas!!,
                    horario!!,
                    horarioId,
                    show,
                    { show = false },
                    {})
            }

            Row(verticalAlignment = Alignment.Top) {
//Boton
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(15.dp)
                        .offset(x = 20.dp, y = 25.dp)

                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("home_viaje_pasajero/$correo")
                        },
                        modifier = Modifier
                            .size(25.dp) // Tamaño del botón
                            .background(Color(137, 13, 88), shape = CircleShape)

                    ) {
                        Icon(
                            modifier = Modifier
                                .size(23.dp),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Icono Cerrar",
                            tint = Color.White

                        )


                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 15.dp, 30.dp, 5.dp)
                        .border(1.dp, Color.LightGray)
                        .background(Color.White),

                    ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val latLng = LatLng(37.7749, -122.4194)
                        var newadress = ""



                        TextoMarker(Label = "Dia: ", Text = horario.horario_dia)

                        if (horario.horario_trayecto == "0") {
                            val address = convertCoordinatesToAddress(destino)
                            newadress = address
                            // println("Direccón origen $address") // Imprime la dirección en la consola

                            TextoMarker(Label = "Origen: ", Text = "UPIITA ")
                            TextoMarker(Label = "Destino: ", Text = "$address")
                            TextoMarker(
                                Label = "Horario de salida: ",
                                Text = "${horario.horario_hora} hrs"
                            )

                        } else {
                            val address = convertCoordinatesToAddress(origen)
                            newadress = address
                            // println("Direccón origen $address") // Imprime la dirección en la consola
                            TextoMarker(Label = "Origen: ", Text = "$address")
                            TextoMarker(Label = "Destino: ", Text = "UPIITA")
                            TextoMarker(
                                Label = "Horario de llegada: ",
                                Text = "${horario.horario_hora} hrs"
                            )

                        }


                    }


                }

            }

        }
    }

}

@Composable
fun MapViewContainer(mapReady: (GoogleMap) -> Unit) {
    val context = LocalContext.current
    AndroidView(
        factory = { context: Context ->
            MapView(context).apply {
                onCreate(Bundle())
                getMapAsync(mapReady)
            }
        },
        update = { mapView ->
            mapView.onResume()
        }
    )
}


/*
@Preview(showBackground = true)
@Composable
fun MyComposablePreviewVerViaje() {
    // Esta función se utiliza para la vista previa
    var correo="hplayasr1700@alumno.ipn.mx"
    val navController = rememberNavController()

    val viajeData: ViajeData = ViajeData(/* provide constructor arguments here if needed */)
    val listaDeViajes: List<ParadaData> = listOf(
        ParadaData(/* provide constructor arguments here if needed */),

    )
    F_VerParadasPasajero(navController = navController, correo = correo, viajeData = viajeData, listaDeViajes, pantalla = "itinerario")
}
*/
