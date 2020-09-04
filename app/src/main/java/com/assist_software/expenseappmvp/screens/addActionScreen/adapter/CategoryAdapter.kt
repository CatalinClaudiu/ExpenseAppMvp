package com.assist_software.expenseappmvp.screens.addActionScreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.addActionScreen.adapter.models.CategoryItem
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(
    private val context: Context,
    private val arrayList: List<CategoryItem>,
    private val categoryAdapterListener: PublishSubject<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(arrayList[position], position)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categoryItem: CategoryItem, position: Int) {
            itemView.run {
                if (!categoryItem.isSelected) {
                    setBackgroundResource(R.drawable.category_box_shape)
                } else {
                    setBackgroundResource(R.drawable.category_box_shape_selected)
                }
                category_image.setImageResource(categoryItem.categoryIcon)
                category_name.text = categoryItem.categoryName.toLowerCase().capitalize()

                itemView.setOnClickListener {
                    deselectItem()
                    categoryItem.isSelected = !categoryItem.isSelected
                    categoryAdapterListener.onNext(categoryItem)
                    notifyItemChanged(position)
                }
            }
        }

        private fun deselectItem() {
            val selectedItem = arrayList.indexOf(arrayList.find { it.isSelected })
            if (selectedItem > -1) {
                arrayList[selectedItem].isSelected = false
                notifyItemChanged(selectedItem)
            }
        }
    }
}