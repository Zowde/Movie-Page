package com.example.moviepage

import InputFilterMinMax
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // load the layout xml

        // show the movie intro text from strings.xml
        val movieDescription: TextView = findViewById(R.id.movieDescription)
        movieDescription.text = getString(R.string.movie_description)
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade)
        movieDescription.startAnimation(fadeAnimation)

        // setup image slider (viewpager2)
        val viewPager: ViewPager2 = findViewById(R.id.movieImageSlider)
        val images = listOf(
            R.drawable.im1,
            R.drawable.im2,
            R.drawable.im3,
            R.drawable.im4,
            R.drawable.im5
        )

        // connect adapter to viewpager
        val adapter1 = ImageSliderAdapter(this, images)
        viewPager.adapter = adapter1
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        viewPager.startAnimation(slideInAnimation)

        val numberOfTickets = findViewById<EditText>(R.id.numberOfTickets)
        numberOfTickets.filters = arrayOf(InputFilterMinMax(1, 10))


        // setup spinner with theatre names
        val theatreSpinner1 = findViewById<Spinner>(R.id.theatreSpinner)
        val theatres = arrayOf(
            getString(R.string.theatre1),
            getString(R.string.theatre2),
            getString(R.string.theatre3),
            getString(R.string.theatre4),
            getString(R.string.theatre5)
        )
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, theatres)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        theatreSpinner1.adapter = adapter2
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        theatreSpinner1.startAnimation(bounceAnimation)

        // when user clicks the "select date" button, open a date picker
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // build the picker
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                selectDateButton.text = selectedDate
            }, year, month, day)

            datePickerDialog.show()
        }

        // apply zoom animation to "get tickets" button
        val getTicketsButton = findViewById<Button>(R.id.getTicketsButton)
        getTicketsButton.setOnClickListener {
            val zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
            getTicketsButton.startAnimation(zoomAnimation)

            val movieTitleText = findViewById<TextView>(R.id.movieTitle)
            val theatreSpinner2 = findViewById<Spinner>(R.id.theatreSpinner)
            val dateButton = findViewById<Button>(R.id.selectDateButton)
            val ticketsInput = findViewById<EditText>(R.id.numberOfTickets)
            val ageGroup = findViewById<RadioGroup>(R.id.ageGroup)

            val movieTitle = movieTitleText.text.toString()
            val theatre = theatreSpinner2.selectedItem.toString()
            val date = dateButton.text.toString()
            val numberOfTickets = ticketsInput.text.toString()

            // get which age group radio is selected
            val selectedAgeId = ageGroup.checkedRadioButtonId
            val selectedAge = findViewById<RadioButton>(selectedAgeId).text.toString()

            // build the full message to show
            val message = getString(
                R.string.ticket_details_message,
                movieTitle, theatre, date, numberOfTickets, selectedAge
            )

            // show the dialog with ticket details
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
