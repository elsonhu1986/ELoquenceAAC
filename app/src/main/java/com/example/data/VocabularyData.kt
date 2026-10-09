package com.example.data

import com.example.model.AACTile
import com.example.model.FitzgeraldCategory
import com.example.model.QuickPhrase

object VocabularyData {

  val FOLDER_FOOD = "folder_food"
  val FOLDER_FEELINGS = "folder_feelings"
  val FOLDER_PLACES = "folder_places"
  val FOLDER_ACTIVITIES = "folder_activities"

  val folderNames = mapOf(
    FOLDER_FOOD to "Food & Drinks",
    FOLDER_FEELINGS to "Feelings & Body",
    FOLDER_PLACES to "Places & Rooms",
    FOLDER_ACTIVITIES to "Play & Activities"
  )

  val defaultTiles: List<AACTile> = listOf(
    // --- Pronouns & People (Yellow) ---
    AACTile("p_i", "I", "I", FitzgeraldCategory.PRONOUN_PEOPLE, "person"),
    AACTile("p_you", "You", "you", FitzgeraldCategory.PRONOUN_PEOPLE, "person"),
    AACTile("p_we", "We", "we", FitzgeraldCategory.PRONOUN_PEOPLE, "group"),
    AACTile("p_mom", "Mom", "Mom", FitzgeraldCategory.PRONOUN_PEOPLE, "face"),
    AACTile("p_dad", "Dad", "Dad", FitzgeraldCategory.PRONOUN_PEOPLE, "face"),
    AACTile("p_friend", "Friend", "friend", FitzgeraldCategory.PRONOUN_PEOPLE, "sentiment_satisfied"),
    AACTile("p_teacher", "Teacher", "teacher", FitzgeraldCategory.PRONOUN_PEOPLE, "school"),
    AACTile("p_doctor", "Doctor", "doctor", FitzgeraldCategory.PRONOUN_PEOPLE, "medical_services"),

    // --- Actions & Verbs (Green) ---
    AACTile("v_want", "Want", "want", FitzgeraldCategory.ACTION_VERB, "touch_app"),
    AACTile("v_need", "Need", "need", FitzgeraldCategory.ACTION_VERB, "priority_high"),
    AACTile("v_go", "Go", "go", FitzgeraldCategory.ACTION_VERB, "arrow_forward"),
    AACTile("v_stop", "Stop", "stop", FitzgeraldCategory.ACTION_VERB, "stop_circle"),
    AACTile("v_like", "Like", "like", FitzgeraldCategory.ACTION_VERB, "thumb_up"),
    AACTile("v_help", "Help", "help", FitzgeraldCategory.ACTION_VERB, "health_and_safety"),
    AACTile("v_eat", "Eat", "eat", FitzgeraldCategory.ACTION_VERB, "restaurant"),
    AACTile("v_drink", "Drink", "drink", FitzgeraldCategory.ACTION_VERB, "local_cafe"),
    AACTile("v_play", "Play", "play", FitzgeraldCategory.ACTION_VERB, "sports_esports"),
    AACTile("v_look", "Look", "look", FitzgeraldCategory.ACTION_VERB, "visibility"),
    AACTile("v_sleep", "Sleep", "sleep", FitzgeraldCategory.ACTION_VERB, "bedtime"),
    AACTile("v_open", "Open", "open", FitzgeraldCategory.ACTION_VERB, "door_sliding"),

    // --- Descriptors & Adjectives (Blue) ---
    AACTile("d_more", "More", "more", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "add"),
    AACTile("d_alldone", "All Done", "all done", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "check_circle"),
    AACTile("d_good", "Good", "good", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "sentiment_very_satisfied"),
    AACTile("d_bad", "Bad", "bad", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "sentiment_dissatisfied"),
    AACTile("d_big", "Big", "big", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "zoom_out_map"),
    AACTile("d_little", "Little", "little", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "pinch"),
    AACTile("d_hot", "Hot", "hot", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "whatshot"),
    AACTile("d_cold", "Cold", "cold", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "ac_unit"),
    AACTile("d_fast", "Fast", "fast", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "speed"),
    AACTile("d_hurt", "Hurt", "hurt", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "healing"),

    // --- Nouns & Folders (Orange) ---
    AACTile("n_bathroom", "Bathroom", "bathroom", FitzgeraldCategory.NOUN_OBJECT, "wc"),
    AACTile("n_water", "Water", "water", FitzgeraldCategory.NOUN_OBJECT, "water_drop"),
    AACTile("n_food_folder", "Food & Drink 📁", "food", FitzgeraldCategory.NOUN_OBJECT, "restaurant_menu", targetFolderId = FOLDER_FOOD),
    AACTile("n_feelings_folder", "Feelings 📁", "feelings", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "mood", targetFolderId = FOLDER_FEELINGS),
    AACTile("n_places_folder", "Places 📁", "places", FitzgeraldCategory.NOUN_OBJECT, "location_on", targetFolderId = FOLDER_PLACES),
    AACTile("n_activities_folder", "Activities 📁", "activities", FitzgeraldCategory.ACTION_VERB, "celebration", targetFolderId = FOLDER_ACTIVITIES),
    AACTile("n_tablet", "Tablet", "tablet", FitzgeraldCategory.NOUN_OBJECT, "tablet_mac"),
    AACTile("n_bed", "Bed", "bed", FitzgeraldCategory.NOUN_OBJECT, "bed"),
    AACTile("n_book", "Book", "book", FitzgeraldCategory.NOUN_OBJECT, "menu_book"),
    AACTile("n_toy", "Toy", "toy", FitzgeraldCategory.NOUN_OBJECT, "smart_toy"),

    // --- Social & Etiquette (Purple) ---
    AACTile("s_yes", "Yes", "yes", FitzgeraldCategory.SOCIAL_ETIQUETTE, "done"),
    AACTile("s_no", "No", "no", FitzgeraldCategory.SOCIAL_ETIQUETTE, "close"),
    AACTile("s_hello", "Hello", "hello", FitzgeraldCategory.SOCIAL_ETIQUETTE, "waving_hand"),
    AACTile("s_goodbye", "Goodbye", "goodbye", FitzgeraldCategory.SOCIAL_ETIQUETTE, "logout"),
    AACTile("s_please", "Please", "please", FitzgeraldCategory.SOCIAL_ETIQUETTE, "volunteer_activism"),
    AACTile("s_thankyou", "Thank You", "thank you", FitzgeraldCategory.SOCIAL_ETIQUETTE, "favorite"),
    AACTile("s_wait", "Wait", "wait a moment", FitzgeraldCategory.SOCIAL_ETIQUETTE, "hourglass_empty"),
    AACTile("s_sorry", "Sorry", "I am sorry", FitzgeraldCategory.SOCIAL_ETIQUETTE, "sentiment_neutral"),

    // --- Prepositions & Core Connectors (Pink) ---
    AACTile("prep_in", "In", "in", FitzgeraldCategory.PREPOSITION_GLUE, "input"),
    AACTile("prep_on", "On", "on", FitzgeraldCategory.PREPOSITION_GLUE, "vertical_align_top"),
    AACTile("prep_under", "Under", "under", FitzgeraldCategory.PREPOSITION_GLUE, "vertical_align_bottom"),
    AACTile("prep_with", "With", "with", FitzgeraldCategory.PREPOSITION_GLUE, "link"),
    AACTile("prep_for", "For", "for", FitzgeraldCategory.PREPOSITION_GLUE, "arrow_right_alt"),
    AACTile("prep_here", "Here", "here", FitzgeraldCategory.PREPOSITION_GLUE, "place"),
    AACTile("prep_there", "There", "there", FitzgeraldCategory.PREPOSITION_GLUE, "near_me"),

    // --- Questions & Negation (Crimson) ---
    AACTile("q_what", "What?", "what", FitzgeraldCategory.QUESTION_NEGATION, "help_outline"),
    AACTile("q_where", "Where?", "where", FitzgeraldCategory.QUESTION_NEGATION, "explore"),
    AACTile("q_who", "Who?", "who", FitzgeraldCategory.QUESTION_NEGATION, "contact_support"),
    AACTile("q_why", "Why?", "why", FitzgeraldCategory.QUESTION_NEGATION, "psychology_alt"),
    AACTile("q_when", "When?", "when", FitzgeraldCategory.QUESTION_NEGATION, "schedule"),
    AACTile("q_dont", "Don't", "don't", FitzgeraldCategory.QUESTION_NEGATION, "block"),
    AACTile("q_cani", "Can I?", "can I", FitzgeraldCategory.QUESTION_NEGATION, "question_mark"),

    // ==========================================
    // --- Subfolder: Food & Drinks ---
    // ==========================================
    AACTile("food_water", "Water", "water", FitzgeraldCategory.NOUN_OBJECT, "water_drop", folderId = FOLDER_FOOD),
    AACTile("food_juice", "Juice", "juice", FitzgeraldCategory.NOUN_OBJECT, "local_bar", folderId = FOLDER_FOOD),
    AACTile("food_milk", "Milk", "milk", FitzgeraldCategory.NOUN_OBJECT, "coffee", folderId = FOLDER_FOOD),
    AACTile("food_snack", "Snack", "snack", FitzgeraldCategory.NOUN_OBJECT, "fastfood", folderId = FOLDER_FOOD),
    AACTile("food_apple", "Apple", "apple", FitzgeraldCategory.NOUN_OBJECT, "nutrition", folderId = FOLDER_FOOD),
    AACTile("food_pizza", "Pizza", "pizza", FitzgeraldCategory.NOUN_OBJECT, "local_pizza", folderId = FOLDER_FOOD),
    AACTile("food_sandwich", "Sandwich", "sandwich", FitzgeraldCategory.NOUN_OBJECT, "lunch_dining", folderId = FOLDER_FOOD),
    AACTile("food_bread", "Bread", "bread", FitzgeraldCategory.NOUN_OBJECT, "bakery_dining", folderId = FOLDER_FOOD),
    AACTile("food_rice", "Rice", "rice", FitzgeraldCategory.NOUN_OBJECT, "rice_bowl", folderId = FOLDER_FOOD),
    AACTile("food_fruit", "Fruit", "fruit", FitzgeraldCategory.NOUN_OBJECT, "forest", folderId = FOLDER_FOOD),
    AACTile("food_cookie", "Cookie", "cookie", FitzgeraldCategory.NOUN_OBJECT, "cookie", folderId = FOLDER_FOOD),
    AACTile("food_icecream", "Ice Cream", "ice cream", FitzgeraldCategory.NOUN_OBJECT, "icecream", folderId = FOLDER_FOOD),

    // ==========================================
    // --- Subfolder: Feelings & Body ---
    // ==========================================
    AACTile("feel_happy", "Happy", "happy", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "sentiment_very_satisfied", folderId = FOLDER_FEELINGS),
    AACTile("feel_sad", "Sad", "sad", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "sentiment_very_dissatisfied", folderId = FOLDER_FEELINGS),
    AACTile("feel_tired", "Tired", "tired", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "bedtime", folderId = FOLDER_FEELINGS),
    AACTile("feel_sick", "Sick", "sick", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "sick", folderId = FOLDER_FEELINGS),
    AACTile("feel_angry", "Angry", "angry", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "mood_bad", folderId = FOLDER_FEELINGS),
    AACTile("feel_excited", "Excited", "excited", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "celebration", folderId = FOLDER_FEELINGS),
    AACTile("feel_scared", "Scared", "scared", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "warning", folderId = FOLDER_FEELINGS),
    AACTile("feel_calm", "Calm", "calm", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "spa", folderId = FOLDER_FEELINGS),
    AACTile("feel_hungry", "Hungry", "hungry", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "restaurant", folderId = FOLDER_FEELINGS),
    AACTile("feel_thirsty", "Thirsty", "thirsty", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "water_drop", folderId = FOLDER_FEELINGS),
    AACTile("feel_pain", "In Pain", "in pain", FitzgeraldCategory.DESCRIPTOR_ADJECTIVE, "emergency", folderId = FOLDER_FEELINGS),

    // ==========================================
    // --- Subfolder: Places & Rooms ---
    // ==========================================
    AACTile("place_home", "Home", "home", FitzgeraldCategory.NOUN_OBJECT, "home", folderId = FOLDER_PLACES),
    AACTile("place_school", "School", "school", FitzgeraldCategory.NOUN_OBJECT, "school", folderId = FOLDER_PLACES),
    AACTile("place_park", "Park", "park", FitzgeraldCategory.NOUN_OBJECT, "park", folderId = FOLDER_PLACES),
    AACTile("place_outside", "Outside", "outside", FitzgeraldCategory.NOUN_OBJECT, "wb_sunny", folderId = FOLDER_PLACES),
    AACTile("place_car", "Car", "car", FitzgeraldCategory.NOUN_OBJECT, "directions_car", folderId = FOLDER_PLACES),
    AACTile("place_store", "Store", "store", FitzgeraldCategory.NOUN_OBJECT, "shopping_cart", folderId = FOLDER_PLACES),
    AACTile("place_bedroom", "Bedroom", "bedroom", FitzgeraldCategory.NOUN_OBJECT, "bed", folderId = FOLDER_PLACES),
    AACTile("place_hospital", "Hospital", "hospital", FitzgeraldCategory.NOUN_OBJECT, "local_hospital", folderId = FOLDER_PLACES),

    // ==========================================
    // --- Subfolder: Play & Activities ---
    // ==========================================
    AACTile("act_music", "Music", "listen to music", FitzgeraldCategory.ACTION_VERB, "music_note", folderId = FOLDER_ACTIVITIES),
    AACTile("act_tv", "Watch TV", "watch TV", FitzgeraldCategory.ACTION_VERB, "tv", folderId = FOLDER_ACTIVITIES),
    AACTile("act_draw", "Draw", "draw", FitzgeraldCategory.ACTION_VERB, "draw", folderId = FOLDER_ACTIVITIES),
    AACTile("act_game", "Play Game", "play a game", FitzgeraldCategory.ACTION_VERB, "sports_esports", folderId = FOLDER_ACTIVITIES),
    AACTile("act_walk", "Walk", "go for a walk", FitzgeraldCategory.ACTION_VERB, "directions_walk", folderId = FOLDER_ACTIVITIES),
    AACTile("act_puzzle", "Puzzle", "puzzle", FitzgeraldCategory.ACTION_VERB, "extension", folderId = FOLDER_ACTIVITIES),
    AACTile("act_clean", "Clean Up", "clean up", FitzgeraldCategory.ACTION_VERB, "cleaning_services", folderId = FOLDER_ACTIVITIES)
  )

