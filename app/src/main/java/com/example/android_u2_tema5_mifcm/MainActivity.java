package com.example.android_u2_tema5_mifcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.spTopics)
  Spinner spTopics;
  @BindView(R.id.tvTopics)
  TextView tvTopics;

  private Set<String> mTopicsSet;
  private SharedPreferences mSharedPreferences;
  private static final String SP_TOPICS = "sharedPreferencesTopics";




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    Bundle extras = getIntent().getExtras();
    if (extras != null)
      Log.i("mifcmupt", extras.getString("descuento"));
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

    //se añadio esto

    mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
    mTopicsSet = mSharedPreferences.getStringSet(SP_TOPICS, new HashSet<String>());
    showTopics();


  }

  private void showTopics() {
    tvTopics.setText(mTopicsSet.toString());
  }


  @OnClick({R.id.btnSuscribir, R.id.btnDesuscribir})
  public void onViewClicked(View view) {

    String topic = getResources().getStringArray(R.array.topicsValues)[spTopics.getSelectedItemPosition()];

    switch (view.getId()) {
      case R.id.btnSuscribir:
        if (!mTopicsSet.contains(topic)) {
          FirebaseMessaging.getInstance().subscribeToTopic(topic);
          mTopicsSet.add(topic);
          saveSharedPreferences();
        }
        break;
      case R.id.btnDesuscribir:

        if (mTopicsSet.contains(topic)) {
          FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
          mTopicsSet.remove(topic);
          saveSharedPreferences();
        }

        break;
    }
  }


  private void saveSharedPreferences() {
    SharedPreferences.Editor editor = mSharedPreferences.edit();
    editor.clear();
    editor.putStringSet(SP_TOPICS, mTopicsSet);
    editor.apply();
    showTopics();
  }






}
