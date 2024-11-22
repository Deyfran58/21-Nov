package cr.ac.utn.appmovil.model

import android.content.Context
import cr.ac.utn.appmovil.contactmanager.R
import cr.ac.utn.appmovil.identities.Contact
import cr.ac.utn.appmovil.interfaces.IDBManager
import cr.ac.utn.appmovil.data.SQLiteManager

class ContactModel(private val context: Context) {
    private var dbManager: IDBManager = SQLiteManager(context)

    fun addContact(contact: Contact) {
        dbManager.add(contact)
    }

    fun updateContact(contact: Contact) {
        dbManager.update(contact)
    }

    fun removeContact(id: String) {
        val result = dbManager.getById(id)
        if (result == null)
            throw Exception(context.getString(R.string.msgNotFoundContact))

        dbManager.remove(id)
    }

    fun getContacts() = dbManager.getAll()

    fun getContact(id: String): Contact {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = context.getString(R.string.msgNotFoundContact)
            throw Exception(message)
        }
        return result
    }

    fun getContactNames(): List<String> {
        val names = mutableListOf<String>()
        val contacts = dbManager.getAll()
        contacts.forEach { i -> names.add(i.FullName) }
        return names.toList()
    }
}
