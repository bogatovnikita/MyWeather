package com.bogatovnikita.myweather.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bogatovnikita.myweather.databinding.FragmentContentProviderContactsBinding

class ContentProviderContactsFragment : Fragment() {

    private var _binding: FragmentContentProviderContactsBinding? = null
    private val binding: FragmentContentProviderContactsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getContacts() {}

    companion object {
        fun newInstance() = ContentProviderContactsFragment()
    }
}