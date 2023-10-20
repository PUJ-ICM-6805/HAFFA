package com.example.haffa.utils

import android.content.Intent
import android.text.TextUtils
import android.widget.EditText
import com.example.haffa.navigation.BottomNavigation
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

class Verification {
    private val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    /**
     * This regex pattern can match phone numbers in the following formats:
     * (123) 456-7890
     * 123-456-7890
     * 123.456.7890
     * 1234567890
     */
    private val VALID_PHONE_NUMBER_REGEX =
        Pattern.compile("^\\(?(\\d{3})\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}\$")

    private val VALID_PASSWD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[.!,]).{8,}\$")

     fun validateForm(emailEdit: EditText, passEdit:EditText): Boolean {
        var valid = true
        val email = emailEdit.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailEdit.error = "Required"
            valid = false
        } else {
            emailEdit.error = null
        }
        val password = passEdit.text.toString()
        if (TextUtils.isEmpty(password)) {
            passEdit.error = "Required"
            valid = false
        } else {
            passEdit.error = null
        }
        return valid
    }

     fun isEmailValid(emailStr: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    fun isTelephoneValid(telephone:String?): Boolean {
        val matcher: Matcher = VALID_PHONE_NUMBER_REGEX.matcher(telephone)
        return matcher.find()
    }
    fun isPasswordValid(password: String?): Boolean {
        if (password == null) {
            return false
        }
        val pattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[.!,]).{8,}\$"
        val matcher: Matcher = Pattern.compile(pattern).matcher(password)
        return matcher.matches()
    }

    fun isAdult(dateOfBirth: String): Boolean {
        if (dateOfBirth.isEmpty()) {
            // Si la cadena de fecha está vacía, no podemos calcular la edad.
            return false
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        val currentDate = Date()

        val dateOfBirthDate = dateFormat.parse(dateOfBirth)

        val calendarCurrent = Calendar.getInstance()
        val calendarDateOfBirth = Calendar.getInstance()
        calendarCurrent.time = currentDate
        calendarDateOfBirth.time = dateOfBirthDate

        val age = calendarCurrent.get(Calendar.YEAR) - calendarDateOfBirth.get(Calendar.YEAR)

        return age >= 18
    }

    fun isDataValid(
        emailEditText: EditText,
        telephoneEditText: EditText,
        passwordEditText: EditText,
        birthDateEditText: EditText
    ): Boolean {
        var check = true

        if (!isEmailValid(emailEditText.text.toString())) {
            emailEditText.error = "Formato de correo electrónico inválido"
            check = false
        }

        if (!isTelephoneValid(telephoneEditText.text.toString())) {
            telephoneEditText.error = "Formato teléfono inválido"
            check = false
        }

        if (passwordEditText.isEnabled && !isPasswordValid(passwordEditText.text.toString())) {
            passwordEditText.error = "Recuerda colocar un número, una mayúscula y un símbolo (, . !)"
            check = false
        }

        if (!isAdult(birthDateEditText.text.toString())) {
            birthDateEditText.error = "Solamente se admiten mayores de edad"
            check = false
        }

        // Agregar verificación de username repetido

        return check
    }

}