package br.com.branetlogistica.core.queue.dto;

import br.com.branetlogistica.core.interfaces.impl.QueueContextDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueDto {

	private Object dto;
	private QueueContextDto context;
	
	
	
}
