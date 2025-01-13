package br.com.branetlogistica.core.feriado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeriadoService {

	private static final Map<LocalDate,Feriado> FERIADOS = new HashMap<LocalDate,Feriado>();

	
	
	@Autowired
	private FeriadoMapper mapper;
	
	 
	
	
	private void iniciarFeriados() {
		List<Feriado> entities =  new ArrayList<Feriado>();
		if(entities == null || entities.isEmpty())
			return;
		
		for(Feriado entity: entities) {
			if(entity.isDisabled())
				continue;
			
			if(FERIADOS.containsKey(entity.getDataFeriado()))
				return;
			
			FERIADOS.put(entity.getDataFeriado(), entity);
			
		}
		
		
	}
	
	public boolean isFeriado(LocalDate teste) {
		if(FERIADOS.isEmpty())
			iniciarFeriados();
				
		if(FERIADOS.containsKey(teste))
			return true;
		
		return false;
		
	}
	
	
}
