package com.example.curdfirestore.NivelPresentacion.PresentacionConductor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.curdfirestore.NivelAplicacion.Pasajeros.GuardarSolicitud
import com.example.curdfirestore.R
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Solicitudes.obtenerFechaEnFormato
import com.example.curdfirestore.NivelAplicacion.TextInMarker
import com.example.curdfirestore.NivelAplicacion.convertCoordinatesToAddress
import com.example.curdfirestore.NivelAplicacion.convertirStringALatLng
import com.example.curdfirestore.NivelAplicacion.HorarioData
import com.example.curdfirestore.NivelAplicacion.MarkerItiData
import com.example.curdfirestore.NivelAplicacion.ParadaData
import com.example.curdfirestore.NivelAplicacion.SolicitudData
import com.google.android.gms.maps.model.LatLng




@Composable
fun VentanaMarkerItinerario(
    navController: NavController,
    email:String,
    marker: MarkerItiData,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    var boton by remember { mutableStateOf(false) }

    var ejecutado by remember { mutableStateOf(false) }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(modifier = Modifier
                .background(Color.White)
                .padding(10.dp)) {
                Text(
                    text = "Información",
                    modifier = Modifier.padding(2.dp),
                    style = TextStyle(
                        Color(137, 13, 88),
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center

                )
                Text(text = "Detalles")


                val addressP = convertCoordinatesToAddress(marker.marker_ubicacion)


                TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                TextInMarker(Label = "Ubicación: ", Text = addressP)
                TextInMarker(Label = "Horario: ", Text = marker.marker_hora)


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    // Primer botón para "Ver viaje"

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    233,
                                    168,
                                    219
                                )
                            ),
                            onClick = {
                                // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                onDismiss()
                            }) {
                            Text(
                                text = "Cerrar", style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            )
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    137,
                                    13,
                                    88
                                )
                            ),
                            onClick = {
                                //Guardar en la bd
                                boton=true
                                //  onDismiss()
                                // navController.navigate("home_viaje_pasajero/$email")

                            }) {
                            Text(
                                text = "Editar", style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            )
                        }

                    }

                }
            }
        }
    }


}
