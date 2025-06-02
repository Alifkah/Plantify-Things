package com.example.todoist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivityy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ambil username dari Intent
        val username = intent.getStringExtra("USERNAME")
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        if (!username.isNullOrEmpty()) {
            val formattedUsername = username.replaceFirstChar { it.uppercase() }
            tvUsername.text = "$formattedUsername!"
        } else {
            tvUsername.text = "User!"
        }

        // Tombol untuk tambah task
        val fabAddTask = findViewById<FloatingActionButton>(R.id.fabAddTask)
        fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        // Navigasi bawah
        val ivHome = findViewById<ImageView>(R.id.ivHome)
        val ivCalendar = findViewById<ImageView>(R.id.ivCalendar)
        val ivFocus = findViewById<ImageView>(R.id.ivFocus)
        val ivCategory = findViewById<ImageView>(R.id.ivCategory)
        val ivSettings = findViewById<ImageView>(R.id.ivSettings)

//        ivHome.setOnClickListener {
//            // Sudah di MainActivity, tidak perlu pindah
//        }
//
//        ivCalendar.setOnClickListener {
//            startActivity(Intent(this, CalendarActivity::class.java))
//        }
//
//        ivFocus.setOnClickListener {
//            startActivity(Intent(this, FocusModeActivity::class.java))
//        }
//
//        ivCategory.setOnClickListener {
//            startActivity(Intent(this, ChooseCategoryActivity::class.java))
//        }
//
//        ivSettings.setOnClickListener {
//            startActivity(Intent(this, SettingsActivity::class.java))
//        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        dialog.show()
    }
}
