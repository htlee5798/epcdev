package lcn.module.common.session;

import java.io.*;

public class SessionValueData implements Serializable{

	private static final long serialVersionUID = 7020395186422487192L;
	
	private Object value;

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
