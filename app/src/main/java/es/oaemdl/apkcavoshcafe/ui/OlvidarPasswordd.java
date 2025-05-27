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

import java.util.Objects;

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
        binding.tvRegister.setOnClickListener( v -> navController.navigate( R.id.navigation_registrar ) );
        binding.tvIniciaSesion.setOnClickListener( v -> navController.navigate( R.id.navigation_login ) );
        binding.btnEnviar.setOnClickListener( v -> btnEnviar_Click() );

        binding.edtCorreo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilCorreo.setError( null );
            }

            @Override public void afterTextChanged(Editable s) { }
        });




    }

    private void btnEnviar_Click() {
        String sCorreo = Objects.requireNonNull(binding.edtCorreo.getText()).toString();

        if ( sCorreo.isEmpty() ) binding.tilCorreo.setError("Ingrese correo");

        if ( binding.tilCorreo.getError() != null ) return;

        //Snackbar.make( view, "Usuario y/o contraseña inválido", Snackbar.LENGTH_LONG ).show();
    }

}