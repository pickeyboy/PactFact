# PactFact

 This is Android Studio Gradle Project. Minimum SDK version configured is 14.

 There is no default signing key hence the app compiles for Debug mode only. 

 To build the app run following command 
	
                ./gradlew assembleDebug


 To run unit test cases run the following command from app folder
	
               ./gradlew clean test


 post-compiled test report can be found in the path below:

                app/build/reports/tests/debug/index.html

Unit testcases are written using jUnit framework, mockito & Robolectric. 
The same ccan be found in app/src/test/ folder. 
