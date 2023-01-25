package com.diaco.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.diaco.sample.Core.IView;
import com.diaco.sample.Core.Presenter;
import com.diaco.sample.Setting.CustomClasses.CustomAdapter;
import com.diaco.sample.Setting.CustomClasses.CustomFragment;
import com.diaco.sample.Setting.CustomClasses.CustomRel;
import com.diaco.sample.Setting.Setting;
import com.diaco.sample.Story.ResponseStories;
import com.diaco.sample.Story.Stories;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class fragment_test extends CustomFragment {
    private EditText editMessage;
    private TextView Message,BtnSend;
    private Socket mSocket;
    Socket temp;
    RecyclerView Rec;
    public CustomAdapter customAdapter;
    List<StoryModel> listStory;
    private boolean isConnected=false;
    {
        /*try {
            IO.Options options=new IO.Options();
            options.forceNew=false;
            options.reconnection=true;
            mSocket= IO.socket("https://socketio-chat-h9jt.herokuapp.com/.",options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    public int layout() {
        return R.layout.fragment_story;
    }

    @Override
    public void onCreateMyView() {
        //getListStroy(getContext());
        Rec=parent.findViewById(R.id.RecStory);
        listStory=new ArrayList<>();
        StoryModel one=new StoryModel();
        one.setLink("https://github.com/chenyuntc/simple-faster-rcnn-pytorch");
        one.src.add("https://lh3.googleusercontent.com/GTmuiIZrppouc6hhdWiocybtRx1Tpbl52eYw4l-nAqHtHd4BpSMEqe-vGv7ZFiaHhG_l4v2m5Fdhapxw9aFLf28ErztHEv5WYIz5fA");

        StoryModel two=new StoryModel();
        two.src.add("https://s3.eu-west-1.amazonaws.com/prod.news.product.which.co.uk/news/wp-content/uploads/2020/03/Android-main-960x480.jpg");
        two.src.add("http://elmparvar.ir/wp-content/uploads/2020/09/android-device-identifiers-featured.jpg");

        StoryModel three=new StoryModel();
        three.src.add("https://github.com/chenyuntc/simple-faster-rcnn-pytorch");
        three.src.add("http://elmparvar.ir/wp-content/uploads/2020/09/android-device-identifiers-featured.jpg");

        StoryModel four=new StoryModel();
        four.src.add("https://lh3.googleusercontent.com/GTmuiIZrppouc6hhdWiocybtRx1Tpbl52eYw4l-nAqHtHd4BpSMEqe-vGv7ZFiaHhG_l4v2m5Fdhapxw9aFLf28ErztHEv5WYIz5fA");
        four.src.add("http://elmparvar.ir/wp-content/uploads/2020/09/android-device-identifiers-featured.jpg");
        four.src.add("https://github.com/chenyuntc/simple-faster-rcnn-pytorch");
        listStory.add(one);
        listStory.add(two);
        listStory.add(three);
        listStory.add(four);
        customAdapter = new CustomAdapter.RecyclerBuilder<StoryModel, AdapterModel>(getContext(), Rec, listStory)
                .setView(() -> new AdapterModel(getContext()))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onClickStory(list.get(position),selectItem,position,customAdapter,list))
                .orientation(RecyclerView.HORIZONTAL)
                .build();

        /*RelativeLayout Download=parent.findViewById(R.id.BtnDownloadBook);
        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });*/
        /*editMessage=parent.findViewById(R.id.EditSend);
        Message=parent.findViewById(R.id.Message);
        BtnSend=parent.findViewById(R.id.BtnSend);
        onSocketConnect();

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.io().reconnection(true);
                mSocket.emit("new message",editMessage.getText().toString());
            }
        });
        TextView btnPermission;
        btnPermission=parent.findViewById(R.id.btnPermission);
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().FinishRelStartRel(new DialogDescription(getContext()));
            }
        });
*/
    }

    private void getListStroy(Context context) {
        Presenter.get_global().OnCreate(context,"https://api.liom-app.ir/","token");
        Presenter.get_global().GetAction(new IView<ResponseStories>() {
            @Override
            public void SendRequest() {

            }

            @Override
            public void OnSucceed(ResponseStories object) {
                //listStory=object.get
            }

            @Override
            public void OnError(String error, int statusCode) {

            }
        },"Story","AllStory","",ResponseStories.class);
    }

    private void onSocketConnect(){
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT,onConnectionError);
        mSocket.on(Socket.EVENT_CONNECT_ERROR,onConnectionError);
        mSocket.on(Socket.EVENT_RECONNECT,onReConnect);
        mSocket.on("new message",onNewMessage);
        if(!mSocket.connected()) {
            mSocket.connect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT,onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT,onConnectionError);
        mSocket.off(Socket.EVENT_CONNECT_ERROR,onConnectionError);
        mSocket.off("new message",onNewMessage);

    }

    private Emitter.Listener onConnect=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.getGlobal().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected){
                        mSocket.emit("add user","devmins");
                        Toast.makeText(MainActivity.getGlobal(), "Connected", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.getGlobal(), "Id:"+mSocket.id(), Toast.LENGTH_SHORT).show();
                        isConnected=true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onReConnect=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.getGlobal().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.getGlobal(), "Reconnect Id:"+mSocket.id(), Toast.LENGTH_SHORT).show();
                    mSocket.emit("chat message","tamam");
                }
            });
        }
    };

    private Emitter.Listener onDisconnect=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.getGlobal().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //isConnected=false;
                    Toast.makeText(MainActivity.getGlobal(), "Disconnected", Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener onConnectionError=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.getGlobal().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.getGlobal(), "ConnectionError", Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener onNewMessage=new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.getGlobal().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject json= (JSONObject) args[0];
                    String Username,message ="";
                    try {
                        Username=json.getString("username");
                        message=json.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message.setText(message);
                }
            });
        }
    };

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(MainActivity.getGlobal().isRelShow) {
            Collections.sort(listStory);
            customAdapter.notifyDataSetChanged();
            MainActivity.getGlobal().HideMyDialog();
        }
    }
}
