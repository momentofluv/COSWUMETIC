package com.swu.coswumetic

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button


class DeletePopUp(context: Context, dialogInterface: dialogInterface, cosName: String) : Dialog(context) {

    val TAG: String = "로그"
    lateinit var cosName : String
    private var dialogInterface: dialogInterface? = null
    init {
        this.dialogInterface = dialogInterface
        this.cosName = cosName
    }

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pop_up)

        dbManager = DBManager(context, "coswumeticDB", null, 1)
        sqlitedb = dbManager.writableDatabase

        Log.d(TAG, "DeletePopUp - onCreate() called")

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dltButton: Button = findViewById(R.id.dltButton)
        val ccButton: Button = findViewById(R.id.ccButton)

        dltButton.setOnClickListener {

            sqlitedb.execSQL("DELETE FROM cosListTBL WHERE cosName = '$cosName';")

            sqlitedb.close()
            dbManager.close()
            Log.d(TAG, "DeletePopUp - 삭제")
            this.dialogInterface?.ondeleteBtnClicked()
            dismiss()
        }

        ccButton.setOnClickListener {
            Log.d(TAG, "DeletePopUp - 취소")
            this.dialogInterface?.oncancelBtnClicked()
            dismiss()
        }
    }

}