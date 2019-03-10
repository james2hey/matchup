package me.jamestoohey.matchup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private val teams = arrayOf(
        "team 1",
        "team 2",
        "team 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.listView)
        val listAdapter = ArrayAdapter<String>(listView.context, android.R.layout.simple_list_item_1, teams)

    }

    fun generateTournament() {

    }
}
