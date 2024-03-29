package com.example.myapplication.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Contact
import com.example.myapplication.databinding.FragmentAddContactBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddContactFragment : Fragment() {
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
        val binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        binding.addImg.setOnClickListener {
            if (binding.phoneNumber.text!!.isNotEmpty() && binding.name.text!!.isNotEmpty() && binding.surname.text!!.isNotEmpty()) {
                findNavController().navigate(R.id.action_addContactFragment_to_allContactsFragment)
                appDatabase.getContactDao().addConttact(
                    Contact(
                        name = binding.name.text!!.trim()
                            .toString() + "${binding.surname.text!!.trim()}",
                        phone = binding.phoneNumber.text.toString()
                    )
                )
            }
        }






        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}