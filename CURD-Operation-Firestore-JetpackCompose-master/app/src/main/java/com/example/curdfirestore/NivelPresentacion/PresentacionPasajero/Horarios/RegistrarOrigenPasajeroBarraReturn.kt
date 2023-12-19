package com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.curdfirestore.R

import com.example.curdfirestore.NivelAplicacion.SearchBar
import com.example.curdfirestore.NivelAplicacion.searchPlaces
import com.example.curdfirestore.NivelAplicacion.convertirStringALatLng
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.maxh
import com.example.curdfirestore.NivelAplicacion.HorarioData
import com.example.curdfirestore.NivelAplicacion.Pasajeros.GuardarHorario
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrarOrigenPasajeroReturn(
    navController: NavController,
    userid: String,
    dia: String,
    horao: String,
    ubiMarkerRet: String
) {
    BoxWithConstraints {
        maxh = this.maxHeight
    }

//Para convertir string a coordenadas
    var markerLat by remember { mutableStateOf(0.0) }
    var markerLon by remember { mutableStateOf(0.0) }
    val markerCoordenadasLatLng = convertirStringALatLng(ubiMarkerRet)
    var first by remember { mutableStateOf(true) }

    if (markerCoordenadasLatLng != null) {
        markerLat = markerCoordenadasLatLng.latitude
        markerLon = markerCoordenadasLatLng.longitude
        // Hacer algo con las coordenadas LatLng
        println("Latitud: ${markerCoordenadasLatLng.latitude}, Longitud: ${markerCoordenadasLatLng.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

    var latitud by remember {
        mutableStateOf("")
    }
    var longitud by remember {
        mutableStateOf("")
    }

    var pasarlatitud by remember {
        mutableStateOf("")
    }
    var pasarlongitud by remember {
        mutableStateOf("")
    }
    var inicial by remember {
        mutableStateOf("si")
    }
    var valorMapa: String? by remember { mutableStateOf("barra") }

    var TipoBusqueda: String by remember { mutableStateOf("barra") }

    val searchResults = remember { mutableStateOf(emptyList<Place>()) }


    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    var ejecutado by remember { mutableStateOf(false) }
    var boton by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery.value) {
        // Lanzar un bloque coroutine para ejecutar la búsqueda de lugares
        try {
            val results = withContext(Dispatchers.IO) {
                searchPlaces(searchQuery.value, context)
            }

            searchResults.value = results
            println("Bloqe $results")
        } catch (e: Exception) {
            // Manejar cualquier error que pueda ocurrir durante la búsqueda
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (valorMapa == "marker") {
                var ubicacionMarker = "$pasarlatitud,$pasarlongitud"
                navController.navigate(route = "registrar_origen_pasajero_marker/$userid/$dia/$horao/$ubicacionMarker")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 30.dp),
            ) {

                val miUbic = LatLng(markerLat, markerLon)
                var markerState = rememberMarkerState(position = miUbic)
                var cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
                }
                latitud = markerState.position.latitude.toString()
                longitud = markerState.position.longitude.toString()

                if (first == false) {
                    if (valorMapa == "barra") {
                        selectedPlace?.let { place ->
                            markerState = rememberMarkerState(
                                position = LatLng(
                                    place.latLng.latitude,
                                    place.latLng.longitude
                                )
                            )
                            pasarlatitud = place.latLng.latitude.toString()
                            pasarlongitud = place.latLng.longitude.toString()


                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                LatLng(
                                    place.latLng.latitude,
                                    place.latLng.longitude
                                ), 17f
                            )
                        }
                    }


                }
                println("Tipo busqueda!!!!!!!!!: $valorMapa")
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {

                    //Marcador en el punto que manda la otra pantalla
                    if (first == true) {
                        Marker(
                            state = MarkerState(miUbic),
                            title = "Origen",
                            snippet = "punto origen",
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                        )
                        first = false
                    } else {
                        selectedPlace?.let { place ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        place.latLng.latitude,
                                        place.latLng.longitude
                                    )
                                ),
                                title = place.name,
                                snippet = place.address,
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                            )
                        }

                    }

                }
                //Termina
                val TextoBarra = "¿De dónde sale?"
                Column(
                    modifier = Modifier

                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    SearchBar(searchQuery, searchResults.value, { newQuery ->
                        searchQuery.value = newQuery
                    }, { place ->
                        selectedPlace = place
                    },
                        onMapButtonClick = { valorRetornadoDelMapa ->
                            valorMapa = valorRetornadoDelMapa
                        },
                        TipoBusqueda,
                        TextoBarra
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .offset(y = maxh - 80.dp),
                horizontalAlignment = Alignment.Start,
            ) {

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(137, 13, 88),
                    ),
                    onClick = {
                        var ubicacionF = "$pasarlatitud,$pasarlongitud"
                        boton=true
                        //navController.navigate(route = "registrar_destino_pasajero/$userid/$dia/$horao/$horad/$ubicacionF")
                    }) {
                    Text(text = "Siguiente")
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.offset(x = 30.dp, y = -110.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(25.dp) // Tamaño del botón
                        .background(Color(137, 13, 88), shape = CircleShape)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(23.dp),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Icono atras",
                        tint = Color.White

                    )
                }
            }

        }

    }

    if (boton==true && ejecutado==false){
        /*val viajeData = ViajeData(
           viaje_origen = "$latitud,$longitud"
         )*/
        val ubicacion="$pasarlatitud,$pasarlongitud"
        val destino="19.5114059,-99.1265259" //Coordenadas de UPIITA
        val horarioData = HorarioData(
            usu_id = userid,
            horario_dia = dia,
            horario_hora=horao,
            horario_destino=destino,
            horario_origen=ubicacion,
            horario_trayecto = "1",
            horario_solicitud = "No"
        )

        GuardarHorario(navController, userid, horarioData)
        //GuardarCoordenadas(navController, userid,viajeData)
        ejecutado=true
    }

}










