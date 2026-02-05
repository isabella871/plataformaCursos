package com.example.cursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.cursos.dto.request.CursoRequestDTO;
import com.example.cursos.dto.response.CursoResponseDTO;
import com.example.cursos.entity.Categoria;
import com.example.cursos.entity.Curso;
import com.example.cursos.repository.CategoriaRepository;
import com.example.cursos.repository.CursoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // Todos los métodos dentro de la clase se ejecutarán dentro de una transacción
//  si algo falla => hace rollback | si todo esta bien => se confirma la operación
public class CursoService {
    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;

    // crear curso
    @Transactional
    public CursoResponseDTO crearCurso(CursoRequestDTO cursoRequestDTO){
        Categoria categoria = categoriaRepository.findById(cursoRequestDTO.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Curso curso = new Curso();
        curso.setTitulo(cursoRequestDTO.getTitulo());
        curso.setDescripcion(cursoRequestDTO.getDescripcion());
        curso.setCategoria(categoria);
        
        cursoRepository.save(curso);

        CursoResponseDTO response = new CursoResponseDTO();
        response.setId(curso.getId());
        response.setTitulo(curso.getTitulo());
        response.setDescripcion(curso.getDescripcion());
        response.setCategoria(curso.getCategoria().getNombre());

        return response;
    }

    @Transactional
    public List<CursoResponseDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDTO> response = new ArrayList<>();

        for (Curso curso : cursos) {
            CursoResponseDTO responseDTO = new CursoResponseDTO();

            responseDTO.setTitulo(curso.getTitulo());
            responseDTO.setId(curso.getId());
            responseDTO.setDescripcion(curso.getDescripcion());
            responseDTO.setCategoria(curso.getCategoria().getNombre());
            response.add(responseDTO);
        }
        return response;
    }

    @Transactional
    public Optional<CursoResponseDTO> buscarId(Long id){
        Optional<Curso> cursoEncontrado = cursoRepository.findById(id);

        if (cursoEncontrado.isPresent()) {
            Curso curso = cursoEncontrado.get();
            CursoResponseDTO response = new CursoResponseDTO();

            response.setId(curso.getId());
            response.setTitulo(curso.getTitulo());
            response.setDescripcion(curso.getDescripcion());
            response.setCategoria(curso.getCategoria().getNombre());

            return Optional.of(response);
        }else{
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean eliminarCurso(Long id){
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<CursoResponseDTO> actualizarCurso(Long id, CursoRequestDTO cursoRequestDTO){
        Optional<Curso> cursoEncontrado = cursoRepository.findById(id);

        if (cursoEncontrado.isPresent()) {
            Curso curso = cursoEncontrado.get();

            curso.setTitulo(cursoRequestDTO.getTitulo());
            curso.setDescripcion(cursoRequestDTO.getDescripcion());
            Curso cursoActualizado = cursoRepository.save(curso);

            CursoResponseDTO response = new CursoResponseDTO();
            response.setId(cursoActualizado.getId());
            response.setTitulo(cursoActualizado.getTitulo());
            response.setDescripcion(cursoActualizado.getDescripcion());
            response.setCategoria(curso.getCategoria().getNombre());
            return Optional.of(response);
        }else{
            return Optional.empty();
        }
    }
}
