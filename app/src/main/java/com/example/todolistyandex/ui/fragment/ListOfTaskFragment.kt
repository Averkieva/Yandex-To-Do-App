package com.example.todolistyandex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistyandex.ui.adapter.ListOfTaskAdapter
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModelFactory
import com.example.todolistyandex.ui.activity.MyApplication
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.adapter.SwipeController
import com.example.todolistyandex.databinding.FragmentListOfTaskBinding
import kotlinx.coroutines.launch

class ListOfTaskFragment : Fragment() {

    private var _binding: FragmentListOfTaskBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { (requireActivity().application as MyApplication).repository }
    private val taskViewModel: ListOfTaskViewModel by viewModels {
        ListOfTaskViewModelFactory(
            repository
        )
    }
    private lateinit var adapter: ListOfTaskAdapter
    private lateinit var swipeController: SwipeController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfTaskBinding.inflate(inflater, container, false)

        binding.addTaskButton.setOnClickListener {
            navigateToCreateTaskFragment(-1)
        }

        binding.newTaskTextView.setOnClickListener {
            navigateToCreateTaskFragment(-1)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListOfTaskAdapter()

        binding.taskListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.taskListRecyclerView.adapter = adapter

        swipeController = SwipeController(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(binding.taskListRecyclerView)

        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.tasks.collect { todoList ->
                adapter.submitList(todoList.toMutableList())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.showCompletedTasks.collect { showCompleted ->
                updateEyeIcon(showCompleted)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.completedTaskCount.collect { count ->
                binding.doneTextView.text = resources.getString(R.string.done, count)
            }
        }
    }

    private fun setupClickListeners() {
        binding.addTaskButton.setOnClickListener {
            navigateToCreateTaskFragment(-1)
        }

        binding.newTaskTextView.setOnClickListener {
            navigateToCreateTaskFragment(-1)
        }

        binding.cellEyeImageView.setOnClickListener {
            taskViewModel.toggleShowCompletedTasks()
        }
    }

    private fun navigateToCreateTaskFragment(taskId: Int) {
        val action =
            ListOfTaskFragmentDirections.actionListOfTaskFragmentToCreateNewTaskFragment(
                taskId
            )
        findNavController().navigate(action)
    }

    private fun updateEyeIcon(showCompleted: Boolean) {
        if (showCompleted) {
            binding.cellEyeImageView.setImageResource(R.drawable.cell_eye)
        } else {
            binding.cellEyeImageView.setImageResource(R.drawable.cell_eye_off)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
