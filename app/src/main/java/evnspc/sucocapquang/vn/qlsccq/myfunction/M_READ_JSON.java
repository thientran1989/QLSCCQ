package evnspc.sucocapquang.vn.qlsccq.myfunction;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.object.CallbackResult;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRU;


public class M_READ_JSON {

	public static CallbackResult read_callback(JsonObject mJO) {
		CallbackResult mCB = null;
		try {
			Gson gson = new Gson();
			mCB = gson.fromJson(mJO.get("CB").getAsString(),CallbackResult.class);
		} catch (Exception e) {

		}
		return mCB;
	}

	// read list
//	public static List<Obj_TRU> read_list_MBA(JsonObject mJO) {
//		List<Obj_TRU> yourList = null;
//		try {
//			Type listType = new TypeToken<List<Obj_TRU>>() {
//			}.getType();
//			yourList = new Gson().fromJson(mJO.get(Obj_TRU.tag_TABLE).getAsString(), listType);
//		} catch (Exception e) {
//
//		}
//		return yourList;
//	}

	public static boolean is_accepted(Context c, CallbackResult mCB) {
		boolean accepted = false;
		try {
			if (mCB != null) {
				if (mCB.getResultString().equals("OK")) {
					accepted = true;
				}
			}
		} catch (Exception e) {

		}
		return accepted;
	}

}
