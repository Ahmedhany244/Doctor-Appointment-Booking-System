package com.global.hr.entity;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
@Embeddable
public class ExcuseId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer doctorid; // matches Doctor's id type
	@Enumerated(EnumType.STRING)
	private DayOfWeek excuseDay; // enum type
	    public Integer getDoctorid() {
		return doctorid;
	}

	 public void setDoctorid(Integer doctorid) {
		 this.doctorid = doctorid;
	 }

	 public DayOfWeek getExcuseDay() {
		 return excuseDay;
	 }

	 public void setExcuseDay(DayOfWeek excuseDay) {
		 this.excuseDay = excuseDay;
	 }
	    public ExcuseId() {}

	    public ExcuseId(Integer doctorid, DayOfWeek excuseDay) {
	        this.doctorid = doctorid;
	        this.excuseDay = excuseDay;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof ExcuseId)) return false;
	        ExcuseId that = (ExcuseId) o;
	        return Objects.equals(doctorid, that.doctorid) &&
	               excuseDay == that.excuseDay;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(doctorid, excuseDay);
	    }

}
