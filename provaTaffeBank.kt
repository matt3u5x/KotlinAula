//Eu fiz o que podia, usei código que tiha feito ja como base. porem esqueci como fazia muitas coisas. É NOIX (vai em inglês mesmo pois se eu mexer mais vai ficar pior ainda)

package com.example.taffebank

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextValue: EditText
    private lateinit var radioGroupTransaction: RadioGroup
    private lateinit var buttonAddTransaction: Button
    private lateinit var listViewTransactions: ListView
    private val transactions = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextValue = findViewById(R.id.editTextValue)
        radioGroupTransaction = findViewById(R.id.radioGroupTransaction)
        buttonAddTransaction = findViewById(R.id.buttonAddTransaction)
        listViewTransactions = findViewById(R.id.listViewTransactions)

        
        adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transactions) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                val transaction = transactions[position]
                if (transaction.startsWith("Crédito")) {
                    view.setBackgroundColor(Color.parseColor("#A5D6A7"))
                } else {
                    view.setBackgroundColor(Color.parseColor("#EF9A9A"))
                }
                return view
            }
        }

        listViewTransactions.adapter = adapter

        buttonAddTransaction.setOnClickListener {
            addTransaction()
        }
    }

    private fun addTransaction() {
        val valueText = editTextValue.text.toString()
        if (valueText.isNotEmpty()) {
            val value = valueText.toDouble()
            val transactionType = when (radioGroupTransaction.checkedRadioButtonId) {
                R.id.radioCredit -> "Crédito: R$ $value"
                R.id.radioDebit -> "Débito: R$ $value"
                else -> return
            }

            transactions.add(transactionType)
            adapter.notifyDataSetChanged()
            editTextValue.text.clear()
        } else {
            Toast.makeText(this, "Por favor, insira um valor.", Toast.LENGTH_SHORT).show()
        }
    }
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textViewTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TafféBank"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_gravity="center"
        android:paddingBottom="16dp"/>

    <EditText
        android:id="@+id/editTextValue"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Valor"
        android:inputType="numberDecimal" />

    <RadioGroup
        android:id="@+id/radioGroupTransaction"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="8dp">

        <RadioButton
            android:id="@+id/radioCredit"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Crédito" />

        <RadioButton
            android:id="@+id/radioDebit"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Débito" />
    </RadioGroup>

    <Button
        android:id="@+id/buttonAddTransaction"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Adicionar Transação"
        android:layout_marginTop="16dp"/>

    <ListView
        android:id="@+id/listViewTransactions"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"/>
</LinearLayout>
