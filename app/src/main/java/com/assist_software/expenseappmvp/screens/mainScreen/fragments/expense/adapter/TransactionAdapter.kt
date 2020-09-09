package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.addActionScreen.enum.CategoryEnum
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.assist_software.expenseappmvp.utils.TimeUtils
import io.reactivex.subjects.PublishSubject

class TransactionAdapter(
    private val context: Context,
    private val arrayList: List<Transaction>,
    private val transactionAdapterListener: PublishSubject<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)

        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.run {
            transactionDate.text = TimeUtils.transactionTimeFormat(context, currentItem.date)
            transactionAmountValue.text = "-" + currentItem.amount.toString()
            transactionCategoryName.text = currentItem.category
            transactionName.text = currentItem.name
            balanceValue.text = currentItem.balance.toString()
            transactionAmountValue.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            if (currentItem.balance < 0)
                balanceValue.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
        }

        when (currentItem.category) {
            CategoryEnum.INCOME.getName(context).toLowerCase().capitalize() -> {
                holder.run {
                    transactionCategoryIcon.setImageResource(CategoryEnum.INCOME.getIcon(context))
                    transactionAmountValue.text = "+" + currentItem.amount.toString()
                    transactionAmountValue.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorGreen
                        )
                    )
                }
            }

            CategoryEnum.FOOD.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.FOOD.getIcon(context))
            }
            CategoryEnum.CAR.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.CAR.getIcon(context))
            }
            CategoryEnum.CLOTHES.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.CLOTHES.getIcon(context))
            }
            CategoryEnum.SAVINGS.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.SAVINGS.getIcon(context))
            }
            CategoryEnum.HEALTH.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.HEALTH.getIcon(context))
            }
            CategoryEnum.BEAUTY.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.BEAUTY.getIcon(context))
            }
            CategoryEnum.TRAVEL.getName(context).toLowerCase().capitalize() -> {
                holder.transactionCategoryIcon.setImageResource(CategoryEnum.TRAVEL.getIcon(context))
            }
        }

        holder.transactionBox.setOnClickListener { transactionAdapterListener.onNext(currentItem) }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionDate: TextView = itemView.findViewById(R.id.transaction_date_textView)
        val transactionCategoryIcon: ImageView =
            itemView.findViewById(R.id.transaction_category_icon)
        val transactionCategoryName: TextView =
            itemView.findViewById(R.id.transaction_category_name_textView)
        val transactionAmountValue: TextView =
            itemView.findViewById(R.id.transaction_amount_value_textView)
        val transactionName: TextView = itemView.findViewById(R.id.transaction_name_textView)
        val balanceValue: TextView = itemView.findViewById(R.id.balance_value_textView)
        val transactionBox: ConstraintLayout = itemView.findViewById(R.id.transaction_item)
    }
}