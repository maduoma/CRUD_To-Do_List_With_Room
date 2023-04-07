package com.dodemy.roomcrudapp.ui.fragments

import com.dodemy.roomcrudapp.databinding.FragmentTaskListBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dodemy.roomcrudapp.ui.adapters.TaskListAdapter
//import com.dodemy.roomcrudapp.ui.tasklist.TaskListFragmentDirections
import com.dodemy.roomcrudapp.ui.viewmodels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskListViewModel by viewModels()

    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListAdapter = TaskListAdapter(
            onItemClickListener = { task ->
                val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(task.id)
                findNavController().navigate(action)
            },
            onDeleteClickListener = { task ->
                viewModel.deleteTask(task)
            }
        )

        binding.apply {
            rvTaskList.layoutManager = LinearLayoutManager(requireContext())
            rvTaskList.adapter = taskListAdapter

            fabAddTask.setOnClickListener {
                val action =
                    TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(0)
                findNavController().navigate(action)
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskListAdapter.submitList(tasks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
