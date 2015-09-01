package com.example.carefreak.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by carefreak on 9/1/2015.
 */
public class FacebookLoginActivity extends Activity implements View.OnClickListener {


    public static CallbackManager callbackmanager;
    private LoginButton btnLogin;
    private Button btnFeed;
    private TextView txtCode;
    private int serverResponseCode = 0;
    URL url;
    HttpURLConnection connection = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackmanager = CallbackManager.Factory.create();
        setContentView(R.layout.facebook_login);
        String loginUrl = "http://192.168.0.100:1337/auth/social";
        btnLogin = (LoginButton) findViewById(R.id.fbLogin);
        btnFeed = (Button) findViewById(R.id.feed);
        txtCode = (TextView) findViewById(R.id.txtCode);
/*
        btnLogin.setOnClickListener(this);
*/
        btnFeed.setOnClickListener(this);

        /*serverUrl = "http://192.168.0.100:1337/user/55e410c34e802342040b084f/avatar";*/

        btnLogin.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txtCode.setText("User ID:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
/*
                try {
                    url = new URL("http://192.168.0.100:1337/auth/social");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
*//*                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");

 *//**//*                   connection.setRequestProperty("Content-Length", "" +
                            Integer.toString(urlParameters.getBytes().length));*//**//*
                    connection.setRequestProperty("Content-Language", "en-US");*//*

                    connection.setRequestProperty("type","facebook");
                    connection.setRequestProperty("access_token",loginResult.getAccessToken().getToken() );
                    connection.setRequestProperty("strategyName", "FacebookTokenStrategy");


                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                    String serverResponseMessage = connection.getResponseMessage();
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    *//*return response.toString();*//*

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (ProtocolException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
                     }
            @Override
            public void onCancel() {
                txtCode.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                txtCode.setText("Login attempt failed.");
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {


        }else if (v == btnFeed){

            try {
                AccessToken token = AccessToken.getCurrentAccessToken();
                url = new URL("http://192.168.0.100:1337/auth/social");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setRequestProperty("type","facebook");
                connection.setRequestProperty("access_token" ,token.toString());
                connection.setRequestProperty("strategyName", "FacebookTokenStrategy");


                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                String serverResponseMessage = connection.getResponseMessage();
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();


            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
/*
            Intent i = new Intent();
*/

        }
    }


 /*   private void fbLogin() {
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String accessToken = loginResult.getAccessToken().toString();

*//*
                        editor.putString("accessToken",accessToken);
                        editor.apply();
*//*

                        Log.e("access token is", accessToken);
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {

                                        Log.e("here", "atlseast");
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            Log.d("task complete", "okay");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                Log.e("result from server is: ", jsonresult);
                                                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                                                if (accessToken != null) {
                                                    if (!accessToken.isExpired()) {
                                                        Toast.makeText(FacebookLoginActivity.this, accessToken.toString(), Toast.LENGTH_LONG).show();
                                                        HttpURLConnection conn = null;
                                                        *//*DataOutputStream dos = null;*//*
                                                        URL url = null;
                                                        try {
                                                            url = new URL("http://192.168.0.100:1337/auth/social");

                                                            Log.e("error", "" + url);
                                                            conn = (HttpURLConnection) url.openConnection();
                                                            conn.setDoInput(true); // Allow Inputs
                                                            conn.setDoOutput(true); // Allow Outputs
                                                            conn.setUseCaches(false); // Don't use a Cached Copy
                                                            conn.setRequestMethod("POST");

                                                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                                            conn.setRequestProperty("type", "facebook");
                                                            conn.setRequestProperty("strategyName", "FacebookTokenStrategy");
                                                            conn.setRequestProperty("access_token", String.valueOf(accessToken));
                                                            conn.setRequestProperty("Connection", "Keep-Alive");

                                                            DataOutputStream dos = new DataOutputStream (
                                                                    conn.getOutputStream ());

                                                            serverResponseCode = conn.getResponseCode();

                                                            Log.e("here", "test" + serverResponseCode);
                                                            String serverResponseMessage = conn.getResponseMessage();

                                                            Log.e("uploadFile", "HTTP Response is : "
                                                                    + serverResponseMessage + ": " + serverResponseCode);
                                                            BufferedReader in = new BufferedReader(
                                                                    new InputStreamReader(conn.getInputStream()));
                                                            String inputLine;
                                                            StringBuffer buff = new StringBuffer();

                                                            while ((inputLine = in.readLine()) != null) {
                                                                buff.append(inputLine);
                                                            }
                                                            in.close();

                                                            Log.e("response is", buff.toString());
                                                            JSONObject respObj = new JSONObject(response.toString());
                                                            String msg = respObj.getString("Message");

                                                            dos.flush();
                                                            dos.close();

                                                        } catch (MalformedURLException e) {
                                                            e.printStackTrace();
                                                        } catch (ProtocolException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("cancelled", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("error", error.toString());
                    }
                });
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //manage login result
        callbackmanager.onActivityResult(requestCode, resultCode, data);
        /*Log.e("data is", ""+data);*/
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

    }
}
