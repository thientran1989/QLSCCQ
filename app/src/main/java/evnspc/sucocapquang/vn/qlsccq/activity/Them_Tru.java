package evnspc.sucocapquang.vn.qlsccq.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.thtsoftlib.function.ThtShow;
import com.thtsoftlib.function.Thtcovert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.fragment.Tru_Fragment;
import evnspc.sucocapquang.vn.qlsccq.myfunction.Function;
import evnspc.sucocapquang.vn.qlsccq.myfunction.M_READ_JSON;
import evnspc.sucocapquang.vn.qlsccq.myfunction.Utils;
import evnspc.sucocapquang.vn.qlsccq.object.CallbackResult;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRU;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TUYEN;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_Text;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_donvi;
import evnspc.sucocapquang.vn.qlsccq.object.ObjectClient;
import tht.library.crouton.Style;


public class Them_Tru extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener,
        com.google.android.gms.location.LocationListener {

    CountDownTimer mcountdown;
    final int time_connnect = 180000;
    String mURL = "http://10.179.0.22:8080/suco/themtru";
    CallbackResult mCB=null;

    private EditText edtTuyen;
    private EditText edtTru;
    private EditText edtCachtram;
    private EditText edtGhichu;
    private EditText edtMangxong;
    private EditText edtNhanhre;
    public static Obj_TRU my_oTRU;
    int LENH_NHANHRE=1,LENH_MANGXONG=2;
    ProgressWheel prov;
    List<Obj_donvi> list_tuyen=null;

    // GPS
    private static final String TAG = Them_Tru.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-06-29 16:05:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        try {
            if(MainActivity.list_tuyen!=null){
                list_tuyen = new ArrayList<Obj_donvi>(MainActivity.list_tuyen);
            }
        }catch (Exception e){

        }
        try {
            prov = (ProgressWheel) findViewById(R.id.progress_wheel);
            edtTuyen = (EditText)findViewById( R.id.edt_tuyen );
            edtTru = (EditText)findViewById( R.id.edt_tru );
            edtCachtram = (EditText)findViewById( R.id.edt_cachtram );
            edtGhichu = (EditText)findViewById( R.id.edt_ghichu );
            edtMangxong = (EditText)findViewById( R.id.edt_mangxong );
            edtNhanhre = (EditText)findViewById( R.id.edt_nhanhre );
        } catch (Exception e){

         }
        edtTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_tuyen!=null){
                    show_alert_list_tuyen("Tuyến",list_tuyen);
                }else{
                    ThtShow.show_crouton_toast(Them_Tru.this,"Tuyến null",Style.ALERT);
                }
            }
        });
        edtMangxong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_alert_list("Măng Xông",Utils.get_list_MANGXONG(),LENH_MANGXONG);
            }
        });
        edtNhanhre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_alert_list("Nhánh Rẽ",Utils.get_NHANHRE(),LENH_NHANHRE);
            }
        });
        my_oTRU = new Obj_TRU();

        // kiem tra play services
        try {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        } catch (Exception e) {
            ThtShow.show_toast(Them_Tru.this, "Lỗi kiểm tra service !");
        }
        // lay toa do
        try {
            displayLocation();
        } catch (Exception e) {
            ThtShow.show_toast(Them_Tru.this, "Lỗi lấy toạ độ !");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tru);
        findViews();
    }

    // gui du lieu
    class Conect_server_sign_async extends AsyncTask<String, String, String> {
        String url = "";
        String KQSV = "not sent";
        String MAYC = "";
        String MADV = "";
        public String TAG_KQ = "Không kết nối được server";
        ObjectClient mOC;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
              loading(true);
            mcountdown = new CountDownTimer(time_connnect, 1000) {
                public void onTick(long millisUntilFinished) {
//                    pDialog.setMessage(" kt kh..."
//                            + String.valueOf(millisUntilFinished / 1000)
//                            + " giây !");
                }

                public void onFinish() {
//                    pDialog.dismiss();
                    loading(false);
                    Toast.makeText(getApplicationContext(), "time out", Toast.LENGTH_LONG).show();
                }
            }.start();
        }

        protected String doInBackground(String... kq) {
            try {
                ObjectClient mOC = new ObjectClient();
                mOC.setCommand("themtru");
                get_tru();
                mOC.setoTRU(my_oTRU);
                upload(mOC);
            } catch (Exception e) {
            }

            return null;

        }

        protected void onPostExecute(String result) {
            mcountdown.cancel();
            loading(false);
            runOnUiThread(new Runnable() {
                public void run() {
                    if(mCB!=null){
                        if(mCB.getResultString().equals(Utils.CB_OK)){
                            ThtShow.show_crouton_toast(Them_Tru.this,"Them diem thanh cong", Style.CONFIRM);
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            ThtShow.show_crouton_toast(Them_Tru.this,mCB.getResultString(), Style.ALERT);
                        }

                    }else{
                        ThtShow.show_crouton_toast(Them_Tru.this,"hok ket noi dc server", Style.ALERT);
                    }


                }
            });
        }

        public void upload(ObjectClient mOC) {
            TAG_KQ = getString(R.string.alert_not_connect_server);
            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            DataInputStream dis = null;
            try {
                URL url = new URL(mURL);
                URLConnection urlConn = url.openConnection();
                urlConn.setConnectTimeout(3000);
                urlConn.setReadTimeout(180000);
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
                        TAG_KQ = mCB.getResultString();
//                        list_MBA = M_READ_JSON.read_list_MBA(mJO);
//                        list_tru = mCB.getList_tru();
//                        list_tram = mCB.getList_tram();
//                        list_tuyen = mCB.getList_tuyen();
                    } catch (Exception e) {
                        TAG_KQ = "loi doc callback :"+e.toString();
                    }
                } else {
                    TAG_KQ = "ko doc dc JSON";
                }
            } catch (Exception ex) {
                TAG_KQ = getString(R.string.alert_not_connect_server);

            }
        }

    }
    public void huy (View v){
        finish();
    }
    public void hoantat(View v){
        try {
            if(my_oTRU!=null){
                if(edtTru.length()>0
                        & edtTuyen.length()>0
                        & edtMangxong.length()>0
                        & edtNhanhre.length()>0){
                    new Conect_server_sign_async().execute();
                }else{
                    ThtShow.show_crouton_toast(Them_Tru.this,"Chưa đủ thông tin",Style.CONFIRM);
                }
            }else{
                ThtShow.show_crouton_toast(Them_Tru.this,"TRU NULL",Style.CONFIRM);
            }
        }catch(Exception e){
            ThtShow.show_crouton_toast(Them_Tru.this,e.toString(),Style.ALERT);
        }


    }
    public void get_tru(){
        my_oTRU.setTRU(edtTru.getText().toString());
        my_oTRU.setCACHTRAM(Thtcovert.string_to_int(edtCachtram.getText().toString()));
        my_oTRU.setGHI_CHU(edtGhichu.getText().toString());
    }
    public String show_alert_list(String title, final List<Obj_Text> list_text,final int lenh){
        final List<String> list = new ArrayList<>();
        for (Obj_Text oT : list_text){
            list.add(oT.NAME);
        }
        CharSequence [] items = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(Them_Tru.this);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                try{
                if(lenh==LENH_MANGXONG){
                    edtMangxong.setText(list.get(item));
                    my_oTRU.setMANGXONG(list_text.get(item).KEY);
                } if(lenh==LENH_NHANHRE){
                    edtNhanhre.setText(list.get(item));
                    my_oTRU.setNHANH_RE(list_text.get(item).KEY);
                }
                }catch(Exception e){

                }

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return "";
    }
    public String show_alert_list_tuyen(String title, final List<Obj_donvi> list_text){
        final List<String> list = new ArrayList<>();
        for (Obj_donvi oT : list_text){
            list.add(oT.getTen_donvi());
        }
        CharSequence [] items = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(Them_Tru.this);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                try{
                    edtTuyen.setText(list.get(item));
                    my_oTRU.setMA_DVI(list_text.get(item).getMa_donvi()

                    );
                }catch(Exception e){

                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return "";
    }
    public void loading (boolean load){
        if(load){
            prov.setVisibility(View.VISIBLE);
        }else{
            prov.setVisibility(View.GONE);
        }
    }
    // ban do
    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi start service \n"+e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            checkPlayServices();
            // Resuming the periodic location updates
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi resume service \n"+e.toString());
        }
    }

    @Override
    protected void onStop() {
        try {
            super.onStop();
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi stop service \n"+e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            stopLocationUpdates();
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi pause service \n"+e.toString());
        }
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                if(my_oTRU!=null){
                    my_oTRU.setX(Thtcovert.double_to_String(latitude));
                    my_oTRU.setY(Thtcovert.double_to_String(longitude));
                }
                ThtShow.show_toast(Them_Tru.this,Thtcovert.double_to_String(latitude)+","+Thtcovert.double_to_String(longitude));
            } else {
                ThtShow.show_toast(Them_Tru.this,"(Chưa mở GPS !)");
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    /**
     * Method to toggle periodic location updates
     * */
    private void togglePeriodicLocationUpdates() {
        try {
            if (!mRequestingLocationUpdates) {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            } else {
                mRequestingLocationUpdates = false;
                stopLocationUpdates();
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        try {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        boolean kq =false;
        try {
            int resultCode = GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Thiết bị không hỗ trợ !", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                kq= false;
            }
            kq= true;
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
        return kq;
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        try {
            displayLocation();
            if (mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            mLastLocation = location;
            displayLocation();
        } catch (Exception e) {
            ThtShow.show_toast(getApplicationContext(), "Lỗi connect service \n"+e.toString());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
}
