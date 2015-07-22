package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;
import java.util.List;

public class CallbackResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Command;
	private String ResultString="";
	private int ResultInt=0;
	public List<?> ResultObj=null;
	public List<Obj_TRAM> list_tram=null;
	public List<Obj_TUYEN> list_tuyen=null;
	public List<Obj_TRU> list_tru=null;
	public List<Obj_SU_CO> list_suco=null;
	public List<Obj_donvi> list_DONVI=null;
	public String kq2="";
	
	public int getResultInt() {
		return ResultInt;
	}
	public void setResultInt(int resultInt) {
		ResultInt = resultInt;
	}
	public CallbackResult() {
		super();
	}
	public String getCommand() {
		return Command;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public String getResultString() {
		return ResultString;
	}
	public void setResultString(String resultString) {
		ResultString = resultString;
	}
	public List<?> getResultObj() {
		return ResultObj;
	}
	public void setResultObj(List<?> resultObj) {
		ResultObj = resultObj;
	}
	public List<Obj_TRAM> getList_tram() {
		return list_tram;
	}
	public void setList_tram(List<Obj_TRAM> list_tram) {
		this.list_tram = list_tram;
	}
	public List<Obj_TUYEN> getList_tuyen() {
		return list_tuyen;
	}
	public void setList_tuyen(List<Obj_TUYEN> list_tuyen) {
		this.list_tuyen = list_tuyen;
	}
	public List<Obj_TRU> getList_tru() {
		return list_tru;
	}
	public void setList_tru(List<Obj_TRU> list_tru) {
		this.list_tru = list_tru;
	}
	public List<Obj_SU_CO> getList_suco() {
		return list_suco;
	}
	public void setList_suco(List<Obj_SU_CO> list_suco) {
		this.list_suco = list_suco;
	}
	public List<Obj_donvi> getList_DONVI() {
		return list_DONVI;
	}
	public void setList_DONVI(List<Obj_donvi> list_DONVI) {
		this.list_DONVI = list_DONVI;
	}
	
	
}
