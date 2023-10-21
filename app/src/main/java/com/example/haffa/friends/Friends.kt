package com.example.haffa.friends

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.haffa.R
import com.example.haffa.friends.FriendAdapter

class Friends : Fragment() {

    private lateinit var adapter: FriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        val contactList = view.findViewById<ListView>(R.id.cardView)
        adapter = FriendAdapter(requireContext(), null)
        contactList.adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestContactsPermission()
    }

    private fun requestContactsPermission() {
        val permission = Manifest.permission.READ_CONTACTS
        if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 1)
        } else {
            loadContacts()
        }
    }

    private fun loadContacts() {
        val cursor: Cursor? = requireContext().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            filter,
            null,
            order
        )
        adapter.changeCursor(cursor)
    }

    companion object {
        private val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.STARRED,
            ContactsContract.Contacts.PHOTO_URI
        )

        private val filter = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} IS NOT NULL"
        private val order = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} COLLATE NOCASE"
    }
}