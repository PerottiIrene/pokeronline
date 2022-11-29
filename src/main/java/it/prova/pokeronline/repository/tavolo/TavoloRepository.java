package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>{
	
	@Query("select distinct t from Tavolo t left join fetch t.utentiGiocatori ")
	List<Tavolo> findAllEagerUtentiGiocatori();

	@Query("from Tavolo t left join fetch t.utentiGiocatori where t.id=?1")
	Tavolo findByIdEagerUtentiGiocatori(Long idTavolo);
	
	

}
