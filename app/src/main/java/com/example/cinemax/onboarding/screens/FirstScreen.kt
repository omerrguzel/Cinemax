package com.example.cinemax.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {


    private var title: String? = null
    private var description: String? = null
    private var imageResource = 0
    private var buttonResource = 0
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = requireArguments().getString(ARG_PARAM1)
            description = requireArguments().getString(ARG_PARAM2)
            imageResource = requireArguments().getInt(ARG_PARAM3)
            buttonResource = requireArguments().getInt(ARG_PARAM4)
        }
    }

    private var _binding: FragmentFirstScreenBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_first_screen, container, false)
        val view = binding.root

        tvTitle=binding.textviewOnboardingHeaderFirst
        tvDescription = binding.textviewOnboardingBodyFirst
        image = binding.imageOnboardingFirst
        tvTitle.text = title
        tvDescription.text = description
        image.setImageResource(imageResource)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            title: String?,
            description: String?,
            imageResource: Int,
            buttonResource: Int
        ): FirstScreen {
            val fragment = FirstScreen()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            args.putInt(ARG_PARAM3, imageResource)
            args.putInt(ARG_PARAM4,buttonResource)
            fragment.arguments = args
            return fragment
        }
    }
}