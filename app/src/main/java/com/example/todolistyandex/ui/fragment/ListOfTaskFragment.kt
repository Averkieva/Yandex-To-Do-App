package com.example.todolistyandex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolistyandex.ui.compose.listoftask.ListOfTaskScreen
import com.example.todolistyandex.ui.activity.MyApplication
import com.example.todolistyandex.ui.viewmodel.ToDoViewModelFactory
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

/**
 * Fragment class for displaying a list of tasks using Jetpack Compose,
 * initializing the ListOfTaskViewModel and setting up the UI with navigation.
 */

class ListOfTaskFragment : Fragment() {

    private val repository by lazy { (requireActivity().application as MyApplication).repository }
    private val taskViewModel: ListOfTaskViewModel by viewModels {
        ToDoViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                ListOfTaskScreen(taskViewModel = taskViewModel, navController = navController)
            }
        }
    }
}