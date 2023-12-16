package com.example.curdfirestore.NivelAplicacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.curdfirestore.NivelAplicacion.Conductor.ObtenerItinerarioConductor
import com.example.curdfirestore.NivelAplicacion.Conductor.ObtenerPasajerosConductor
import com.example.curdfirestore.NivelAplicacion.Conductor.ObtenerSolicitudesConductor
import com.example.curdfirestore.NivelAplicacion.Conductor.ObtenerViajeRegistrado
import com.example.curdfirestore.NivelAplicacion.Pasajeros.ObtenerItinerarioPasajero
import com.example.curdfirestore.NivelAplicacion.Pasajeros.ObtenerParadasPasajero


import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.HomeViajeConductor
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.HomeViajePasajero



import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarDestinoConductor
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarOrigenConductor
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarDestinoConductorMarker
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarDestinoConductorReturn
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarOrigenConductorMarker
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarOrigenConductorReturn
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarParadaBarra
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarParadaBarraReturn
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarParadaMarker
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarDestinoPasajero
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarDestinoPasajeroMarker
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarDestinoPasajeroReturn
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarOrigenPasajero
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarOrigenPasajeroMarker
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarOrigenPasajeroReturn
import com.example.curdfirestore.NivelPresentacion.PresentacionPasajero.Horarios.RegistrarViajePas
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.AgregarParadas
import com.example.curdfirestore.NivelPresentacion.PresentacionConductor.Viajes.RegistrarViajeCon


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        // main screen
        composable(
            route = Screens.Login.route
        ) {
            Login(  navController = navController) {
                navController.navigate("HomeCon/$it")
            }
        }
        composable("ResetPassword"){
            ResetPassword( navController = navController)
        }

        composable( "HomeCon/{correo}"
        ) {
            val correo= it.arguments?.getString("correo")?:""
            val pantalla="Home"
            ObtenerHome ( //Esta es en ConsultasBD
                navController = navController,
                correo,
                pantalla
            )

        }
 //agregado 08/12/2023 Hannia: Pantalla del pasajero

        // get data screen

        composable( "perfil_conductor/{userid}"
        ) {
            val pantalla="Perfil"
            val userID= it.arguments?.getString("userid")?:""
            println("Este es el correo perfil")
            println(userID)
            ObtenerHome (
                navController = navController,
                userID,
                pantalla
            )

        }
        composable( "home_viaje_conductor/{userid}"
        ) {
            val userID= it.arguments?.getString("userid")?:""


            HomeViajeConductor (
                navController = navController,
                userID
            )

        }
