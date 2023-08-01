package com.swu.coswumetic

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swu.coswumetic.adaptor.EveryListViewAdapter

class EverythingActivity : AppCompatActivity() {

    lateinit var dbManager : DBManager
    lateinit var sqlitedb : SQLiteDatabase

    data class EveryList (
        var recyclerCosName : String,
        var recyclerExpDate : String
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_everything)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_mypage
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_add -> {
                    startActivity(Intent(applicationContext, AddActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_mypage -> {
                    startActivity(Intent(applicationContext, MypageActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        val rv_everyList = findViewById<RecyclerView>(R.id.everyListRecycler)

        var everyList = ArrayList<EveryList>()

        dbManager = DBManager(this, "coswumeticDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor : Cursor
        cursor = sqlitedb.rawQuery("SELECT cosName, expDate FROM cosListTBL ORDER BY expDate", null)

        var num : Int = 0

        while(cursor.moveToNext()) {

            var strCosName = cursor.getString((cursor.getColumnIndex("cosName")).toInt()).toString()
            var strExpDate = cursor.getString((cursor.getColumnIndex("expDate")).toInt()).toString()
            everyList.add(EveryList(strCosName, strExpDate))
            num++
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        val everyListAdapter = EveryListViewAdapter(everyList)
        everyListAdapter.notifyDataSetChanged()

        rv_everyList.adapter = everyListAdapter
        rv_everyList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}