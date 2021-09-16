package com.pashssh.messenger.ui.fragments

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.pashssh.messenger.*
import com.pashssh.messenger.databinding.FragmentContactsBinding
import com.pashssh.messenger.utils.AppValueEventListener
import com.pashssh.messenger.utils.READ_CONTACTS
import com.pashssh.messenger.utils.checkAndRequestPermission
import org.w3c.dom.Text


class ContactsFragment : Fragment() {

    data class ContactModel(
        val fullName: String = "",
        val phone: String = "",
        val uid: String = ""
    )

    private var _binding: FragmentContactsBinding? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<ContactModel, ContactsHolder>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mEventListener: AppValueEventListener
    private var mapListener = hashMapOf<DatabaseReference, AppValueEventListener>()

    private val binding get() = _binding!!

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_contact_fullname)
        val status: TextView = view.findViewById(R.id.item_contact_status)
        val photo: ImageView = view.findViewById(R.id.item_contact_photo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater)
        initContacts()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.contactsRecyclerView
        mRefContacts = REF_DATABASE.child(CONTACTS_CHILD).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<ContactModel>()
            .setQuery(mRefContacts, ContactModel::class.java).build()
        mAdapter = object : FirebaseRecyclerAdapter<ContactModel, ContactsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                return ContactsHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_contact, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: ContactModel
            ) {
                mRefUsers = REF_DATABASE.child(USERS_CHILD).child(model.uid)

                mEventListener = AppValueEventListener {
                    val modelIt = it.getValue(UserEntity::class.java)
                    holder.name.text = modelIt?.username.toString()
                    holder.status.text = modelIt?.state.toString()
                    holder.itemView.setOnClickListener {
                        if (modelIt != null) {
                            requireView().findNavController().navigate(
                                ContactsFragmentDirections.actionContactsToSingleChat(
                                    modelIt.uid
                                )
                            )
                        }
                    }
                }

                mRefUsers.addValueEventListener(mEventListener)
                mapListener[mRefUsers] = mEventListener

            }
        }

        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        mapListener.forEach {
            it.key.removeEventListener(it.value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initContacts() {
        if (checkAndRequestPermission(requireActivity(), READ_CONTACTS)) {
            val arrayContacts = arrayListOf<ContactModel>()
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
            Log.d("MYTAG", arrayContacts.toString())
            cursor?.close()

            REF_DATABASE.child(PHONES_CHILD).addListenerForSingleValueEvent(AppValueEventListener {
                it.children.forEach { snapshot ->
                    arrayContacts.forEach { contact ->
                        if (snapshot.key == contact.phone &&
                            snapshot.key != AUTH.currentUser!!.phoneNumber
                        ) {
                            REF_DATABASE.child(CONTACTS_CHILD).child(CURRENT_UID)
                                .child(snapshot.value.toString()).setValue(
                                    ContactModel(
                                        contact.fullName,
                                        contact.phone,
                                        snapshot.value.toString()

                                    )
                                )

                        }
                    }
                }
            })
        }
    }
}