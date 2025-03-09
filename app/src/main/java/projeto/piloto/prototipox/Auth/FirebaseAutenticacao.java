package projeto.piloto.prototipox.Auth;

import static com.google.firebase.FirebaseError.ERROR_INVALID_EMAIL;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class FirebaseAutenticacao {

  private ViewBinding viewBinding;
  private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

  public FirebaseAutenticacao(ViewBinding viewBinding){
    this.viewBinding = viewBinding;
  }

  public void realizarCadastro(Activity activity, String inputEmail, String inputSenha, String inputConfirmarSenha) {

    if (inputConfirmarSenha.equals(inputSenha)) {
      firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputSenha)
              .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> resposta) {
                  if(resposta.isSuccessful()){
                    Snackbar.make(viewBinding.getRoot(), "Conta criada com sucesso !.", Snackbar.LENGTH_LONG).show();
                  }else{
                    tratarRespostaAutenticacao(resposta);
                  }

                }
              });
    } else {
      Snackbar.make(this.viewBinding.getRoot(), "As senhas digitadas não são iguais.", Snackbar.LENGTH_LONG).show();
    }

  }

  public void tratarRespostaAutenticacao(Task<AuthResult> resposta) {
    if (!resposta.isSuccessful()) {
      try {
        throw resposta.getException();
      } catch (FirebaseAuthWeakPasswordException e) {
        Snackbar.make(this.viewBinding.getRoot(), "Senha fraca.A senha deve conter no mínimo 6 caracteres.", Snackbar.LENGTH_LONG).show();
      } catch (FirebaseAuthInvalidCredentialsException e) {
        if(e.getErrorCode().equals("ERROR_INVALID_EMAIL")){
          Snackbar.make(this.viewBinding.getRoot(), "E-mail formatado incorretamente.Use o formato nome@dominio.com", Snackbar.LENGTH_LONG).show();
        }
        if(e.getErrorCode().equals("ERROR_INVALID_CREDENTIAL")){
          Snackbar.make(this.viewBinding.getRoot(), "A senha ou e-mail estão incorretos.", Snackbar.LENGTH_LONG).show();
        }
      } catch (FirebaseAuthUserCollisionException e) {
        Snackbar.make(this.viewBinding.getRoot(), "O e-mail já está vinculado a outra conta.", Snackbar.LENGTH_LONG).show();
      } catch (Exception e) {
        Snackbar.make(this.viewBinding.getRoot(), "Erro inesperado, contate o administrador.", Snackbar.LENGTH_LONG).show();
      }
    }
  }

  public void realizarLogin(Activity activity,String email, String senha) {
    firebaseAuth.signInWithEmailAndPassword(email, senha)
      .addOnCompleteListener(activity,new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> resposta) {
          if(resposta.isSuccessful()){
            Snackbar.make(viewBinding.getRoot(), "Seja bem vindo.", Snackbar.LENGTH_LONG).show();
          }else{
            tratarRespostaAutenticacao(resposta);
          }
        }
      });
  }
}
