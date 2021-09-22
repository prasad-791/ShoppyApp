package com.example.shoppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public static boolean isForgotPasswordFragment = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        frameLayout = findViewById(R.id.register_fl);
        if(setSignUpFragment){
            setSignUpFragment = false;
            setDefauLtFragment(new SignUpFragment());
        }else{
            setDefauLtFragment(new SignInFragment());
        }
    }
    private void setDefauLtFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slidein_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            SignUpFragment.disableCloseBtn = false;
            SignInFragment.disableCloseBtn = false;
            if(isForgotPasswordFragment){
                isForgotPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}