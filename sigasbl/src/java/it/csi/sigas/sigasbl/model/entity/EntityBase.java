package it.csi.sigas.sigasbl.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EntityBase {	
	
	@Column(name="ins_user")
	protected String insUser;
	
	@Column(name="ins_date")
	protected Date insDate;
	
	@Column(name="mod_user", nullable=false)
	protected String modUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mod_date", nullable=false)
	protected Date modDate;	
		
	@Version
	@Column(name="version", nullable=false)
	protected transient Long version;

	public String getInsUser() {
		return insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}	

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}	

}
