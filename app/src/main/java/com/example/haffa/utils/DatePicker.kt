package com.example.haffa.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.Calendar

// This class, DatePicker, extends DialogFragment and implements DatePickerDialog.OnDateSetListener.
class DatePicker(private val dateEditText: EditText) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    // This function creates a new DatePickerDialog with the current date as the default date.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    // This function is called when the user sets a date using the date picker dialog.
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        // Format the selected date and set it in the associated EditText.
        val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
        dateEditText.setText(selectedDate)
    }
}