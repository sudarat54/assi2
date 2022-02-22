package com.example.assi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_regiter.*

class Register : AppCompatActivity() {
    var auth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regiter)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null){
            val it = Intent(this, Member::class.java)
            startActivity(it)
            finish()
        }

        cmdRegister.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val pass = edtPassword.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(this,"กรุณากรอก Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                Toast.makeText(this,"กรุณากรอก Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {task ->
                if (!task.isSuccessful){
                    if (pass.length<8){
                        edtPassword.error = "กรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร"
                    }else{
                        Toast.makeText(this, "Login ล้มเหลว เนื่องจาก : " + task.exception!!.message,Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show()
                    val it = Intent(this, Member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }

        cmdLogin.setOnClickListener {
            val it = Intent(this, cmdLogin::class.java)
            startActivity(it)
        }

    }
}
