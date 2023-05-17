package com.example.myapplication.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Contact
import com.example.myapplication.databinding.FragmentEditContactBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditContactFragment : Fragment() {
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
        val binding = FragmentEditContactBinding.inflate(
            layoutInflater,
            container,
            false
        )
        val id = arguments?.getInt("contact_id")
        val contacts = appDatabase.getContactDao().getAllContacts()
        var contact: Contact? = null
        for (i in contacts) {
            if (i.id == id) {
                contact = i
            }
        }

        binding.name.setText(contact?.name)
        binding.phoneNumber.setText(contact?.phone)

        binding.saveChangesImg.setOnClickListener {
            if (contact != null) {
                appDatabase.getContactDao().updateContact(contact)
            }
            requireActivity().onBackPressed()
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}