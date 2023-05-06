package com.zohausman.mycandycotton.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.zohausman.mycandycotton.R;


public class DashBoard extends AppCompatActivity {
Button btnlogout;
public DrawerLayout drawerLayout;
public ActionBarDrawerToggle actionBarDrawerToggle;
NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setTitle("CottonCandi");

        // Set the ActionBar
        setSupportActionBar(findViewById(R.id.toolbar));

        try {
            btnlogout = findViewById(R.id.Logout);
            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }
            });

            // Navigation
            nav = findViewById(R.id.navmenuview);
            drawerLayout = findViewById(R.id.my_drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(
                    DashBoard.this,
                    drawerLayout,
                    R.string.open_drawer,
                    R.string.close_drawer
            );

            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_1:
                            Toast.makeText(DashBoard.this, "You clicked on Menu", Toast.LENGTH_SHORT).show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}