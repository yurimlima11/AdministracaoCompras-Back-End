package com.yurimiranda.administracaocompras.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.yurimiranda.administracaocompras.entities.Categoria;
import com.yurimiranda.administracaocompras.repositories.CategoriaRepository;
import com.yurimiranda.administracaocompras.services.exception.DataIntegrityException;
import com.yurimiranda.administracaocompras.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria getById(Integer id){
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encontrada! Id: " + id + " Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria create(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		getById(categoria.getId());
		return categoriaRepository.save(categoria);
	}
	
	public void delete(Integer id) {
		try {
			categoriaRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
