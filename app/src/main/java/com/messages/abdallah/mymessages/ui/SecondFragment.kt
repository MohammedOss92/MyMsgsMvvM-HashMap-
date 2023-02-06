package com.messages.abdallah.mymessages.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.messages.abdallah.mymessages.ViewModel.MsgsViewModel
import com.messages.abdallah.mymessages.adapter.Msgs_Adapter
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.databinding.FragmentSecondBinding
import com.messages.abdallah.mymessages.repository.MsgsRepo


class SecondFragment : Fragment() {

    private var _binding : FragmentSecondBinding?=null
    private val binding get() = _binding!!
    private var argsId = -1
    private var MsgTypes_name = ""


    private val msgsAdapter by lazy { Msgs_Adapter() }
    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository = MsgsRepo(retrofitService)//, LocaleSource(this))
    private val viewModel: MsgsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argsId = SecondFragmentArgs.fromBundle(requireArguments()).id
        MsgTypes_name = SecondFragmentArgs.fromBundle(requireArguments()).msgType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), argsId.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(requireContext(), MsgTypes_name, Toast.LENGTH_LONG).show()

        setUpRv()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setUpRv() {

//        binding.rcMsgTypes.apply {
//            adapter = msgstypesAdapter
//            setHasFixedSize(true)
//        }



        viewModel.responseMsgs.observe(viewLifecycleOwner) { listShows ->
            msgsAdapter.msgsModel = listShows
            binding.rcMsgs.adapter = msgsAdapter
        }
    }
}