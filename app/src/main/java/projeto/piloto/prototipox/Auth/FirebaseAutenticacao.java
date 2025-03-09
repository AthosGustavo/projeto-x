package projeto.piloto.prototipox.Auth;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
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
                  tratarRespostaAutenticacao(resposta);
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
        Snackbar.make(this.viewBinding.getRoot(), "A senha digitada não corresponde à conta de e-mail fornecida.", Snackbar.LENGTH_LONG).show();
      } catch (FirebaseAuthUserCollisionException e) {
        Snackbar.make(this.viewBinding.getRoot(), "O e-mail já está vinculado a outra conta.", Snackbar.LENGTH_LONG).show();
      } catch (FirebaseAuthInvalidUserException e) {
        Snackbar.make(this.viewBinding.getRoot(), "Nenhum usuário foi encontrado para este e-mail.", Snackbar.LENGTH_LONG).show();
      } catch (Exception e) {
        Snackbar.make(this.viewBinding.getRoot(), "Erro inesperado, contate o administrador.", Snackbar.LENGTH_LONG).show();
      }
    }
  }

  public void realizarLogin() {

  }
}
