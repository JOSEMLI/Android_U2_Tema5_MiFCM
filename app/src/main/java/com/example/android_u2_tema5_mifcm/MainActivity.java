package com.example.android_u2_tema5_mifcm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Bundle extras = getIntent().getExtras();
    if(extras!=null)
      Log.i("mifcmupt",extras.getString("descuento"));
    else
      Log.i("mifcmupt", "no hay valores");

    //se añadio
    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
          @Override
          public void onSuccess(InstanceIdResult instanceIdResult) {
            Log.d("token_Id", instanceIdResult.getToken());
          }
        });


  }
}
