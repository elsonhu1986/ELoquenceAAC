package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object IconHelper {

  fun getIcon(name: String): ImageVector {
    return when (name.lowercase()) {
      "person" -> Icons.Default.Person
      "group" -> Icons.Default.Groups
      "face" -> Icons.Default.Face
      "sentiment_satisfied" -> Icons.Default.SentimentSatisfied
      "school" -> Icons.Default.School
      "medical_services" -> Icons.Default.MedicalServices
      "touch_app" -> Icons.Default.TouchApp
      "priority_high" -> Icons.Default.PriorityHigh
      "arrow_forward" -> Icons.AutoMirrored.Filled.ArrowForward
      "stop_circle" -> Icons.Default.StopCircle
      "thumb_up" -> Icons.Default.ThumbUp
      "health_and_safety" -> Icons.Default.HealthAndSafety
      "restaurant" -> Icons.Default.Restaurant
      "local_cafe" -> Icons.Default.LocalCafe
      "sports_esports" -> Icons.Default.SportsEsports
      "visibility" -> Icons.Default.Visibility
      "bedtime" -> Icons.Default.Bedtime
      "door_sliding" -> Icons.Default.MeetingRoom
      "add" -> Icons.Default.Add
      "check_circle" -> Icons.Default.CheckCircle
      "sentiment_very_satisfied" -> Icons.Default.SentimentVerySatisfied
      "sentiment_dissatisfied" -> Icons.Default.SentimentDissatisfied
      "zoom_out_map" -> Icons.Default.ZoomOutMap
      "pinch" -> Icons.Default.Pinch
      "whatshot" -> Icons.Default.Whatshot
      "ac_unit" -> Icons.Default.AcUnit
      "speed" -> Icons.Default.Speed
      "healing" -> Icons.Default.Healing
      "wc" -> Icons.Default.Wc
      "water_drop" -> Icons.Default.WaterDrop
      "restaurant_menu" -> Icons.Default.RestaurantMenu
      "mood" -> Icons.Default.Mood
      "location_on" -> Icons.Default.LocationOn
      "celebration" -> Icons.Default.Celebration
      "tablet_mac" -> Icons.Default.TabletMac
      "bed" -> Icons.Default.Bed
      "menu_book" -> Icons.Default.MenuBook
      "smart_toy" -> Icons.Default.SmartToy
      "done" -> Icons.Default.Done
      "close" -> Icons.Default.Close
      "waving_hand" -> Icons.Default.WavingHand
      "logout" -> Icons.AutoMirrored.Filled.Logout
      "volunteer_activism" -> Icons.Default.VolunteerActivism
      "favorite" -> Icons.Default.Favorite
      "hourglass_empty" -> Icons.Default.HourglassEmpty
      "sentiment_neutral" -> Icons.Default.SentimentNeutral
      "input" -> Icons.Default.Input
      "vertical_align_top" -> Icons.Default.VerticalAlignTop
      "vertical_align_bottom" -> Icons.Default.VerticalAlignBottom
      "link" -> Icons.Default.Link
      "arrow_right_alt" -> Icons.AutoMirrored.Filled.ArrowRightAlt
      "place" -> Icons.Default.Place
      "near_me" -> Icons.Default.NearMe
      "help_outline" -> Icons.AutoMirrored.Filled.HelpOutline
      "explore" -> Icons.Default.Explore
      "contact_support" -> Icons.Default.ContactSupport
      "psychology_alt" -> Icons.Default.Psychology
      "schedule" -> Icons.Default.Schedule
      "block" -> Icons.Default.Block
      "question_mark" -> Icons.AutoMirrored.Filled.HelpOutline
      "local_bar" -> Icons.Default.LocalBar
      "coffee" -> Icons.Default.Coffee
      "fastfood" -> Icons.Default.Fastfood
      "nutrition" -> Icons.Default.Eco
      "local_pizza" -> Icons.Default.LocalPizza
      "lunch_dining" -> Icons.Default.LunchDining
      "bakery_dining" -> Icons.Default.BakeryDining
      "rice_bowl" -> Icons.Default.RiceBowl
      "forest" -> Icons.Default.Forest
      "cookie" -> Icons.Default.Cookie
      "icecream" -> Icons.Default.Icecream
      "sentiment_very_dissatisfied" -> Icons.Default.SentimentVeryDissatisfied
      "sick" -> Icons.Default.Sick
      "mood_bad" -> Icons.Default.MoodBad
      "warning" -> Icons.Default.Warning
      "spa" -> Icons.Default.Spa
      "emergency" -> Icons.Default.Emergency
      "home" -> Icons.Default.Home
      "park" -> Icons.Default.Park
      "wb_sunny" -> Icons.Default.WbSunny
      "directions_car" -> Icons.Default.DirectionsCar
      "shopping_cart" -> Icons.Default.ShoppingCart
      "local_hospital" -> Icons.Default.LocalHospital
      "music_note" -> Icons.Default.MusicNote
      "tv" -> Icons.Default.Tv
      "draw" -> Icons.Default.Draw
      "directions_walk" -> Icons.Default.DirectionsWalk
      "extension" -> Icons.Default.Extension
      "cleaning_services" -> Icons.Default.CleaningServices
      "folder" -> Icons.Default.Folder
      "star" -> Icons.Default.Star
      "chat" -> Icons.AutoMirrored.Filled.Chat
      "volume_up" -> Icons.AutoMirrored.Filled.VolumeUp
      else -> Icons.AutoMirrored.Filled.Chat
    }
  }

  val availableIconNames = listOf(
    "person", "group", "face", "school", "medical_services",
    "touch_app", "priority_high", "arrow_forward", "stop_circle", "thumb_up",
    "health_and_safety", "restaurant", "local_cafe", "sports_esports", "visibility",
    "bedtime", "door_sliding", "add", "check_circle", "sentiment_very_satisfied",
    "sentiment_dissatisfied", "healing", "wc", "water_drop", "restaurant_menu",
    "mood", "location_on", "celebration", "tablet_mac", "bed", "menu_book",
    "smart_toy", "done", "close", "favorite", "place", "home", "park",
    "directions_car", "shopping_cart", "music_note", "tv", "draw", "directions_walk",
    "star", "chat"
  )
}
