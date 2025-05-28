package es.oaemdl.apkcavoshcafe.ui;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executors;

import es.oaemdl.apkcavoshcafe.MainActivity;
import es.oaemdl.apkcavoshcafe.R;
import es.oaemdl.apkcavoshcafe.controllers.UsuarioDAO;
import es.oaemdl.apkcavoshcafe.databinding.FragmentLoginBinding;
import es.oaemdl.apkcavoshcafe.models.Usuario;

public class Login extends Fragment {
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;

    private static final String TYPE_GOOGLE_ID_TOKEN_CREDENTIAL = GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

    private CredentialManager credentialManager;
    FragmentLoginBinding binding;
    View view;
    Context context;
    NavController navController;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate( inflater, container, false );
        return view = binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        navController = Navigation.findNavController( view );
        credentialManager = CredentialManager.create(getActivity().getBaseContext());
        mAuth = FirebaseAuth.getInstance();

        binding.tvRegister.setOnClickListener( v -> navController.navigate( R.id.navigation_registrar ) );
        binding.tvRegistrate.setOnClickListener( v -> navController.navigate( R.id.navigation_registrar ) );
        binding.tvOlvidarPasswordd.setOnClickListener(v -> navController.navigate( R.id.navigation_olvidar_passwordd ) );
        binding.btnLogin.setOnClickListener( v -> btnLogin_Click() );
        binding.btnLoginGoogle.setOnClickListener( v -> btnLoginGoogle_Click() );

        binding.edtCorreo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilCorreo.setError( null );
            }

            @Override public void afterTextChanged(Editable s) { }
        });

        binding.edtPasswordd.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilPasswordd.setError( null );
            }

            @Override public void afterTextChanged(Editable s) { }
        });

    }

    private void btnLoginGoogle_Click() {
        Log.d(TAG, "btnLoginGoogle_Click() llamado. Iniciando flujo de Google Sign-In.");
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(getString(R.string.default_web_client_id))
                .build();


        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        credentialManager.getCredentialAsync(
                getActivity().getBaseContext(),
                request,
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {

                        handleSignIn(result.getCredential());
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e(TAG, "Couldn't retrieve user's credentials: " + e.getLocalizedMessage());
                    }
                }
        );
    }

    private void handleSignIn(Credential credential) {
        if (getActivity() != null) {
            requireActivity().runOnUiThread(() -> {

                if (credential instanceof CustomCredential
                        && credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {

                    Bundle credentialData = ((CustomCredential) credential).getData();
                    GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);


                    Toast.makeText(context, "Inicio de sesión con Google: " + googleIdTokenCredential.getId(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ID de Google: " + googleIdTokenCredential.getId());

                    firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken());
                } else {
                    Log.w(TAG, "La credencial no es de tipo Google ID!");
                    Toast.makeText(context, "Credencial no reconocida para Google.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());

                        if (getActivity() != null) {
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(context, "Fallo la autenticación de Firebase.", Toast.LENGTH_SHORT).show();
                            });
                        }
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (getActivity() != null) {
            requireActivity().runOnUiThread(() -> {
                if (user != null) {
                    Toast.makeText(context, "Usuario autenticado: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.navigation_inicio);
                } else {
                    Toast.makeText(context, "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void btnLogin_Click() {
        String sCorreo = binding.edtCorreo.getText().toString();
        String sPasswordd = binding.edtPasswordd.getText().toString();
        String sMensaje = "";

        if ( sCorreo.isEmpty() ) binding.tilCorreo.setError("Ingrese correo");
        if ( sPasswordd.isEmpty() ) binding.tilPasswordd.setError("Ingrese contraseña");

        if ( binding.tilCorreo.getError() != null ||
             binding.tilPasswordd.getError() != null  ) return;

        Usuario usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO( context);

        usuario.setCorreo( sCorreo );
        usuario.setPasswordd( sPasswordd );

        if(usuarioDAO.Login(usuario)){
            MainActivity.usuario = usuario;
            navController.navigate(R.id.navigation_inicio);
        }else{
            Snackbar.make( view, "Usuario y/o contraseña inválido", Snackbar.LENGTH_LONG ).show();
        }


    }

}