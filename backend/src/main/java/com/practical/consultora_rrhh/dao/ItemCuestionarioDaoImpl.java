package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IitemCuestionarioDao;
import com.practical.consultora_rrhh.interfaces.repositorio.itemCuestionarioRepository;
import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public class ItemCuestionarioDaoImpl implements IitemCuestionarioDao {
private final itemCuestionarioRepository repository;
public ItemCuestionarioDaoImpl(itemCuestionarioRepository repository) {
    this.repository = repository;
}

    @Override
    @Transactional
    public ItemCuestionario guardarItem(ItemCuestionario itemCuestionario) {
return  repository.save(itemCuestionario);
    }

    @Override
    @Transactional
      public List<ItemCuestionario> guardarItems(List<ItemCuestionario> items) {
return  repository.saveAll(items);
    }
}
