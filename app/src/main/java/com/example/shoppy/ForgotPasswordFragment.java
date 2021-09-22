package com.example.shoppy;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.shoppy.RegisterActivity.isForgotPasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FloatingActionButton back;
    private Button sendmail;
    private FrameLayout frameLayout;
    private ViewGroup container;
    private ProgressBar progressBar;
    private ImageView mail_img;
    private TextView msg;
    private EditText email;

    private FirebaseAuth firebaseAuth;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View view =  inflater.inflate(R.layout.fragment_forgot_password, container, false);

        back = view.findViewById(R.id.btn_back);
        sendmail = view.findViewById(R.id.btn_send_mail);
        frameLayout = getActivity().findViewById(R.id.register_fl);
        progressBar = view.findViewById(R.id.progressBar_fp);
        mail_img = view.findViewById(R.id.img_mail);
        msg = view.findViewById(R.id.mail_msg);
        container = view.findViewById(R.id.email_img_msg_container);
        email = view.findViewById(R.id.forgot_pass_email);

        firebaseAuth = FirebaseAuth.getInstance();

        mail_img.setVisibility(View.GONE);
        msg.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Enter valid data", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendmail.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                mail_img.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            String success_msg = "Reset Link has been sent";
                            mail_img.setImageResource(R.drawable.green_mail);
                            msg.setTextColor(getResources().getColor(R.color.success));
                            msg.setText(success_msg);
                            msg.setVisibility(View.VISIBLE);

                        }else{
                            String error = task.getException().getMessage();
                            mail_img.setImageResource(R.drawable.red_mail);
                            msg.setTextColor(getResources().getColor(R.color.failure));
                            msg.setText(error);
                            msg.setVisibility(View.VISIBLE);
                            sendmail.setEnabled(true);
                        }
                    }
                });
            }
        });

        // back to sign in
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isForgotPasswordFragment = false;
                setFragment(new SignInFragment());
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