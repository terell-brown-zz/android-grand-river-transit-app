_**On Demand Grand River Transit Information for Kitchener-Waterloo-Cambridge.**_

###Overview:
Provides up-to-date transit information for Kitchener-Waterloo-Cambridge including all routes and stops operated by Grand River Transit.

Never miss your bus again because you don't have data to search for arrival times. All your transit information is completely accessible without Data or Wi-Fi.

Are you unaware of your surroundings but need to find a bus to take as soon as possible? The Nearby Stops feature shows you a map of your current location and some stops that are nearby. Select one of those stops and you'll be presented with all the buses going to that stop and their arrival times within the next four hours (Note: This feature does requires GPS, WiFi or Data)

And last but not least -- Is there a certain bus you take frequently or a stop you use everyday on your way to school or work? Save it to your favorites list for easy access to relevant, personalized transit information as soon as you open the app.

[![Play Store](http://developer.android.com/images/brand/en_generic_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=ca.simba.grtwaterloo&hl=en)

###Screenshots:
<img src=https://cloud.githubusercontent.com/assets/8221118/8537062/af5fdc52-2416-11e5-9cca-1dbc4d561bbb.png width="150">
<img src=https://cloud.githubusercontent.com/assets/8221118/8537063/af628d9e-2416-11e5-8503-28ddb0adcab5.png width="150">
<img src=https://cloud.githubusercontent.com/assets/8221118/8537065/af6337d0-2416-11e5-93c6-c0b21760f2f8.png width="150">
<img src=https://cloud.githubusercontent.com/assets/8221118/8537064/af62dcf4-2416-11e5-8100-ce0ad4451885.png width="150">
<img src=https://cloud.githubusercontent.com/assets/8221118/8537066/af6632c8-2416-11e5-9e2d-108988da2bcb.png width="150">


###Source of Transit Data:
Transit data including all routes and bus stops operated by Grand River Transit was downloaded from the following website: http://www.regionofwaterloo.ca/en/regionalGovernment/GRT_GTFSdata.asp

###Learnings:
####UI:
* Basic Material Design Guidelines
* RecyclerView / Custom Adapter / LayoutManager / ItemDecorator/ItemAnimator
* Toasts / Snackbars / Floating Action Button
* Toolbad vs. Action Bar
* Sliding Tab Layout / View Pager
* Sliding Navigation Drawer
* Best practices for resource files including string/id/dimen.xml
* Embedded Web Browser

####Databases:
* Database Design Principles
(Tables, Fields, Records, Primary Key, Foreign Key, Composite Key, Relationships, Orthogonality)
* SQLiteOpenHelper, SQLiteAssetHelper
* SQLite, MySQL Queries
* Dealing with Cursors

####Backend Components:
* Google Maps API
* Running Tasks on Background Threads
* Storing Favourites and Settings using SharedPreferences

####Architecture Design:
* Creating Activiry Superclass to implement similarites of other activities
* Singleton Design Pattern for SharedPreferences such as Favourites and Settings
* Dedicated class to store all constants for global access by other classes
* Dedicated class for initial launch setup

####Miscelaneous:
* Dealing with Dates and Times
* Publishing App to Play Store


###Improvement Areas:
Through a post on [Reddit](https://www.reddit.com/r/androiddev/comments/3cff1g/feedback_on_code_formatting_in_android_project_i/) requesting feed back on my android projects I recieved several suggestions for areas of improvement which include:

- [ ] Using ButterKnife for effecient View Binding
- [ ] Using an interface to implement the singleton design pattern
- [ ] Using ReactiveLocation for efficient implementation of GoogleMaps API
- [ ] Using AsyncTask / LoaderManager or RxJava to run non UI methods in background thread
- [ ] Access database using DAO instead of simply DBHelper
- [ ] Use support annotation such as @Nullable, @NonNull, @UiThread , etc.

I will do my best to make these changes to the project, however it is already quite large and will take quite a bit of refactoring. I might instead implement these learning in upcoming projects from scratch. 

 



