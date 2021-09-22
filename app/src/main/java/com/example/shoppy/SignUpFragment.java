package com.example.shoppy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppy.Modals.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv_alreadyacc;
    private FrameLayout frameLayout;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button signup;
    private ProgressBar progressBar;
    private FloatingActionButton close;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public static boolean disableCloseBtn = false;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        tv_alreadyacc = view.findViewById(R.id.tv_alreadyacc);
        frameLayout = getActivity().findViewById(R.id.register_fl);
        name = view.findViewById(R.id.signup_name);
        email = view.findViewById(R.id.signup_email);
        password = view.findViewById(R.id.signup_pass);
        signup = view.findViewById(R.id.btn_signup);
        progressBar = view.findViewById(R.id.progressBar);
        close = view.findViewById(R.id.btn_close_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(disableCloseBtn){
            close.setVisibility(View.GONE);
        }else{
            close.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // as guest
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = false;
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // changing fragment
        tv_alreadyacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        // signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Enter valid data", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signup.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    // sending verification mail
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                // adding into firestore database
                                                String uid = firebaseAuth.getUid();
                                                User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString());

                                                firebaseFirestore.collection("Users").document(uid).set(user).
                                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                Map<String,Object> listSize = new HashMap<>();
                                                                listSize.put("list_size",(long)0);
                                                                firebaseFirestore.collection("Users").document(uid).collection("USER_DATA").document("MY_WISHLIST")
                                                                        .set(listSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            Toast.makeText(getActivity(), "Please verify your email address by clicking on the received email link and then Login", Toast.LENGTH_LONG).show();
                                                                        }else{
                                                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            signup.setEnabled(true);
                                                                        }
                                                                    }
                                                                });
                                                            }else{
                                                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                signup.setEnabled(true);
                                                                }
                                                            }
                                                        });
                                            }else{
                                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                                signup.setEnabled(true);
                                            }
                                        }
                                    });

                                }else{
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signup.setEnabled(true);
                                }
                            }
                        });
            }
        });
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slidein_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}