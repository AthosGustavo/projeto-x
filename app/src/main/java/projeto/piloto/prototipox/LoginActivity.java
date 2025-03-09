package projeto.piloto.prototipox;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultKt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import projeto.piloto.prototipox.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding activityLoginBinding;
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


  }

  public void clickAindaNaoTemConta(){

    activityLoginBinding.textoMudaLoginCadastro.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View view) {

        cadastro = !cadastro;

        if(cadastro){
          activityLoginBinding.inputConfirmarSenha.setVisibility(View.VISIBLE);
          activityLoginBinding.btnEntrarCadastrarEntrar.setText("Cadastrar");
          activityLoginBinding.textoMudaLoginCadastro.setText("Já tem uma conta ?");
        }else{
          activityLoginBinding.inputConfirmarSenha.setVisibility(View.GONE);
          activityLoginBinding.btnEntrarCadastrarEntrar.setText("Entrar");
          activityLoginBinding.textoMudaLoginCadastro.setText("Ainda não tem uma conta ?");
        }



      }
    });
  }
}