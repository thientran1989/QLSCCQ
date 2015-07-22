package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;

public class Obj_donvi implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ma_donvi;
	public String ten_donvi;
	public String x_chuan;
	public String y_chuan;
	public String x_tam;
	public String y_tam;
	public String cap;
	public String ten_full;
	public String ten_rutgon;
	
	public Obj_donvi(String ma_donvi, String ten_donvi, String x_chuan,
			String y_chuan, String x_tam, String y_tam) {
		super();
		this.ma_donvi = ma_donvi;
		this.ten_donvi = ten_donvi;
		this.x_chuan = x_chuan;
		this.y_chuan = y_chuan;
		this.x_tam = x_tam;
		this.y_tam = y_tam;
	}
	public Obj_donvi() {
	}
	
	public String getTen_full() {
		return ten_full;
	}
	public void setTen_full(String ten_full) {
		this.ten_full = ten_full;
	}
	public String getTen_rutgon() {
		return ten_rutgon;
	}
	public void setTen_rutgon(String ten_rutgon) {
		this.ten_rutgon = ten_rutgon;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getX_chuan() {
		return x_chuan;
	}
	public void setX_chuan(String x_chuan) {
		this.x_chuan = x_chuan;
	}
	public String getY_chuan() {
		return y_chuan;
	}
	public void setY_chuan(String y_chuan) {
		this.y_chuan = y_chuan;
	}
	public String getX_tam() {
		return x_tam;
	}
	public void setX_tam(String x_tam) {
		this.x_tam = x_tam;
	}
	public String getY_tam() {
		return y_tam;
	}
	public void setY_tam(String y_tam) {
		this.y_tam = y_tam;
	}
	public String getMa_donvi() {
		return ma_donvi;
	}
	public void setMa_donvi(String ma_donvi) {
		this.ma_donvi = ma_donvi;
	}
	public String getTen_donvi() {
		return ten_donvi;
	}
	public void setTen_donvi(String ten_donvi) {
		this.ten_donvi = ten_donvi;
	}
	
	public static final String TAG_table_donvi="SC_DONVI";
	
	public static final String TAG_ma_donvi="ma_donvi";
	public static final String TAG_ten_donvi="ten_donvi";
	public static final String TAG_x_chuan="x_chuan";
	public static final String TAG_y_chuan="y_chuan";
	public static final String TAG_x_tam="x_tam";
	public static final String TAG_y_tam="y_tam";
	public static final String TAG_cap="cap";
	public static final String TAG_ten_full="TEN_FULL";
	public static final String TAG_ten_rutgon="TEN_RUTGON";
	

}
