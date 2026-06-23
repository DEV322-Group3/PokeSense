package com.example.pokesense

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue                  // getValue = needed for "by"
//import androidx.compose.runtime.mutableStateOf           // mutableStateOf = simple screen state
//import androidx.compose.runtime.remember                 // remember = keeps screen state
//import androidx.compose.runtime.setValue                  // setValue = needed for "by"
import androidx.compose.runtime.collectAsState              // collectAsState = lets MainActivity read ViewModel state

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel     // viewModel = gets EncounterViewModel
import com.example.pokesense.ui.EncounterScreen           // Encounter screen UI
import com.example.pokesense.ui.HomeScreen                // Home screen UI
import com.example.pokesense.ui.CaughtListScreen          // CaughtList screen UI
import com.example.pokesense.ui.ResultScreen                // Result screen after catch
import com.example.pokesense.ui.theme.PokeSenseTheme
import com.example.pokesense.viewmodel.EncounterViewModel // Encounter screen logic/state

import androidx.lifecycle.ViewModel                           // ViewModel = base ViewModel type for the factory
import androidx.lifecycle.ViewModelProvider                   // ViewModelProvider = creates ViewModel with repository
import com.example.pokesense.data.repository.PokemonRepositoryImpl // Real repository worker
import com.example.pokesense.data.local.AppDatabase              // AppDatabase = Room database setup

class MainActivity : ComponentActivity(),  SensorEventListener {

    // sensorManager = system service that provides access to device sensors
    private lateinit var sensorManager: SensorManager

    // lightSensor = ambient light sensor reference
    private var lightSensor: Sensor? = null

    // temperatureSensor = ambient temperature sensor reference
    private var temperatureSensor: Sensor? = null

    // currentLight = latest light sensor reading, default to 0f until first callback
    private var currentLight: Float = 0f

    // currentTemperature = latest temperature sensor reading, default to 0f until first callback
    private var currentTemperature: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // database = gets the app Room database
        val database = AppDatabase.getInstance(applicationContext)

        // pokemonCatchDao = gets Room save/read commands
        val pokemonCatchDao = database.pokemonCatchDao()

        // repository = real worker for API and Room save work
        val repository = PokemonRepositoryImpl(
            pokemonCatchDao = pokemonCatchDao
        )

        // encounterViewModelFactory = creates EncounterViewModel with repository
        val encounterViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return EncounterViewModel(repository) as T
            }
        }

        // sensorManager = gets the sensor system service
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // lightSensor = gets the ambient light sensor
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // temperatureSensor = gets the ambient temperature sensor
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)


        setContent {
            PokeSenseTheme {
//                // currentScreen = decides which screen is showing    // Dont use this, UI will get destroy on rotate
//                var currentScreen by remember { mutableStateOf("home") }

                // encounterViewModel = holds encounter state and starts API call
                val encounterViewModel: EncounterViewModel = viewModel(
                    factory = encounterViewModelFactory
                )

                // uiState = screen state from EncounterViewModel
                val uiState by encounterViewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (uiState.currentScreen) {
                        "home" -> {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                onStartEncounter = {
                                    encounterViewModel.startEncounter(
                                        lightLevel = currentLight,
                                        temperature = currentTemperature
                                    )
                                },
                                onCaughtList = {
                                    encounterViewModel.goToCaughtList()
                                }
                            )
                        }

                        "encounter" -> {
                            EncounterScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = encounterViewModel,
                                onGoHome = {
                                    encounterViewModel.goHome()
                                },
                                onResult = {
                                    encounterViewModel.goToResult()
                                }
                            )
                        }

                        "caughtlist" -> {
                            CaughtListScreen(
                                modifier = Modifier.padding(innerPadding),
                                onGoHome = {
                                    encounterViewModel.goHome()
                                }
                            )
                        }

                        "result" -> {
                            ResultScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = encounterViewModel,
                                onGoHome = {
                                    encounterViewModel.goHome()
                                },
                                onGoCaughtList = {
                                    encounterViewModel.goToCaughtList()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Register light sensor while app is in foreground
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        // Register temperature sensor while app is in foreground
        temperatureSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister all sensors when app goes to background to preserve battery and resources
        sensorManager.unregisterListener(this)
    }

    // called by system when a sensor value changes, store latest reading
    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_LIGHT -> currentLight = event.values[0]
            Sensor.TYPE_AMBIENT_TEMPERATURE -> currentTemperature = event.values[0]
        }
    }

    // onAccuracyChanged = required SensorEventListener override
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeSenseTheme {
        Greeting("Android")
    }
}