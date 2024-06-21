package com.example.todolistyandex.ui.fragment

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.ui.activity.MyApplication
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.viewmodel.TaskViewModel
import com.example.todolistyandex.ui.viewmodel.TaskViewModelFactory
import com.example.todolistyandex.databinding.FragmentCreateNewTaskBinding
import com.example.todolistyandex.data.model.ToDoItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNewTaskFragment : Fragment() {

    private var _binding: FragmentCreateNewTaskBinding? = null
    private val binding get() = _binding!!

    private val args: CreateNewTaskFragmentArgs by navArgs()
    private val repository by lazy { (requireActivity().application as MyApplication).repository }
    private val viewModel: TaskViewModel by viewModels { TaskViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInitialTask()
        setupPriorityPopupMenu()
        setupClickListeners()
        setupDateSwitchListener()
    }

    private fun setupInitialTask() {
        val taskId = args.id
        if (taskId != -1) {
            viewModel.getTaskById(taskId).observe(viewLifecycleOwner, Observer { task ->
                task?.let {
                    viewModel.setTask(it)
                    binding.apply {
                        dateSetText.text = it.deadlineComplete
                        newEditText.setText(it.text)
                        priorityText.text = when (it.priority) {
                            ListOfTaskStatus.HIGH -> getString(R.string.high)
                            ListOfTaskStatus.NORMAL -> getString(R.string.no)
                            ListOfTaskStatus.LOW -> getString(R.string.low)
                        }
                    }
                }
            })
        } else {
            viewModel.setTask(ToDoItem(id = 0, text = "", priority = ListOfTaskStatus.NORMAL))
        }
    }

    private fun setupPriorityPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.priorityLabelText)
        popupMenu.menuInflater.inflate(R.menu.list_of_prioritets, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val priorityText = binding.priorityText
            when (menuItem.itemId) {
                R.id.priority_high -> {
                    viewModel.setPriority(ListOfTaskStatus.HIGH)
                    priorityText.text = getString(R.string.high)
                    true
                }

                R.id.priority_normal -> {
                    viewModel.setPriority(ListOfTaskStatus.NORMAL)
                    priorityText.text = getString(R.string.no)
                    true
                }

                R.id.priority_low -> {
                    viewModel.setPriority(ListOfTaskStatus.LOW)
                    priorityText.text = getString(R.string.low)
                    true
                }

                else -> false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

        binding.priorityLabelText.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            closeImageButton.setOnClickListener {
                findNavController().popBackStack()
            }

            saveTaskText.setOnClickListener {
                saveTask()
            }

            deleteImageButton.setOnClickListener {
                viewModel.deleteTodoItem()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupDateSwitchListener() {
        binding.dateSwitch.setOnCheckedChangeListener { _, isChecked ->
            val dateSetText = binding.dateSetText
            if (isChecked) {
                chooseDate(dateSetText)
            } else {
                dateSetText.visibility = View.INVISIBLE
                viewModel.taskItem.value?.deadlineComplete = ""
            }
        }
    }

    private fun saveTask() {
        viewModel.setText(binding.newEditText.text.toString())
        viewModel.saveToDoItem()
        findNavController().popBackStack()
    }

    private fun chooseDate(dateText: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                viewModel.setDeadline(formattedDate)
                dateText.visibility = View.VISIBLE
                dateText.text = formattedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}