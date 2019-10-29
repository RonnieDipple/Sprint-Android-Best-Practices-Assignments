package com.example.rxjava2_mortgage_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.pow

/*# Sprint-Android-Best-Practices

## Build a Reactive mortgage calculator.

## Instructions:

1. To calculate an amortized mortgage payment, you'll need to collect the following information in your UI:
* Purchase price
* Down payment
* Interest Rate
* Loan length
2. Consider the best way to set up your UI to get the right kind of input (i.e., decide between EditText, Spinner, etc for each item). Use RxJava (RxBinding is fine if you want to use it!) to make the UI responsive to a change in the input.
3. You can calculate the payment amount using the formula A = P*r*(1+r)^n/((1+r)^n - 1).
4. Set up an RxJava-based Retrofit client against the random number API described at [https://qrng.anu.edu.au/API/api-demo.php] to retrieve simulated mortgage rates. Get at least two numbers and scale them between reasonable mortgage rates. For example, you might get two numbers and use the higher one as a 30 year fixed rate and the lower one as a 15 year fixed rate. Return these values in your UI either as a guide for the user or directly in your UI widgets.
//api https://qrng.anu.edu.au/API/jsonI.php?length=2&type=uint8
## Stretch goals
1. Create an amortization table that shows the remaining balance, the total payment, the principal paid, and the interest paid for each month.
2. Polish the UI. Consider using TextInputLayout for any EditText fields.*/

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable
    lateinit var secondDisposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obsPurchasePrice = edit_text_purchase_price.textChanges().filter { it.length > 1 }
        val obsDownPayment = edit_text_down_payment.textChanges().filter { it.length > 1 }
        val obsInterestRate = edit_text_interest_rate.textChanges().filter { it.length > 1 }
        val obsLoanLength = edit_text_loan_length.textChanges().filter { it.length > 1 }


        val obsCombined = Observables.combineLatest(
            obsPurchasePrice,
            obsDownPayment,
            obsInterestRate,
            obsLoanLength
        ) { purchasePrice,
            downPayment,
            interestRate,
            loanLength ->
            calculateNum(purchasePrice, downPayment, interestRate, loanLength)


        }

        disposable = obsCombined.observeOn(AndroidSchedulers.mainThread()).subscribe {nums -> text_view.text = nums.toString()}

        val retrofit = Retrofit.Builder().baseUrl("https://qrng.anu.edu.au/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(GetRandomNumber::class.java)

        disposable = service.getRandomNum("2", "uint8")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {numbers ->
                    val numsList = numbers.nums.toIntArray()
                    numsList.sort()
                    val n = numsList[0] / 1000.toDouble()
                    edit_text_purchase_price.setText("${numsList[3] * 10}")
                    edit_text_down_payment.setText("${numsList[2]}")
                    edit_text_interest_rate.setText("$n")
                    edit_text_loan_length.setText("${numsList[1] / 1000}")

                },
        {fail -> println(fail)}
        )


    }

    private fun calculateNum(
        purchasePrice: CharSequence,
        downPayment: CharSequence,
        interestRate: CharSequence,
        loanLength: CharSequence
    ): String {

        val pPrice = purchasePrice.toString().toDouble()
        val dPayment = downPayment.toString().toDouble()
        val iRate = interestRate.toString().toDouble()
        val lLength = loanLength.toString().toDouble()
        val x = pPrice - dPayment
        val y = (iRate / 100) / 12
        val z = lLength * 12

        val stringSplitter = z.toString().split(".")
        val stringNum = stringSplitter[0]

        return "That will be $stringNum monthly "


    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
