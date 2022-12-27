package com.infinite.hib;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;



@ManagedBean(name="bus")
@SessionScoped
@Entity
@Table(name="bus_details")
public class Bus {
	
	@Id
	@Column(name="bus_id")
	private String bus_id;
	
	@Column(name="bus_no")
	private String bus_no;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type_of_bus")
	private BusType type_of_bus;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type_of_servies")
	private BusServies type_of_servies;
	
	@Column(name="no_of_seat")
	private int no_of_seat;
	
	@Enumerated(EnumType.STRING)
	@Column(name="bus_status")
	private BusStatus bus_status;

	public String getBus_id() {
		return bus_id;
	}

	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}

	public String getBus_no() {
		return bus_no;
	}

	public void setBus_no(String bus_no) {
		this.bus_no = bus_no;
	}

	public BusType getType_of_bus() {
		return type_of_bus;
	}

	public void setType_of_bus(BusType type_of_bus) {
		this.type_of_bus = type_of_bus;
	}

	public BusServies getType_of_servies() {
		return type_of_servies;
	}

	public void setType_of_servies(BusServies type_of_servies) {
		this.type_of_servies = type_of_servies;
	}

	public int getNo_of_seat() {
		return no_of_seat;
	}

	public void setNo_of_seat(int no_of_seat) {
		this.no_of_seat = no_of_seat;
	}

	public BusStatus getBus_status() {
		return bus_status;
	}

	public void setBus_status(BusStatus bus_status) {
		this.bus_status = bus_status;
	}
	
	
	
}