# MoodApp-2.0
First app in Android Studio. Using JSON data received from URL displays it as a recipe in recipe_activity and main picture in main_activity. Options_activity enables moving between those two activities, allows logging into FB account using profile picture and name of the User, also.

# APIs used:
 - Facebook - logging in the FB account
 - Graph - retrievieng data from logged-in account
 - Picasso - displaying images

# TODO
 - rearrange the fb logging + refine logout
 - move data-retrieving actions to main activity ( use intents: from main -> EVERYWHERE )
 - transfer FAB from main to options_activity
