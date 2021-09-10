package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentContactsBinding
import com.pashssh.messenger.utils.AppValueEventListener
import com.pashssh.messenger.utils.READ_CONTACTS
import com.pashssh.messenger.utils.checkAndRequestPermission


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater)

        initContacts()


        return binding.root
    }

    data class ContactModel(
        val fullName: String,
        val phoneNumber: String
    )

    private fun initContacts() {
        if (checkAndRequestPermission(requireActivity(), READ_CONTACTS)) {
            var arrayContacts = arrayListOf<ContactModel>()
            val cursor = requireContext().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val fullName =
                        it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone =
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            .replace(Regex("[\\s,-]"), "")
                    arrayContacts.add(ContactModel(fullName, phone))
                }
            }
            cursor?.close()

            REF_DATABASE.child(PHONES_CHILD).addListenerForSingleValueEvent(AppValueEventListener {
                it.children.forEach { snapshot ->
                    arrayContacts.forEach { contact ->
                        if (snapshot.key == contact.phoneNumber) {
                            REF_DATABASE.child(USERS_CHILD).child(AUTH.currentUser!!.uid)
                                .child("contacts").child(snapshot.value.toString())
                                .setValue(snapshot.value.toString())
                        }
                    }
                }
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}