//Agregado 09/12/2023
        composable( "home_viaje_pasajero/{userid}"
        ) {
            val userID= it.arguments?.getString("userid")?:""
            HomeViajePasajero(
                navController = navController,
                userID
            )

        }

        /*--Rutas del conductor, especificamente sobre el viaje----*/


        composable( "registrar_viaje_conductor/{userid}"){
            val userID= it.arguments?.getString("userid")?:""

//Esta es la informacion del viaje de origen
            RegistrarViajeCon (
                navController = navController,
                userID,
            )
        }
        /*Pantalla que manda al mapa para registrar el punto de origen*/
        composable( "registrar_origen_conductor/{userid}/{dia}/{horao}/{horad}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val horad=it.arguments?.getString("horad")?:""

//Registrar origen conductor barra
            RegistrarOrigenConductor (
                navController = navController,
                userID,
                dia,
                horao,
                horad
            )
        }

        composable( "registrar_origen_conductor_return/{userid}/{dia}/{horao}/{horad}/{umarkerdrag}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val horad=it.arguments?.getString("horad")?:""
            val ubiMarkerDrag=it.arguments?.getString("umarkerdrag")?:""
//Este es el mapa para el origen
            RegistrarOrigenConductorReturn (
                navController = navController,
                userID,
                dia,
                horao,
                horad,
                ubiMarkerDrag

            )
        }
        composable( "registrar_destino_conductor_return/{userid}/{dia}/{horao}/{horad}/{umarkerdrag}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val horad=it.arguments?.getString("horad")?:""
            val ubiMarkerDrag=it.arguments?.getString("umarkerdrag")?:""

//Este es el mapa para el origen

            RegistrarDestinoConductorReturn (
                navController = navController,
                userID,
                dia,
                horao,
                horad,
                ubiMarkerDrag
            )
        }

        composable( "registrar_origen_conductor_marker/{userid}/{dia}/{horao}/{horad}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val horad=it.arguments?.getString("horad")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
//Este es el mapa para el origen

            RegistrarOrigenConductorMarker (
                navController = navController,
                userID,
                dia,
                horao,
                horad,
                ubicacionMarker
            )

        }
        composable( "registrar_destino_conductor_marker/{userid}/{dia}/{horao}/{horad}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val horad=it.arguments?.getString("horad")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
//Este es el mapa para el origen
            RegistrarDestinoConductorMarker (
                navController = navController,
                userID,
                dia,
                horao,
                horad,
                ubicacionMarker
            )

        }

        /*Pantalla con el mapa para registrar con la barra el punto de llegada*/
        composable( "registrar_destino_conductor/{userid}/{dia}/{hora}/{horad}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val hora=it.arguments?.getString("hora")?:""
            val horad=it.arguments?.getString("horad")?:""
//Este es el mapa para el origen
            RegistrarDestinoConductor (
                navController = navController,
                userID,
                dia,
                hora,
                horad
            )

        }
        /*Termina conductor viaje - empiezan paradas*/

        /*Pantalla que manda al mapa para registrar una parada en el mapa, con barra de busqueda*/
        composable( "registrar_parada_barra/{userid}/{viajeid}/{nombrep}/{horap}"){
            val userID= it.arguments?.getString("userid")?:""
            val viajeid=it.arguments?.getString("viajeid")?:""
            val nombrep=it.arguments?.getString("nombrep")?:""
            val horap=it.arguments?.getString("horap")?:""

//Registrar origen conductor barra
            RegistrarParadaBarra (
                navController = navController,
                userID,
                viajeid,
                nombrep,
                horap
            )

        }


        //Pantalla con el mapa, seleccionar ubicacion de la parada con el marker

        composable( "registrar_parada_marker/{userid}/{viajeid}/{nombrep}/{horap}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val viajeid=it.arguments?.getString("viajeid")?:""
            val nombrep=it.arguments?.getString("nombrep")?:""
            val horap=it.arguments?.getString("horap")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
//Este es el mapa para el origen

            RegistrarParadaMarker (
                navController = navController,
                userID,
                viajeid,
                nombrep,
                horap,
                ubicacionMarker
            )

        }
//Pantalla con el mapa, seleccionar ubicacion con la barra de busqueda pero regresando del marker

        composable( "registrar_parada_return/{userid}/{viajeid}/{nombrep}/{horap}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val viajeid=it.arguments?.getString("viajeid")?:""
            val nombrep=it.arguments?.getString("nombrep")?:""
            val horap=it.arguments?.getString("horap")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
//Este es el mapa para el origen

            RegistrarParadaBarraReturn (
                navController = navController,
                userID,
                viajeid,
                nombrep,
                horap,
                ubicacionMarker
            )

        }

        //Rutas del pasajero, especificamente sobre el viaje
        //Agregado 10/12/2023
        composable( "registrar_viaje_pasajero/{userid}"){
            val userID= it.arguments?.getString("userid")?:""
//Esta es la informacion del viaje de origen
            RegistrarViajePas (
                navController = navController,
                userID,
            )
        }
        //10/12/2023
        /*Pantalla que manda al mapa para registrar el punto de origen*/
        composable( "registrar_origen_pasajero/{userid}/{dia}/{horao}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""

//Registrar origen conductor barra
            RegistrarOrigenPasajero (
                navController = navController,
                userID,
                dia,
                horao

            )

        }


        //10/12/2023
        composable( "registrar_origen_pasajero_return/{userid}/{dia}/{horao}/{umarkerdrag}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val ubiMarkerDrag=it.arguments?.getString("umarkerdrag")?:""
//Este es el mapa para el origen
            RegistrarOrigenPasajeroReturn (
                navController = navController,
                userID,
                dia,
                horao,
                ubiMarkerDrag

            )

        }

        //Checar  ---
        composable( "registrar_destino_pasajero_return/{userid}/{dia}/{horao}/{umarkerdrag}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val ubiMarkerDrag=it.arguments?.getString("umarkerdrag")?:""

//Este es el mapa para el origen
            RegistrarDestinoPasajeroReturn (
                navController = navController,
                userID,
                dia,
                horao,
                ubiMarkerDrag
            )

        }

        //10/12/2023
        composable( "registrar_origen_pasajero_marker/{userid}/{dia}/{horao}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
            RegistrarOrigenPasajeroMarker (
                navController = navController,
                userID,
                dia,
                horao,
                ubicacionMarker
            )

        }

