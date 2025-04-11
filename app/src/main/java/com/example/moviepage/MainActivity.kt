package com.example.moviepage
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Ensure your layout file is correct
      ///////////here we access the part of the introduction of the movie and insert a short description
        val movieDescription: TextView = findViewById(R.id.movieDescription)
        movieDescription.text = getString(R.string.movie_description)

        //////////////// initialize the viewPager2 for the image slider
        val viewPager: ViewPager2 = findViewById(R.id.movieImageSlider)
        val images = listOf(
            R.drawable.im1, // Replace with actual image names in your drawable folder
            R.drawable.im2,
            R.drawable.im3,
            R.drawable.im4,
            R.drawable.im5
        )

        // set the adapter for the ViewPager2
        val adapter1 = ImageSliderAdapter(this, images)
        viewPager.adapter = adapter1
        ///////////////////////////////////////////
        //////////// adding theatres to the spinner//////////////////////
        val theatreSpinner1 = findViewById<Spinner>(R.id.theatreSpinner)
        val theatres = arrayOf(
            getString(R.string.theatre1),
            getString(R.string.theatre2),
            getString(R.string.theatre3),
            getString(R.string.theatre4),
            getString(R.string.theatre5)
        )
        val adapter2= ArrayAdapter(this, android.R.layout.simple_spinner_item, theatres)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        theatreSpinner1.adapter = adapter2
        ////////////////////////////////////////////////////

        ////////////////handel the selected date button/////////////////////
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                selectDateButton.text = selectedDate
            }, year, month, day)

            datePickerDialog.show()
        }
       ////////////////////////////////////////////////////////////

        //////////////////
        val getTicketsButton = findViewById<Button>(R.id.getTicketsButton)
        val movieTitleText = findViewById<TextView>(R.id.movieTitle)
        val theatreSpinner2 = findViewById<Spinner>(R.id.theatreSpinner)
        val dateButton = findViewById<Button>(R.id.selectDateButton)
        val ticketsInput = findViewById<EditText>(R.id.numberOfTickets)
        val ageGroup = findViewById<RadioGroup>(R.id.ageGroup)

        getTicketsButton.setOnClickListener {
            val movieTitle = movieTitleText.text.toString()
            val theatre = theatreSpinner2.selectedItem.toString()
            val date = dateButton.text.toString()
            val numberOfTickets = ticketsInput.text.toString()

            val selectedAgeId = ageGroup.checkedRadioButtonId
            val selectedAge = findViewById<RadioButton>(selectedAgeId).text.toString()

            val message = getString(R.string.ticket_details_message,
                movieTitle, theatre, date, numberOfTickets, selectedAge)


            AlertDialog.Builder(this)
                .setTitle(getString(R.string.ticket_details_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
                    Toast.makeText(this, getString(R.string.order_confirmed), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}