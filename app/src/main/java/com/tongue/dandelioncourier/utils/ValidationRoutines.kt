package com.tongue.dandelioncourier.utils

import androidx.core.util.PatternsCompat

class ValidationRoutines {

    companion object{
        var emailValidator:(content:String) -> Pair<Boolean,String> = {
            var errorMessage = ""
            var valid = true
            if (it.isBlank()) {
                errorMessage = "Campo vacío"
                valid = false
            }
            if (!PatternsCompat.EMAIL_ADDRESS.matcher(it).matches()){
                errorMessage = "Formato incorrecto"
                valid = false
            }
            Pair(valid,errorMessage)
        }

        var passwordValidator:(content: String) -> Pair<Boolean,String> = {
            var errorMessage = ""
            var valid = true
            if (it.isBlank()){
                errorMessage = "Campo vacío"
                valid = false
            }
            Pair(valid,errorMessage)
        }

        var validateNotEmptyString:(content: String) -> Pair<Boolean,String> = {
            var errorMessage = ""
            var valid = true
            if (it.isNullOrBlank()){
                errorMessage = "Campo vacío"
                valid = false
            }
            Pair(valid,errorMessage)
        }

    }

}