package com.diaco.sample;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.diaco.sample.Setting.CustomClasses.CustomFragment;
import com.diaco.sample.Setting.CustomClasses.CustomRel;

public class DialogDescription extends CustomRel {
    TextView btnPermission;
    public DialogDescription(Context context) {
        super(context, R.layout.dialog_description);
        btnPermission=findViewById(R.id.btnPermission);
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.getGlobal().startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                MainActivity.getGlobal().HideMyDialog();

            }
        });
    }
}
