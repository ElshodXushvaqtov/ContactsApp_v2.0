package com.example.myapplication.util

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Contact
import com.example.myapplication.databinding.FragmentContactInfoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactInfoBinding.inflate(
            layoutInflater,
            container,
            false
        )
        val id=arguments?.getInt("id")
        val contacts=appDatabase.getContactDao().getAllContacts()

        var contact:Contact?=null

        for (i in contacts){
            if(i.id==id){
                contact=i
            }
        }

        binding.name.text=contact?.name
        binding.phone.text=contact?.phone

        binding.callInInfo.setOnClickListener {
            Call_Info(binding.phone.text.toString())
        }

        binding.delete.setOnClickListener {
            if(contact!=null){

                appDatabase.getContactDao().deleteContact(contact)
            }
            requireActivity().onBackPressed()
        }
        binding.edit.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt("contact_id",contact!!.id)
            findNavController().navigate(R.id.action_contactInfoFragment_to_editContactFragment)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun Call_Info(phone_number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            if (phone_number.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phone_number")
                activity?.startActivity(callIntent)
            }
        }
    }
}