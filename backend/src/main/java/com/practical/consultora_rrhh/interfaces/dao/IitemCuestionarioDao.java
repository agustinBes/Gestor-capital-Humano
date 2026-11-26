package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;

import java.util.List;

public interface IitemCuestionarioDao  {

    ItemCuestionario guardarItem(ItemCuestionario itemCuestionario);

    List<ItemCuestionario> guardarItems(List<ItemCuestionario> items);

    List<ItemCuestionario> obtenerItems(Long idCuestionario);
}
