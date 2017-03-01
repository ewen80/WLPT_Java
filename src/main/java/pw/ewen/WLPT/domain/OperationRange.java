package pw.ewen.WLPT.domain;

import pw.ewen.WLPT.domain.entity.MyResource;

import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

/*
 * 保存资源范围内的操作列表，可以理解为包含一个资源列表的权限包
 */
public abstract class OperationRange {
	
	private String ID;
	private Predicate<MyResource> resourceSelector;
	private int operations;
	
	/**
	 * 构造函数
	 * @param rs 资源选择器
	 * @param resourcesSet  待选择资源集合
	 */
	public OperationRange(Predicate<MyResource> rs){
		this.resourceSelector = rs;
	}
	/**
	 * 选择资源生成资源范围
	 * @return 经过范围筛选后的资源列表
	 */
	public Set<MyResource> selectResource(Set<MyResource> resources){
		return resources.stream().filter(resourceSelector).collect(toSet());
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	//获取操作权限(CRUD 0X0000-无操作权限  0X1111-全操作权限)
	public int getOperations() {
		return operations;
	}
	//设置操作权限(CRUD 0X0000-无操作权限  0X1111-全操作权限)
	public void setOperations(int operations) {
		this.operations = operations;
	}
}
