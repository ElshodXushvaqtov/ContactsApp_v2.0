package com.example.myapplication.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.Contact
import com.example.myapplication.databinding.ContactItemBinding

class ContactsAdapter(
    var contacts: MutableList<Contact>,
    var context: Context,
    var activity: Activity,
    val choosedContact: ChoosedContact
) :
    RecyclerView.Adapter<ContactsAdapter.MyHolder>() {

    inner class MyHolder(binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.contactName
        val phone = binding.contactNumber
        val card = binding.contactContainer
        val call = binding.call
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val index = contacts[position]
        holder.name.text = index.name
        holder.phone.text = index.phone
        holder.card.setOnClickListener {
            choosedContact.onClick(index)
        }
        holder.call.setOnClickListener {
            Call(index.phone)
        }
    }

    interface ChoosedContact {

        fun onClick(contact: Contact)

    }

    fun Call(number: String) {

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity, arrayOf(android.Manifest.permission.CALL_PHONE), 1
            )
        } else {
            if (number.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                activity.startActivity(callIntent)
            }
        }

    }

}