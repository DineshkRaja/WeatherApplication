package com.example.weatherappcd.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherappcd.databinding.ItemWeatherDaysBinding
import com.example.weatherappcd.view.model.WeatherMappers.toWeatherObject
import com.example.weatherappcd.view.model.WeatherModelClass
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class WeeklyWeatherAdapter(
    val context: Context,
    private val listener: (WeatherModelClass) -> Unit
) : ListAdapter<WeatherModelClass, WeeklyWeatherAdapter.ViewHolder>(WeathersComparator()) {

    private var weatherFutureMap: Map<Int, List<WeatherModelClass>> = mapOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setDailyWeatherData(dataSet: Map<Int, List<WeatherModelClass>>) {
        this.weatherFutureMap = dataSet
        notifyDataSetChanged()
    }

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
            ItemWeatherDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = weatherFutureMap.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = weatherFutureMap[position] ?: return
        holder.bind(itemList)
    }


    inner class ViewHolder(private val binding: ItemWeatherDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherList: List<WeatherModelClass>) {
            with(toWeatherObject(weatherList, adapterPosition)) {
                binding.futureDate.text =
                    time.format(DateTimeFormatter.ofPattern("E", Locale.getDefault()))
                binding.futureTemp.text = "${this?.temperature}°C"
                binding.futureStatus.text = weatherTypes.weatherDesc ?: "N/A"
                binding.futureHumidity.text = "${humidity}%"
                Glide.with(context).load(weatherTypes.iconRes)
                    .into(binding.futureStatusImage)
                binding.weeklyWeatherParent.setOnClickListener {
                    listener.invoke(this)
                }
            }
        }
    }
}