package com.example.media_matrix;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize preferences and API client
        preferenceManager = new PreferenceManager(this);
        ApiClient.getInstance().setPreferenceManager(preferenceManager);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        setupNavigation();
        setupToolbar();
        setupDrawer();
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNav, navController);
        }

        // Update toolbar title on destination change
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_home) {
                binding.toolbarTitle.setText(R.string.app_name);
            } else if (destination.getId() == R.id.nav_today) {
                binding.toolbarTitle.setText(R.string.nav_today);
            } else if (destination.getId() == R.id.nav_radio) {
                binding.toolbarTitle.setText(R.string.nav_radio);
            } else if (destination.getId() == R.id.nav_search) {
                binding.toolbarTitle.setText(R.string.nav_search);
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                binding.drawerLayout.closeDrawer(binding.navView);
            } else {
                binding.drawerLayout.openDrawer(binding.navView);
            }
        });
    }

    private void setupDrawer() {
        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            binding.drawerLayout.closeDrawers();

            if (id == R.id.drawer_search) {
                navController.navigate(R.id.nav_search);
            } else if (id == R.id.drawer_my_feed) {
                navController.navigate(R.id.nav_home);
            } else if (id == R.id.drawer_trending) {
                navController.navigate(R.id.nav_today);
            } else if (id == R.id.drawer_live_channels) {
                navController.navigate(R.id.nav_radio);
            }
            return true;
        });

        // Update drawer header with user info
        updateDrawerHeader();
    }

    private void updateDrawerHeader() {
        View headerView = binding.navView.getHeaderView(0);
        if (headerView != null && preferenceManager.isLoggedIn()) {
            var user = preferenceManager.getUser();
            if (user != null) {
                android.widget.TextView username = headerView.findViewById(R.id.nav_username);
                username.setText(user.getUsername());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}