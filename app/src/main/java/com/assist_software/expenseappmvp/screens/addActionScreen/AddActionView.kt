package com.assist_software.expenseappmvp.screens.addActionScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.data.database.entities.Income
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.CategoryAdapter
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.models.CategoryItem
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.models.DateTime
import com.assist_software.expenseappmvp.screens.mainScreen.HomeActivity
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.assist_software.expenseappmvp.utils.TimeUtils
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_add_action.view.*
import kotlinx.android.synthetic.main.transaction_toolbar.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddActionView(var activity: AddActionActivity) : DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    val layout: View = View.inflate(activity, R.layout.activity_add_action, null)
    var dateTime = DateTime()

    private var defaultDetailsText: String = ""
    private var defaultDetailsImage: ByteArray = ByteArray(0)


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateTime.savedDay = dayOfMonth
        dateTime.savedMonth = month + 1
        dateTime.savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(activity, this, dateTime.hour, dateTime.minute, true).show()

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTime.savedHour = hourOfDay
        dateTime.savedMinute = minute

        layout.date_EditText.setText(dateTime.savedYear.toString() + "-" +
                dateTime.savedMonth.toString() + "-" +
                dateTime.savedDay.toString() + " " +
                dateTime.savedHour.toString() + ":" +
                dateTime.savedMinute.toString()
        )

    }

    fun showHomeScreen() {
        HomeActivity.start(activity)
        activity.finish()
    }

    fun initToolbar() {
        val toolbar = layout.toolbar as Toolbar
        activity.run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        toolbar.run {
            title = activity.getString(R.string.add_action)
            setTitleTextAppearance(activity, R.style.HomeToolbarFont)
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        dateTime.day = cal.get(Calendar.DAY_OF_MONTH)
        dateTime.month = cal.get(Calendar.MONTH)
        dateTime.year = cal.get(Calendar.YEAR)
        dateTime.hour = cal.get(Calendar.HOUR)
        dateTime.minute = cal.get(Calendar.MINUTE)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getUTCTimestamp(): Long {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
        val dateString = dateTime.savedDay.toString() + "-" +
                dateTime.savedMonth.toString() + "-" +
                dateTime.savedYear.toString() + " " +
                dateTime.savedHour.toString() + ":" +
                dateTime.savedMinute.toString()
        val date = sdf.parse(dateString)
        var calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.timeInMillis
    }

    fun initDatePicker() {
        getDateTimeCalendar()
        val datePickerDialog =
            DatePickerDialog(activity, this, dateTime.year, dateTime.month, dateTime.day)
        datePickerDialog.show()
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    }

    private fun loadImageInViewFromLocalStorage(data: Intent?) {
        layout.details_ImageView.run {
            setBackgroundResource(android.R.color.transparent)
            setImageURI(data?.data)
        }
    }

    private fun loadImageInViewFromCamera(data: Intent?) {
        val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
        layout.details_ImageView.run {
            setBackgroundResource(android.R.color.transparent)
            setImageBitmap(bitmap)
        }
    }

    fun initCategoryGrid(listener: PublishSubject<CategoryItem>, categoryData: List<CategoryItem>) {
        val numberOfColumns = 4
        layout.grid_recycler.run {
            layoutManager = GridLayoutManager(activity, numberOfColumns)
            adapter = CategoryAdapter(activity, categoryData, listener)
            setHasFixedSize(true)
        }
    }

    fun openCamera(): Observable<Any> {
        return RxView.clicks(layout.add_image)
    }

    fun addImageFromCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        return activity.startActivityForResult(intent, Constants.CAMERA_IMAGE)
    }

    fun openLocalStorage(): Observable<Any> {
        return RxView.clicks(layout.details_ImageView)
    }

    fun addImageFromLocalStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        return activity.startActivityForResult(intent, Constants.ADD_LOCAL_IMAGE)
    }

    fun loadImageInView(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.ADD_LOCAL_IMAGE) {
            loadImageInViewFromLocalStorage(data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.CAMERA_IMAGE) {
            loadImageInViewFromCamera(data)
        }
    }

    fun onClickDeletePhoto(): Observable<Any> {
        return RxView.clicks(layout.delete)
    }

    fun removePhotoFromImageView() {
        layout.details_ImageView.run {
            setImageResource(android.R.color.transparent)
            setBackgroundResource(R.drawable.image_placeholder)
        }
    }

    fun openDatePicker(): Observable<Any> {
        return RxView.clicks(layout.date_EditText)
    }

    fun onSaveClick(): Observable<Any> {
        return RxView.clicks(layout.button_save)
    }

    fun getIncome(uid: String, category: String): Income {
        if (layout.details_EditText.text.isNotEmpty()) {
            defaultDetailsText = layout.details_EditText.text.toString()
        }
        if (layout.details_ImageView != null) {
            defaultDetailsImage = imageToBitmap(layout.details_ImageView)
        }
        val amount = if (layout.amount_EditText.text.toString()
                .isEmpty()
        ) 0.0 else layout.amount_EditText.text.toString().toDouble()
        return Income(
            uid = uid,
            incomeDate = getUTCTimestamp(),
            incomeAmount = amount,
            incomeCategory = category,
            incomeDetails = defaultDetailsText,
            incomeImage = defaultDetailsImage
        )
    }

    fun getExpense(uid: String, category: String): Expense {
        if (layout.details_EditText.text.isNotEmpty()) {
            defaultDetailsText = layout.details_EditText.text.toString()
        }
        if (layout.details_ImageView != null) {
            defaultDetailsImage = imageToBitmap(layout.details_ImageView)
        }
        val amount = if (layout.amount_EditText.text.toString()
                .isEmpty()
        ) 0.0 else layout.amount_EditText.text.toString().toDouble()
        return Expense(
            uid = uid,
            expenseDate = getUTCTimestamp(),
            expenseAmount = amount,
            expenseCategory = category,
            expenseDetails = defaultDetailsText,
            expenseImage = defaultDetailsImage
        )
    }

    private fun imageToBitmap(image: ImageView): ByteArray {
        var returnArray = ByteArray(0)
        if (image.drawable != null && (image.drawable as BitmapDrawable).bitmap != null) {
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            returnArray = stream.toByteArray()
        }
        return returnArray
    }

    fun changeTitle() {
        val toolbar = layout.toolbar as Toolbar
        toolbar.title = layout.context.getString(R.string.edit_action)
    }

    fun populateEditScreen(transaction: Transaction, image: ByteArray) {
        layout.date_EditText.setText(TimeUtils.gmtToLocalTime(transaction.date))
        layout.amount_EditText.setText(transaction.amount.toString())
        layout.details_EditText.setText(transaction.details)
        layout.details_ImageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
    }
}