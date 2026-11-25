package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.autenticacion.usuarioCandidatoDtoAuth;
import com.practical.consultora_rrhh.dto.request.cuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.interfaces.Exportable;
import com.practical.consultora_rrhh.interfaces.auxiliares.IautenticarClave;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;
import java.util.Optional;

public interface IserviceCuestionario extends IgenerarCuestionario , IautenticarClave {

    cuestionarioDto recuperarCuestionario(String clave, String documento);

    List<Cuestionario> recuperarCuestionarios(List<Long> candidato);

    byte[] exportar(List<evaluadosDto> datos) throws ErrorExportException;

    cuestionarioDto actualizarCuestionario(cuestionarioDtoCreate c, Long id);

Cuestionario obtenerEntidad(Long id);
}
