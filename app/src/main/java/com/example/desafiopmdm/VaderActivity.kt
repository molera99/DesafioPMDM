package com.example.desafiopmdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityMainBinding
import com.example.desafiopmdm.databinding.ActivityVaderBinding

class VaderActivity : AppCompatActivity() {
    lateinit var binding: ActivityVaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}