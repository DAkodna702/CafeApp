package es.oaemdl.apkcavoshcafe.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import es.oaemdl.apkcavoshcafe.MainActivity;
import es.oaemdl.apkcavoshcafe.R;
import es.oaemdl.apkcavoshcafe.controllers.UsuarioDAO;
import es.oaemdl.apkcavoshcafe.databinding.FragmentLoginBinding;
import es.oaemdl.apkcavoshcafe.models.Usuario;

public class Login extends Fragment {
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

        binding.tvRegister.setOnClickListener( v -> navController.navigate( R.id.navigation_registrar ) );
        binding.tvRegistrate.setOnClickListener( v -> navController.navigate( R.id.navigation_registrar ) );
        binding.tvOlvidarPasswordd.setOnClickListener(v -> navController.navigate( R.id.navigation_olvidar_passwordd ) );
        binding.btnLogin.setOnClickListener( v -> btnLogin_Click() );

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