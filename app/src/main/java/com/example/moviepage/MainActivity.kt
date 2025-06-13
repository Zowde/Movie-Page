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
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Load the UI layout from XML

        // Show movie description text from strings.xml with fade animation
        val movieDescription: TextView = findViewById(R.id.movieDescription)
        movieDescription.text = getString(R.string.movie_description)
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade)
        movieDescription.startAnimation(fadeAnimation)

        // Set up rating SeekBar and show initial rating value (0)
        val seekBar = findViewById<SeekBar>(R.id.ratingSeekBar)
        val ratingText = findViewById<TextView>(R.id.ratingValue)
        ratingText.text = getString(R.string.rating_current, 0)

        // Update rating text when user changes the SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val ratingTextFormatted = getString(R.string.rating_current, progress)
                ratingText.text = ratingTextFormatted
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Notifications switch: show toast when toggled
        val notificationsSwitch = findViewById<Switch>(R.id.notificationsSwitch)
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) {
                getString(R.string.notifications_on)
            } else {
                getString(R.string.notifications_off)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Spinner to select theatre from a list of options
        val theatreSpinner = findViewById<Spinner>(R.id.theatreSpinner)
        val theatres = arrayOf(
            getString(R.string.theatre1),
            getString(R.string.theatre2),
            getString(R.string.theatre3),
            getString(R.string.theatre4),
            getString(R.string.theatre5)
        )
        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, theatres)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        theatreSpinner.adapter = adapter

        // Set up image slider with ViewPager2 and dots indicator
        val viewPager: ViewPager2 = findViewById(R.id.movieImageSlider)
        val images = listOf(
            R.drawable.im1,
            R.drawable.im2,
            R.drawable.im3,
            R.drawable.im4,
            R.drawable.im5
        )
        val adapter1 = ImageSliderAdapter(this, images)
        viewPager.adapter = adapter1

        // Attach dots indicator to viewpager (for page indicators)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.attachTo(viewPager)

        // Start slide-in animation on the image slider
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        viewPager.startAnimation(slideInAnimation)

        // When user clicks "select date" button, open a date picker dialog
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create and show the date picker dialog
            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    // Format selected date and set as button text
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    selectDateButton.text = selectedDate
                }, year, month, day)

            datePickerDialog.show()
        }

        // "Get Tickets" button: zoom animation and show ticket details dialog
        val getTicketsButton = findViewById<Button>(R.id.getTicketsButton)
        getTicketsButton.setOnClickListener {
            val zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
            getTicketsButton.startAnimation(zoomAnimation)

            // Collect info from UI elements
            val movieTitleText = findViewById<TextView>(R.id.movieTitle)
            val theatreSpinner2 = findViewById<Spinner>(R.id.theatreSpinner)
            val dateButton = findViewById<Button>(R.id.selectDateButton)
            val ticketsInput = findViewById<EditText>(R.id.numberOfTickets)
            val ageGroup = findViewById<RadioGroup>(R.id.ageGroup)

            val movieTitle = movieTitleText.text.toString()
            val theatre = theatreSpinner2.selectedItem.toString()
            val date = dateButton.text.toString()
            val numberOfTickets = ticketsInput.text.toString()

            // Check if user selected a date
            if (date == getString(R.string.select_date)) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if number of tickets is entered
            if (numberOfTickets.isEmpty()) {
                Toast.makeText(this, "Please enter the number of tickets", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if age group is selected
            val selectedAgeId = ageGroup.checkedRadioButtonId
            if (selectedAgeId == -1) {
                Toast.makeText(this, "Please select an age group", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val selectedAge = findViewById<RadioButton>(selectedAgeId).text.toString()

            // Build the ticket details message
            val message = getString(
                R.string.ticket_details_message,
                movieTitle, theatre, date, numberOfTickets, selectedAge
            )

            // Inflate the custom dialog layout for ticket details
            val dialogView = layoutInflater.inflate(R.layout.dialog_ticket_details, null)

            val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
            val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
            val confirmButton = dialogView.findViewById<Button>(R.id.confirmBtn)
            val cancelButton = dialogView.findViewById<Button>(R.id.cancelBtn)

            dialogTitle.text = getString(R.string.ticket_details_title)
            dialogMessage.text = message

            // Create and show the dialog
            val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            // Confirm button closes dialog and shows confirmation toast
            confirmButton.setOnClickListener {
                Toast.makeText(this, getString(R.string.order_confirmed), Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
            }

            // Cancel button just closes the dialog
            cancelButton.setOnClickListener {
                customDialog.dismiss()
            }

            customDialog.show()
        }
    }
}
