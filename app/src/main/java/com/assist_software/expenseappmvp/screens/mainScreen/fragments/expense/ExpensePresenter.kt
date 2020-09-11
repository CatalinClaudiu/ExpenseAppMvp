package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.data.database.entities.Income
import com.assist_software.expenseappmvp.data.database.models.UserWithExpenses
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.IncomeRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.screens.addActionScreen.enum.CategoryEnum
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.FragmentDialogListener
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.assist_software.expenseappmvp.utils.DateUtils
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.assist_software.expenseappmvp.utils.disposeBy
import com.trinnguyen.SegmentView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class ExpensePresenter(
    private val view: ExpenseView,
    private val sharedPref: SharedPrefUtils,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val compositeDisposables: CompositeDisposable
) : SegmentView.OnSegmentItemSelectedListener, FragmentDialogListener {

    private var endDay: Long = 0
    private var uid: String = ""
    private val startWeek = DateUtils.getEndDate(Calendar.WEEK_OF_MONTH)
    private val onItemClick = PublishSubject.create<Transaction>()

    fun onCreate() {

        initComponents()
        initTransactionRecyclerView(onItemClick)
        view.initSegmentComponents()
        view.setUpChart(getDataFromDB(uid, startWeek, endDay))
        itemClickAction()
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    override fun onSegmentItemSelected(index: Int) {
        when (index) {
            0 -> {
                getWeekExpense(startWeek)
                view.setUpChart(getDataFromDB(uid, startWeek, endDay))
            }
            1 -> {
                val startMonth = DateUtils.getEndDate(Calendar.MONTH)
                getWeekExpense(startMonth)
                view.setUpChart(getDataFromDB(uid, startMonth, endDay))
            }
            2 -> {
                val startYear = DateUtils.getEndDate(Calendar.YEAR)
                getWeekExpense(startYear)
                view.setUpChart(getDataFromDB(uid, startYear, endDay))
            }
        }
    }

    override fun onSegmentItemReselected(index: Int) {}

    private fun initComponents() {
        if (sharedPref.hasKey(Constants.USER_ID)) {
            uid = sharedPref.read(Constants.USER_ID, "").toString()
        }
        endDay = Calendar.getInstance().timeInMillis
        val startWeek = DateUtils.getEndDate(Calendar.WEEK_OF_MONTH)
        getWeekExpense(startWeek)
    }

    private fun getWeekExpense(startWeek: Long) {
        return expenseRepository.getExpenseByDate(startWeek, endDay, uid)
            .observeOn(rxSchedulers.androidUI())
            .doOnSuccess { expense ->
                view.setTotalExpense(expense)
            }
            .subscribe()
            .disposeBy(compositeDisposables)
    }

    private fun initTransactionRecyclerView(listener: PublishSubject<Transaction>) {
        val userDetails = userRepository.loadUserWithExpenses(uid)

        val incomeList = mutableListOf<Income>()
        incomeList.addAll(userDetails.incomes)

        val expenseList = mutableListOf<Expense>()
        expenseList.addAll(userDetails.expenses)

        val transactionList = mutableListOf<Transaction>()
        for (index in 0 until incomeList.size) {
            var transactionName: String =
                view.layout.context?.getString(R.string.income) ?: String.toString()

            val transactionObject = Transaction(
                incomeList[index].incomeId,
                incomeList[index].incomeDate,
                incomeList[index].incomeAmount,
                incomeList[index].incomeCategory,
                transactionName,
                0.0,
                incomeList[index].incomeDetails,
                incomeList[index].incomeImage
            )
            transactionList.add(transactionObject)
        }

        for (index in 0 until expenseList.size) {
            var transactionName: String =
                view.layout.context?.getString(R.string.expense) ?: String.toString()

            val transactionObject = Transaction(
                expenseList[index].expenseId,
                expenseList[index].expenseDate,
                expenseList[index].expenseAmount,
                expenseList[index].expenseCategory,
                transactionName,
                0.0,
                expenseList[index].expenseDetails,
                expenseList[index].expenseImage
            )
            transactionList.add(transactionObject)
        }

        var orderedList: List<Transaction> = transactionList.sortedByDescending { it.date }

        if (orderedList.isNotEmpty()) {
            calculateDynamicBalance(orderedList, userDetails)
        }


        view.initAdapter(orderedList, listener)
    }

    private fun calculateDynamicBalance(
        orderedList: List<Transaction>,
        userDetails: UserWithExpenses
    ): List<Transaction> {
        orderedList[0].balance = userDetails.userDetails.userCurrentBalance
        orderedList[orderedList.size - 1].balance = orderedList[orderedList.size - 1].amount
        for (index in 1 until orderedList.size) {
            if (orderedList[index].category == CategoryEnum.INCOME.getName(view.layout.context)
                    .toLowerCase().capitalize()
            ) {
                if (orderedList[index - 1].category == CategoryEnum.INCOME.getName(view.layout.context)
                        .toLowerCase().capitalize()
                ) {
                    orderedList[index].balance =
                        orderedList[index - 1].balance - orderedList[index - 1].amount
                } else {
                    orderedList[index].balance =
                        orderedList[index - 1].balance + orderedList[index - 1].amount
                }
            } else {
                orderedList[index].balance =
                    orderedList[index - 1].balance + orderedList[index - 1].amount
            }
        }
        orderedList[orderedList.size - 1].balance = orderedList[orderedList.size - 1].amount

        return orderedList
    }

    private fun itemClickAction() {
        onItemClick.observeOn(rxSchedulers.androidUI())
            .subscribe(
                {
                    view.showDialog(it, this)
                },
                { Timber.i(it.localizedMessage) })
            .disposeBy(compositeDisposables)
    }

    override fun onDeleteClick(transactionItem: Transaction) {
        val index = view.getAdapterItemList()
            .indexOfFirst { t -> t.transactionId == transactionItem.transactionId }
        if (index != -1) {
            when (transactionItem.category) {
                CategoryEnum.INCOME.getName(view.layout.context).toLowerCase()
                    .capitalize()
                -> incomeRepository.deleteIncomeById(transactionItem.transactionId)
                else -> expenseRepository.deleteExpenseById(transactionItem.transactionId)
            }
            view.removeItemFromAdapter(index)
        }
    }

    private fun getDataFromDB(
        userId: String,
        startDate: Long,
        endDate: Long
    ): MutableList<Expense> {
        return expenseRepository.getExpenseByCategoryInInterval(userId, startDate, endDate)
    }
}