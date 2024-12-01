package com.example.spinner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var secondNameET : EditText
    private lateinit var firstNameET : EditText
    private lateinit var ageET : EditText
    private lateinit var spinnerPositionS : Spinner
    private lateinit var saveBTN: Button
    private lateinit var listEmployeeLW: ListView
    private lateinit var toolbar: Toolbar
    private lateinit var spinnerToolbarS: Spinner

    private var listPersonAdapter: PersonListAdapter? = null
    private var persons: MutableList<Person> = mutableListOf()
    private var position: MutableList<String> = mutableListOf()
    private var mainPosition: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fillListPersons(persons)
        fillListPosition(position)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        spinnerToolbarS = findViewById(R.id.spinnerToolbarS)
        val adapterForSpinner = ArrayAdapter(
            this,
            R.layout.spinner_item,
            position
        )
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerToolbarS.adapter = adapterForSpinner

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    if(item == "Должность"){
                        listPersonAdapter = PersonListAdapter(this@MainActivity, persons)
                        listEmployeeLW.adapter = listPersonAdapter
                        listPersonAdapter?.notifyDataSetChanged()
                    }else{
                        val tmpPerson = persons.filter { it.position == item }.toMutableList()
                        listPersonAdapter = PersonListAdapter(this@MainActivity, tmpPerson)
                        listEmployeeLW.adapter = listPersonAdapter
                        listPersonAdapter?.notifyDataSetChanged()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        spinnerToolbarS.onItemSelectedListener = itemSelectedListener

        secondNameET = findViewById(R.id.secondNameET)
        firstNameET = findViewById(R.id.firstNameET)
        ageET = findViewById(R.id.ageET)
        spinnerPositionS = findViewById(R.id.spinnerPositionET)
        val adapterForSpinnerMain = ArrayAdapter(
            this,
            //android.R.layout.simple_spinner_item,
            R.layout.spinner_item,
            position
        )
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPositionS.adapter = adapterForSpinnerMain

        saveBTN = findViewById(R.id.saveBTN)
        listEmployeeLW = findViewById(R.id.listEmployeeLW)

        listPersonAdapter = PersonListAdapter(this@MainActivity, persons)
        listEmployeeLW.adapter = listPersonAdapter
        listPersonAdapter?.notifyDataSetChanged()

        saveBTN.setOnClickListener {
            createPerson()
            listPersonAdapter = PersonListAdapter(this@MainActivity, persons)
            listEmployeeLW.adapter = listPersonAdapter
            listPersonAdapter?.notifyDataSetChanged()
            clearEditFields()
        }

        listEmployeeLW.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val item = listPersonAdapter?.getItem(position)
                listPersonAdapter?.remove(item)
            }
    }



    private fun createPerson() {
        val firstName = firstNameET.text.toString()
        val secondName = secondNameET.text.toString()
        val age = ageET.text.toString()
        mainPosition = spinnerPositionS.selectedItem.toString()
        val person = Person(firstName, secondName, age.toInt(), mainPosition)
        persons.add(person)
    }

    private fun clearEditFields() {
        firstNameET.text.clear()
        secondNameET.text.clear()
        ageET.text.clear()
        spinnerPositionS.setSelection(0).toString()
    }

    private fun fillListPersons(listPersons: MutableList<Person>){
        listPersons.add(Person("Олег","Петров",34, "Терапевт"))
        listPersons.add(Person("Наталия","Иванова",27, "Терапевт"))
        listPersons.add(Person("Валерий","Степанов",41, "Хирург"))
        listPersons.add(Person("Ольга","Всильева",19, "Пульманолог"))
        listPersons.add(Person("Виола","Фиолетовая",25, "Офтальмолог"))
        listPersons.add(Person("Кристина","Ракетова",28, "Кардиолог"))
        listPersons.add(Person("Анжела","Федотова",35, "Ревматолог"))
        listPersons.add(Person("Арнольн","Шварцинегер",44, "Ортопед"))
    }

    private fun fillListPosition(listPosition: MutableList<String>){
        listPosition.add("Должность")
        listPosition.add("Терапевт")
        listPosition.add("Хирург")
        listPosition.add("Пульманолог")
        listPosition.add("Офтальмолог")
        listPosition.add("Кардиолог")
        listPosition.add("Ортопед")
        listPosition.add("Ревматолог")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMenuMain ->{
                finishAffinity()
                Toast.makeText(this,"Приложение завершено", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}