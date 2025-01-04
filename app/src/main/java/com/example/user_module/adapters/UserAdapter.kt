package com.example.user_module.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user_module.R
import com.example.user_module.models.User

class UserAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        val tvUserPassword: TextView = itemView.findViewById(R.id.tvUserPassword)
        val tvUserBirthDate: TextView = itemView.findViewById(R.id.tvUserBirthDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvUserId.text = "ID: ${user.id}"
        holder.tvUserName.text = "Name: ${user.firstName} ${user.lastName}"
        holder.tvUserEmail.text = "Email: ${user.email}"
        holder.tvUserPassword.text = "Password: ${user.password}"
        holder.tvUserBirthDate.text = "Birthdate: ${user.birthdate}"
    }

    override fun getItemCount(): Int = userList.size

    fun updateUsers(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }
}
