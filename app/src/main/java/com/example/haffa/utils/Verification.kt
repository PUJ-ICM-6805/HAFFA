package com.example.haffa.utils

import android.text.TextUtils
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Utility class for verification and validation operations.
 */
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

    /**
     * Validates the input fields for email and password.
     *
     * @param emailEdit The email EditText field.
     * @param passEdit The password EditText field.
     * @return True if the fields are valid, false otherwise.
     */
    fun validateForm(emailEdit: EditText, passEdit: EditText): Boolean {
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

    /**
     * Checks if the given email address is valid.
     *
     * @param emailStr The email address to check.
     * @return True if the email is valid, false otherwise.
     */
    fun isEmailValid(emailStr: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    /**
     * Checks if the given telephone number is valid.
     *
     * @param telephone The telephone number to check.
     * @return True if the telephone number is valid, false otherwise.
     */
    fun isTelephoneValid(telephone: String?): Boolean {
        val matcher: Matcher = VALID_PHONE_NUMBER_REGEX.matcher(telephone)
        return matcher.find()
    }

    /**
     * Checks if the given password is valid. The password must contain at least one digit,
     * one uppercase letter, one symbol (., !), and be at least 8 characters long.
     *
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    fun isPasswordValid(password: String?): Boolean {
        if (password == null) {
            return false
        }
        val matcher: Matcher = VALID_PASSWD_REGEX.matcher(password)
        return matcher.matches()
    }

    /**
     * Checks if the given date of birth corresponds to an adult. The date should be in "dd/MM/yyyy" format.
     *
     * @param dateOfBirth The date of birth to check.
     * @return True if the date corresponds to an adult, false otherwise.
     */
    fun isAdult(dateOfBirth: String): Boolean {
        if (dateOfBirth.isEmpty()) {
            // If the date string is empty, we cannot calculate the age.
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

    /**
     * Validates various user data fields, including email, telephone, password, and birth date.
     *
     * @param emailEditText The email EditText field.
     * @param telephoneEditText The telephone EditText field.
     * @param passwordEditText The password EditText field.
     * @param birthDateEditText The birth date EditText field.
     * @return True if all fields are valid, false otherwise.
     */
    fun isDataValid(
        emailEditText: EditText,
        telephoneEditText: EditText,
        passwordEditText: EditText,
        birthDateEditText: EditText
    ): Boolean {
        var check = true

        if (!isEmailValid(emailEditText.text.toString())) {
            emailEditText.error = "Invalid email format"
            check = false
        }

        if (!isTelephoneValid(telephoneEditText.text.toString())) {
            telephoneEditText.error = "Invalid phone format"
            check = false
        }

        if (passwordEditText.isEnabled && !isPasswordValid(passwordEditText.text.toString())) {
            passwordEditText.error = "Must contain at least one digit, one uppercase letter, and one symbol (., !). Minimum length: 8"
            check = false
        }

        if (!isAdult(birthDateEditText.text.toString())) {
            birthDateEditText.error = "Only adults are allowed"
            check = false
        }

        // TODO: Add username repetition verification here

        return check
    }
}
