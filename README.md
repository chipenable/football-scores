Football Scores

It's a training app for Android Udacity course. It wasn't created from 
scratch, I had almost ready application, that I had to change to pass specification.

List of changes:

1. I created a collection widget that shows today football scores. 
2. Design of the app was changed a little. 
3. I added contentDescription tag for certain views. 
4. The app supports layout mirroring now. 
5. All strings were moved to string.xml file. 
6. Fixed small bag. onCreate(db) was added in onUpgrade() function (ScoresDBHelper.java) 

To build and launch this app you must add API KEY in build.gradle file.
You can get API KEY on http://api.football-data.org/register
