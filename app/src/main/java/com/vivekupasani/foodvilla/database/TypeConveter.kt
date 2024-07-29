package com.vivekupasani.foodvilla.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class TypeConveter {

    @TypeConverter
    fun anytoString(value: Any?): String {
        if (value == null) {
            return " "
        } else {
            return value as String
        }
    }

    @TypeConverter
    fun StringtoAny(value : String?) : Any{
        if (value == null){
            return " "
        }
        else{
            return value
        }
    }

}