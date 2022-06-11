package com.example.androidfirebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_save: Button = findViewById<Button>(R.id.btn_save)
        
        btn_save.setOnClickListener {
            val firstName: String = findViewById<EditText>(R.id.firstname).text.toString();
            val lastName: String = findViewById<EditText>(R.id.lastname).text.toString();
            saveFireStore(firstName, lastName)
            readFireStoreData()
        }


    }

    private fun saveFireStore(firstName: String, lastName: String) {

        val user:MutableMap<String, Any> = HashMap()
        user["firstName"] = firstName
        user["lastName"] = lastName

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "record added successfully",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "record failed to be added", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readFireStoreData() {
        db.collection("users")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result) {
                        result.append(document.data.getValue("firstName")).append(" ")
                            .append(document.data.getValue("lastName")).append("\n\n")
                    }
                    findViewById<TextView>(R.id.result_from_firebase).text = result
                }
            }
    }
}