//10/12/2023
        composable( "registrar_destino_pasajero_marker/{userid}/{dia}/{horao}/{origen}/{umarker}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val horao=it.arguments?.getString("horao")?:""
            val origen=it.arguments?.getString("origen")?:""
            val ubicacionMarker=it.arguments?.getString("umarker")?:""
            RegistrarDestinoPasajeroMarker (
                navController = navController,
                userID,
                dia,
                horao,
                origen,
                ubicacionMarker
            )

        }

        /*Pantalla con el mapa para registrar con la barra el punto de llegada*/
        composable( "registrar_destino_pasajero/{userid}/{dia}/{hora}"){
            val userID= it.arguments?.getString("userid")?:""
            val dia=it.arguments?.getString("dia")?:""
            val hora=it.arguments?.getString("hora")?:""
            RegistrarDestinoPasajero (
                navController = navController,
                userID,
                dia,
                hora

            )

        }

        composable( "ver_itinerario_conductor/{userid}"){
            val userID= it.arguments?.getString("userid")?:""
            ObtenerItinerarioConductor(
                navController = navController,
                userID
            )

        }
//Nueva parada, formulario
        composable( "nueva_parada/{viajeid}/{email}"
        ) {
            val viajeID= it.arguments?.getString("viajeid")?:""
            val userID= it.arguments?.getString("email")?:""
            println("Este es el id del viaje $viajeID")
            AgregarParadas (
                navController = navController,
                viajeID,
                userID
            )

        }

        composable( "ver_viaje/{viajeid}/{email}/{pantalla}"
        ) {
            val viajeID= it.arguments?.getString("viajeid")?:""
            val userID= it.arguments?.getString("email")?:""
val pantalla=it.arguments?.getString("pantalla")?:""
            //Consulta a ConsultasBD
            ObtenerViajeRegistrado(
                navController = navController,
                viajeID,
                userID,
                pantalla
            )
        }

        //Agregad10/12/2023 --***
        composable( "ver_paradas_pasajero/{correo}/{idviaje}"
        ) {

            val correo= it.arguments?.getString("correo")?:""
            val idhorario=it.arguments?.getString("idviaje")?:""

            //Consulta a ConsultasBD
            ObtenerParadasPasajero(navController = navController, correo =
            correo, horarioId =idhorario )
        }

        //13/12/2023
        composable( "ver_itinerario_pasajero/{correo}"
        ) {
            val correo= it.arguments?.getString("correo")?:""


            //Consulta a ConsultasBD
            ObtenerItinerarioPasajero(navController = navController, userId =
            correo)
        }
        //15/12/2023
        composable( "ver_solicitudes_conductor/{correo}"
        ) {
            val correo= it.arguments?.getString("correo")?:""
            //Consulta a ConsultasBD
            ObtenerSolicitudesConductor(navController = navController, userId =
            correo)
        }

        composable( "ver_pasajeros_conductor/{correo}"
        ) {
            val correo= it.arguments?.getString("correo")?:""
            //Consulta a ConsultasBD
            ObtenerPasajerosConductor(navController = navController, userId =
            correo)
        }


    }
}

