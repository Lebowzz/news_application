package com.usj.news_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.usj.news_application.R
import com.usj.news_application.models.User


class AccountInfoAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<AccountInfoAdapter.AccountInfoViewHolder>() {

    class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val lastName: TextView = itemView.findViewById(R.id.tvLastName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val birthdate: TextView = itemView.findViewById(R.id.tvBirthDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account_info, parent, false)
        return AccountInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountInfoViewHolder, position: Int) {
        val user = userList[position]
        holder.firstName.text = "First Name: ${user.firstName}"
        holder.lastName.text = "Last Name: ${user.lastName}"
        holder.email.text = "Email: ${user.email}"
        holder.birthdate.text = "Birthdate: ${user.birthdate}"
    }

    override fun getItemCount(): Int = userList.size
}
