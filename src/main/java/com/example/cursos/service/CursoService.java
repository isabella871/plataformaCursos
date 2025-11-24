package com.example.cursos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.cursos.dto.CursoRequestDTO;
import com.example.cursos.dto.CursoResponseDTO;
import com.example.cursos.entity.Curso;
import com.example.cursos.repository.CursoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // Todos los métodos dentro de la clase se ejecutarán dentro de una transacción
//  si algo falla => hace rollback | si todo esta bien => se confirma la operación
public class CursoService {
    private final CursoRepository cursoRepository;

    // crear curso
    @Transactional
    public CursoResponseDTO crearCurso(CursoRequestDTO cursoRequestDTO){
        CursoResponseDTO response = new CursoResponseDTO();
        Curso curso = new Curso();

        curso.setTitulo(cursoRequestDTO.getTitulo());
        curso.setDescripcion(cursoRequestDTO.getDescripcion());
        
        cursoRepository.save(curso);

        response.setTitulo(curso.getTitulo());
        response.setDescripcion(curso.getDescripcion());

        return response;
    }

    @Transactional
    public List<CursoResponseDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDTO> response = new ArrayList<>();

        for (Curso curso : cursos) {
            CursoResponseDTO responseDTO = new CursoResponseDTO();

            responseDTO.setTitulo(curso.getTitulo());
            responseDTO.setDescripcion(curso.getDescripcion());
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

            response.setTitulo(curso.getTitulo());
            response.setDescripcion(curso.getDescripcion());

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
            response.setTitulo(cursoActualizado.getTitulo());
            response.setDescripcion(cursoActualizado.getDescripcion());
            return Optional.of(response);
        }else{
            return Optional.empty();
        }
    }
}
