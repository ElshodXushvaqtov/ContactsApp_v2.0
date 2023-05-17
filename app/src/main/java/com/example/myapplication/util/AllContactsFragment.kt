package com.example.myapplication.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ContactsAdapter
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.Contact
import com.example.myapplication.databinding.FragmentAllContactsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllContactsFragment : Fragment() {
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
        val binding = FragmentAllContactsBinding.inflate(layoutInflater, container, false)
        val contacts = appDatabase.getContactDao().getAllContacts()

        if (contacts.isNotEmpty()) {
            binding.noContactsImg.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            val adapter = ContactsAdapter(
                contacts,
                requireContext(),
                requireActivity(),
                object : ContactsAdapter.ChoosedContact {
                    override fun onClick(contact: Contact) {
                        val bundle = Bundle()
                        bundle.putInt("id", contact.id)
                        findNavController().navigate(
                            R.id.action_allContactsFragment_to_contactInfoFragment,
                            bundle
                        )
                    }
                })
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.addContactBtn.setOnClickListener {
            findNavController().navigate(R.id.action_allContactsFragment_to_addContactFragment)
        }
        binding.toolbar.menu
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}