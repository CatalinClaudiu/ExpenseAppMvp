package com.assist_software.expenseappmvp.screens.addActionScreen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.data.database.entities.Income
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.CategoryAdapter
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.models.CategoryItem
import com.assist_software.expenseappmvp.screens.mainScreen.HomeActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_add_action.view.*
import kotlinx.android.synthetic.main.transaction_toolbar.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddActionView(var activity: AddActionActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_add_action, null)
    private val c = Calendar.getInstance()

    var defaultDetailsText: String = ""

    fun showHomeScreen() {
        HomeActivity.start(activity)
        activity.finish()
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

    private fun getUTCTimestamp(): Long {
        return c.timeInMillis
    }

    fun initDatePicker() {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)

        val dpd = DatePickerDialog(
                activity,
                { view, mYear, mMonth, mDay ->
                    layout.date_EditText.setText(mYear.toString() + "-" + mMonth.toString() + "-" + mDay.toString() + " at " + hour + ":" + min)
                },
                year,
                month,
                day
        )
        dpd.show()
        dpd.datePicker.maxDate = System.currentTimeMillis();
    }

    fun getIncome(uid: String, category: String): Income {
        if (layout.amount_EditText.text.toString() != null) {
            defaultDetailsText = layout.details_EditText.text.toString()
        }
        val amount = if (layout.amount_EditText.text.toString().isEmpty()) 0.0 else layout.amount_EditText.text.toString().toDouble()
        return Income(
                uid = uid,
                incomeDate = getUTCTimestamp(),
                incomeAmount = amount,
                incomeCategory = category,
                incomeDetails = defaultDetailsText,
                incomeImage = imageToBitmap(layout.details_ImageView)
        )
    }

    fun getExpense(uid: String, category: String): Expense {
        if (layout.amount_EditText.text.toString().isNotEmpty()) {
            defaultDetailsText = layout.amount_EditText.text.toString()
        }
        val amount = if (layout.amount_EditText.text.toString().isEmpty()) 0.0 else layout.amount_EditText.text.toString().toDouble()
        return Expense(
                uid = uid,
                expenseDate = getUTCTimestamp(),
                expenseAmount = amount,
                expenseCategory = category,
                expenseDetails = defaultDetailsText,
                expenseImage = imageToBitmap(layout.details_ImageView)
        )
    }

    private fun imageToBitmap(image: ImageView): ByteArray {
        var returnArray = ByteArray(0)
        if (image.drawable != null) {
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            returnArray = stream.toByteArray()
        }
        return returnArray
    }
}