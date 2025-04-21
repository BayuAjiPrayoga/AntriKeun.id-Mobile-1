package com.wanderlust.antrikeun

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var selectedDateTime: Calendar
    private lateinit var tvSelectedDateTime: TextView
    private var isDateTimePicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDateTime = findViewById<Button>(R.id.btnSelectDateTime)
        val btnBook = findViewById<Button>(R.id.btnBook)
        tvSelectedDateTime = findViewById(R.id.tvSelectedDateTime)

        selectedDateTime = Calendar.getInstance()

        btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }

        btnBook.setOnClickListener {
            if (!isDateTimePicked) {
                Toast.makeText(this, "Silakan pilih waktu terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val formattedTime = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(selectedDateTime.time)
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Booking")
                .setMessage("Pesan antrian untuk tanggal $formattedTime?")
                .setPositiveButton("Ya") { _, _ ->
                    Toast.makeText(this, "Antrian berhasil dipesan!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun showDateTimePicker() {
        val now = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            TimePickerDialog(this, { _, hour, minute ->
                selectedDateTime.set(year, month, dayOfMonth, hour, minute)
                val formatted = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                tvSelectedDateTime.text = "Waktu dipilih: ${formatted.format(selectedDateTime.time)}"
                isDateTimePicked = true
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show()
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show()
    }
}
