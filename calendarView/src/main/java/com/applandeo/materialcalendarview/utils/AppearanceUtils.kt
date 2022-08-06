package com.applandeo.materialcalendarview.utils

import android.graphics.drawable.Drawable
import android.view.View
import com.applandeo.materialcalendarview.R
import com.applandeo.materialcalendarview.databinding.CalendarViewBinding


/**
 * Created by Applandeo Team.
 */

internal fun View.setAbbreviationsLabels(
    binding: CalendarViewBinding,
    color: Int,
    firstDayOfWeek: Int
) {
    val labels = listOf(
        binding.mondayLabel,
        binding.tuesdayLabel,
        binding.wednesdayLabel,
        binding.thursdayLabel,
        binding.fridayLabel,
        binding.saturdayLabel,
        binding.sundayLabel
    )

    val abbreviations =
        context.resources.getStringArray(R.array.material_calendar_day_abbreviations_array)

    labels.forEachIndexed { index, label ->
        label.text = abbreviations[(index + firstDayOfWeek - 1) % 7]

        if (color != 0) {
            label.setTextColor(color)
        }
    }
}

internal fun View.setHeaderColor(binding:CalendarViewBinding,color: Int) {
    if (color == 0) return
    binding.calendarHeader.setBackgroundColor(color)
}

internal fun View.setHeaderLabelColor(binding:CalendarViewBinding,color: Int) {
    if (color == 0) return
    binding.currentDateLabel.setTextColor(color)
}

internal fun View.setAbbreviationsBarColor(binding:CalendarViewBinding,color: Int) {
    if (color == 0) return
    binding.abbreviationsBar.setBackgroundColor(color)
}

internal fun View.setPagesColor(binding:CalendarViewBinding,color: Int) {
    if (color == 0) return
    binding.calendarViewPager.setBackgroundColor(color)
}

internal fun View.setPreviousButtonImage(binding:CalendarViewBinding,drawable: Drawable?) {
    if (drawable == null) return
    binding.previousButton.setImageDrawable(drawable)
}

internal fun View.setForwardButtonImage(binding:CalendarViewBinding,drawable: Drawable?) {
    if (drawable == null) return
    binding.forwardButton.setImageDrawable(drawable)
}

internal fun View.setHeaderVisibility(binding:CalendarViewBinding,visibility: Int) {
    binding.calendarHeader.visibility = visibility
}

internal fun View.setNavigationVisibility(binding:CalendarViewBinding,visibility: Int) {
    binding.previousButton.visibility = visibility
    binding.forwardButton.visibility = visibility
}

internal fun View.setAbbreviationsBarVisibility(binding:CalendarViewBinding,visibility: Int) {
    binding.abbreviationsBar.visibility = visibility
}
