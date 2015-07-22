package evnspc.sucocapquang.vn.qlsccq.server;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thtsoftlib.function.Thtcovert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.activity.MainActivity;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_SUCO;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_TRAM;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_TRU;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_TUYEN;
import evnspc.sucocapquang.vn.qlsccq.myfunction.Function;
import evnspc.sucocapquang.vn.qlsccq.myfunction.M_READ_JSON;
import evnspc.sucocapquang.vn.qlsccq.object.CallbackResult;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRAM;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRU;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TUYEN;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_donvi;
import evnspc.sucocapquang.vn.qlsccq.object.ObjectClient;
import evnspc.sucocapquang.vn.qlsccq.utils.CONFIG_LINK;
import evnspc.sucocapquang.vn.qlsccq.utils.DB_COMMAND;


public class Conect_server_sign_async extends AsyncTask<String, String, String> {
    String KQSV = "Không kết nối được server";
    Context mCon=null;
    ObjectClient mOC;
    CountDownTimer mcountdown;
    CallbackResult mCB=null;
    String my_url="";
    View prov =null;
    RecyclerView recList;
    // lay hoa don
    Adapter_TRAM mAdapter_tram;
    Adapter_TUYEN mAdapter_tuyen;
    Adapter_TRU mAdapter_tru;
    Adapter_SUCO mAdapter_suco;

    List<Obj_TRAM> list_tram =null;
    List<Obj_TUYEN> list_tuyen =null;
    List<Obj_TRU> list_tru =null;
    List<Obj_donvi> list_donvi =null;

    // tuyen
    public Conect_server_sign_async(Context mCon, String my_url, ObjectClient mOC, View prov, RecyclerView recList,Adapter_TUYEN mAdapter_tuyen){
        this.mCon = mCon;
        this.my_url = my_url;
        this.mOC =mOC;
        this.prov = prov;
        this.recList = recList;
        this.mAdapter_tuyen = mAdapter_tuyen;
    }

//    public Conect_server_sign_async(Context mCon, String my_url, ObjectClient mOC, View prov, RecyclerView recList, Adapter_SANLUONG mAdapter){
//        this.mCon = mCon;
//        this.my_url = my_url;
//        this.mOC =mOC;
//        this.prov = prov;
//        this.recList = recList;
//        this.mAdapter_sanluong = mAdapter;
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(prov!=null){
            prov.setVisibility(View.VISIBLE);
        }
        mcountdown = new CountDownTimer(CONFIG_LINK.READ_TIMEOUT,1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                if(prov!=null){
                    prov.setVisibility(View.GONE);
                }
                Toast.makeText(mCon.getApplicationContext(), "time out", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    protected String doInBackground(String... kq) {
        try {
            upload(my_url,mOC);
        } catch (Exception e) {
        }

        return null;

    }
    protected void onPostExecute(String result) {
//            pDialog.dismiss();
        mcountdown.cancel();
        ((Activity)mCon).runOnUiThread(new Runnable() {
            public void run() {
                if (mCB != null) {
                    if (mCB.getResultString().equals("OK")) {
                        if (prov != null) {
                            prov.setVisibility(View.GONE);
                        }
                        if (mCB.getCommand().equals(DB_COMMAND.LENH_GET_TUYEN)) {
                            load_tuyen_ok(list_tuyen);
                        }
                    } else {
                        Toast.makeText(mCon.getApplicationContext(), mCB.getResultString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mCon.getApplicationContext(), KQSV, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void upload(String mURL,ObjectClient mOC) {
        KQSV = mCon.getString(R.string.alert_not_connect_server);
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream dis = null;
        try {
            URL url = new URL(mURL);
            URLConnection urlConn = url.openConnection();
            urlConn.setConnectTimeout(CONFIG_LINK.CONNECT_TIMEOUT);
            urlConn.setReadTimeout(CONFIG_LINK.READ_TIMEOUT);
            connection = (HttpURLConnection) urlConn;
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setAllowUserInteraction(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            outputStream = new DataOutputStream(
                    connection.getOutputStream());

            KQSV = Function.alldata2server(mOC, null);
            Function.write_String_to_byte(outputStream, KQSV);
            outputStream.flush();
            dis = new DataInputStream(connection.getInputStream());
            KQSV = "";
            KQSV = Function.byte_to_String(dis);
            Log.i("KQSV", KQSV);
            outputStream.close();
            dis.close();
            JsonParser jp = new JsonParser();
            JsonObject mJO = jp.parse(KQSV).getAsJsonObject();
            mCB = M_READ_JSON.read_callback(mJO);
            if (mCB != null) {
                try {
                    KQSV = mCB.getResultString();
                    list_tru = mCB.getList_tru();
                    list_tram = mCB.getList_tram();
                    list_tuyen = mCB.getList_tuyen();
                    list_donvi = mCB.getList_DONVI();
                    if(list_donvi!=null){
                        MainActivity.list_tuyen = new ArrayList<Obj_donvi>(list_donvi);
                    }
                } catch (Exception e) {
                    KQSV = "loi doc callback :"+e.toString();
                }
            } else {
                KQSV = "ko doc dc JSON";
            }
        } catch (Exception ex) {
            if(my_url.contains(CONFIG_LINK.IP_LOCAL)){
                KQSV = mCon.getString(R.string.alert_not_connect_server);
            }else{
                my_url = my_url.replace(CONFIG_LINK.IP_SERVER,CONFIG_LINK.IP_LOCAL);
                upload(my_url,mOC);
            }
        }

    }
    // load hoa don thanh cong
    public void load_tuyen_ok(List<Obj_TUYEN> list) {
        if (list != null) {
            try {
                mAdapter_tuyen = new Adapter_TUYEN(mCon,list);
                recList.setAdapter(mAdapter_tuyen);
            } catch (Exception e) {
                Toast.makeText(mCon.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
