package com.swu.coswumetic

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar

class DetailActivity : AppCompatActivity(), dialogInterface {

    @SuppressLint("MissingInflatedId")
    val Tag: String = "로그"
    @SuppressLint("ResourceAsColor", "MissingInflatedId")

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvCategory: TextView
    lateinit var tvCosName: TextView
    lateinit var tvOpenDate: TextView
    lateinit var tvExpDate: TextView
    lateinit var tvLeftDate: TextView
    lateinit var tvMemo: TextView


    lateinit var str_CosName: String
    lateinit var str_category: String
    lateinit var str_openDate: String
    lateinit var str_expDate: String
    lateinit var str_leftDate: String
    lateinit var str_memo: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.d(TAG, "DetailActivity - onCreate() called")


        tvCategory = findViewById(R.id.catInfo)
        tvCosName = findViewById(R.id.nameInfo)
        tvOpenDate = findViewById(R.id.opendateInfo)
        tvExpDate = findViewById(R.id.expDateDetailInfo)
        tvLeftDate = findViewById(R.id.dDayInfo)
        tvMemo = findViewById(R.id.memotext)

        val intent = intent
        str_CosName = intent.getStringExtra("cosName").toString()

        dbManager = DBManager(this, "coswumeticDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery(
            "SELECT * from cosListTBL WHERE cosName = '" + str_CosName + "';",
            null
        )

        if (cursor.moveToNext()) {
            str_category = cursor.getString((cursor.getColumnIndex("category")).toInt()).toString()
            str_openDate = cursor.getString((cursor.getColumnIndex("openDate")).toInt()).toString()
            str_expDate = cursor.getString((cursor.getColumnIndex("expDate")).toInt()).toString()
            str_memo = cursor.getString((cursor.getColumnIndex("memo")).toInt()).toString()
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        var dataFormat = SimpleDateFormat("yyyy-MM-dd")
        var today = Calendar.getInstance()
        var lastDate = dataFormat.parse(str_expDate)

        var calcDate = (lastDate.time - today.time.time) / (60 * 60 * 24 * 1000)

        str_leftDate = calcDate.toString() + "일 남았습니다."

        tvCategory.text = str_category
        tvCosName.text = str_CosName
        tvOpenDate.text = str_openDate
        tvExpDate.text = str_expDate
        tvLeftDate.text = str_leftDate
        tvMemo.text = str_memo

        val deleteButton: Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val deleteDialog = DeletePopUp(this, this, str_CosName)
            deleteDialog.show()
        }

        // 하단바
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_add
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

    }

    fun onDialogBtnClicked(view: View){
        Log.d(TAG, "DetailActivity - onDialogBtnClicked() called")

        val PopupActivity = DeletePopUp(this, this, str_CosName)

        PopupActivity.show()
    }

    override fun ondeleteBtnClicked() {
        Log.d(TAG, "DetailActivity - ondeleteBtnClicked() called")
        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
        val listintent : Intent = Intent(this, HomeActivity::class.java)
        finish()
    }

    override fun oncancelBtnClicked() {
        Log.d(TAG, "DetailActivity - oncancelBtnClicked() called")
    }
}