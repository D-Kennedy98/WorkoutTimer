# WorkoutTimer

WorkoutTimer is an Andorid Mobile Application for timing workouts. I initially developed WorkoutTimer as part my Mobile Application Development course at the University of Newcastle. I have since improved my design by implementing dynamic features which allow the user to create their own custom workouts.
The workouts are composed of sets of exercises in which the user can customise their name and length. This allows for a completely personalised experience which caters for any user's workout requirements. The workouts are saved locally using the Room Persistence Library, enabling users to return to their workouts any time.

**Creating a Workout**
1. In the Workouts screen, tap the '+' button to navigate to the Create Workout screen.
2. From the Create Workout Screen, input a title for the workout (max 35 characters). 
3. Input an exercise name (max 30 characters) and a duration (max 90 mins), then tap the 'Add Exercise' button. (The max duration for a workout is 300 mins).
4. Once you have added all the exercises that you require, tap the 'Save' button.

**Starting a Workout**
1. Tap the a workout tile to navigate the Timer screen for the chosen workout. 
2. Tap the start button to start the workout timer. You can start or stop the workout at any time using the 'Start' or 'Stop' buttons.
3. When the workout is complete, an alarm will sound. You have the choice of restarting the workout using the 'Restart' button or navigating back the Workouts screen using the 'Back' button in the top left of the screen.

**Deleting a Workout**
1. In the Workouts screen, swipe any workout tile off the screen to delete it. This will permanently remove it from the database.




Min SDK Version: 23 (API 23: Android 6.0 Marshmallow)

Target SDK Version: 29 (API 29: Android 10.0 Q)