  val defaultQuickPhrases: List<QuickPhrase> = listOf(
    QuickPhrase("qp_help", "Need Help", "I need help right now.", "Urgent", "emergency", isHighPriority = true),
    QuickPhrase("qp_bathroom", "Bathroom", "I need to use the bathroom.", "Urgent", "wc", isHighPriority = true),
    QuickPhrase("qp_pain", "In Pain", "I am in pain.", "Urgent", "healing", isHighPriority = true),
    QuickPhrase("qp_overwhelmed", "Need Break", "I feel overwhelmed and need a quiet break.", "Comfort", "spa", isHighPriority = true),
    QuickPhrase("qp_hungry", "Hungry", "I am hungry and would like something to eat.", "Needs", "restaurant"),
    QuickPhrase("qp_thirsty", "Thirsty", "I am thirsty, can I please have some water?", "Needs", "water_drop"),
    QuickPhrase("qp_yes", "Yes Please", "Yes, please.", "Social", "done"),
    QuickPhrase("qp_no", "No Thanks", "No, thank you.", "Social", "close"),
    QuickPhrase("qp_wait", "Give Me Time", "Please give me a moment to type my thoughts.", "Communication", "hourglass_empty"),
    QuickPhrase("qp_love", "I Love You", "I love you so much.", "Social", "favorite"),
    QuickPhrase("qp_all_done", "I Am Finished", "I am all done now.", "Daily", "check_circle"),
    QuickPhrase("qp_where_going", "Where Going?", "Where are we going next?", "Questions", "explore")
  )
}
