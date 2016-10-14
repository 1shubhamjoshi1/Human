package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.firebase.User;

import org.json.JSONObject;

import java.util.Arrays;

public class Register extends AppCompatActivity {

    private Firebase ref = new Firebase(Constants.BASE_URL);
    private LoginButton mFacebookLoginButton;
    private CallbackManager mFacebookCallbackManager;

    private String profileImage;
    private String email;
    private String fullName;
    /* A reference to the Firebase */
    private Firebase mFirebaseRef = new Firebase(Constants.BASE_URL);

    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;

    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;
    private static final String TAG = "shubhamjoshi.human";

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mAuthProgressDialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (isLoggedIn()) {
            startActivity(new Intent(this, TrainSet.class));
        } else {
            mFacebookCallbackManager = CallbackManager.Factory.create();
            mFacebookLoginButton = (LoginButton) findViewById(R.id.login_with_facebook);
            mFacebookLoginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday", "user_friends"));
            mFacebookAccessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    Log.i(TAG, "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
                    Register.this.onFacebookAccessTokenChange(currentAccessToken);
                }
            };




        /* Create the Firebase ref that is used for all authentication with Firebase */


        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
//            mAuthProgressDialog = new ProgressDialog(this);
//            mAuthProgressDialog.setTitle("Loading");
//            mAuthProgressDialog.setMessage("Authenticating with Firebase...");
//            mAuthProgressDialog.setCancelable(false);
//            mAuthProgressDialog.show();

            mAuthStateListener = new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    //      mAuthProgressDialog.hide();
                    setAuthenticatedUser(authData);
                }
            };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
            mFirebaseRef.addAuthStateListener(mAuthStateListener);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            /* Otherwise, it's probably the request by the Facebook login button, keep track of the session */
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if user logged in with Facebook, stop tracking their token
        if (mFacebookAccessTokenTracker != null) {
            mFacebookAccessTokenTracker.stopTracking();
        }

        // if changing configurations, stop tracking firebase session.

        if(mAuthStateListener!=null)
        mFirebaseRef.removeAuthStateListener(mAuthStateListener);
    }


    /***********************
     * MAIN
     ****************************/

    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            /* Hide all the login buttons */
            //mFacebookLoginButton.setVisibility(View.GONE);

            /* show a provider specific status text */
            String name = null;
            if (authData.getProvider().equals("facebook")
                    || authData.getProvider().equals("google")
                    || authData.getProvider().equals("twitter")) {
                name = (String) authData.getProviderData().get("displayName");
            } else if (authData.getProvider().equals("anonymous")
                    || authData.getProvider().equals("password")) {
                name = authData.getUid();
            } else {
                Log.e(TAG, "Invalid provider: " + authData.getProvider());
            }
            if (name != null) {
                //  mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
            }


            //startActivity(new Intent(Register.this,TrainSet.class));

            Bundle params = new Bundle();
            params.putString("fields", "id,email,gender,cover,picture.type(large)");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if (response != null) {
                                try {
                                    JSONObject data = response.getJSONObject();
                                    if (data.has("picture")) {
                                        profileImage = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        email = data.getString("email");

                                        Firebase branchUser = new Firebase(Constants.BASE_URL + "/Users");

                                        branchUser.child((email != null) ? email.replace('.', ',') : "a").setValue(new User("sds", email, profileImage));


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).executeAsync();

        } else {
            /* No authenticated user show all the login buttons */
            // mFacebookLoginButton.setVisibility(View.VISIBLE);

        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        supportInvalidateOptionsMenu();
    }


    /*********************
     * MAIN ENDS
     *********************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* If a user is currently authenticated, display a logout menu */
        if (this.mAuthData != null) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Unauthenticate from Firebase and from providers where necessary.
     */
    private void logout() {
        if (this.mAuthData != null) {
            /* logout of Firebase */
            mFirebaseRef.unauth();
            /* Logout of any of the Frameworks. This step is optional, but ensures the user is not logged into
             * Facebook/Google+ after logging out of Firebase. */
            if (this.mAuthData.getProvider().equals("facebook")) {
                /* Logout from Facebook */
                LoginManager.getInstance().logOut();
            }
            /* Update authenticated user and show login buttons */
            setAuthenticatedUser(null);
        }
    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            //  mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");

            SharedPreferences.Editor editor = getSharedPreferences("EMAIL", MODE_PRIVATE).edit();
            editor.putString("email", email);
            editor.commit();
            startActivity(new Intent(Register.this, TrainSet.class));
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            //  mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {

            mFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new AuthResultHandler("facebook"));
        } else {
            // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
            if (this.mAuthData != null && this.mAuthData.getProvider().equals("facebook")) {
                mFirebaseRef.unauth();
                setAuthenticatedUser(null);
            }
        }
    }

}

