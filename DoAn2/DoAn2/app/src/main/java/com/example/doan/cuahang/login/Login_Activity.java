package com.example.doan.cuahang.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.doan.Home.MainActivity;
import com.example.doan.R;
import com.example.doan.admin.AdminActivity;
import com.example.doan.cuahang.StoreActivity;
import com.example.doan.model.Customer;
import com.example.doan.model.Store;
import com.example.doan.model.User;
import com.example.doan.signup.SignUpActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
    private static final int RESULT_CODE_GOOGLE = 100;
    GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    Button btnLogin, btnGoogle;
    TextView txtSignUp;
    TextInputLayout inputEmail, inputPass;
    TextInputEditText edtemail, edtpassword;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ProgressBar pb;
    FirebaseDatabase database;
    LocationManager locationManager;
    boolean GpsStatus;
    int PERMISSION_ALL = 1;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        init();
        edtemail.addTextChangedListener(new Login_Activity.ValidationTextWatcher(edtemail));
        edtpassword.addTextChangedListener(new Login_Activity.ValidationTextWatcher(edtpassword));
        btnGoogle.setOnClickListener(view -> signIn());
        //cấp quyền
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        final ProgressDialog dialog = new ProgressDialog(Login_Activity.this);
        dialog.setMessage("Vui lòng đợi");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        //dialog.show();


        if (mAuth.getCurrentUser() != null) {
            final String userID = fAuth.getCurrentUser().getUid();
            try {
                mData.child("KhachHang").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            Customer user = dataSnapshot.getValue(Customer.class);
                            Log.d("abcxyz", String.valueOf(user));
                            int phanquyen = user.getPhanQuyen();
                            if (phanquyen == 0) {
                                Intent i = new Intent(Login_Activity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                dialog.hide();
                            }
                        } catch (Exception e) {
                            Intent i = new Intent(Login_Activity.this, StoreActivity.class);
                            startActivity(i);
                            finish();
                            dialog.hide();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } catch (Exception e) {
                mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Store user = dataSnapshot.getValue(Store.class);
                        Log.d("abcxyz", String.valueOf(user));
                        Intent i = new Intent(Login_Activity.this, StoreActivity.class);
                        startActivity(i);
                        finish();
                        dialog.hide();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        } else {
            dialog.hide();
        }

        btnLogin.setOnClickListener(v -> {
            if (validateEmail() & validatePassword()) {
                final String email1 = edtemail.getText().toString();
                final String pass1 = edtpassword.getText().toString();
                pb.setVisibility(View.VISIBLE);
                if (email1.equals("admin@gmail.com") && pass1.equals("admin1")) {
                    Intent i = new Intent(Login_Activity.this, AdminActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    mAuth.signInWithEmailAndPassword(email1, pass1)
                            .addOnCompleteListener(Login_Activity.this, task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công",
                                            Toast.LENGTH_SHORT).show();
                                    final String userId = fAuth.getCurrentUser().getUid();
                                    try {
                                        mData.child("KhachHang").child(userId).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                try {
                                                    Customer user = dataSnapshot.getValue(Customer.class);
                                                    Log.d("abcxyz", String.valueOf(user));
                                                    int phanquyen = user.getPhanQuyen();
                                                    if (phanquyen == 0) {
                                                        CheckGpsStatus();
                                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                        DatabaseReference databaseReference1 = firebaseDatabase.getReference("KhachHang");
                                                        databaseReference1.child(userId).child("userPass").setValue(pass1);
                                                        Intent i = new Intent(Login_Activity.this, MainActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                } catch (Exception e) {
                                                    Intent i = new Intent(Login_Activity.this, StoreActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } catch (Exception e) {
                                        mData.child("CuaHang").child(userId).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Store user = dataSnapshot.getValue(Store.class);
                                                Log.d("abcxyz", String.valueOf(user));
                                                Intent i = new Intent(Login_Activity.this, StoreActivity.class);
                                                startActivity(i);
                                                finish();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }


                                } else {
                                    Toast.makeText(Login_Activity.this, "Đăng nhập thất bại",
                                            Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.GONE);
                                }
                            });
                }

            }
        });
        txtSignUp.setOnClickListener(v -> {
            Intent i = new Intent(Login_Activity.this, SignUpActivity.class);
            startActivity(i);
        });
    }


    private void signIn() {
        String serverClientId = getString(R.string.default_web_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();

        GoogleApiClient googleClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleClient);
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);

//        signInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });

        signInClient.silentSignIn().addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            if (account != null) {
                                Log.d("abc", "signIn: " + account.getServerAuthCode());
                                firebaseAuth(account);
                            }// alo e
                        } catch (ApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Log.e("thuynga", task.getException().getMessage());
                        startActivityForResult(signInIntent, RESULT_CODE_GOOGLE);
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("thuynga", e.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case RESULT_CODE_GOOGLE:
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = Auth.GoogleSignInApi.getSignInResultFromIntent(data).getSignInAccount();
                    Log.d("abc", "signIn: " + account.getServerAuthCode());
                    firebaseAuth(account);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }

    private void firebaseAuth(GoogleSignInAccount account) {

//        FirebaseUser user = fAuth.getCurrentUser();
//        User user1 = new User();
//        user1.setUserId(account.getId());
//        user1.setName(account.getDisplayName());
//        user1.setProfile(account.getFamilyName());
//        database.getReference().child("User1").child(account.getId()).setValue(user1);
//        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
//        startActivity(intent);

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        User user1 = new User();
                        user1.setUserId(user.getUid());
                        user1.setName(user.getDisplayName());
                        user1.setProfile(user1.getProfile());
                        database.getReference().child("User1").child(user.getUid()).setValue(user1);
                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login_Activity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //check GPS
    public void CheckGpsStatus() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus) {
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Vui lòng bật vị trí thiết bị để tiếp tục ứng dụng!", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (edtpassword.getText().toString().trim().isEmpty()) {
            inputPass.setError("Bắt buộc nhập mật khẩu");
            requestFocus(edtpassword);
            return false;
        } else if (edtpassword.getText().toString().length() < 6) {
            inputPass.setError("Mật khẩu phải là 6 ký tự");
            requestFocus(edtpassword);
            return false;
        } else {
            inputPass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (edtemail.getText().toString().trim().isEmpty()) {
            inputEmail.setError("Bắt buộc nhập mật Email");
            requestFocus(edtemail);
            return false;
        } else {
            String emailId = edtemail.getText().toString();
            Boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                inputEmail.setError("Sai định dạng Email, ex: abc@example.com");
                requestFocus(edtemail);
                return false;
            } else {
                inputEmail.setErrorEnabled(false);
            }
        }
        return true;
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            if (view.getId() == R.id.edtEmail) {
                validateEmail();
                return;
            } else if (view.getId() == R.id.edtPassword) {
                validatePassword();
                return;
            }
            return;
        }
    }

    public void init() {
        btnLogin = findViewById(R.id.btnSignIn);
        btnGoogle = findViewById(R.id.btnGoogle);
        txtSignUp = findViewById(R.id.txtSignUp);
        edtemail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        pb = findViewById(R.id.pbLogin);
    }
}