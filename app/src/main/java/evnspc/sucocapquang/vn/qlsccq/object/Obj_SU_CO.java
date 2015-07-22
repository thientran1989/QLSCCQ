package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;


public class Obj_SU_CO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String TIME_PHATHIEN;
	public java.sql.Timestamp TG_PHATHIEN ;
	public String TIME_XULY;
	public java.sql.Timestamp TG_XULY ;
	public String NOI_DUNG;
	public String NGUYEN_NHAN;
	public String KHAC_PHUC;
	public String PHAM_VI;
	public String DON_VI;
	public String TRU;
	public String SO_QD;
	public String NGUYEN_NHAN_TTE;
	
	public static final String tag_TABLE="SC_SU_CO";
	public static final String tag_TIME_PHATHIEN ="TIME_PHATHIEN";
	public static final String tag_TG_PHATHIEN ="TG_PHATHIEN";
	public static final String tag_TIME_XULY ="TIME_XULY";
	public static final String tag_TG_XULY ="TG_XULY";
	public static final String tag_NOI_DUNG ="NOI_DUNG";
	public static final String tag_NGUYEN_NHAN ="NGUYEN_NHAN";
	public static final String tag_KHAC_PHUC ="KHAC_PHUC";
	public static final String tag_PHAM_VI ="PHAM_VI";
	public static final String tag_DON_VI ="DON_VI";
	public static final String tag_TRU ="TRU";
	public static final String tag_SO_QD ="SO_QD";
	public static final String tag_NGUYEN_NHAN_TTE ="NGUYEN_NHAN_TTE";
	
	public java.sql.Timestamp getTG_PHATHIEN() {
		return TG_PHATHIEN;
	}
	public void setTG_PHATHIEN(java.sql.Timestamp tG_PHATHIEN) {
		TG_PHATHIEN = tG_PHATHIEN;
	}
	public java.sql.Timestamp getTG_XULY() {
		return TG_XULY;
	}
	public void setTG_XULY(java.sql.Timestamp tG_XULY) {
		TG_XULY = tG_XULY;
	}
	public String getNOI_DUNG() {
		return NOI_DUNG;
	}
	public void setNOI_DUNG(String nOI_DUNG) {
		NOI_DUNG = nOI_DUNG;
	}
	public String getNGUYEN_NHAN() {
		return NGUYEN_NHAN;
	}
	public void setNGUYEN_NHAN(String nGUYEN_NHAN) {
		NGUYEN_NHAN = nGUYEN_NHAN;
	}
	public String getKHAC_PHUC() {
		return KHAC_PHUC;
	}
	public void setKHAC_PHUC(String kHAC_PHUC) {
		KHAC_PHUC = kHAC_PHUC;
	}
	public String getPHAM_VI() {
		return PHAM_VI;
	}
	public void setPHAM_VI(String pHAM_VI) {
		PHAM_VI = pHAM_VI;
	}
	public String getDON_VI() {
		return DON_VI;
	}
	public void setDON_VI(String dON_VI) {
		DON_VI = dON_VI;
	}
	public String getTRU() {
		return TRU;
	}
	public void setTRU(String tRU) {
		TRU = tRU;
	}
	public String getSO_QD() {
		return SO_QD;
	}
	public void setSO_QD(String sO_QD) {
		SO_QD = sO_QD;
	}
	public String getTIME_PHATHIEN() {
		return TIME_PHATHIEN;
	}
	public String getNGUYEN_NHAN_TTE() {
		return NGUYEN_NHAN_TTE;
	}
	public void setNGUYEN_NHAN_TTE(String nGUYEN_NHAN_TTE) {
		NGUYEN_NHAN_TTE = nGUYEN_NHAN_TTE;
	}
	public String getTIME_XULY() {
		return TIME_XULY;
	}
}
