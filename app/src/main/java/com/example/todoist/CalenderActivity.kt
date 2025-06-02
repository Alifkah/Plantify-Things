//package com.example.yourapp
//
//import android.graphics.Color
//import android.graphics.Typeface
//import android.os.Bundle
//import android.view.Gravity
//import android.widget.GridLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import java.text.SimpleDateFormat
//import java.util.*
//
//class CalendarActivity : AppCompatActivity() {
//
//    private lateinit var tvDateToday: TextView
//    private lateinit var tvMonth: TextView
//    private lateinit var fabAddTask: FloatingActionButton
//    private lateinit var gridCalendar: GridLayout
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.calendar_activity)
//
//        tvDateToday = findViewById(R.id.tvDateToday)
//        tvMonth = findViewById(R.id.tvMonth)
//        fabAddTask = findViewById(R.id.fabAddTask)
//        gridCalendar = findViewById(R.id.gridCalendar)
//
//        setTodayDate()
//        setMonthHeader()
//        populateCalendar()
//
//        fabAddTask.setOnClickListener {
//            Toast.makeText(this, "Add Task clicked", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun setTodayDate() {
//        val dateFormat = SimpleDateFormat("MMM dd, yyyy - 'Today'", Locale.getDefault())
//        val today = Calendar.getInstance().time
//        tvDateToday.text = dateFormat.format(today)
//    }
//
//    private fun setMonthHeader() {
//        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
//        val currentDate = Calendar.getInstance().time
//        tvMonth.text = monthFormat.format(currentDate)
//    }
//
//    private fun populateCalendar() {
//        gridCalendar.removeAllViews()
//
//        val calendar = Calendar.getInstance()
//        val currentMonth = calendar.get(Calendar.MONTH)
//        val currentYear = calendar.get(Calendar.YEAR)
//
//        // Atur kalender ke hari pertama di bulan ini
//        calendar.set(Calendar.DAY_OF_MONTH, 1)
//        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 // Mulai dari 0
//
//        // Dapatkan jumlah hari di bulan ini
//        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//
//        // Total cell (termasuk offset dari awal minggu)
//        val totalCells = firstDayOfWeek + daysInMonth
//
//        for (i in 0 until totalCells) {
//            val tv = TextView(this)
//            val layoutParams = GridLayout.LayoutParams().apply {
//                width = 0
//                height = GridLayout.LayoutParams.WRAP_CONTENT
//                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
//                setMargins(8, 8, 8, 8)
//            }
//
//            tv.layoutParams = layoutParams
//            tv.gravity = Gravity.CENTER
//            tv.setPadding(16, 16, 16, 16)
//
//            if (i >= firstDayOfWeek) {
//                val day = i - firstDayOfWeek + 1
//                tv.text = day.toString()
//                tv.setTextColor(Color.BLACK)
//                tv.setBackgroundResource(R.drawable.bg_date_circle)
//                tv.setTypeface(null, Typeface.BOLD)
//
//                tv.setOnClickListener {
//                    Toast.makeText(this, "Tanggal $day diklik", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                tv.text = "" // Kosongkan offset
//            }
//
//            gridCalendar.addView(tv)
//        }
//    }
//}
