# WorkoutTimer

I initially developed WorkoutTimer as part my Mobile Application Development course at the University of Newcastle. I have since improved my design by implementing dynamic features which allow the user to create their own custom workouts.

The workouts are composed of sets of exercises in which the user has control of their name and length. This allows for a completely personalised experience which caters for the any user's workout requirements. The workouts are saved locally using the Room Persistence Library, enabling users to return to their workouts when anytime.

<b>Creating a Workout</b>

-In the Workouts screen, tap the '+' icon to navigate to the Create Workout screen.
-From the Create Workout Screen, input a title for the workout (max 35 characters). 
-Input an exercise name (max 30 characters) and a duration (max 90 mins), then tap the 'Add Exercise' button. (The max duration for a workout is 300 mins).
-Once you have added all the exercises that you require, tap the save icon.

<b>Starting a Workout</b>

-Tap the workout tile to navigate the Timer screen. 
-Tap the start button to start the workout timer. You can start or stop the workout at any time using the 'Start' or 'Stop' buttons.
-When the workout is complete, an alarm will sound. You have the choice of restarting the workout using the 'Restart' button or navigating back the Workouts screen using the 'Back' button in the top left of the screen.

<b>Deleting a Workout</b>

-In the Workouts screen, swipe any workout tile off the screen to delete it. This will permanently remove it from the database.
