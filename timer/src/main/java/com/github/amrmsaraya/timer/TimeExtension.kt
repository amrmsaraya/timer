package com.github.amrmsaraya.timer

/**
 * Extension function to [Long] which converts the timeInMillis to [Time]
 * @return [Time] version of the timeInMillis
 */
fun Long.toTime(): Time {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this / (1000 * 60)) % 60
    val seconds = (this / 1000) % 60
    val millis = this % 1000

    return Time(
        timeInMillis = this,
        hours = hours.toInt(),
        minutes = minutes.toInt(),
        seconds = seconds.toInt(),
        millis = millis.toInt()
    )
}