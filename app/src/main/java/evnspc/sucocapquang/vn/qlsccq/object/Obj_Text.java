package evnspc.sucocapquang.vn.qlsccq.object;

import java.io.Serializable;

public class Obj_Text implements Serializable{
	private static final long serialVersionUID = 1L;
	public String KEY;
	public String NAME;
	
	public Obj_Text(String kEY, String nAME) {
		super();
		KEY = kEY;
		NAME = nAME;
	}

}
