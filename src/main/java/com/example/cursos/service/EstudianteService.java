package com.example.cursos.service;

import com.example.cursos.dto.request.EstudianteRequestDTO;
import com.example.cursos.dto.response.EstudianteResponseDTO;
import com.example.cursos.entity.Estudiante;
import com.example.cursos.repository.EstudianteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    @Transactional
    public EstudianteResponseDTO crearEstudiante(EstudianteRequestDTO estudianteRequestDTO){
        Estudiante estudiante = new Estudiante();
        EstudianteResponseDTO response = new EstudianteResponseDTO();

        estudiante.setNombre(estudianteRequestDTO.getNombre());
        estudiante.setEmail(estudianteRequestDTO.getEmail());

        estudianteRepository.save(estudiante);

        response.setId(estudiante.getId());
        response.setNombre(estudiante.getNombre());
        response.setEmail(estudiante.getEmail());
        return response;
    }

    @Transactional
    public List<EstudianteResponseDTO> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<EstudianteResponseDTO> response = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            EstudianteResponseDTO dto = new EstudianteResponseDTO();
            dto.setId(estudiante.getId());
            dto.setNombre(estudiante.getNombre());
            dto.setEmail(estudiante.getEmail());
            response.add(dto);
        }
        return response;
    }

    @Transactional
    public Optional<EstudianteResponseDTO> buscarId(Long id){
        Optional<Estudiante> encontrado = estudianteRepository.findById(id);

        if (encontrado.isPresent()) {
            Estudiante estudiante = encontrado.get();
            EstudianteResponseDTO response = new EstudianteResponseDTO();

            response.setId(estudiante.getId());
            response.setNombre(estudiante.getNombre());
            response.setEmail(estudiante.getEmail());
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Transactional
    public Boolean eliminarEstudiante(Long id){
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<EstudianteResponseDTO> actualizarEstudiante(Long id, EstudianteRequestDTO estudianteRequestDTO){
        Optional<Estudiante> encontrado = estudianteRepository.findById(id);

        if (encontrado.isPresent()) {
            Estudiante estudiante = encontrado.get();

            estudiante.setNombre(estudianteRequestDTO.getNombre());
            estudiante.setEmail(estudianteRequestDTO.getEmail());
            Estudiante actualizado = estudianteRepository.save(estudiante);

            EstudianteResponseDTO response = new EstudianteResponseDTO();
            response.setId(actualizado.getId());
            response.setNombre(actualizado.getNombre());
            response.setEmail(actualizado.getEmail());
            return Optional.of(response);
        }
        return Optional.empty();
    }
}
