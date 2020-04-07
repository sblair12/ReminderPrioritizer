package com.blair.reminderprioritizer

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blair.reminderprioritizer.entity.Reminder
import com.blair.reminderprioritizer.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.reminder_list_item.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val newReminderActivityRequestCode = 1

    private lateinit var adapter: ReminderAdapter
    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_top)

        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        reminderViewModel.allReminders.observe(this, Observer { reminders ->
            reminders?.let { adapter.setReminders(it)}
        })

        adapter = ReminderAdapter(this)
        reminderRecyclerView.layoutManager = LinearLayoutManager(this)
        reminderRecyclerView.adapter = adapter

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewReminderActivity::class.java)
            startActivityForResult(intent, newReminderActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newReminderActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewReminderActivity.EXTRA_DESCRIPTION)?.let {
                val reminder = Reminder(UUID.randomUUID().toString(), it)
                reminderViewModel.add(reminder)
            }
        }
        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class ReminderAdapter internal constructor(
        context: Context
    ): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var reminderList = emptyList<Reminder>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ReminderAdapter.ReminderViewHolder {
            val reminderListView = inflater.inflate(R.layout.reminder_list_item, parent, false)
            return ReminderViewHolder(reminderListView)
        }

        override fun getItemCount(): Int {
            return this.reminderList.size
        }

        override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
            val reminder = this.reminderList[position]

            holder.descriptionTextView.text = reminder.description
        }

        internal fun setReminders(reminders: List<Reminder>) {
            this.reminderList = reminders
            notifyDataSetChanged()
        }

        inner class ReminderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var descriptionTextView: TextView = itemView.description
        }
    }
}
