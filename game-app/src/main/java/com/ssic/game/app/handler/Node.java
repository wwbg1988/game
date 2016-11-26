package com.ssic.game.app.handler;

import java.util.Date;
import java.util.List;

import com.ssic.game.app.controller.dto.AppGroupUserDto;

import lombok.Getter;
import lombok.Setter;

public class Node {
	  
	 /** 
	  * 节点编号 
	  */  
	 @Getter
	 @Setter
	 public String id;  
	 /** 
	  * 节点内容 
	  */  
	 @Getter
	 @Setter
	 public String text;  
	 /** 
	  * 父节点编号 
	  */  
	 @Getter
	 @Setter
	 public String parentId;  
	 @Getter
	 @Setter
	 public String groupId;
	 @Getter
	 @Setter
	 public Date createtime;
	 /** 
	  * 孩子节点列表 
	  */  
	 @Getter
	 @Setter
	 private Children children = new Children();  
	 @Getter
	 @Setter
	 private List<AppGroupUserDto> listnode;
	   
	 // 先序遍历，拼接JSON字符串  
	 public String toString() {    
	  String result = "{"  
	   + "id : '" + id + "'"  
	   + ", text : '" + text + "'";  
	    
	  if (children != null && children.getSize() != 0) {  
	   result += ", children : " + children.toString();  
	  } else {  
	   result += ", leaf : true";  
	  }  
	      
	  return result + "}";  
	 }  
	   
	 // 兄弟节点横向排序  
	 public void sortChildren() {  
	  if (children != null && children.getSize() != 0) {  
	   children.sortChildren();  
	  }  
	 }  
	   
	 // 添加孩子节点  
	 public void addChild(Node node) {  
	  this.children.addChild(node);  
	 } 
}
