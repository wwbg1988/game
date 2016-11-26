package com.ssic.game.admin.model;

import java.util.HashSet;
import java.util.Set;




public class Tresourcetype implements java.io.Serializable {

	private String id;
	private String name;
	private Set<Tresource> tresources = new HashSet<Tresource>(0);

	public Tresourcetype() {
	}

	public Tresourcetype(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Tresourcetype(String id, String name, Set<Tresource> tresources) {
		this.id = id;
		this.name = name;
		this.tresources = tresources;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

}
