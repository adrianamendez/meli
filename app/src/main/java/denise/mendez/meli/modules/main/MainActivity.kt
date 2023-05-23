package denise.mendez.meli.modules.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import denise.mendez.meli.R
import denise.mendez.meli.utils.hideKeyboard

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController().addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController().removeOnDestinationChangedListener(this)
    }

    private fun navController(): NavController =
        findNavController(R.id.fragment_host)

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        currentFocus?.hideKeyboard()
    }
}
