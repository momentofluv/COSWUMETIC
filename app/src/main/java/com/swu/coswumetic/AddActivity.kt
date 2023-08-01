package com.swu.coswumetic


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.app.Activity
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat



class AddActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb : SQLiteDatabase

    lateinit var edtCosName : EditText
    lateinit var edtOpenDate : EditText
    lateinit var edtExpDate : EditText
    lateinit var edtMemo : EditText
    lateinit var btnAdd : Button
    lateinit var str_image : String

    lateinit var edtCategory : String

    lateinit var photo: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initImageViewProfile()

        edtCosName = findViewById(R.id.cosName)
        edtOpenDate = findViewById(R.id.openDate)
        edtExpDate = findViewById(R.id.expDate)
        edtMemo = findViewById(R.id.memotext)
        btnAdd = findViewById(R.id.rgsButton)

        val categoryList = arrayOf("카테고리", "기초화장품", "색조화장품", "바디케어", "선케어", "헤어케어")
        val catSpinner: Spinner = findViewById(R.id.catSpinner)
        val catadapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, categoryList
        )

        catSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                edtCategory = categoryList.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        catSpinner.adapter = catadapter

        insertData()




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

                R.id.bottom_add -> return@setOnItemSelectedListener true

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

    fun insertData() {
        dbManager = DBManager(this, "coswumeticDB", null, 1)

        btnAdd.setOnClickListener {
            var str_cosName : String = edtCosName.text.toString()
            var str_openDate : String = edtOpenDate.text.toString()
            var str_expDate : String = edtExpDate.text.toString()
            var str_memo : String = edtMemo.text.toString()

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO cosListTBL VALUES ('"+str_cosName+"', '"+edtCategory+"', '"+str_openDate+"', '"+str_expDate+"', '"+str_memo+"', '"+str_image+"');")
            sqlitedb.close()
            Toast.makeText(this, "화장품 등록 성공", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initImageViewProfile() {
        photo = findViewById(R.id.photoButton)

        photo.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri: Uri? = data?.data


                if (selectedImageUri != null) {
                    photo.setImageURI(selectedImageUri)
                    Log.d("test", "" + selectedImageUri)
                    str_image = selectedImageUri.toString()

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"


        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }
}