package org.acme.insurance.policyquote.domain;
/*
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity which represents greet.
 */
@Entity
@Table(name = "policy_quote")
public class PolicyQuote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyQuoteID;

    @Column(name  = "age")
    private int age;
    
    @Column(name  = "credit_score")
    private int creditScore;

    @Column(name  = "dl_number")
    private String dlNumber;
    
    @Column(name  = "driver_name")
    private String driverName;

    @Column(name  = "num_of_accidents")
    private int numberOfAccidents;

    @Column(name  = "num_of_tickets")
    private int numberOfTickets;
    
    @Column(name  = "policy_type")
    private String policyType;    

    @Column(name  = "price")
    private int price;
    
    @Temporal(TemporalType.DATE)
    @Column(name  = "request_date")
    private Date requestDate;

    @Column(name  = "ssn")
    private String ssn;    
    
    @Column(name  = "vehicle_year")
    private int vehicleYear;

	public Long getPolicyQuoteID() {
		return policyQuoteID;
	}

	public void setPolicyQuoteID(Long policyQuoteID) {
		this.policyQuoteID = policyQuoteID;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public String getDlNumber() {
		return dlNumber;
	}

	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public int getNumberOfAccidents() {
		return numberOfAccidents;
	}

	public void setNumberOfAccidents(int numberOfAccidents) {
		this.numberOfAccidents = numberOfAccidents;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(int vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	
	public String toString() {
		return "Policy Quote Details: [Driver Name=" + this.driverName
				+ ", Age=" + this.age
				+ ", SSN=" + this.ssn
				+ ", Credit Score=" + this.creditScore
				+ ", Number of Accidents=" + this.numberOfAccidents
				+ ", Number of Tickets=" + this.numberOfTickets
				+ ", Vehicle Year=" + this.vehicleYear;
	}

}
