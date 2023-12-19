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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.curdfirestore.R
import com.example.curdfirestore.NivelAplicacion.SearchBar
import com.example.curdfirestore.NivelAplicacion.searchPlaces
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
var tipoSeleccion="barra"
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrarOrigenPasajero(
    navController: NavController,
    userid:String,
    dia:String,
    horao:String,

) {
    BoxWithConstraints {
        maxh = this.maxHeight
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

            if (valorMapa=="marker"){
                var ubicacionMarker = "$pasarlatitud,$pasarlongitud"
                navController.navigate(route = "registrar_origen_pasajero_marker/$userid/$dia/$horao/$ubicacionMarker")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 30.dp),
            ) {
                val miUbic = LatLng(19.389816, -99.110234)
                var markerState = rememberMarkerState(position = miUbic)
                var cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
                }

                latitud = markerState.position.latitude.toString()
                longitud = markerState.position.longitude.toString()

                if (valorMapa == "barra") {
                    selectedPlace?.let { place ->
                        markerState = rememberMarkerState(
                            position = LatLng(
                                place.latLng.latitude,
                                place.latLng.longitude
                            )
                        )
                        pasarlatitud=  place.latLng.latitude.toString()
                        pasarlongitud=  place.latLng.longitude.toString()


                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(
                                place.latLng.latitude,
                                place.latLng.longitude
                            ), 17f
                        )
                    }
                }



                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {

                    selectedPlace?.let { place ->
                        Marker(
                            state = MarkerState(position = LatLng(place.latLng.latitude, place.latLng.longitude)),
                            title = place.name,
                            snippet = place.address,
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                        )
                    }
                }

                val TextoBarra="¿De dónde sale?"
                Column(
                    modifier = Modifier

                        .align(Alignment.TopStart).fillMaxWidth().padding(12.dp),
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .offset( y = maxh -80.dp)
                    ,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(137, 13, 88),
                        ),
                        onClick = {
                            var ubicacionF = "$pasarlatitud,$pasarlongitud"
                            boton = true
                           // navController.navigate(route = "registrar_destino_pasajero/$userid/$dia/$horao/$ubicacionF")
                          //  navController.navigate(route = "registrar_viajed_conductor/$userid/$dia/$horao/$horad/$ubicacionF")
                        }) {
                        Text(text = "Siguiente")
                    }
                }


            }


            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.offset(x = 30.dp, y=-110.dp)
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

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun RegistrarOrigenPasajeroPreview() {
    // Esta función se utiliza para la vista previa
    var correo="hplayasr1700@alumno.ipn.mx"
    val navController = rememberNavController()
    RegistrarOrigenPasajero(navController = navController, correo,
        dia="Lunes", horao="7:00")

}









