package com.probo.proboandroidapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AfterSubmit : AppCompatActivity() {
    lateinit var rcemail: TextView
    lateinit var rcdob: TextView
    lateinit var rcpass: TextView
    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_submit)

        //Image
        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.buttonLoadPicture)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        //Text
        rcemail = findViewById(R.id.shw_email)
        rcdob = findViewById(R.id.shw_dob)
        rcpass = findViewById(R.id.shw_pass)
        val intent = intent
        val email = intent.getStringExtra("email")
        val pass = intent.getStringExtra("password")
        val dob = intent.getStringExtra("dob")
        rcemail.text = email
        rcpass.text = pass
        rcdob.text = dob
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
}