package com.example.calendar.ui.theme

import javax.inject.Inject

interface Languages {

    val taskTextTopBodyLayer: String

    val editTextTopBodyLayer: String

    //Event
    val addNewEvent: String

    val enterYourEvent: String

    //Repeat
    val repeat: String

    val oneTime: String

    val everyDay: String

    val everyWeek: String

    val everyTwoWeek: String

    val everyMonth: String

    val everyYear: String

    val accept: String

    //Date
    val date: String

    val day: String

    val month: String

    val year: String

    val time: String

    val hour: String

    val minute: String

    //Description
    val enterYourDescription: String

    //Toast
    val toastEnterNumberBetweenRange: String

    val toastEnterNumber: String

    val toastNameContainTwentyCharacters: String

    val toastNameContainHundredTwentyCharacters: String

    val toastEnterName: String

    class English @Inject constructor() : Languages {
        override val taskTextTopBodyLayer: String
            get() = "Events on "
        override val editTextTopBodyLayer: String
            get() = "Enter new name or description"
        override val addNewEvent: String
            get() = "Add a new event"
        override val enterYourEvent: String
            get() = "Enter your event"
        override val repeat: String
            get() = "Repeat"
        override val oneTime: String
            get() = "One time"
        override val everyDay: String
            get() = "Every day"
        override val everyWeek: String
            get() = "Every week"
        override val everyTwoWeek: String
            get() = "Every two week"
        override val everyMonth: String
            get() = "Every month"
        override val everyYear: String
            get() = "Every year"
        override val accept: String
            get() = "Accept"
        override val date: String
            get() = "Date"
        override val day: String
            get() = "Day"
        override val month: String
            get() = "Month"
        override val year: String
            get() = "Year"
        override val time: String
            get() = "Time"
        override val hour: String
            get() = "Hour"
        override val minute: String
            get() = "Minute"
        override val enterYourDescription: String
            get() = "Enter your description"
        override val toastEnterNumberBetweenRange: String
            get() = "Enter a number between 1 and 60"
        override val toastEnterNumber: String
            get() = "Enter number"
        override val toastNameContainTwentyCharacters: String
            get() = "Name can contain only 20 characters"
        override val toastNameContainHundredTwentyCharacters: String
            get() = "Name can contain only 120 characters"
        override val toastEnterName: String
            get() = "Enter name"
    }
}