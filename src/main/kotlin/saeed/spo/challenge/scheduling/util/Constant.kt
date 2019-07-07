package saeed.spo.challenge.scheduling.util

//building
const val MAX_BUILDING_COUNT = 100
const val MIN_BUILD_COUNT = 1

const val MAX_BUILDING_COUNT_ERROR_MSG = "err.building.maxCountIs100"
const val MIN_BUILDING_COUNT_ERROR_MSG = "err.building.minCountIs1"
const val NO_ROOM_IN_BUILDING_ERROR_MSG= "err.building.noRoom"

//rooms
const val MAX_ROOM_COUNT = 100
const val MIN_ROOM_COUNT = 1

const val MAX_ROOM_COUNT_ERROR_MSG = "err.building.room.maxCountIs100"
const val MIN_ROOM_COUNT_ERROR_MSG = "err.building.room.minCountIs1"

//cleaners
const val MIN_CLEANING_CAPACITY = 1
const val MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG = "err.building.room.cleaner.minCleaningCapacitySeniorIs1"
const val MIN_CLEANING_CAPACITY_JUNIOR_ERROR_MSG = "err.building.room.cleaner.minCleaningCapacityJuniorIs1"
const val JUNIOR_CLEANER_CAPACITY_GREATER_EQUAL_TO_SENIOR_CLEANER = "err.building.room.cleaner.seniorCapacityShouldBeMoreThanJuniorCapacity"