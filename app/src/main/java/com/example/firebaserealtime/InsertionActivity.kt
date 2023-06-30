package com.example.firebaserealtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpPosition: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.name)
        etEmpPosition = findViewById(R.id.position)
        etEmpSalary = findViewById(R.id.salary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("person")

        btnSaveData.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empPosition = etEmpPosition.text.toString()
        val empSalary = etEmpSalary.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empPosition.isEmpty()) {
            etEmpPosition.error = "Please enter position"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter salary"
        }
        val empId = dbRef.push().key!!

        val person = PersonModel(empId, empName, empPosition, empSalary)

        dbRef.child(empId).setValue(person).addOnCompleteListener {
                Toast.makeText(this, "Data has been inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpPosition.text.clear()
                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}