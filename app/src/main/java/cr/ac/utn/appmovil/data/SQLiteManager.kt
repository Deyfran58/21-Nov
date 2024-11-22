package cr.ac.utn.appmovil.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import cr.ac.utn.appmovil.identities.Contact
import cr.ac.utn.appmovil.interfaces.IDBManager

class SQLiteManager(context: Context) : IDBManager {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    override fun add(contact: Contact) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_ID, contact.Id)
            put(DatabaseHelper.COL_NAME, contact.Name)
            put(DatabaseHelper.COL_PHONE, contact.Phone.toString())
            put(DatabaseHelper.COL_EMAIL, contact.Email)
            put(DatabaseHelper.COL_LASTNAME, contact.LastName)
            put(DatabaseHelper.COL_ADDRESS, contact.Address)
            put(DatabaseHelper.COL_COUNTRY, contact.Country)
        }
        db.insert(DatabaseHelper.TABLE_CONTACT, null, values)
        db.close()
    }

    override fun update(contact: Contact) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_NAME, contact.Name)
            put(DatabaseHelper.COL_PHONE, contact.Phone.toString())
            put(DatabaseHelper.COL_EMAIL, contact.Email)
            put(DatabaseHelper.COL_LASTNAME, contact.LastName)
            put(DatabaseHelper.COL_ADDRESS, contact.Address)
            put(DatabaseHelper.COL_COUNTRY, contact.Country)
        }
        db.update(DatabaseHelper.TABLE_CONTACT, values, "${DatabaseHelper.COL_ID} = ?", arrayOf(contact.Id))
        db.close()
    }

    override fun remove(id: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_CONTACT, "${DatabaseHelper.COL_ID} = ?", arrayOf(id))
        db.close()
    }

    override fun getAll(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_CONTACT,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val contact = Contact(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)),
                    lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LASTNAME)),
                    phone = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PHONE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL)),
                    address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ADDRESS)),
                    country = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COUNTRY)),
                    photo = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Placeholder para Photo
                )
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contactList
    }

    override fun getById(id: String): Contact? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_CONTACT,
            null,
            "${DatabaseHelper.COL_ID} = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val contact = Contact(
                id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)),
                lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LASTNAME)),
                phone = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PHONE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL)),
                address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ADDRESS)),
                country = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COUNTRY)),
                photo = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Placeholder para Photo
            )
            cursor.close()
            db.close()
            contact
        } else {
            cursor.close()
            db.close()
            null
        }
    }
}
