package com.example.curdfirestore.NivelAplicacion

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth


class LoginViewModel: ViewModel () {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit
    ) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("Logueo", "Logueado!!")
                        home()

                    } else {
                        Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                    }

                }
            } catch (ex: Exception) {
                Log.d("Logueo", "SignInWithEmailandPassword: ${ex.message}")
            }
        }


    fun signOut() = viewModelScope.launch {
        auth.signOut()
    }




    fun resetPassword(
        email: String,
        context: Context,
        navController: NavController,
    )
    {
 try{
    auth.sendPasswordResetEmail(email).addOnCompleteListener() { task ->
        if (task.isSuccessful) {
            Log.d("ResetPassword", "Se ha enviado el correo de restablecimiento!!")
            Toast.makeText(context, "Se ha enviado el correo de restablecimiento", Toast.LENGTH_SHORT).show()
            navController.navigate(route = "login")
        } else {
            Toast.makeText(context, "Correo electrónico no registrado", Toast.LENGTH_SHORT).show()
        }
    }
}
       catch (e:Exception){

           Log.d("ResetPassword","Error al restablcer la contraseña ${e.message}")
       }
    }
}