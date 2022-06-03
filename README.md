# Timer
[![Jitpack](https://img.shields.io/jitpack/v/github/amrmsaraya/timer)](https://jitpack.io/#amrmsaraya/timer)

Timer is a general purpose kotlin library that use kotlin coroutines, flows and channels to provide timer features with the most easy and efficient way

## FEATURES
- Stopwatch
- Countdown timer
- Observe timer using coroutine flow that emits `Time()`  class which provide time in an easy and formatted way
- Stopwatch laps
- Start, pause, resume and reset timer
- Configure countdown timer timer and delay anytime
- Get the current status of the stopwatch
- Attach `CoroutineScope` like `viewModelScope` or `lifecycleScope` to the class constructor to cancel active coroutines or use the default scope provided by the class and call `clear()` function to prevent any memory leak 


## USAGE
#### Add the JitPack repository to your root build.gradle file 

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

#### Add the dependency using Groovy
```groovy
implementation 'com.github.amrmsaraya:timer:1.0.3'
```

#### Add the dependency using Kotlin DSL
```kotlin
implementation("com.github.amrmsaraya:timer:1.0.3")
```

## EXAMPLE
#### Timer

```kotlin
// Create a timer object
val timer = Timer()

// Configure the timer to coundown from 4000 milliseconds 
// and it emits the new value every 20 milliseconds
timer.configure(timeMillis = 4_000, delay = 20)

// getTimer take a function as a parameter that invoked when the timer is finished
// and returns a flow of Time() that you can collect
timer.getTimer(
    onFinish = {
      // Some logic when timer is finished
    }
).collect { time ->
    // time has the latest timer value
}

// Start timer (start is a suspending function)
timer.start()

// Pause timer (pause is a suspending function)
timer.pause()

// Reset timer to the initial value (reset is a suspending function)
timer.reset()

// Reset timer to the initial value (reset is a suspending function)
timer.reset()

// Returns IDLE, PAUSED or RUNNING
timer.status() 

// Cancels all coroutines
timer.clear() 
```

#### Stopwatch

```kotlin
// Create a stopwatch object
val stopwatch = Stopwatch()

// Configure the stopwatch to emit the new value every 20 milliseconds
stopwatch.configure(delay = 20)

// getStopwatch returns a flow of Time() that you can collect
stopwatch.getStopwatch().collect { time ->
    // time has the latest stopwatch value
}

// Start stopwatch (start is a suspending function)
stopwatch.start()

// Pause stopwatch (pause is a suspending function)
stopwatch.pause()

// Reset stopwatch (reset is a suspending function)
stopwatch.reset()

// Returns IDLE, PAUSED or RUNNING
stopwatch.status() 

// Cancels all coroutines
stopwatch.clear() 
```


## LICENSE
Timer library is licensed under [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
