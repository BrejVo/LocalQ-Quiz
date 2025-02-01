package com.example.testowyquiz

import android.content.Intent
import android.graphics.Insets.add
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testowyquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Guzik który wywołuje quiz z kujawsko pomorskiego
        binding.btnKujpom.setOnClickListener {
            val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
            startActivity(explicitIntent)
        }

        // Guzik który wywołuje quiz z wielkopolskiego
        binding.btnWlkp.setOnClickListener {
            val explicitIntent = Intent(applicationContext, ThirdActivity::class.java)
            startActivity(explicitIntent)
        }

    }


}