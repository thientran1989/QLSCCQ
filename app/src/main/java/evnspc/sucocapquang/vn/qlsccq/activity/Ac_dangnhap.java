package evnspc.sucocapquang.vn.qlsccq.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thtsoftlib.function.ThtShow;
import com.thtsoftlib.function.Tht_Network;
import com.thtsoftlib.function.Tht_Screen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.myfunction.Function;
import evnspc.sucocapquang.vn.qlsccq.myfunction.M_READ_JSON;
import evnspc.sucocapquang.vn.qlsccq.object.CallbackResult;
import evnspc.sucocapquang.vn.qlsccq.object.ObjectClient;

public class Ac_dangnhap extends Activity {
	String KQSV = "not sent";
	String MA_KH = "";
	String MAT_KHAU = "";
	EditText edt_MA_KH, edt_MATKHAU;
//	DBAdapter mdb;
//	ProgressWheel prov;
	Button btn_dangnhap;
//	Obj_device mDV;

    private ProgressDialog pDialog;
    CountDownTimer mcountdown;
    final int time_connnect = 180000;
    String mURL = "http://10.179.0.22:8080/smartcare/getkh";
    CallbackResult mCB=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Tht_Screen.hide_title(this);
		Tht_Screen.hide_keyboard(this);
		setContentView(R.layout.activity_dangnhap);
//		mdb = new DBAdapter(this);
//		mdb.open();
		edt_MA_KH = (EditText) findViewById(R.id.edt_tendangnhap_dangnhap);
		edt_MATKHAU = (EditText) findViewById(R.id.edt_matkhau_dangnhap);
		edt_MA_KH.setText("");
//		prov = (ProgressWheel) findViewById(R.id.progress_wheel);
		btn_dangnhap = (Button) findViewById(R.id.btn_dangnhap);

	}

	// mo class
	public void to_intent_main(View v) {
		if (Tht_Network.isNetworkAvailable(this)) {
			Intent i = new Intent(Ac_dangnhap.this, evnspc.sucocapquang.vn.qlsccq.activity.MainActivity.class);
//			Bundle b = new Bundle();
//			b.putInt(Utils.REQ, Utils.REQ_DANGNHAP);
//			b.putString(Utils.ma_khachhang,edt_MA_KH.getText().toString());
//			b.putString(Utils.mat_khau,edt_MATKHAU.getText().toString());
//			i.putExtras(b);
			startActivity(i);

//            new Conect_server_sign_async().execute();
		} else {
			ThtShow.show_toast(this, "Mạng không khả dụng !");
		}

	}

	// load du lieu
	class load_du_lieu extends AsyncTask<String, String, String> {
		int lenh = 0;

		public load_du_lieu(int lenh) {
			this.lenh = lenh;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mcountdown = new CountDownTimer(time_connnect, 1000) {
				public void onTick(long millisUntilFinished) {

				}

				public void onFinish() {
					// ThtShow.show_toast(Ac_dangnhap.this,
					// "Không kêt nối được server");
					// finish();
					stop_load();
				}
			}.start();
		}

		protected String doInBackground(String... kq) {
			try {
				// if (LENH !=Utils.REQ_DANGNHAP){
//				new Conect_server_sign_async(Ac_dangnhap.this, mDV,null).execute();
				// }else{
				// new Conect_server_sign_async(Ac_dangnhap.this,mDV).execute();
				// }

			} catch (Exception e) {

			}
			return null;

		}

		protected void onPostExecute(String result) {
			mcountdown.cancel();
			runOnUiThread(new Runnable() {
				public void run() {
					// finish();
					stop_load();
				}
			});

		}
	}

	public void start_load() {
//		prov.setVisibility(View.VISIBLE);
//		btn_dangnhap.setEnabled(false);
	}

	public void stop_load() {
//		prov.setVisibility(View.GONE);
//		btn_dangnhap.setEnabled(true);
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
            pDialog = new ProgressDialog(Ac_dangnhap.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            mcountdown = new CountDownTimer(time_connnect, 1000) {
                public void onTick(long millisUntilFinished) {
                    pDialog.setMessage(" kt kh..."
                            + String.valueOf(millisUntilFinished / 1000)
                            + " giây !");
                }

                public void onFinish() {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "time out", Toast.LENGTH_LONG).show();
                }
            }.start();
        }

        protected String doInBackground(String... kq) {
            try {
                ObjectClient mOC = new ObjectClient();
                mOC.setCommand("dangnhap");
                upload(mOC);
            } catch (Exception e) {
            }

            return null;

        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            mcountdown.cancel();
            runOnUiThread(new Runnable() {
                public void run() {
                    if(mCB!=null){
                        Toast.makeText(getApplicationContext(),mCB.getResultString(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),KQSV,Toast.LENGTH_LONG).show();
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

//				InputStream is = new BufferedInputStream(
//						connection.getInputStream());
//				byte[] buffer = new byte[512];
//				byte[] data = new byte[0];
//				int bytesRead;
//				bytesRead = is.read(buffer);
//				while (bytesRead > 0) {
//					byte[] newData = new byte[data.length + bytesRead];
//					System.arraycopy(data, 0, newData, 0, data.length);
//					System.arraycopy(buffer, 0, newData, data.length, bytesRead);
//					data = newData;
//					bytesRead = is.read(buffer);
//				}
//				ByteArrayInputStream inputStream = new ByteArrayInputStream(
//						data);
//				dis = new DataInputStream(inputStream);
                dis = new DataInputStream(connection.getInputStream());
                KQSV = "";
                KQSV = Function.byte_to_String(dis);
                Log.i("KQSV", KQSV);
                outputStream.close();
                dis.close();
                JsonParser jp = new JsonParser();
                JsonObject mJO = jp.parse(KQSV).getAsJsonObject();
//                TAG_KQ="mJO :"+mJO.toString();
                mCB = M_READ_JSON.read_callback(mJO);
                if (mCB != null) {
                    try {
                        TAG_KQ = mCB.getResultString();
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

}
