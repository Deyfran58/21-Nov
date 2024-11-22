package cr.ac.utn.appmovil.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ContactDatabase"
        private const val DATABASE_VERSION = 1

        const val TABLE_CONTACT = "Contact"
        const val COL_ID = "Id"
        const val COL_NAME = "Name"
        const val COL_LASTNAME = "LastName"
        const val COL_PHONE = "Phone"
        const val COL_EMAIL = "Email"
        const val COL_ADDRESS = "Address"
        const val COL_COUNTRY = "Country"
        // No incluimos Photo porque SQLite no es ideal para imágenes grandes
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CONTACT (
                $COL_ID TEXT PRIMARY KEY,
                $COL_NAME TEXT,
                $COL_LASTNAME TEXT,
                $COL_PHONE INTEGER,
                $COL_EMAIL TEXT,
                $COL_ADDRESS TEXT,
                $COL_COUNTRY TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACT")
        onCreate(db)
    }
}
