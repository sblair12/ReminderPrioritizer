package com.blair.reminderprioritizer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_new_reminder.*

class NewReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)

        button_save.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(edit_description.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val description = edit_description.text.toString()
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_DESCRIPTION = "com.blair.reminderprioritizer.REPLY"
    }
}
