package com.example.weatherappcd.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherappcd.databinding.ItemHourlyStatusBinding
import com.example.weatherappcd.view.model.WeatherModelClass
import java.time.format.DateTimeFormatter
import java.util.Locale

class DailyWeatherAdapter(private val context: Context) :
    ListAdapter<WeatherModelClass, DailyWeatherAdapter.ViewHolder>(WeathersComparator()) {

    private var weatherModelList: MutableList<WeatherModelClass> = mutableListOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setDailyWeatherData(dataSet: MutableList<WeatherModelClass>) {
        this.weatherModelList = dataSet
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemHourlyStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    class WeathersComparator : DiffUtil.ItemCallback<WeatherModelClass>() {
        override fun areItemsTheSame(
            oldItem: WeatherModelClass,
            newItem: WeatherModelClass
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherModelClass,
            newItem: WeatherModelClass
        ): Boolean {
            return oldItem.time == newItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHourlyStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = weatherModelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(weatherModelList[position]) {
                binding.weatherDayStatus.text = time.format(DateTimeFormatter.ofPattern("h a", Locale.getDefault()))
                binding.weatherDayTemp.text = "${temperature}°C"
                Glide.with(context).load(weatherTypes.iconRes)
                    .into(binding.weatherDayStatusImage)
            }
        }
    }
}