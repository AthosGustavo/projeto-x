package projeto.piloto.prototipox.layout;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import projeto.piloto.prototipox.Auth.FirebaseAutenticacao;
import projeto.piloto.prototipox.R;
import projeto.piloto.prototipox.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding activityLoginBinding;
  private FirebaseAutenticacao firebaseAutenticacaoAuth;
  private boolean cadastro = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());

    EdgeToEdge.enable(this);
    setContentView(activityLoginBinding.getRoot());
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    clickAindaNaoTemConta();
    configuraBtnEntrarCadastrar();
    firebaseAutenticacaoAuth = new FirebaseAutenticacao(activityLoginBinding);

  }

  private void configuraBtnEntrarCadastrar() {
    activityLoginBinding.btnEntrarCadastrarEntrar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String inputConfirmarSenha = activityLoginBinding.inputConfirmarSenha.getText().toString();
        String inputSenha = activityLoginBinding.inputSenha.getText().toString();
        String inputEmail = activityLoginBinding.inputEmail.getText().toString();

        try{
          if (cadastro) {
            firebaseAutenticacaoAuth.realizarCadastro(LoginActivity.this,inputEmail,inputSenha,inputConfirmarSenha);
          } else {
            firebaseAutenticacaoAuth.realizarLogin(LoginActivity.this,inputEmail,inputSenha);
          }
        }catch(IllegalArgumentException ex){
          Snackbar.make(activityLoginBinding.getRoot(), "Atenção, os campos de e-mail e senha devem ser preenchidos", Snackbar.LENGTH_LONG).show();
        }

      }
    });
  }

  private void clickAindaNaoTemConta() {
    activityLoginBinding.textoMudaLoginCadastro.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        cadastro = !cadastro;
        if (cadastro) {
          activityLoginBinding.inputConfirmarSenha.setVisibility(View.VISIBLE);
          activityLoginBinding.btnEntrarCadastrarEntrar.setText("Cadastrar");
          activityLoginBinding.textoMudaLoginCadastro.setText("Já tem uma conta ?");
        } else {
          activityLoginBinding.inputConfirmarSenha.setVisibility(View.GONE);
          activityLoginBinding.btnEntrarCadastrarEntrar.setText("Entrar");
          activityLoginBinding.textoMudaLoginCadastro.setText("Ainda não tem uma conta ?");
        }
      }
    });
  }


}