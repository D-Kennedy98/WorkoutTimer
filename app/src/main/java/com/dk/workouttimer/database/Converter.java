/*
 * Author: Dominic Kennedy
 * Purpose: define type converter methods for converting List<Exercise> into strings
 *          so that they can be stored as a Workout attribute in the com.dk.workouttimer.database
 *
 */

package com.dk.workouttimer.database;

import com.dk.workouttimer.models.Exercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converter {

    /**
     * convert List<Exercise> to string for com.dk.workouttimer.database storage
     * @param exerciseList exercises objects for conversion
     * @return string form of List<Exercise>
     */
    @TypeConverter
    public String listToString(List<Exercise> exerciseList) {
        if(exerciseList == null) {
            return null;
        }

        Gson gson = new Gson();
        // anon class defining the parameterized list so that gson recognises List<Exercise> not just List
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return gson.toJson(exerciseList, listType);
    }

    /**
     * convert string back to List<Exercise> when retrieving from com.dk.workouttimer.database
     * @param exercisesString string form of List<Exercise> to be converted back
     * @return original List<Exercise> that was stored in com.dk.workouttimer.database
     */
    @TypeConverter
    public List<Exercise> stringToList(String exercisesString) {
        if(exercisesString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return gson.fromJson(exercisesString, listType);
    }

}