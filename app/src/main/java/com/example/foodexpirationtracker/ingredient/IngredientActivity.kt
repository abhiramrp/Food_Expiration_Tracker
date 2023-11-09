package com.example.foodexpirationtracker.ingredient

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.foodexpirationtracker.DATA_INGREDIENTS
import com.example.foodexpirationtracker.R
import com.example.foodexpirationtracker.databinding.ActivityIngredientBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class IngredientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientBinding

    private var userId: String? = null

    private lateinit var ingredientTitle: EditText
    private lateinit var ingredientDate : EditText
    private lateinit var ingredientNotes : EditText

    private lateinit var firebaseDb : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(PARAM_USER_ID)) {
            userId = intent.getStringExtra(PARAM_USER_ID)
        } else {
            Toast.makeText(this, "Error adding ingredient", Toast.LENGTH_SHORT).show()
            finish()
        }

        firebaseDb = Firebase.firestore

        ingredientTitle = binding.editTitle
        ingredientDate = binding.editDate
        ingredientNotes = binding.editNotes

    }

    fun setDatePicker(v: View) {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            // on below line we are passing context.
            this,
            { view, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our edit text.
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                ingredientDate.setText(dat)
            },
            // on below line we are passing year, month
            // and day for the selected date in our date picker.
            year,
            month,
            day
        )
        // at last we are calling show
        // to display our date picker dialog.
        datePickerDialog.show()



    }

    private fun generateKeywords(name: String): List<String> {
        val keywords = mutableListOf<String>()
        for (i in 0 until name.length) {
            for (j in (i+1)..name.length) {
                keywords.add(name.slice(i until j))
            }
        }
        return keywords
    }

    fun postIngredient(v: View) {
        val title = ingredientTitle.text.toString()
        val ingredientID = firebaseDb.collection(DATA_INGREDIENTS).document()
        val expiryDate = ingredientDate.text.toString()
        val notes = ingredientNotes.text.toString()

        val ingredient = Ingredient(ingredientID.id, title, userId, generateKeywords(title), expiryDate, notes)

        ingredientID.set(ingredient)
            .addOnCompleteListener{ finish() }
            .addOnFailureListener{ e ->
                e.printStackTrace()
                Toast.makeText(this, "Failed to add ingredient", Toast.LENGTH_SHORT).show()
            }



    }

    companion object {

        val PARAM_USER_ID = "UserId"
        fun newIntent(context: Context, userId: String?): Intent {
            val intent = Intent(context, IngredientActivity::class.java)
            intent.putExtra(PARAM_USER_ID, userId)
            return intent
        }
    }

}