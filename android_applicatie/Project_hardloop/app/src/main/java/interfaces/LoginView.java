package interfaces;

/**
 * Created by niek on 25-12-2015.
 * Interface die word gebruikt voor de callback van de login presenter
 */
public interface LoginView {

    void onLogin();

    void onError(String message);
}
