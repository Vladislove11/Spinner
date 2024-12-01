package com.example.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PersonListAdapter(context: Context, personList: MutableList<Person>) :
    ArrayAdapter<Person>(context, R.layout.list_item, personList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            val person = getItem(position)
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            }

            val firstNameET = view?.findViewById<TextView>(R.id.itemFirstNameET)
            val secondNameET = view?.findViewById<TextView>(R.id.itemSecondNameET)
            val ageET = view?.findViewById<TextView>(R.id.itemAgeET)
            val positionET = view?.findViewById<TextView>(R.id.itemPositionET)

            firstNameET?.text = person?.firstName
            secondNameET?.text = person?.secondName
            ageET?.text = person?.age.toString()
            positionET?.text = person?.position

            return view!!

    }
}