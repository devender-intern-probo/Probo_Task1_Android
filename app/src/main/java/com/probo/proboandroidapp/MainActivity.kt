package com.probo.proboandroidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword:EditText
    lateinit var etRepeatPassword:EditText
    val MIN_PASSWORD_LENGTH = 8;
    var yyyy = Calendar.getInstance().get(Calendar.YEAR)
    var mm = Calendar.getInstance().get(Calendar.MONTH)
    var dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    lateinit var btsignup : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewInitializations()
    }

    fun viewInitializations() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etRepeatPassword = findViewById(R.id.et_repeat_password)
        btsignup = findViewById(R.id.bt_register)

        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {

        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }
        if (etPassword.text.toString().equals("")) {
            etPassword.setError("Please Enter Password")
            return false
        }
        if (etRepeatPassword.text.toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password")
            return false
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please Enter Valid Email")
            return false
        }

        // checking if DOB is 18+ or not

        val datePicker = findViewById<DatePicker>(R.id.datePicker1)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { _, year, month, day ->
            val month = month + 1
            dd = day;
            mm = month;
            yyyy = year;
        }
        if(!isValidDob()) {
            return false;
        }
        // checking minimum password Length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }

        //checking if password is in correct format
        if(!isValidPasswordFormat(etPassword.text.toString())){
            etPassword.setError("Enter password with correct format!!")
            return false;
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etRepeatPassword.text.toString())) {
            etRepeatPassword.setError("Password does not match")
            return false
        }
        return true
    }

    fun isValidDob(): Boolean {
        val userAge: Calendar = GregorianCalendar(yyyy, mm, dd)
        val minAdultAge: Calendar = GregorianCalendar()
        minAdultAge.add(Calendar.YEAR, -18)
        if (minAdultAge.before(userAge)) {
            val msg = "User should be 18+"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    fun isEmailValid(email: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return EMAIL_REGEX.toRegex().matches(email);
    }

    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(password).matches()
    }

    // Hook Click Event

    fun afterSubmit (view: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
            // Here you can call you API

            btsignup.setOnClickListener { v ->
                // get the value which input by user in EditText and convert it to string
                val em: String = etEmail.getText().toString()
                val pass: String = etPassword.getText().toString()
                val dob= "DOB: $dd/$mm/$yyyy"
                // Create the Intent object of this class Context() to Second_activity class
                val intent = Intent(applicationContext, AfterSubmit::class.java)
                // now by putExtra method put the value in key, value pair key is
                // message_key by this key we will receive the value, and put the string
                intent.putExtra("email", em);
                intent.putExtra("password", pass);
                intent.putExtra("dob", dob);
                // start the Intent
                startActivity(intent)
            }

        }
    }
}