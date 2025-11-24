package com.example.cursos.service;

import com.example.cursos.dto.CategoriaRequestDTO;
import com.example.cursos.dto.CategoriaResponseDTO;
import com.example.cursos.entity.Categoria;
import com.example.cursos.repository.CategoriaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoria = new Categoria();
        CategoriaResponseDTO response = new CategoriaResponseDTO();

        categoria.setNombre(categoriaRequestDTO.getNombre());
        
        categoriaRepository.save(categoria);
        response.setNombre(categoria.getNombre());
        return response;
    }

    @Transactional
    public List<CategoriaResponseDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponseDTO> response = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CategoriaResponseDTO dto = new CategoriaResponseDTO();
            dto.setNombre(categoria.getNombre());
            response.add(dto);
        }
        return response;
    }

    @Transactional
    public Optional<CategoriaResponseDTO> buscarId(Long id){
        Optional<Categoria> encontrada = categoriaRepository.findById(id);

        if (encontrada.isPresent()) {
            Categoria categoria = encontrada.get();
            CategoriaResponseDTO response = new CategoriaResponseDTO();

            response.setNombre(categoria.getNombre());
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Transactional
    public Boolean eliminarCategoria(Long id){
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<CategoriaResponseDTO> actualizarCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO){
        Optional<Categoria> encontrada = categoriaRepository.findById(id);

        if (encontrada.isPresent()) {
            Categoria categoria = encontrada.get();

            categoria.setNombre(categoriaRequestDTO.getNombre());
            Categoria actualizada = categoriaRepository.save(categoria);

            CategoriaResponseDTO response = new CategoriaResponseDTO();
            response.setNombre(actualizada.getNombre());

            return Optional.of(response);
        }
        return Optional.empty();
    }
}
