package evnspc.sucocapquang.vn.qlsccq.utils;

import java.util.ArrayList;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.object.Obj_Text;

public class Utils {
	
	public static final String version = "Phiên bản : 1.8";
	public static final String CAP_PHAN_XUONG ="PHANXUONG";
	public static final String CAP_CONGTY ="CONGTY";
	public static final String CAP_DIENLUC ="DIENLUC";
	
	public static final int SUA =0;
	public static final int TAO =1;
	
	public static final String CB_TONTAI ="TONTAI";
	public static final String CB_OK ="OK";
	
	public static final List<Obj_Text> get_NHANHRE(){
		List<Obj_Text> list_tinhtrang = new ArrayList<Obj_Text>();
		list_tinhtrang.add(new Obj_Text("KO", "KHÔNG"));
		list_tinhtrang.add(new Obj_Text("NHANHRE", "NHÁNH RẼ"));
		return list_tinhtrang;
	}
	
	public static final List<Obj_Text> get_list_LOAITRAM(){
		List<Obj_Text> list_tinhtrang = new ArrayList<Obj_Text>();
		list_tinhtrang.add(new Obj_Text("DIENLUC", "ĐIỆN LỰC"));
		list_tinhtrang.add(new Obj_Text("TBA110KV", "TBA 110Kv"));
		return list_tinhtrang;
	}
	public static final List<Obj_Text> get_list_MANGXONG(){
		List<Obj_Text> list_tinhtrang = new ArrayList<Obj_Text>();
		list_tinhtrang.add(new Obj_Text("24FO", "MĂNG XÔNG 24FO"));
		list_tinhtrang.add(new Obj_Text("12FO", "MĂNG XÔNG 12FO"));
		list_tinhtrang.add(new Obj_Text("ODF-24FO", "ODF-24FO"));
		list_tinhtrang.add(new Obj_Text("ODF-12FO", "ODF-12FO"));
		return list_tinhtrang;
	}
	public static final List<Obj_Text> get_list_ODF(){
		List<Obj_Text> list_tinhtrang = new ArrayList<Obj_Text>();
		list_tinhtrang.add(new Obj_Text("ODF-24FO", "ODF-24FO"));
		list_tinhtrang.add(new Obj_Text("ODF-12FO", "ODF-12FO"));
		return list_tinhtrang;
	}
	public static final List<Obj_Text> get_list_CAPQUANG(){
		List<Obj_Text> list_tinhtrang = new ArrayList<Obj_Text>();
		list_tinhtrang.add(new Obj_Text("F8-24FO", "CÁP QUANG F8-24FO"));
		list_tinhtrang.add(new Obj_Text("F8-12FO", "CÁP QUANG F8-12FO"));
		list_tinhtrang.add(new Obj_Text("F8-12FO-DKXD", "CÁP QUANG F8-12FO DỰ KIẾN XD"));
		return list_tinhtrang;
	}
}
