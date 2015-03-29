package com.circularuins.wstest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class MainActivity extends ActionBarActivity {
    private Button btnStart;
    private Button btnSend;
    private Button btnReset;
    private Button btnDisconnect;
    private TextView tv;
    private EditText et;
    private StringBuilder mSb = new StringBuilder();
    private WebSocket ws;

    private class Chat extends ChatTask {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mSb.append(s);
            tv.setText(mSb);
        }
    }

    public void init() {
        //コマンドの設定
        Command punch = new Command("puch", 10, 1);
        Command kick = new Command("kick", 15, 2);
        Command fire = new Command("fire", 30, 5);
        Command freeze = new Command("freeze", 30, 5);
        Command poison = new Command("poison", 35, 7);
        //プレイヤーの設定
        Player player = new Player(100, 50, 25, 25, 33);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnDisconnect = (Button)findViewById(R.id.btnDisconnect);
        tv = (TextView)findViewById(R.id.tv);
        et = (EditText)findViewById(R.id.edit);

        //対戦開始
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient.getDefaultInstance().websocket(
                        "ws://circularuins.com:3003/game",
                        "my-protocol",
                        new AsyncHttpClient.WebSocketConnectCallback() {
                            @Override
                            public void onCompleted(Exception ex, WebSocket webSocket) {
                                if(ex != null) {
                                    ex.printStackTrace();
                                    return;
                                }

                                ws = webSocket;
                                //自分の基本情報を送信する
                                webSocket.send(getLocalIpv4Address());
                                //相手の基本情報を受け取るコールバック
                                webSocket.setStringCallback(new WebSocket.StringCallback() {
                                    @Override
                                    public void onStringAvailable(String s) {
                                        new Chat().execute(s);
                                        Log.d("WS", "対戦相手：" + s);
                                    }
                                });
                            }
                        });
            }
        });

        //コマンド送信
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send(et.getText().toString());
                ws.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        new Chat().execute(s);
                        Log.d("WS", "コマンド：" + s);
                    }
                });
            }
        });

        //リセット
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send("h8ze@91bmkfp3");
                tv.setText("");
                mSb = new StringBuilder();
            }
        });

        //接続を切断する
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ws.send("h8ze@91bmkfp3");
                tv.setText("");
                mSb = new StringBuilder();
                ws.close();
            }
        });
    }

    //端末のIPアドレスを取得する
    public String getLocalIpv4Address(){
        try{
            for (Enumeration<NetworkInterface> networkInterfaceEnum = NetworkInterface.getNetworkInterfaces();
                 networkInterfaceEnum.hasMoreElements();) {
                NetworkInterface networkInterface = networkInterfaceEnum.nextElement();
                for (Enumeration<InetAddress> ipAddressEnum = networkInterface.getInetAddresses();
                      ipAddressEnum.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) ipAddressEnum.nextElement();
                    //---check that it is not a loopback address and it is ipv4---
                    if(!inetAddress.isLoopbackAddress() &&
                            InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex){
            Log.e("getLocalIpv4Address", ex.toString());
        }
        return null;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
