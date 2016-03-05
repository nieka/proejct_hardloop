package presenters;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import Entity.User;
import controllers.DatabaseHandler;
import interfaces.LoginView;
public class LoginPresenter implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "LoginPresener";
    private static final String AUTH_ERROR = "auth_error";

    private LoginView loginView;
    private DatabaseHandler databaseHandler;

    public LoginPresenter(LoginView loginView, DatabaseHandler databaseHandler){
        this.loginView = loginView;
        this.databaseHandler = databaseHandler;

        //check if the user is already has singd in
        if(databaseHandler.getUserCount()  == 1){
            loginView.onLogin();
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            User user;
            if (acct.getPhotoUrl() == null) {
                user = new User(acct.getId(), acct.getEmail(), acct.getDisplayName(), null);
            } else {
                user = new User(acct.getId(), acct.getEmail(), acct.getDisplayName(), acct.getPhotoUrl().toString());
            }

            databaseHandler.setUser(user);
            System.out.println(acct.getDisplayName());
            loginView.onLogin();
        } else {
            loginView.onError(AUTH_ERROR);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
