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

import es.oaemdl.apkcavoshcafe.R;
import es.oaemdl.apkcavoshcafe.databinding.FragmentOlvidarPassworddBinding;

public class OlvidarPasswordd extends Fragment {
    FragmentOlvidarPassworddBinding binding;
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
        binding = FragmentOlvidarPassworddBinding.inflate( inflater, container, false );
        return view = binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        navController = Navigation.findNavController( view );

        binding.tvSignIn.setOnClickListener( v -> navController.navigate( R.id.navigation_login ) );
        binding.tvIniciaSesion.setOnClickListener( v -> navController.navigate( R.id.navigation_login ) );
        binding.btnRegistrar.setOnClickListener( v -> btnRegistrar_Click() );

        binding.edtNombres.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilNombres.setError( null );
            }

            @Override public void afterTextChanged(Editable s) { }
        });

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

        binding.edtConfirmarPasswordd.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilConfirmarPasswordd.setError( null );
            }

            @Override public void afterTextChanged(Editable s) { }
        });

    }

    private void btnRegistrar_Click() {
        String sNombres = binding.edtNombres.getText().toString();
        String sCorreo = binding.edtCorreo.getText().toString();
        String sPasswordd = binding.edtPasswordd.getText().toString();
        String sConfirmarPasswordd = binding.edtConfirmarPasswordd.getText().toString();

        if ( sNombres.isEmpty() ) binding.tilNombres.setError("Ingrese nombres");
        if ( sCorreo.isEmpty() ) binding.tilCorreo.setError("Ingrese correo");
        if ( sPasswordd.isEmpty() ) binding.tilPasswordd.setError("Ingrese contraseña");
        if ( sConfirmarPasswordd.isEmpty() ) binding.tilConfirmarPasswordd.setError("Ingrese contraseña");
        if ( !sPasswordd.isEmpty() && !sConfirmarPasswordd.isEmpty() &&
                !sPasswordd.equals( sConfirmarPasswordd ) ) {
            binding.tilPasswordd.setError("Las contraseñas deben ser iguales");
            binding.tilConfirmarPasswordd.setError("Las contraseñas deben ser iguales");
        }

        if ( binding.tilNombres.getError() != null ||
                binding.tilCorreo.getError() != null ||
                binding.tilPasswordd.getError() != null ||
                binding.tilConfirmarPasswordd.getError() != null ) return;

        //Snackbar.make( view, "Usuario y/o contraseña inválido", Snackbar.LENGTH_LONG ).show();
    }

}