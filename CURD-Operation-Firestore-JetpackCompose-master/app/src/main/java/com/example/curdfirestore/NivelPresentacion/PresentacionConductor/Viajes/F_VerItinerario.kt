package com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.NivelAplicacion.LineaGris
import com.example.curdfirestore.NivelAplicacion.TexItinerario
import com.example.curdfirestore.NivelAplicacion.TituloPantalla
import com.example.curdfirestore.NivelAplicacion.convertCoordinatesToAddress
import com.example.curdfirestore.NivelAplicacion.convertirStringALatLng
import com.example.curdfirestore.NivelAplicacion.eliminarParada
import com.example.curdfirestore.NivelAplicacion.eliminarViaje
import com.example.curdfirestore.NivelAplicacion.pruebaMenu
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.maxh
import com.example.curdfirestore.NivelAplicacion.ViajeDataReturn
import com.google.android.gms.maps.model.LatLng


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F_VerItinerarioConductor(
    navController: NavController,
    userId: String,
    viajes: List<ViajeDataReturn>

) {
    var unDia by remember { mutableStateOf(false) }
    var martes by remember { mutableStateOf(true) }
    var miercoles by remember { mutableStateOf(true) }
    var jueves by remember { mutableStateOf(true) }
    var viernes by remember { mutableStateOf(true) }
    var sabado by remember { mutableStateOf(true) }
    var domingo by remember { mutableStateOf(true) }
    val pantalla="itinerario"
    val pantalla2="detalles"


    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {
                pruebaMenu(navController, userId)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TituloPantalla(Titulo = "Itinerario", navController,)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    var nmar=0
                    var nlun=0
                    var nmier=0
                    var njue=0
                    var nvie=0
                    var nsab=0
                    var ndom=0
                    var displayedDays = 0

                    for (viaje in viajes){

                        //---------------VISUALIZAR------------------------------------------
                        //Convertir String a coordenadas  -- ORIGEN

                        var markerLatO by remember { mutableStateOf(0.0) }
                        var markerLonO by remember { mutableStateOf(0.0) }

                        val markerCoordenadasLatLngO = convertirStringALatLng(viaje.viaje_origen)

                        if (markerCoordenadasLatLngO != null) {
                            markerLatO = markerCoordenadasLatLngO.latitude
                            markerLonO = markerCoordenadasLatLngO.longitude
                            //println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                        } else {
                            println("Error al convertir la cadena a LatLng")
                        }

                        //-------------DESTINO-------------------------------------------

                        var markerLatD by remember { mutableStateOf(0.0) }
                        var markerLonD by remember { mutableStateOf(0.0) }

                        val markerCoordenadasLatLngD = convertirStringALatLng(viaje.viaje_destino)

                        if (markerCoordenadasLatLngD != null) {
                            markerLatD = markerCoordenadasLatLngD.latitude
                            markerLonD = markerCoordenadasLatLngD.longitude
                        } else {
                            println("Error al convertir la cadena a LatLng")
                        }

                        val coord_origen = LatLng(markerLatO, markerLonO)
                        val origen = convertCoordinatesToAddress(coord_origen)

                        val coord_destino = LatLng(markerLatD, markerLonD)
                        val destino = convertCoordinatesToAddress(coord_destino)

                        //------------------------------------------------------------------

                        val sortedViajes = listOf("Lunes", "Martes", "Miércoles","Jueves", "Viernes", "Sábado", "Domingo")
                            .sortedBy { day ->
                                when (day) {
                                    "Lunes" -> 1
                                    "Martes" -> 2
                                    "Miércoles" -> 3
                                    "Jueves" -> 4
                                    "Viernes" -> 5
                                    "Sábado" -> 6
                                    "Domingo" -> 7
                                    else -> 8 // Set a default value for days not specified
                                }
                            }

                        if (displayedDays==0) {
                            sortedViajes.forEach { day ->

                                viajes.filter { it.viaje_dia == day }.forEach { viaje ->

                                    //val uniqueLinkId = "detalles_viaje/${viaje.viaje_id}/$userId/$pantalla2"


                                    if (viaje.viaje_dia == "Lunes")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {

                                            if (nlun == 0) {
                                                Text(
                                                    text = "Lunes",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            nlun++

                                            //----------------VER DETALLES DEL VIAJE-----------------------

                                                if (viaje.viaje_trayecto == "1") {
                                                    TexItinerario(
                                                        Label = "Trayecto:",
                                                        Text = "UPIITA como destino"
                                                    )
                                                    TexItinerario(
                                                        Label = "Hora de partida: ",
                                                        Text = "${viaje.viaje_hora_partida}"
                                                    )
                                                    /* TexItinerario(Label = "Día:", Text = "${viaje.viaje_dia}")
                                                    TexItinerario(Label = "Origen:", Text = "${origen}")
                                                    TexItinerario(Label = "Destino:", Text = "UPIITA")
                                                    TexItinerario(Label = "Hora de llegada: ", Text = "${viaje.viaje_hora_llegada}")*/
                                                } else {
                                                    TexItinerario(
                                                        Label = "Trayecto:",
                                                        Text = "UPIITA como origen"
                                                    )
                                                    TexItinerario(
                                                        Label = "Hora de partida: ",
                                                        Text = "${viaje.viaje_hora_partida}"
                                                    )

                                                }
                                              //BOTON PARA ENLACE A OTRA PANTALLA


                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember {
                                                    mutableStateOf(
                                                        false
                                                    )
                                                }
                                                TextButton(onClick = {
                                                    showDialog = true
                                                }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = {
                                                            showDialog = false
                                                        },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(
                                                                    documentIdToDelete
                                                                )
                                                                eliminarParada(
                                                                    documentIdToDelete
                                                                )

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()

                                        }
                                    } ///------------


                                    if (viaje.viaje_dia == "Martes")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (nmar == 0) {
                                                Text(
                                                    text = "Martes",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            nmar++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {

                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------

                                    if (viaje.viaje_dia == "Miércoles")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (nmier == 0) {
                                                Text(
                                                    text = "Miércoles",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            nmier++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------

                                    if (viaje.viaje_dia == "Jueves")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (njue == 0) {
                                                Text(
                                                    text = "Jueves",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            njue++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------

                                    if (viaje.viaje_dia == "Viernes")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (nvie == 0) {
                                                Text(
                                                    text = "Viernes",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            nvie++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------


                                    if (viaje.viaje_dia == "Sábado")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (nsab == 0) {
                                                Text(
                                                    text = "Sábado",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            nsab++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------

                                    if (viaje.viaje_dia == "Domingo")
                                    {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            if (ndom == 0) {
                                                Text(
                                                    text = "Domingo",
                                                    style = TextStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(137, 13, 88),
                                                        fontSize = 18.sp
                                                    )
                                                )
                                            }
                                            ndom++

                                            if (viaje.viaje_trayecto == "1") {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como destino")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            } else {
                                                TexItinerario(Label = "Trayecto:", Text = "UPIITA como origen")
                                                TexItinerario(Label = "Hora de partida: ", Text = "${viaje.viaje_hora_partida}")
                                            }

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .fillMaxWidth()
                                            ) {

                                                //Boton para ver viaje
                                                TextButton(onClick = {
                                                    navController.navigate("ver_viaje/${viaje.viaje_id}/$userId/$pantalla")
                                                }

                                                ) {
                                                    Text(
                                                        text = "Ver",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //Boton para editar
                                                TextButton(onClick = { /*TODO*/ }) {
                                                    Text(
                                                        text = "Editar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                //------------BOTON PARA ELIMINAR---------------------------------------
                                                var showDialog by remember { mutableStateOf(false) }
                                                TextButton(onClick = { showDialog = true }) {
                                                    Text(
                                                        text = "Eliminar",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(104, 104, 104),
                                                        )
                                                    )
                                                }

                                                if (showDialog) {
                                                    AlertDialog(
                                                        onDismissRequest = { showDialog = false },
                                                        title = {
                                                            Text("Eliminar viaje")
                                                        },
                                                        text = {
                                                            Text("¿Estas seguro de eliminar este viaje?")
                                                        },
                                                        confirmButton = {
                                                            TextButton(onClick = {

                                                                val documentIdToDelete =
                                                                    viaje.viaje_id;
                                                                eliminarViaje(documentIdToDelete)
                                                                eliminarParada(documentIdToDelete)

                                                                showDialog = false
                                                            }) {
                                                                Text("Aceptar ")
                                                                Text("${viaje.viaje_id}")
                                                            }
                                                        },
                                                        dismissButton = {
                                                            TextButton(onClick = {
                                                                showDialog = false
                                                            }) {
                                                                Text("Cancelar")
                                                            }
                                                        }
                                                    )

                                                }

                                                //---------------------------------------------------
                                            }
                                            LineaGris()
                                        }
                                    } ///------------

                                }


                            }
                            displayedDays++
                        }


                    }
                }
            }
        }
    }
}


