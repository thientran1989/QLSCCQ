/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package evnspc.sucocapquang.vn.qlsccq.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.activity.MainActivity;
import evnspc.sucocapquang.vn.qlsccq.activity.Them_Tru;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_SUCO;
import evnspc.sucocapquang.vn.qlsccq.adapter.Adapter_TRU;
import evnspc.sucocapquang.vn.qlsccq.myfunction.Function;
import evnspc.sucocapquang.vn.qlsccq.myfunction.M_READ_JSON;
import evnspc.sucocapquang.vn.qlsccq.object.CallbackResult;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_SU_CO;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRAM;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRU;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TUYEN;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_donvi;
import evnspc.sucocapquang.vn.qlsccq.object.ObjectClient;


public class Suco_Fragment extends Fragment {

    private ProgressDialog pDialog;
    CountDownTimer mcountdown;
    final int time_connnect = 180000;
    String mURL = "http://10.179.0.22:8080/suco/getds";
    CallbackResult mCB=null;
    List<Obj_TRAM> list_tram =null;
    List<Obj_TUYEN> list_tuyen =null;
    List<Obj_TRU> list_tru =null;
    List<Obj_SU_CO> list_suco =null;
    List<Obj_donvi> list_donvi =null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mReAdapter;
    Adapter_SUCO mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recList;
    EditText edt_search;
    ProgressWheel prov;

	public static Suco_Fragment newInstance(String text){
		Suco_Fragment mFragment = new Suco_Fragment();
//		Bundle mBundle = new Bundle();
//		mBundle.putString(TEXT_FRAGMENT, text);
//		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_tram, container, false);
        try{
            recList = (RecyclerView) rootView.findViewById(R.id.cardList);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi giao dien :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }

        // set adapter
        try{
            prov = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
            edt_search = (EditText)rootView.findViewById(R.id.edt_search);
            recList = (RecyclerView) rootView.findViewById(R.id.cardList);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi giao dien :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            mSwipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.green, R.color.blue);
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi tao refesh color :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new Conect_server_sign_async().execute();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 2500);
                }
            });
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi refesh :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            new Conect_server_sign_async().execute();
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi load :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.get_search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // end


		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
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
                    Toast.makeText(getActivity().getApplicationContext(), "time out", Toast.LENGTH_LONG).show();
                }
            }.start();
        }

        protected String doInBackground(String... kq) {
            try {
                ObjectClient mOC = new ObjectClient();
                mOC.setCommand("getds");
                upload(mOC);
            } catch (Exception e) {
            }

            return null;

        }

        protected void onPostExecute(String result) {
            mcountdown.cancel();
            loading(false);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (list_suco != null) {
                        set_list(list_suco);
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
                        list_tru = mCB.getList_tru();
                        list_tram = mCB.getList_tram();
                        list_tuyen = mCB.getList_tuyen();
                        list_suco = mCB.getList_suco();
                        list_donvi = mCB.getList_DONVI();
                        if(list_donvi!=null){
                            MainActivity.list_tuyen = new ArrayList<Obj_donvi>(list_donvi);
                        }
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

    public void set_list(List<Obj_SU_CO> list){
        if(list!=null) {
            mAdapter = new Adapter_SUCO(getActivity(), list);
            recList.setAdapter(mAdapter);
            edt_search.setHint("Tim kiem trong " + list.size() + " diem");
        }
    }
    public void them_tru(View v){
        Intent i = new Intent(getActivity(),Them_Tru.class);
        startActivity(i);
    }
    public void loading (boolean load){
        if(load){
            prov.setVisibility(View.VISIBLE);
        }else{
            prov.setVisibility(View.GONE);
        }
    }
}
