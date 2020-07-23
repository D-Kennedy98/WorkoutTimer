package com.dk.workouttimer;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converter {

    @TypeConverter
    public String ListToString(List<Exercise> exerciseList){
        if(exerciseList == null) {
            return null;
        }

        Gson gson = new Gson();
        // anon class defining the parameterized list so that gson recognises List<Exercise> not just List
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return gson.toJson(exerciseList, listType);
    }

    @TypeConverter
    public List<Exercise> StringToWorkout(String exercisesString) {
        if(exercisesString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return gson.fromJson(exercisesString, listType);
    }



}
