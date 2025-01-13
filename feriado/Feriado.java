package br.com.branetlogistica.core.feriado;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import br.com.branetlogistica.core.interfaces.IEntity;
import br.com.branetlogistica.core.log.auditory.service.AuditListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode.Include;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feriado")
@EntityListeners(AuditListener.class)
public class Feriado implements IEntity  {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "disabled")
	private boolean disabled;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private Long createdBy;

	@LastModifiedBy
	@Column(name = "updated_by",  updatable = true, insertable = false)
	private Long updatedBy;
	
	@LastModifiedDate
	@Column(name = "updated_at",  updatable = true, insertable = false)
	private LocalDateTime updatedAt;
		
	@Version
	@Column(name = "version", nullable = false)
	private Long version;
	
	@Column(name = "data_feriado", nullable = false)
	private LocalDate dataFeriado;
	
	@Column(name = "descicao", nullable = false)
	private String descricao;
}
