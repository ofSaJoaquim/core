package br.com.branetlogistica.core.interfaces;

import java.time.LocalDateTime;

public interface IEntity {	
	
	public Long getId();
	public boolean isDisabled();
		
	public void setCreatedBy(Long createdBy);
	public Long getCreatedBy();	
	public void setCreatedAt(LocalDateTime createdAt);	
	public LocalDateTime getCreatedAt();
	
	
	
	public void setUpdatedBy(Long updateddBy);
	public Long getUpdatedBy();	
	public void setUpdatedAt(LocalDateTime updatedAt);
	public LocalDateTime getUpdatedAt();
	
	
	public void setVersion(Long version);
	public Long getVersion();
	
}
