package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;

import evnspc.sucocapquang.vn.qlsccq.myfunction.Utils;

@SuppressWarnings("serial")
public class Obj_TUYEN implements Serializable{
	
	public String MA_TUYEN;
	public String TEN_TUYEN;
	public String TRAM_DAU;
	public String TRAM_CUOI;
	public String LOAI_CAP;
	public String CHIEU_DAI;
	public String TT_SOI;
	
	public static final String tag_TABLE_TUYEN="SC_TUYEN";
	public static final String tag_MA_TUYEN="MA_TUYEN";
	public static final String tag_TEN_TUYEN="TEN_TUYEN";
	public static final String tag_TRAM_DAU="TRAM_DAU";
	public static final String tag_TRAM_CUOI="TRAM_CUOI";
	public static final String tag_LOAI_CAP="LOAI_CAP";
	public static final String tag_CHIEU_DAI="CHIEU_DAI";
	public static final String tag_TT_SOI="TT_SOI";
	public String getMA_TUYEN() {
		return MA_TUYEN;
	}
	public void setMA_TUYEN(String mA_TUYEN) {
		MA_TUYEN = mA_TUYEN;
	}
	public String getTEN_TUYEN() {
		return TEN_TUYEN;
	}
	public void setTEN_TUYEN(String tEN_TUYEN) {
		TEN_TUYEN = tEN_TUYEN;
	}
	public String getTRAM_DAU() {
		return TRAM_DAU;
	}
	public void setTRAM_DAU(String tRAM_DAU) {
		TRAM_DAU = tRAM_DAU;
	}
	public String getTRAM_CUOI() {
		return TRAM_CUOI;
	}
	public void setTRAM_CUOI(String tRAM_CUOI) {
		TRAM_CUOI = tRAM_CUOI;
	}
	public String getLOAI_CAP() {
		return LOAI_CAP;
	}
	public void setLOAI_CAP(String lOAI_CAP) {
		LOAI_CAP = lOAI_CAP;
	}
	public String getCHIEU_DAI() {
		return CHIEU_DAI;
	}
	public void setCHIEU_DAI(String cHIEU_DAI) {
		CHIEU_DAI = cHIEU_DAI;
	}
	
	public String getTT_SOI() {
		return TT_SOI;
	}
	public void setTT_SOI(String tT_SOI) {
		TT_SOI = tT_SOI;
	}
	public String getLOAI_CAP_label() {
		String KQ = getLOAI_CAP();
		try {
			for (Obj_Text oT : Utils.get_list_CAPQUANG()) {
				if(oT.KEY.equals(KQ)){
					KQ = oT.NAME;
				}
			}
		} catch (Exception e) {
			
		}
		return KQ;
	}
	
}
