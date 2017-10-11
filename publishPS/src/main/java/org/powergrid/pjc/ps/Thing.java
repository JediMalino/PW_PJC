package org.powergrid.pjc.ps;

public class Thing {
	private ThingState state;
	private int id;
	private String name;
	
	public Thing(ThingState ts, int id, String name) {
		this.setState(ts);
		this.setId(id);
		this.setName(name);
	}

	public ThingState getState() {
		
		return state;
	}
	
	public void setState() {
		setState(ThingState.randomState());
	}

	public void setState(ThingState state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Thing [state=" + state + ", id=" + id + ", name=" + name + "]";
	}
	
}
