package com.example.fileinfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FILE_RESULT_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnselect.setOnClickListener() {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "*/*"
            startActivityForResult(intent, FILE_RESULT_CODE)
        }

        btnClr.setOnClickListener() {
            textView6.text = ""
            textView7.text = ""
            textView8.text = ""
            textView9.text = ""
            textView10.text = ""
        }

        btnCls.setOnClickListener() {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            exitProcess(1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FILE_RESULT_CODE -> if (resultCode == RESULT_OK)
            {
                val fileFullLocation = data?.data?.path
//                val fileDir = File(fileFullLocation)
//                val fileSizeInBytes: Long = fileDir.getName.length()
//
////                var a = DocumentFile.fromSingleUri(context, fileDir)

                val fileDirectory = data?.data?.lastPathSegment.toString()
                val namePosition = fileDirectory.lastIndexOf("/")
                val dotPosition = fileDirectory.lastIndexOf(".")
                val directoryPosition = fileDirectory.lastIndexOf(":")
                val name = namePosition.plus(1).let { fileDirectory.substring(it, fileDirectory.length)}
                val directory = directoryPosition.plus(1).let { fileDirectory.substring(it, fileDirectory.length)}
                val filenameWithoutExt = namePosition.let {name.substring(0, it)}.toString()
                val ext = dotPosition.plus(1).let {fileDirectory.substring(it, fileDirectory.length)}
                val file = File(fileFullLocation.toString())
                textView6.text = filenameWithoutExt
                textView7.text = ext
                textView8.text = file.length().toString()
                textView9.text = directory

                when {
                    textView7.text.toString() == "jpg" -> {
                        textView10.text = "IMAGE"
                    }
                    textView7.text.toString() == "pdf" -> {
                        textView10.text = "DOCUMENT"
                    }
                    textView7.text.toString() == "doc" -> {
                        textView10.text = "DOCUMENT"
                    }
                }
            }
        }
    }
}
