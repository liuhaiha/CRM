package com.tydic.traffic.tafa.utils.compare;

import java.io.Serializable;

/**
 * ClassName:CompareMo <br/>
 * Function: 比较用MO. <br/>
 * *
 * 
 * @author think
 */

public class CompareMo implements Serializable {

	private static final long serialVersionUID = -1352319586471852154L;

	private int id;

	private String type;

	private String oldText;

	private String newText;

	public CompareMo(int i, String oldLine, String newLine, String tag) {
		this.id = i;
		this.oldText = oldLine;
		this.newText = newLine;
		this.type = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOldText() {
		return oldText;
	}

	public void setOldText(String oldText) {
		this.oldText = oldText;
	}

	public String getNewText() {
		return newText;
	}

	public void setNewText(String newText) {
		this.newText = newText;
	}
}
