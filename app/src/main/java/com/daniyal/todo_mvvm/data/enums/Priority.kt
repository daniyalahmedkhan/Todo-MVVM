package com.daniyal.todo_mvvm.data.enums

enum class Priority {
    Green,
    Medium,
    Average,
    Low;


}

public class Pri {


    companion object  {
        fun getClaimStatus(status: Int): String?{
            return when (status) {
                9 -> Priority.Green.toString()
                1 -> Priority.Medium.toString()
                else -> Priority.Medium.toString()
            }
        }

    }
}