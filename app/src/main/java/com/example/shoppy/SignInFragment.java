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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import static com.example.shoppy.RegisterActivity.isForgotPasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FrameLayout frameLayout;
    private TextView tv_newacc;
    private EditText email;
    private EditText password;
    private Button signin;
    private ProgressBar progressBar;
    private FloatingActionButton close;
    private TextView forgotpass;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public static boolean disableCloseBtn = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);

        tv_newacc = view.findViewById(R.id.tv_newacc);
        frameLayout = getActivity().findViewById(R.id.register_fl);
        email = view.findViewById(R.id.signin_email);
        password = view.findViewById(R.id.signin_password);
        signin = view.findViewById(R.id.btn_signin);
        progressBar = view.findViewById(R.id.progressBarSignIn);
        close = view.findViewById(R.id.btn_close_signin);
        forgotpass = view.findViewById(R.id.tv_forgotpass);

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
        // forgot password
        forgotpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isForgotPasswordFragment = true;
                setFragment(new ForgotPasswordFragment());
            }
        });

        // as a guest
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = false;
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // to create new account
        tv_newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        // signing in
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Enter valid data", Toast.LENGTH_SHORT).show();
                    return ;
                }
                progressBar.setVisibility(View.VISIBLE);
                signin.setEnabled(false);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);

                            // email verification
                            if(firebaseAuth.getCurrentUser().isEmailVerified()) {

                                String uid = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firebaseFirestore.collection("Users").document(uid);
                                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        String docpass = value.getString("password");

                                        if(!docpass.equals(password.getText().toString())){
                                            Toast.makeText(getActivity(), " Updated info", Toast.LENGTH_SHORT).show();
                                            documentReference.update("password",password.getText().toString());
                                        }
                                    }
                                });
                                if(disableCloseBtn){
                                    disableCloseBtn = false;
                                }else{
                                    Intent intent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(intent);
                                }
                                getActivity().finish();
                            }else{
                                Toast.makeText(getActivity(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                signin.setEnabled(true);
                            }
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            signin.setEnabled(true);
                        }
                    }
                });
            }
        });
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slidein_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}