package com.example.shoppy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.shoppy.Modals.DBqueries;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.shoppy.RegisterActivity.setSignUpFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int MY_ACCOUNT_FRAGMENT = 5;
    private NavigationView navigationView;
    private ImageView actionBarLogo;
    private Toolbar toolbar;
    private Window window;

    public static DrawerLayout drawer;

    private Dialog signInDialog;
    private FirebaseUser currentUser;

    public static boolean showCart = false;

    private int currentFragment=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        actionBarLogo = findViewById(R.id.action_bar_logo);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#555BED"));
        actionBar.setBackgroundDrawable(colorDrawable);

        frameLayout = findViewById(R.id.main_fl);

        drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_my_order, R.id.nav_my_wishlist,R.id.nav_rewards,R.id.nav_my_cart,R.id.nav_my_account,R.id.nav_signout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
                if(currentUser != null) {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        actionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), HOME_FRAGMENT);
                    } else if (id == R.id.nav_my_order) {
                        goToFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.nav_my_wishlist) {
                        goToFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);
                    } else if (id == R.id.nav_rewards) {
                        goToFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);
                    } else if (id == R.id.nav_my_cart) {
                        goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.nav_my_account) {
                        goToFragment("My Account", new MyAccountFragment(), MY_ACCOUNT_FRAGMENT);
                    } else if (id == R.id.nav_signout) {
                        FirebaseAuth.getInstance().signOut();
                        DBqueries.clearData();
                        Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();
                    }
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }else{
                    drawer.closeDrawer(GravityCompat.START);
                    signInDialog.show();
                    return false;
                }
            }
        });
        if(showCart){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart",new MyCartFragment(),-2);
        }else{
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(),HOME_FRAGMENT);
        }

        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.dialog_signin_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.dialog_signup_btn);

        final Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else{
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment == HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_search){
            // todo: appbar action search
            return true;
        }else if(id==R.id.action_notification){
            // todo: appbar action notification
            return true;
        }else if(id==R.id.action_cart){
            // todo: appbar action cart
            if(currentUser == null){
                signInDialog.show();
            }else{
                goToFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
            }
            return true;
        }else if(id == android.R.id.home){
            if(showCart){
                showCart = false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title,Fragment fragment,int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo == CART_FRAGMENT){
            navigationView.getMenu().getItem(4).setChecked(true);
        }
    }

    private void setFragment(Fragment fragment,int fragmentNo){
        if(fragmentNo == currentFragment) {
            return ;
        }
        if(fragmentNo == REWARDS_FRAGMENT){
            window.setStatusBarColor(Color.parseColor("#5B04B1"));
            toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
        }else{
            window.setStatusBarColor(getResources().getColor(R.color.purple_700));
//            window.setStatusBarColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }
        currentFragment = fragmentNo;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if(currentFragment == HOME_FRAGMENT){
                currentFragment = -1;
                super.onBackPressed();
            }else {
                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    actionBarLogo.setVisibility(View.VISIBLE);
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    invalidateOptionsMenu();
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }
}