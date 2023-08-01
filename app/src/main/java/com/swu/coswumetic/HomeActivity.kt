package com.swu.coswumetic

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swu.coswumetic.adaptor.ExpiryListViewAdapter


class HomeActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    data class ExpiryList(
        val recycler_cosName: String,
        val recycler_expDate: String,
        // val recycler_photoUri : String
    )

    private val OPEN_GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val rvExpDateList = findViewById<RecyclerView>(R.id.expDateListRecycler)
        val expiryList = ArrayList<ExpiryList>()

        dbManager = DBManager(this, "coswumeticDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery(
            "SELECT cosName, expDate, photo FROM cosListTBL ORDER BY expDate",
            null
        )

        var num: Int = 0

        while (cursor.moveToNext()) {
            var strCosName = cursor.getString((cursor.getColumnIndex("cosName")).toInt()).toString()
            var strExpDate = cursor.getString((cursor.getColumnIndex("expDate")).toInt()).toString()
            expiryList.add(ExpiryList(strCosName, strExpDate))
            //var strPhoto = cursor.getString((cursor.getColumnIndex("photo")).toInt()).toString()
            //expiryList.add(ExpiryList(strCosName, strExpDate, strPhoto))
            num++
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        val expiryListAdapter = ExpiryListViewAdapter(expiryList)
        expiryListAdapter.notifyDataSetChanged()

        rvExpDateList.adapter = expiryListAdapter
        rvExpDateList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_home
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_home -> return@setOnItemSelectedListener true

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

    }
}

