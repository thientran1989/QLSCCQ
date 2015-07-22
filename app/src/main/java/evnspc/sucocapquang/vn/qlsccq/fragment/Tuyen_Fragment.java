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
import evnspc.sucocapquang.vn.qlsccq.server.Conect_server_sign_async;
import evnspc.sucocapquang.vn.qlsccq.utils.CONFIG_LINK;
import evnspc.sucocapquang.vn.qlsccq.utils.DB_COMMAND;


public class Tuyen_Fragment extends Fragment {

    String mURL = "http://10.179.0.22:8080/suco/getds";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mReAdapter;
    Adapter_TUYEN mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recList;
    EditText edt_search;
    ProgressWheel prov;
    ObjectClient mOC = null;

	public static Tuyen_Fragment newInstance(String text){
		Tuyen_Fragment mFragment = new Tuyen_Fragment();
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
        mOC = new ObjectClient();
        mOC.setCommand(DB_COMMAND.LENH_GET_TUYEN);
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
                            new Conect_server_sign_async(getActivity(),mURL,mOC,prov,recList,mAdapter).execute();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 2500);
                }
            });
        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "loi refesh :\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            new Conect_server_sign_async(getActivity(),mURL,mOC,prov,recList,mAdapter).execute();
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


		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}


}
