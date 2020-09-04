package com.assist_software.expenseappmvp.screens.addActionScreen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.CategoryAdapter
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.models.CategoryItem
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_add_action.view.*
import kotlinx.android.synthetic.main.transaction_toolbar.view.*
import java.util.*

class AddActionView(var activity: AddActionActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_add_action, null)

    private fun loadImageInViewFromLocalStorage(data: Intent?) {
        layout.details_ImageView.run {
            setBackgroundResource(android.R.color.transparent)
            setImageURI(data?.data)
        }
    }

    private fun loadImageInViewFromCamera(data: Intent?) {
        var bitmap: Bitmap = data?.extras?.get("data") as Bitmap
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

    fun getAmount(): Observable<TextViewAfterTextChangeEvent>? {
        return RxTextView.afterTextChangeEvents(layout.amount_EditText).skipInitialValue()
    }

    fun getDetailsText(): Observable<TextViewAfterTextChangeEvent>? {
        return RxTextView.afterTextChangeEvents(layout.details_EditText).skipInitialValue()
    }

    fun getCategory(): Observable<Any> {
        return RxView.clicks(layout.grid_recycler)
    }

    fun initDatePicker(): Long {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)

        var gmtTimestamp: Long = -1

        layout.date_EditText.date_EditText.setOnClickListener {
            val dpd = DatePickerDialog(
                activity,
                { view, mYear, mMonth, mDay ->
                    layout.date_EditText.setText(mYear.toString() + "-" + mMonth.toString() + "-" + mDay.toString() + " at " + hour + ":" + min)
                    gmtTimestamp = c.run {
                        c.set(Calendar.YEAR, mYear)
                        c.set(Calendar.MONTH, mMonth)
                        c.set(Calendar.DAY_OF_MONTH, mDay)
                        timeInMillis
                    }
                },
                year,
                month,
                day
            )
            dpd.show()
            dpd.datePicker.maxDate = System.currentTimeMillis();
        }
        return gmtTimestamp
    }
}