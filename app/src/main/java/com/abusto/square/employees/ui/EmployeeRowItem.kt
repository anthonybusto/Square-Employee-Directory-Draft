package com.abusto.square.employees.ui

import com.abusto.square.employee_domain.Employee
import com.abusto.square.employees.R
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeRowItem constructor(private val employee: Employee) : Item<GroupieViewHolder>() {

    init {
        extras[KEY_EMPLOYEE_UUID] = employee.uuid
    }

    override fun getLayout() = R.layout.item_employee

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val root = viewHolder.root
        root.employee_name.text = employee.fullName
        root.employee_team.text = employee.team
        Glide.with(root.context)
            .load(employee.photoUrlSmall)
            .into(root.employee_icon)
    }

    override fun toString(): String = employee.uuid

    companion object{
        const val KEY_EMPLOYEE_UUID = "uuid"
    }
}