package pw.ewen.WLPT.entity;
/*
 * 代表权限模块管理的资源
 *  
 */
public abstract class Resource {
	private String ID;
	private String type;
	
	//资源类型
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//资源唯一标志号
	public String getID(){
		return this.ID;
	}
}
