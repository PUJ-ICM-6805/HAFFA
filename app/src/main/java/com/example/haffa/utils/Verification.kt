package com.example.haffa.utils

import android.content.Intent
import android.text.TextUtils
import android.widget.EditText
import com.example.haffa.navigation.BottomNavigation
import com.google.firebase.auth.FirebaseUser
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
}