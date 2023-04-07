package com.dodemy.roomcrudapp.ui.fragments

//import com.dodemy.roomcrudapp.databinding.FragmentTaskDetailsBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dodemy.roomcrudapp.databinding.FragmentTaskDetailsBinding
//import com.dodemy.roomcrudapp.ui.taskdetails.TaskDetailsFragmentArgs
import com.dodemy.roomcrudapp.ui.viewmodels.TaskDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskDetailsViewModel by viewModels()
    private val args: TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchTask(args.taskId)

        viewModel.task.observe(viewLifecycleOwner) { task ->
            binding.apply {
                if (task != null) {
                    etTaskTitle.setText(task.title)
                    etTaskDescription.setText(task.description)
                    cbTaskCompleted.isChecked = task.isCompleted
                }

                etTaskTitle.doAfterTextChanged { validateInput() }
                etTaskDescription.doAfterTextChanged { validateInput() }

                btnSaveTask.setOnClickListener {
                    viewModel.saveTask(
                        etTaskTitle.text.toString(),
                        etTaskDescription.text.toString(),
                        cbTaskCompleted.isChecked
                    )
                    findNavController().navigateUp()
                }

                if (task != null) {
                    btnDeleteTask.visibility = View.VISIBLE
                    btnDeleteTask.setOnClickListener {
                        viewModel.deleteTask()
                        findNavController().navigateUp()
                    }
                }
                // Call validateInput to initialize the save button's state
                validateInput()
            }
        }
    }

    private fun validateInput() {
        binding.btnSaveTask.isEnabled =
            binding.etTaskTitle.text.isNotEmpty() && binding.etTaskDescription.text.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
