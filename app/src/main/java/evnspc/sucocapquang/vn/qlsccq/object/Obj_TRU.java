package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.myfunction.Utils;

public class Obj_TRU implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String TRU;
	public String X;
	public String Y;
	public String MANGXONG;
	public String NHANH_RE;
	public int CACHTRAM ;
	public String GHI_CHU;
	public java.sql.Timestamp TG_TAO ;
	public String MA_DVI;
	
	public static final String tag_TABLE="SC_TRU";
	public static String tag_TRU="TRU";
	public static String tag_X="X";
	public static String tag_Y="Y";
	public static String tag_MANGXONG="MANGXONG";
	public static String tag_NHANH_RE="NHANH_RE";
	public static String tag_CACHTRAM ="CACHTRAM";
	public static String tag_GHI_CHU ="GHI_CHU";
	public static String tag_TG_TAO ="TG_TAO";
	public static String tag_MA_DVI ="MA_DVI";

	public String getTRU() {
		return TRU;
	}
	public void setTRU(String tRU) {
		TRU = tRU;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	public String getMANGXONG() {
		return MANGXONG;
	}
	public void setMANGXONG(String mANGXONG) {
		MANGXONG = mANGXONG;
	}
	public String getNHANH_RE() {
		return NHANH_RE;
	}
	public void setNHANH_RE(String nHANH_RE) {
		NHANH_RE = nHANH_RE;
	}
	
	public int getCACHTRAM() {
		return CACHTRAM;
	}
	public void setCACHTRAM(int cACHTRAM) {
		CACHTRAM = cACHTRAM;
	}
	public String getGHI_CHU() {
		return GHI_CHU;
	}
	public void setGHI_CHU(String gHI_CHU) {
		GHI_CHU = gHI_CHU;
	}
	
	public java.sql.Timestamp getTG_TAO() {
		return TG_TAO;
	}
	public void setTG_TAO(java.sql.Timestamp tG_TAO) {
		TG_TAO = tG_TAO;
	}
	
	public String getMA_DVI() {
		return MA_DVI;
	}
	public void setMA_DVI(String mA_DVI) {
		MA_DVI = mA_DVI;
	}
	public String getNHANH_RE_label() {
		String KQ = getNHANH_RE();
		try {
			for (Obj_Text oT : Utils.get_NHANHRE()) {
				if(oT.KEY.equals(KQ)){
					KQ = oT.NAME;
				}
			}
		} catch (Exception e) {
			
		}
		return KQ;
	}
	public String getMANGXONG_label() {
		String KQ = getMANGXONG();
		try {
			for (Obj_Text oT : Utils.get_list_MANGXONG()) {
				if(oT.KEY.equals(KQ)){
					KQ = oT.NAME;
				}
			}
		} catch (Exception e) {
			
		}
		return KQ;
	}
	public String getDONVI_label(List<Obj_donvi> list_tuyen) {
		String KQ = getMA_DVI();
		try {
			for (Obj_donvi oT : list_tuyen) {
				if(oT.ma_donvi.equals(KQ)){
					KQ = oT.getTen_donvi();
				}
			}
		} catch (Exception e) {
			
		}
		return KQ;
	}
}
