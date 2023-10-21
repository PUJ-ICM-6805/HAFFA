package com.example.haffa.friends

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.haffa.R
import com.example.haffa.databinding.CardFriendBinding

class FriendAdapter(context: Context, cursor: Cursor?) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup?): View {
        val binding = CardFriendBinding.inflate(LayoutInflater.from(context), parent, false)
        return binding.root
    }

    @SuppressLint("Range", "Recycle")
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val binding = CardFriendBinding.bind(view!!)

        // Nombre del contacto
        val contactNameText: TextView = binding.name
        contactNameText.text = cursor!!.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

        // Teléfono principal del contacto
        val contactId = cursor.getInt(0)
        val phoneCursor = context!!.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(contactId.toString()), null)
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            val phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactPhoneText: TextView = binding.phone
            contactPhoneText.text = phoneNumber
            phoneCursor.close()
        }

        // Avatar del contacto si lo tiene, si no, avatar por defecto
        val contactPhotoImageView: ImageView = binding.cardImg
        val photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
        if (photoUri != null) {
            contactPhotoImageView.setImageURI(Uri.parse(photoUri))
            Glide.with(context).load(photoUri).transform(CircleCrop()).into(contactPhotoImageView)
        } else {
            // Si no hay foto de avatar, podemos mostrar una imagen predeterminada
            contactPhotoImageView.setImageResource(R.drawable.img_default_avatar)
        }

    }
}