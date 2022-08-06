package com.applandeo.materialcalendarview.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.R
import com.applandeo.materialcalendarview.databinding.CalendarViewDayBinding
import com.applandeo.materialcalendarview.utils.*
import kotlinx.android.synthetic.main.calendar_view_day.view.*
import kotlinx.android.synthetic.main.calendar_view_day.view.dayLabel
import kotlinx.android.synthetic.main.calendar_view_picker_day.view.*
import java.util.*

private const val INVISIBLE_IMAGE_ALPHA = 0.12f

/**
 * This class is responsible for loading a one day cell.
 *
 *
 * Created by Applandeo team
 */
class CalendarDayAdapter(
    context: Context,
    private val calendarPageAdapter: CalendarPageAdapter,
    private val calendarProperties: CalendarProperties,
    dates: MutableList<Date>,
    pageMonth: Int
) : ArrayAdapter<Date>(context, calendarProperties.itemLayoutResource, dates) {

    private val pageMonth = if (pageMonth < 0) 11 else pageMonth

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
//        val binding = CalendarViewBinding.inflate(LayoutInflater.from(context))
        val binding = CalendarViewDayBinding.inflate(LayoutInflater.from(context), parent, false)
        val dayView = view
            ?: LayoutInflater.from(context)
                .inflate(calendarProperties.itemLayoutResource, parent, false)
//        val dayView = binding.root

        val day = GregorianCalendar().apply { time = getItem(position) }

        dayView.dayIcon?.loadIcon(day)

        setLabelColors(dayView.dayLabel, day)
        dayView.dayLabel.text = day[Calendar.DAY_OF_MONTH].toString()
        dayView.tvIsTodayEvent?.visibility = if (day.isToday){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        dayView.tvIsTodayNoEvent?.visibility = if (day.isToday){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        return dayView
    }

    private fun setLabelColors(dayLabel: TextView, day: Calendar) {
        when {
            // Setting not current month day color
            !day.isCurrentMonthDay() -> dayLabel.setDayColors(calendarProperties.anotherMonthsDaysLabelsColor)

            // Setting view for all SelectedDays
            day.isSelectedDay() -> {
                calendarPageAdapter.selectedDays
                    .firstOrNull { selectedDay -> selectedDay.calendar == day }
                    ?.let { selectedDay -> selectedDay.view = dayLabel }
                setSelectedDayColors(dayLabel, day, calendarProperties)
            }

            // Setting disabled days color
            !day.isActiveDay() -> dayLabel.setDayColors(calendarProperties.disabledDaysLabelsColor,backgroundRes = R.drawable.background_highlight_color_circle_selector)

            // Setting custom label color for event day
            day.isEventDayWithLabelColor() -> setCurrentMonthDayColors(
                day,
                dayLabel,
                calendarProperties
            )

            day.isToday -> {

            }

            // Setting current month day color
            else -> setCurrentMonthDayColors(day, dayLabel, calendarProperties)
        }
    }

    private fun Calendar.isSelectedDay() = calendarProperties.calendarType != CalendarView.CLASSIC
            && this[Calendar.MONTH] == pageMonth
            && SelectedDay(this) in calendarPageAdapter.selectedDays

    private fun Calendar.isEventDayWithLabelColor() =
        this.isEventDayWithLabelColor(calendarProperties)

    private fun Calendar.isCurrentMonthDay() = this[Calendar.MONTH] == pageMonth
            && !(calendarProperties.minimumDate != null
            && this.before(calendarProperties.minimumDate)
            || calendarProperties.maximumDate != null
            && this.after(calendarProperties.maximumDate))

    private fun Calendar.isActiveDay() = this !in calendarProperties.disabledDays

    private fun ImageView.loadIcon(day: Calendar) {
        if (!calendarProperties.eventsEnabled) {
            visibility = View.GONE
            return
        }

        calendarProperties.eventDays.firstOrNull { it.calendar == day }?.let { eventDay ->
            loadImage(eventDay.imageDrawable)
            // If a day doesn't belong to current month then image is transparent
            if (!day.isCurrentMonthDay() || !day.isActiveDay()) {
                alpha = INVISIBLE_IMAGE_ALPHA
            }
        }
    }
}