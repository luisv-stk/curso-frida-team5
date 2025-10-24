package com.frida.example.demo.repository;

import com.frida.example.demo.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    // Métodos de consulta personalizados pueden añadirse aquí si es necesario
}
